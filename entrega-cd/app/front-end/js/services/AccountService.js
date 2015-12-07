app.service('AccountService', ['$http', function($http){
    
    this.editAccount = function(callback, error, user) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/person/edit/', {user: JSON.stringify(user), token: token}).
		then(callback, error);
    }
    
    this.changePassword = function(callback, error, user) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/person/chane-password/', {user: JSON.stringify(user), token: token}).
		then(callback, error);
    }
}]);