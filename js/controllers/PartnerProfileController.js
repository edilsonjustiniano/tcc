app.controller('PartnerProfileController', function($scope, $routeParams, PartnerService) {

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
	$scope.myPartner = true;

	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';

	$scope.getPartnerData = function() {

		PartnerService.getPartnerData($scope.partner, function(callback){
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

		PartnerService.isMyPartner($scope.partner.email, function(callback){
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
		
		PartnerService.addPartner($scope.partner, function(callback) {

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
		
		PartnerService.cancelPartner($scope.partner, function(callback) {

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