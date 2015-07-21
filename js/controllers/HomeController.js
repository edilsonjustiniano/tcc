app.service('HomeService', function($http){
	
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

app.controller('HomeController', function ($scope, HomeService) {

	$scope.typeOfAccount = '';
	$scope.possiblePartners = [];
	$lastEvaluation = [];
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';

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
		/* Only contractor user can perform this query */
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		HomeService.getPossiblePartners(function(callback) {
			if (callback.success) { /* Ivalid session or expired session */
				if (callback.data == undefined) 
					return;
				var array = callback.data;
				array.forEach(function(iter){
					$scope.possiblePartners.push({name: iter[0], email: iter[1]});
				});
			}
		});
	};

	$scope.getPossiblePartners();


	/* Add partner */
	$scope.addPartner = function (partner) {
		if (partner == null) {
			return;
		}  

		HomeService.addPartner(partner, function(callback) {

			if (!callback.success) { /* Invalid session or expired session */

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
	
			} else {

				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = callback.token;
				$scope.possiblePartners = []; //reset the list of possible partners
				//Reload the list of all possible partners
				$scope.getPossiblePartners();
			}
		});
		
	};

	/* Open Partner Profile */
	$scope.openPartnerProfile = function(partner) {
		if (partner == null) {
			return;
		}

		// Encode the String
		var encodedString = btoa(partner.email + "|" + partner.name);
		console.log(encodedString);

		// Decode the String
		var decodedString = atob(encodedString);
		console.log(decodedString);

		var slash = encodedString.indexOf("/");
		
		if (slash > -1) {
			encodedString = encodedString.substr(0, slash) + '__' + encodedString.substr(slash + 1);
		}

		window.location.href = "home.html#/partner-profile/" + encodedString;
		// $location.path("#/partner-profile/"+partner.email);

	};
});