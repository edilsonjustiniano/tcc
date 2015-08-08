app.service('ServiceProviderService', function($http){

    this.encodeEmail = function (person) {

		// Encode the String
		var encodedString = btoa(person.email + "|" + person.service);
		console.log(encodedString);

		// Decode the String
		var decodedString = atob(encodedString);
		console.log(decodedString);

		var slash = encodedString.indexOf("/");
		
		if (slash > -1) {
			encodedString = encodedString.substr(0, slash) + '__' + encodedString.substr(slash + 1);
		}

		return encodedString;
	};
    
    this.getServiceProviderData = function(serviceProvider, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/serviceProvider/getServiceProviderData', {provider: serviceProvider.email, service: serviceProvider.service, token: token}).
		success(callback);
	};
    
    
    this.saveEvaluate = function(serviceProvider, note, comments, callback) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/serviceProvider/saveEvaluate', {provider: serviceProvider.email, service: serviceProvider.service, note: note, comments: comments, token: token}).
		success(callback);
    };
    
    this.getServicesByName = function(service, data) {
		$http.get('http://localhost:8080/WebService/service/getServicesByName/' + service).success(data);
	};
    
    this.getServicesProvidersByService = function (service, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getServiceProvidersByService', {
            service: service,
            token: token
        }).
        success(callback);
    };
    
    this.getQuantityOfPartnersEvaluateIt = function(service, callback) {
    };
});