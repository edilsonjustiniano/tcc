app.service('HomeService', function($http){
	
	this.getTypeOfAccount = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};

	this.getPossiblePartners = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};
});

app.controller('HomeController', function ($scope, HomeService) {

	$scope.typeOfAccount = '';
	$scope.possiblePartners = [];
	$lastEvaluation = [];

	$scope.getTypeOfAccount = function() {
		HomeService.getTypeOfAccount(function(callback) {
			if (!callback.success) { /* Ivalid session or expired session */
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = callback.token;
				$scope.name = callback.data[0][0]; //name
				//$scope.email = callback.data[0][1]; //email (It works)
				//$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.typeOfAccount = callback.data[0][3]; //typeOfAccount
				window.sessionStorage.setItem('typeOfAccount', $scope.typeOfAccount);
			}
		});
	};

	$scope.typeOfAccount = $scope.getTypeOfAccount();


	/* Get the possible partners for you */
	$scope.getPossiblePartners = function() {
		HomeService.getTypeOfAccount(function(callback) {
			if (!callback.success) { /* Ivalid session or expired session */
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = callback.token;
				$scope.name = callback.data[0][0]; //name
				//$scope.email = callback.data[0][1]; //email (It works)
				//$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.typeOfAccount = callback.data[0][3]; //typeOfAccount
				window.sessionStorage.setItem('typeOfAccount', $scope.typeOfAccount);
			}
		});
	};

	//$scope.getPossiblePartners();


});