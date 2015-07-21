app.service('PartnerProfileService', function($http){
	
	this.getPartnerData = function(partnerEmail, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/person/getPersonData', {partner: partnerEmail, token: token}).
		success(callback);
	};

	this.isMyPartner = function(partnerEmail, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/isMyPartner', {partner: partnerEmail, token: token}).
		success(callback);
	};

	this.addPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/addPartner', {partner: partner.email, token: token}).
		success(callback);
	};

	this.cancelPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/cancelPartner', {partner: partner.email, token: token}).
		success(callback);
	};
});

app.controller('PartnerProfileController', function($scope, $routeParams, PartnerProfileService) {

	// Decode the String
	var doubleUnderscore = $routeParams.partner.indexOf("__");
		
	if (doubleUnderscore > -1) {
		$routeParams.partner = $routeParams.partner.substr(0, doubleUnderscore) + '/' + $routeParams.partner.substr(doubleUnderscore + 2);
	}
	
	var decodedPartnerData = atob($routeParams.partner);
	console.log(decodedPartnerData);
	
	var data = decodedPartnerData.split("|");

	$scope.partner = {};
	$scope.partner.email = data[0]; //email
	$scope.partner.name = data[1]; //name
	$scope.myPartner = false;

	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';

	$scope.getPartnerData = function() {

		PartnerProfileService.getPartnerData($scope.partner, function(callback){
			if (!callback.success) { /* Invalid Session or Expired */
				window.localStorage['token'] = null;
				window.sessionStorage.setItem('typeOfAccount', null);
				window.location.href = 'index.html';
			} else {
				
			}
		});
	};

	$scope.getPartnerData();

	$scope.isMyPartner = function() {

		PartnerProfileService.isMyPartner($scope.partner.email, function(callback){
			if (!callback.success) { /* Invalid Session or Expired */
				window.localStorage['token'] = null;
				window.sessionStorage.setItem('typeOfAccount', null);
				window.location.href = 'index.html';
			} else {
				$scope.myPartner = callback.isMyPartner;
			}
		});
	};

	$scope.isMyPartner();


	/* Add partner */
	$scope.addPartner = function () {
		
		PartnerProfileService.addPartner($scope.partner, function(callback) {

			if (!callback.success) { /* Invalid session or expired session */

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
	
			} else {

				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = callback.token;
				$scope.isMyPartner();
			}
		});
		
	};


	/* Cancel partner */
	$scope.cancelPartner = function () {
		
		PartnerProfileService.cancelPartner($scope.partner, function(callback) {

			if (!callback.success) { /* Invalid session or expired session */

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
	
			} else {

				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = callback.token;
				$scope.isMyPartner();
			}
		});
		
	};
});