app.service('ReportService', function($http){
    
    this.lastEvaluateOfServiceProvider = function(serviceProvider, service, limit, callback, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/report/lastEvaluateOfServiceProvider?token=' + token +
         '&serviceProvider=' + serviceProvider + '&service=' + service + '&limit=' + limit).
		then(callback, error);
    };
    
    this.lastEvaluateOfServiceInNetwork = function(serviceProvider, service, limit, callback, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/report/lastEvaluateOfServiceInNetwork?token=' + token +
         '&serviceProvider=' + serviceProvider + '&service=' + service + '&limit=' + limit).
		then(callback, error);
    };
});