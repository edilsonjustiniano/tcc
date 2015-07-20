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
});

app.controller('PartnerProfileController', function($scope, $routeParams, PartnerProfileService) {

	// Decode the String
	var decodedPartnerData = atob($routeParams.partner);
	console.log(decodedPartnerData);
	
	var data = decodedPartnerData.split("|");

	$scope.partner = {};
	$scope.partner.email = data[0]; //email
	$scope.partner.name = data[1]; //name
	$scope.isMyPartner = false;

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
				$scope.isMyPartner = callback.isMyPartner;
			}
		});
	};

	$scope.isMyPartner();
});