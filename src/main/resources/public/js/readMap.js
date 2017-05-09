function myMap(route) {
    route = JSON.parse(route);
    console.log(route[0]);

    var myOptions = {
        zoom: 8,
        center: route[0].location,
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
            origin: route[0].location,
            destination: route[route.length - 1].location,
            waypoints: route.slice(1, route.length - 1),
            travelMode: 'WALKING',
            avoidTolls: true
        }, function(response, status) {
            if (status === 'OK') {
                console.log(response);
                display.setDirections (response);
            } else {
                alert('Could not display directions due to: ' + status);
            }
        });
    }

    google.maps.event.addListener(map, "click", function(event) {
        displayRoute(directionsService, directionsDisplay, route);
    });

}
