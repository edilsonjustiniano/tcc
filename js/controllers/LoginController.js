//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('LoginService', function($http){
	
	this.login = function(person, callback) {
		$http.post('http://localhost:8080/WebService/session/login', JSON.stringify(person)).
		success(callback);
	};
});


TCCApp.controller('LoginController', function($scope, LoginService) {

	//UF Fields
	$scope.email = '';
	$scope.password = '';
	$scope.loading = false;
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';
	
	$scope.login = function() {

		/* show loading image But it is not Work. It seems it wait for the finish */
		$scope.loading = true;
		
		var person = new Person();
		person.setEmail($scope.email);
		person.setPassword($scope.password);

		LoginService.login(person, function (callback){
			if (!callback.success) {
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				$scope.loading = false;
			} else {
				$scope.msg.type = 'SUCCESS';
				$scope.msg.type = callback.mesage;
				window.localStorage['token'] = callback.token;
				window.location.href = 'home.html#/home';
			}
		});	
	};
});