var _waypoints = new Array();
var kievLoc = new google.maps.LatLng(50.45041656494141, 30.52354049682617);
var markers = new Array();
var stack = new Array();
var displayRoute = function(test){};

function myMap() {
    var map = new google.maps.Map(document.getElementById('myMap'), {
        zoom: 12,
        center: { location: new google.maps.LatLng(parseFloat(50.45041656494141), parseFloat(30.52354049682617)) } // Australia.
    });

    infoWindow = new google.maps.InfoWindow;

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map.setCenter(pos);
            console.log(pos);
        }, function() {
            map.setCenter(new google.maps.LatLng(parseFloat(50.45041656494141), parseFloat(30.52354049682617)));
        });
    } else {
        handleLocationError(false, infoWindow, map.getCenter());
    }

    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer({
        draggable: true,
        map: map,
    });

    displayRoute = function displayRoute(waypoints) {

        directionsService.route({
            origin: waypoints[0],
            destination: waypoints[waypoints.length - 1],
            waypoints: waypoints.slice(1, waypoints.length - 1),
            travelMode: 'WALKING',
            avoidTolls: true
        }, function(response, status) {
            if (status === 'OK') {
                directionsDisplay.setDirections(response);
            } else {
                alert('Could not display directions due to: ' + status);
            }
        });
    }

    google.maps.event.addListener(map, "click", function(event) {
        var latitude = event.latLng.lat();
        var longitude = event.latLng.lng();
        console.log(latitude + ', ' + longitude);

        _waypoints.push({ location: new google.maps.LatLng(latitude, longitude) });
        console.log(_waypoints.toString())
        if (_waypoints.length < 2) {
            var marker = new google.maps.Marker({
                position: event.latLng,
                map: map
            });
            markers.push(marker);
        } else {
            setMapOnAll(null);
            displayRoute(_waypoints);
        }

    });

    google.maps.event.addListener(directionsDisplay, 'directions_changed', function(event) {
        console.log(directionsDisplay.directions.request);
        var request = directionsDisplay.directions.request;
        _waypoints[0] = request.origin;
        if (_waypoints.length > 2) {
            _waypoints[_waypoints.length - 1] = request.destination;
            for (var i = 1; i < _waypoints.length - 1; i++) {
                console.log("Kek");
                _waypoints[i] = request.waypoints[i - 1]
            }
        }
        document.getElementById("route").value = JSON.stringify(_waypoints);
        stack.push(_waypoints);
    });


    function setMapOnAll(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

}

function back() {
    _waypoints = stack.pop();
    displayRoute(_waypoints)
}
