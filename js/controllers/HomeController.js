app.service('HomeService', function($http){
    /*
	this.getPossiblePartners = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/getPossiblePartners', {token: token}).
		success(callback);
	};
    */
    
    this.getFeedLastPartnership = function(callback) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/feed/lastPartnership', {token: token}).
		success(callback);
    };
    
    this.getFeedLastestRatings = function(callback) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/feed/lastestRatings', {token: token}).
		success(callback);
    };
});

app.controller('HomeController', function ($scope, HomeService, PartnerService, SessionService) {

	$scope.typeOfAccount = '';
    $scope.feeds = []; //mix of the both feeds partnership and ratings
    
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

	$scope.typeOfAccount = $scope.getTypeOfAccount();


	/* Get the possible partners for you */
	/*
    $scope.getPossiblePartners = function() {
		// Only contractor user can perform this query
		if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
			return;
		}
		HomeService.getPossiblePartners(function(callback) {
			if (callback.success) { // Ivalid session or expired session 
				if (callback.data == undefined)
					return;
				var array = callback.data;
				array.forEach(function(iter){
					$scope.possiblePartners.push({name: iter[0], email: iter[1], photo: iter[3]});
				});
			}
		});
	};

	$scope.getPossiblePartners();
    */

	
	/* Open Partner Profile */
    /*
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
    */

	
    $scope.getFeedLastPartnership = function() {
        HomeService.getFeedLastPartnership(function(callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
            } else {
                var array = callback.data;
				if (array != null && array.length > 0) {
					array.forEach(function(iter) {
						$scope.feeds.push({
							partner: iter[0],
                            user: iter[1],
                            receivedIn: iter[2],
                            answeredIn: iter[3],
                            isRating: iter[6]
						});
					});
				}

            }
        });
    };
    
    $scope.getFeedLastPartnership();
    
    
    $scope.getFeedLastestRatings = function() {
        HomeService.getFeedLastestRatings(function(callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
            } else {
                var array = callback.data;
				if (array != null && array.length > 0) {
					array.forEach(function(iter) {
                        
                        var dateSplit = iter[4].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        $scope.feeds.push({
							serviceProvider: iter[0],
                            service: iter[1],
                            note: iter[2],
                            comments: iter[3],
                            date: day + '/' + month + '/' + year, //date
                            to: iter[5],
                            isRating: iter[6]
						});
					});
				}
            }
        });
    };
    
    $scope.getFeedLastestRatings();
});
