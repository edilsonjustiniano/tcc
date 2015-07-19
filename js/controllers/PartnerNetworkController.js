app.service('PartnerNetworkService', function($http){
	
	this.getTypeOfAccount = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/session/getUserInfo', {token: token}).
		success(callback);
	};

	this.getAllPartners = function(limit, offset, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/getAllPartners', {limit: limit, offset: offset, token: token}).
		success(callback);
	};

	this.searchNewPartners = function(limit, offset, partnerName, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchNewPartners', {limit: limit, offset: offset, partner: partnerName, token: token}).
		success(callback);	
	};

	this.searchNewPartnersOnlyByName = function(partnerName, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchNewPartnersOnlyByName', {partner: partnerName, token: token}).
		success(callback);
	};
	
	this.addPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/addPartner', {partner: partner.email, token: token}).
		success(callback);
	};

	this.isDuplicatadedPartner = function (name, email, allPartners) {
		for (var i = 0; i < allPartners.length; i++) {
			if (allPartners[i].name == name && allPartners[i].email == email) {
				return true;
			}
		};
		return false;
	};
});

app.controller('PartnerNetworkController', function ($scope, PartnerNetworkService) {

	$scope.typeOfAccount = '';
	$scope.partners = [];

	$scope.newPartner = '';
	$scope.possibleNewPartners = [];

	$scope.filtro = '';
	$scope.limit = 20;
	$scope.offset = 0;
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

	$scope.getTypeOfAccount();


	/* Get the possible partners for you */
	$scope.getAllPartners = function() {
		/* Only contractor user can perform this query */
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		PartnerNetworkService.getAllPartners($scope.limit, $scope.offset, function(callback) {
			if (callback.success) { /* Ivalid session or expired session */
				var array = callback.data;
				array.forEach(function(iter){
					$scope.partners.push({name: iter[0], email: iter[1]});
				});
			}
		});
	};

	$scope.getAllPartners();


	/* Search the possible partners for you according with name informed by you */
	$scope.searchNewPartners = function() {

		if ($scope.newPartner.length <= 2 ) {
			$scope.possibleNewPartners = [];
			return;
		}

		/* Only contractor user can perform this query */
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		PartnerNetworkService.searchNewPartners($scope.limit, $scope.offset, $scope.newPartner, function(callback) {
			
			if (callback.success) { 
				$scope.possibleNewPartners = [];
				var array = callback.data;
				
				if (array == undefined || array.length <= 2) { /* Make another request, but in this time, get the possible partners filtering only by name */
					array.forEach(function(iter){
						$scope.possibleNewPartners.push({name: iter[0], email: iter[1]});
					});

					PartnerNetworkService.searchNewPartnersOnlyByName($scope.newPartner, function(callback) {

						if (callback.success) {
							array = callback.data;
							array.forEach(function(iter){

								if (!PartnerNetworkService.isDuplicatadedPartner(iter[0], iter[1], $scope.possibleNewPartners) ) {
									$scope.possibleNewPartners.push({name: iter[0], email: iter[1]});
								}
								
							});
						}
					});
				} else {

					array.forEach(function(iter){
						$scope.possibleNewPartners.push({name: iter[0], email: iter[1]});
					});
				}
				window.localStorage['token'] = callback.token;
			}
		});
	};
	
	/* Add partner */
	$scope.addPartner = function (partner) {
		if (partner == null) {
			return;
		}  

		PartnerNetworkService.addPartner(partner, function(callback) {

			if (!callback.success) { /* Invalid session or expired session */

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
	
			} else {

				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = callback.token;
				$scope.possibleNewPartners = []; //reset the list of possible partners
				$scope.partners = [];
				$scope.getAllPartners(); //Reload the list of yours partners
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


		window.location.href = "home.html#/partner-profile/" + encodedString;
		// $location.path("#/partner-profile/"+partner.email);

	};
});