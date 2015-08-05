app.service('PartnerNetworkService', function($http){
	
	this.isDuplicatadedPartner = function (name, email, allPartners) {
		for (var i = 0; i < allPartners.length; i++) {
			if (allPartners[i].name == name && allPartners[i].email == email) {
				return true;
			}
		};
		return false;
	};
});

app.controller('PartnerNetworkController', function ($scope, PartnerNetworkService, PartnerService, SessionService) {

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
		SessionService.getTypeOfAccount(function(callback) {
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
		PartnerService.getAllPartners($scope.limit, $scope.offset, function(callback) {
			if (callback.success) { /* Ivalid session or expired session */
				var array = callback.data;
				array.forEach(function(iter){
					$scope.partners.push({name: iter[0], email: iter[1], photo: iter[2]});
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
		PartnerService.searchNewPartners($scope.limit, $scope.offset, $scope.newPartner, function(callback) {
			
			if (callback.success) { 
				$scope.possibleNewPartners = [];
				var array = callback.data;
				
				if (array == undefined || array.length <= 2) { /* Make another request, but in this time, get the possible partners filtering only by name */
					array.forEach(function(iter){
						$scope.possibleNewPartners.push({name: iter[0], email: iter[1], photo: iter[3]});
					});

					PartnerService.searchNewPartnersOnlyByName($scope.newPartner, function(callback) {

						if (callback.success) {
							array = callback.data;
							array.forEach(function(iter){

								if (!PartnerNetworkService.isDuplicatadedPartner(iter[0], iter[1], $scope.possibleNewPartners) ) {
									$scope.possibleNewPartners.push({name: iter[0], email: iter[1], photo: iter[3]});
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
	
	
	/* Open Partner Profile */
	$scope.openPartnerProfile = function(partner) {
		if (partner == null) {
			return;
		}

		// Encode the String
		var encodedString = PartnerService.encodePartnerEmail(partner);//btoa(partner.email + "|" + partner.name);
		// console.log(encodedString);

		// // Decode the String
		// var decodedString = atob(encodedString);
		// console.log(decodedString);

		// var slash = encodedString.indexOf("/");
		
		// if (slash > -1) {
		// 	encodedString = encodedString.substr(0, slash) + '__' + encodedString.substr(slash + 1);
		// }

		window.location.href = "#/partner-profile/" + encodedString;
		// $location.path("#/partner-profile/"+partner.email);

	};
});