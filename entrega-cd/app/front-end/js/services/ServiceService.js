app.service('ServiceService', function($http){

    this.getServicesByName = function(service, success, error) {
		$http.get('http://localhost:8080/WebService/services/service/' + service).
        then(success, error);
	};
});