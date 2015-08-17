app.controller('ServiceProviderProfileController', function ($scope, $routeParams, ServiceProviderService) {

    // Decode the String
    var doubleUnderscore = $routeParams.provider.indexOf("__");

    if (doubleUnderscore > -1) {
        $routeParams.provider = $routeParams.provider.substr(0, doubleUnderscore) + '/' + $routeParams.provider.substr(doubleUnderscore + 2);
    }

    var decodedPartnerData = atob($routeParams.provider);
    console.log(decodedPartnerData);

    var data = decodedPartnerData.split("|");

    $scope.serviceProvider = {};
    $scope.serviceProvider.email = data[0]; //email
    $scope.serviceProvider.service = data[1]; //service
    
    /* Rating data in my network */
    $scope.ratingInMyNetwork = [];
    $scope.averageInMyNetwork = 0;
    $scope.percentInMyNetwork = 0;
    $scope.personsWhoseRateitInMyNetwork = '\n';
    $scope.showCommentsFromMyNetwork = false;
    
    /* Rating data in my company */
    $scope.ratingInMyCompany = [];
    $scope.averageInMyCompany = 0;
    $scope.percentInMyCompany = 0;
    $scope.personsWhoseRateitInMyCompany = '\n';
    $scope.showCommentsFromMyCompany = false;
    
    /* Rating data in my city */
    $scope.ratingInMyCity = [];
    $scope.averageInMyCity = 0;
    $scope.percentInMyCity = 0;
    $scope.personsWhoseRateitInMyCity = '\n';
    $scope.showCommentsFromMyCity = false;
    
    
    $scope.msg = {}; /* Error or success mesage */
    $scope.msg.type = '';
    $scope.msg.msg = '';

    $scope.showAddEvaluate = false;
    $scope.showAddMesage = false;
    
    $scope.getServiceProviderData = function () {

        ServiceProviderService.getServiceProviderData($scope.serviceProvider, function (callback) {
            if (!callback.success) { /* Invalid Session or Expired */
                window.localStorage['token'] = null;
                window.sessionStorage.setItem('typeOfAccount', null);
                window.location.href = 'index.html';
            } else {
                var array = callback.data;
                if (array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.serviceProvider.name = iter[0]; //name
                        $scope.serviceProvider.photo = iter[3] == null ? iter[3] = 'image/user-profile.png' : iter[3] = iter[3];
                    });
                }
            }
        });
    };

    $scope.getServiceProviderData();


    /* Get evaluate rating for my network partners */
    $scope.getServiceProviderRatingInMyNetworkPartners = function() {
        if ($scope.serviceProvider != null && $scope.serviceProvider != undefined) {
            ServiceProviderService.getServiceProviderRatingInMyNetworkPartners($scope.serviceProvider, function(callback) {
                if (!callback.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = callback.data;
                    array.forEach(function(iter) {
                        var dateSplit = iter[3].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        
                        $scope.ratingInMyNetwork.push({
                            partner : iter[0], //partner name
                            note    : iter[1], //note
                            comments: iter[2], //comments
                            date    : day + '/' + month + '/' + year //date
                        });
                        $scope.personsWhoseRateitInMyNetwork += iter[0] + '\n';
                    });
                    window.localStorage['token'] = callback.token;
                    
                    $scope.averageInMyNetwork = ServiceProviderService.calculateAverage($scope.ratingInMyNetwork);
                    $scope.percentInMyNetwork = ServiceProviderService.calculatePercentage($scope.averageInMyNetwork);
                }
            });
        }
    };
    
    $scope.getServiceProviderRatingInMyNetworkPartners();
    
    
    $scope.enableCommentsFromMyNetwork = function() {
        if ($scope.showCommentsFromMyNetwork) {
            $scope.showCommentsFromMyNetwork = false;
        } else {
            $scope.showCommentsFromMyNetwork = true;
        }
    }
    
    
    
    /* Get evaluate rating for my company */
    $scope.getServiceProviderRatingInMyCompany = function() {
        if ($scope.serviceProvider != null && $scope.serviceProvider != undefined) {
            ServiceProviderService.getServiceProviderRatingInMyCompany($scope.serviceProvider, function(callback) {
                if (!callback.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = callback.data;
                    array.forEach(function(iter) {
                        var dateSplit = iter[3].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        $scope.ratingInMyCompany.push({
                            partner : iter[0], //partner name
                            note    : iter[1], //note
                            comments: iter[2], //comments
                            date    : day + '/' + month + '/' + year  //date
                        });
                        $scope.personsWhoseRateitInMyCompany += iter[0] + '\n';
                    });
                    window.localStorage['token'] = callback.token;
                    
                    $scope.averageInMyCompany = ServiceProviderService.calculateAverage($scope.ratingInMyCompany);
                    $scope.percentInMyCompany = ServiceProviderService.calculatePercentage($scope.averageInMyCompany);
                }
            });
        }
    };
    
    $scope.getServiceProviderRatingInMyCompany();
    
    $scope.enableCommentsFromMyCompany = function() {
        if ($scope.showCommentsFromMyCompany) {
            $scope.showCommentsFromMyCompany = false;
        } else {
            $scope.showCommentsFromMyCompany = true;
        }
    }

    
    
    
    /* Get evaluate rating for my city */
    $scope.getServiceProviderRatingInMyCity = function() {
        if ($scope.serviceProvider != null && $scope.serviceProvider != undefined) {
            ServiceProviderService.getServiceProviderRatingInMyCity($scope.serviceProvider, function(callback) {
                if (!callback.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = callback.data;
                    array.forEach(function(iter) {
                        var dateSplit = iter[3].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        $scope.ratingInMyCity.push({
                            partner : iter[0], //partner name
                            note    : iter[1], //note
                            comments: iter[2], //comments
                            date    : day + '/' + month + '/' + year  //date
                        });
                        $scope.personsWhoseRateitInMyCity += iter[0] + '\n';
                    });
                    window.localStorage['token'] = callback.token;
                    
                    $scope.averageInMyCity = ServiceProviderService.calculateAverage($scope.ratingInMyCity);
                    $scope.percentInMyCity = ServiceProviderService.calculatePercentage($scope.averageInMyCity);
                }
            });
        }
    };
    
    $scope.getServiceProviderRatingInMyCity();
    
    $scope.enableCommentsFromMyCity = function() {
        if ($scope.showCommentsFromMyCity) {
            $scope.showCommentsFromMyCity = false;
        } else {
            $scope.showCommentsFromMyCity = true;
        }
    }
    
    
    
    
    /* save evaluate */
    $scope.saveEvaluate = function () {
        
        if ($scope.evaluateNote == undefined || $scope.evaluateNote == null) {
            $scope.msg.type = 'ERROR';
            $scope.msg.msg = 'Selecione uma nota para prosseguir';
            return;
        }
        
        ServiceProviderService.saveEvaluate($scope.serviceProvider, $scope.evaluateNote, $scope.comments, function (callback) {
            if (!callback.success) { /* Invalid Session or Expired */
                window.localStorage['token'] = null;
                window.sessionStorage.setItem('typeOfAccount', null);
                window.location.href = 'index.html';
            } else {
                if (!callback.isNewEvaluate) {
                    $scope.msg.type = 'ERROR';
                    $scope.msg.msg = callback.mesage;
                } else {
                    $scope.msg.type = 'SUCCESS';
                    $scope.msg.msg = callback.mesage;
                }
                window.localStorage['token'] = callback.token;
            }
        });
    };
    
    /* open evaluate form */
    $scope.evaluateButtonClick = function() {
        $scope.showAddEvaluate = true;
        $scope.showAddMesage = false;
    };
    
    /* open mesage form */
    $scope.mesageButtonClick = function() {
        $scope.showAddEvaluate = false;
        $scope.showAddMesage = true;
    };
});