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

	$scope.getTypeOfAccount = function() {
		SessionService.getTypeOfAccount(function(callback) {
            var data = callback.data;
			if (!data.success) { /* Ivalid session or expired session */
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = data.mesage;
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = data.token;
                var userData = data.results[0];
				$scope.name = userData.name; //name
				//$scope.email = callback.data[0][1]; //email (It works)
				//$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.typeOfAccount = userData.typeOfAccount; //typeOfAccount
				window.sessionStorage.setItem('typeOfAccount', $scope.typeOfAccount);
			}
		}, $scope.error);
	};

	$scope.getTypeOfAccount();


	/* Get the possible partners for you */
	$scope.getAllPartners = function() {
		/* Only contractor user can perform this query */
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		PartnerService.getAllPartners(function(callback) {
            var data = callback.data;
			if (data.success) { /* Ivalid session or expired session */
				var array = data.results;
				array.forEach(function(iter){
					$scope.partners.push({
                        name: iter.name, 
                        email: iter.email, 
                        photo: iter.photo == null ? iter.photo = 'image/user-profile.png' : iter.photo = iter.photo
                    });
				});
			}
		}, $scope.error);
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
									$scope.possibleNewPartners.push({
                                        name: iter[0], 
                                        email: iter[1], 
                                        photo: iter[3] == null ? iter[3] = 'image/user-profile.png' : iter[3] = iter[3]
                                    });
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

		window.location.href = "#/partner-profile/" + encodedString;
	};
    
     $scope.error = function(response) {
        console.log('error: '); 
    };
});