app.service('SessionService', function($http){
	
	this.getUserInfoFromSession = function(callback, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/session/userinfo/' + token).
		then(callback, error);
	};
	
	this.getTypeOfAccount = function(callback, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/session/userinfo/' + token).
		then(callback, error);
	};
});