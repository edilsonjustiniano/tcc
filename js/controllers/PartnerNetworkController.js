app.service('PartnerNetworkService', function($http){
	
	this.getTypeOfAccount = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};

	this.getPossiblePartners = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/getPossiblePartners', {token: token}).
		success(callback);
	};

	this.addPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/addPartner', {partner: partner.email, token: token}).
		success(callback);
	}
});

app.controller('PartnerNetworkController', function ($scope, PartnerNetworkService) {

	$scope.typeOfAccount = '';
	// $scope.possiblePartners = [];
	// $lastEvaluation = [];
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';

	$scope.getTypeOfAccount = function() {
		PartnerNetworkService.getTypeOfAccount(function(callback) {
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
	// $scope.getPossiblePartners = function() {
	// 	/* Only contractor user can perform this query */
	// 	if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
	// 		return;
	// 	}
	// 	PartnerNetworkService.getPossiblePartners(function(callback) {
	// 		if (callback.success) { /* Ivalid session or expired session */
	// 			var array = callback.data;
	// 			array.forEach(function(iter){
	// 				$scope.possiblePartners.push({name: iter[0], email: iter[1]});
	// 			});
	// 		}
	// 	});
	// };

	// $scope.getPossiblePartners();


	/* Add partner */
	// $scope.addPartner = function (partner) {
	// 	if (partner == null) {
	// 		return;
	// 	}  

	// 	PartnerNetworkService.addPartner(partner, function(callback) {

	// 		if (!callback.success) { /* Invalid session or expired session */

	// 			window.sessionStorage.setItem('typeOfAccount', null);
	// 			window.localStorage['token'] = null;
	// 			window.location.href = "index.html";
	
	// 		} else {

	// 			$scope.msg.type = 'SUCCESS';
	// 			$scope.msg.msg = callback.mesage;
	// 			window.localStorage['token'] = callback.token;
	// 			$scope.possiblePartners = []; //reset the list of possible partners
	// 			//Reload the list of all possible partners
	// 			$scope.getPossiblePartners();
	// 		}
	// 	});
		
	// };
});