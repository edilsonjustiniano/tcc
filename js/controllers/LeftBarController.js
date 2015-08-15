app.controller('LeftBarController', 
               function ($scope, SessionService, PartnerService, RatingService, ServiceProviderService) {

    $scope.user = {};
    $scope.lastestRatings = [];
    $scope.possiblePartners = [];
    
	$scope.getTypeOfAccount = function() {
		SessionService.getTypeOfAccount(function(callback) {
			if (!callback.success) { /* Ivalid session or expired session */
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = callback.token;
				$scope.user.name = callback.data[0][0]; //name
				//$scope.user.email = callback.data[0][1]; //email (It works)
				//$scope.user.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.user.typeOfAccount = callback.data[0][3]; //typeOfAccount
				window.sessionStorage.setItem('typeOfAccount', $scope.user.typeOfAccount);
                $scope.user.photo = callback.data[0][4]; //photo
			}
		});
	};

	$scope.typeOfAccount = $scope.getTypeOfAccount();


	/* Get the possible partners for you */
	$scope.getPossiblePartners = function() {
		// Only contractor user can perform this query
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		PartnerService.getPossiblePartners(function(callback) {
			if (callback.success) { // Ivalid session or expired session
				if (callback.data == undefined)
					return;
				var array = callback.data;
				array.forEach(function(iter){
					$scope.possiblePartners.push({
                        name: iter[0], 
                        email: iter[1], 
                        photo: iter[3] == null ? iter[3] = 'image/user-profile.png': iter[3] = iter[3]
                    });
				});
			}
		});
	};

	$scope.getPossiblePartners();

    
    /* Open Partner Profile */
	$scope.openPartnerProfile = function(partner) {
		if (partner == null) {
			return;
		}

		// Encode the String
		var encodedString = PartnerService.encodePartnerEmail(partner); //btoa(partner.email + "|" + partner.name);
		// console.log(encodedString);

		// // Decode the String
		// var decodedString = atob(encodedString);
		// console.log(decodedString);

		// var slash = encodedString.indexOf("/");

		// if (slash > -1) {
		// 	encodedString = encodedString.substr(0, slash) + '__' + encodedString.substr(slash + 1);
		// }

		window.location.href = "home.html#/partner-profile/" + encodedString;
		// $location.path("#/partner-profile/"+partner.email);

	};
    
    
    /* Open service Provider profile */
    $scope.openServiceProviderProfileByService = function(serviceProvider) {
        console.log("Service Provider Selected: " + serviceProvider.name + " | " + serviceProvider.service);
        var encodedData = ServiceProviderService.encodeEmail(serviceProvider);
        console.log('Service : ' + serviceProvider.email + "|" + serviceProvider.service);
        console.log('Encoded Data: ' + encodedData);

        window.location.href = "#/service-provider-profile/" + encodedData;
    };


	/* Get the lastest ratings */
	$scope.getMyLastestRatings = function() {
		RatingService.getMyLastestRatings(function(callback) {
			if (!callback.success) {
				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
			} else {
				var array = callback.data;
				if (array != null && array.length > 0) {
					array.forEach(function(iter) {
                        
                        var dateSplit = iter[6].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
						$scope.lastestRatings.push({
							name: iter[0],
                            email: iter[1],
                            photo: iter[2] == null ? iter[2] = 'image/user-profile.png' : iter[2] = iter[2],
                            service: iter[3],
                            note: iter[4],
                            comments: iter[5],
                            date: day + '/' + month + '/' + year,
                            showComments: false
						});
					});
				}

			}
		});
	};
    
    $scope.getMyLastestRatings();
    
    /* Show the comment */
    $scope.showComments = function(serviceProvider) {
        
        for(var i = 0; i < $scope.lastestRatings.length; i++) {
            if ($scope.lastestRatings[i].email !=  serviceProvider.email || 
                $scope.lastestRatings[i].service != serviceProvider.service) {

                $scope.lastestRatings[i].showComments = false;
            }
        }
        
        for(var i = 0; i < $scope.lastestRatings.length; i++) {
            if ($scope.lastestRatings[i].email ==  serviceProvider.email && 
                $scope.lastestRatings[i].service == serviceProvider.service) {
                
                if ($scope.lastestRatings[i].showComments) {
                    $scope.lastestRatings[i].showComments = false;
                } else {
                    $scope.lastestRatings[i].showComments = true;
                }
                
                break;
            }
        }
    };
});
