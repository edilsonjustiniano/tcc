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
    
    //commons partners
    $scope.commonsPartners = [];
    $scope.limitCommonsPartner = 3;
    $scope.otherCommonsPartner = '';

	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';
    
    $scope.loading = true;

	$scope.getPartnerData = function() {

		PartnerService.getPartnerData($scope.partner.email, function(callback){
            var data = callback.data;
			if (!data.success) { /* Invalid Session or Expired */
				window.localStorage['token'] = null;
				window.sessionStorage.setItem('typeOfAccount', null);
				window.location.href = 'index.html';
			} else {
                var userData = data.results[0];
				$scope.partner.photo = userData.photo; //get photo
                $scope.partner.photo == null ? $scope.partner.photo = 'image/user-profile.png' : $scope.partner.photo = $scope.partner.photo;
			}
		});
	};

	$scope.getPartnerData();

	$scope.isMyPartner = function() {

		PartnerService.isMyPartner($scope.partner.email, function(callback){
            var data = callback.data;
			if (!data.success) { /* Invalid Session or Expired */
				window.localStorage['token'] = null;
				window.sessionStorage.setItem('typeOfAccount', null);
				window.location.href = 'index.html';
			} else {
				$scope.myPartner = data.isMyPartner;
			}
		});
	};

	$scope.isMyPartner();


	/* Add partner */
	$scope.addPartner = function () {
		
		PartnerService.addPartner($scope.partner, function(callback) {
            var data = callback.data;
			if (!data.success) { /* Invalid session or expired session */

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
	
			} else {

				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = data.mesage;
				window.localStorage['token'] = data.token;
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
    
    
    /* Get commons partners between me and this guy */
    $scope.getCommonsPartners = function() {
        
        PartnerService.getCommonsPartners($scope.partner, function(callback) {
            var data = callback.data;
            if (!data.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";
            } else {
                var array = data.results;
                if (array!= null && array.length > 0){
                    array.forEach(function(iter) {
                        $scope.commonsPartners.push({
                            name : iter.name,
                            email: iter.email
                        });
                    });
                }
                
                if ($scope.commonsPartners.length > $scope.limitCommonsPartner) {
                 
                    for (var i = $scope.commonsPartners.length; i > $scope.limitCommonsPartner; i--) {
                        $scope.otherCommonsPartner = $scope.commonsPartners[i - 1].name + '\n' + $scope.otherCommonsPartner;
                    }
                }
                
            }
            $scope.loading = false;
        });
    };
    
    $scope.getCommonsPartners();
    
    /* Open Partner Profile */
	$scope.openPartnerProfile = function(partner) {
		if (partner == null) {
			return;
		}

		// Encode the String
		var encodedString = PartnerService.encodePartnerEmail(partner); //btoa(partner.email + "|" + partner.name);
		window.location.href = "home.html#/partner-profile/" + encodedString;
		// $location.path("#/partner-profile/"+partner.email);

	};
});