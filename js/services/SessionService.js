app.service('SessionService', function($http){
	
	this.getUserInfoFromSession = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};
	
	this.getTypeOfAccount = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};
});