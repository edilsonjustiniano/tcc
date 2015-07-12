//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('MenuBarService', function($http){
	
	this.getUserInfoFromSession = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};
});


TCCApp.controller('MenuBarController', function($scope, MenuBarService) {

	$scope.name = '';
	$scope.typeOfAccount = '';
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';
	

	$scope.getUserInfoFromSession = function() {
		
		MenuBarService.getUserInfoFromSession(function (callback) {
			if (!callback.success) { /* Ivalid session or expired session */
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = callback.token;
				$scope.name = callback.data[0][0]; //name
				var arrayNames = $scope.name.split(" "); //Show only the first name
				$scope.name = arrayNames[0];
				//$scope.email = callback.data[0][1]; //email (It works)
				//$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.typeOfAccount = callback.data[0][3]; //typeOfAccount
			}
		});
	};

	$scope.logout = function() {
		window.localStorage['token'] = null;
		window.location.href = "index.html";	
	};


	$scope.getUserInfoFromSession();
});
