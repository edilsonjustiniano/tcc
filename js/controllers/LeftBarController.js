app.controller('LeftBarController', 
               function ($scope, SessionService, PartnerService, RatingService, ServiceProviderService) {

    $scope.user = {};
    $scope.lastestRatings = [];
    $scope.possiblePartners = [];
    $scope.loading = true;
    
	$scope.getTypeOfAccount = function() {
        
		SessionService.getTypeOfAccount(function(callback) {
            var data = callback.data;
			if (!data.success) { /* Ivalid session or expired session */
				window.localStorage['token'] = null;
				window.location.href = 'index.html';
			} else {
				//get data from return and fill the components according to type of account
				window.localStorage['token'] = data.token;
                var userData = data.results[0];
				$scope.user.name = userData.name; //name
				//$scope.user.email = callback.data[0][1]; //email (It works)
				//$scope.user.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
				$scope.user.typeOfAccount = userData.typeOfAccount; //typeOfAccount
				window.sessionStorage.setItem('typeOfAccount', $scope.user.typeOfAccount);
                $scope.user.photo = userData.photo; //photo
                $scope.user.photo == null ? $scope.user.photo = 'image/user-profile.png' : $scope.user.photo = $scope.user.photo;
			}
		}, $scope.error);
	};

	$scope.typeOfAccount = $scope.getTypeOfAccount();


	/* Get the possible partners for you */
	$scope.getPossiblePartners = function() {
		// Only contractor user can perform this query
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
        
		PartnerService.getPossiblePartners(function(callback) {
            var data = callback.data;
			if (data.success) { // Ivalid session or expired session
				if (data.results == undefined)
					return;
				var array = data.results;
				array.forEach(function(iter){
                    //if ($scope.possiblePartners.length < 4) {
                        $scope.possiblePartners.push({
                            name: iter.name, 
                            email: iter.email, 
                            photo: iter.photo == null ? iter.photo = 'image/user-profile.png': iter.photo = iter.photo
                        });
                    //}
				});
			}
		}, $scope.error);
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
    $scope.openServiceProviderProfile = function(serviceProvider) {
        console.log("Service Provider Selected: " + serviceProvider.name + " | " + serviceProvider.service);
        var encodedData = ServiceProviderService.encodeEmail(serviceProvider);
        console.log('Service : ' + serviceProvider.email + "|" + serviceProvider.service);
        console.log('Encoded Data: ' + encodedData);

        window.location.href = "#/service-provider-profile/" + encodedData;
    };


	/* Get the lastest ratings */
	$scope.getMyLastestRatings = function() {
		RatingService.getMyLastestRatings(function(callback) {
            var data = callback.data;
			if (!data.success) {
				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
			} else {
				var array = data.results;
				if (array != null && array.length > 0) {
					array.forEach(function(iter) {
                        
                        var dateSplit = iter.date.split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
						$scope.lastestRatings.push({
							name: iter.serviceProviderName,
                            email: iter.serviceProviderEmail,
                            photo: iter.serviceProviderPhoto == null ? iter.serviceProviderPhoto = 'image/user-profile.png' : iter.serviceProviderPhoto = iter.serviceProviderPhoto,
                            service: iter.service,
                            note: iter.note,
                            comments: iter.comments,
                            date: day + '/' + month + '/' + year,
                            showComments: false
						});
					});
				}
                
                $scope.loading = false;
			}
		}, $scope.error);
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
    
    $scope.error = function (response) {
        console.log('error: ');
    };
});
