var markers = new Array();

function myMap(routes) {
    console.log(routes);
    routes = JSON.parse(routes);

    var myOptions = {
        zoom: 12,
        center: routes[0].location,
    };

    var map = new google.maps.Map(document.getElementById("myMap"),
        myOptions);

    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer({
        draggable: true,
        map: map,
    });

    function displayRoute(service, display, waypoints) {

        service.route({
            origin: routes[0].location,
            destination: routes[routes.length - 1].location,
            waypoints: routes.slice(1, routes.length - 1),
            travelMode: 'WALKING',
            avoidTolls: true
        }, function(response, status) {
            if (status === 'OK') {
                console.log(response);
                display.setDirections(response);
            } else {
                alert('Could not display directions due to: ' + status);
            }
        });
    }

    displayRoute(directionsService, directionsDisplay, routes);

    google.maps.event.addListener(map, "click", function(event) {
        var latitude = event.latLng.lat();
        var longitude = event.latLng.lng();
        console.log(latitude + ', ' + longitude);

        routes.push({
            location: new google.maps.LatLng(parseFloat(latitude), parseFloat(longitude))
        });
        console.log(routes.toString())
        if (routes.length < 2) {
            var marker = new google.maps.Marker({
                position: event.latLng,
                map: map
            });
            markers.push(marker);
        } else {
            setMapOnAll(null);
            displayRoute(directionsService, directionsDisplay, routes);
        }

    });

    google.maps.event.addListener(directionsDisplay, 'directions_changed', function(event) {
        console.log(directionsDisplay.directions.request);
        var request = directionsDisplay.directions.request;
        routes[0] = {location: new google.maps.LatLng(parseFloat(request.origin.lat()), parseFloat(request.origin.lng()))};
        console.log(routes[0])
        if (routes.length > 2) {
            routes[routes.length - 1] = {location: new google.maps.LatLng(parseFloat(request.destination.lat()), parseFloat(request.destination.lng()))};
            for (var i = 1; i < routes.length - 1; i++) {
                console.log("waypoint");
                var tmp = request.waypoints[i - 1].location
                routes[i] = {location: new google.maps.LatLng(parseFloat(tmp.lat()), parseFloat(tmp.lng()))};
            }
        }
        document.getElementById("route").value = JSON.stringify(routes);
    });

    function setMapOnAll(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }
}