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
    
    /* Rating in my partner network */
    this.getServiceProviderRatingInMyNetworkPartners = function(serviceProvider, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getServiceProviderRatingInMyNetworkPartners', {
            serviceProvider: serviceProvider.email,
            service: serviceProvider.service,
            token: token
        }).
        success(callback);
    };
    
    /* Rating in my company */
    this.getServiceProviderRatingInMyCompany = function(serviceProvider, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getServiceProviderRatingInMyCompany', {
            serviceProvider: serviceProvider.email,
            service: serviceProvider.service,
            token: token
        }).
        success(callback);
    };
    
    
    /* Rating in my company */
    this.getServiceProviderRatingInMyCity = function(serviceProvider, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getServiceProviderRatingInMyCity', {
            serviceProvider: serviceProvider.email,
            service: serviceProvider.service,
            token: token
        }).
        success(callback);
    };
    
    /* Calculate the average to show to user in profile page */
    this.calculateAverage = function(array) {
        var total = 0;
        if (array.length == 0) {
            return total.toFixed(2);
        }
        array.forEach(function(iter) {
            total += iter.note;
        });
        
        total = total / array.length;
        return total.toFixed(2);
    };
    
    this.calculatePercentage = function(average) {
        // 5 = 100%
        // 4 = x
        //5x = 400
        //x = 400/5
        //x = 80
        
        if (average == 0) {
           return 0; 
        }
        var percent = (average * 100) / 5;
        return percent.toFixed(2);
    };

    this.addService = function(service, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/addService', {
            service: service,
            token: token
        }).
        success(callback);

    };

    this.getMyServices = function(callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getMyServices', {
            token: token
        }).
        success(callback);

    };
});