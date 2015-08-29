app.controller('ServiceController', function ($scope, ServiceProviderService, ServiceService) {

    $scope.services = [];
    $scope.service = '';
    $scope.msg = {}; /* Error or success mesage */
    $scope.msg.type = '';
    $scope.msg.msg = '';
    $scope.myServices = [];

    $scope.getServicesByService = function () {
        if ($scope.service.length < 3) {
            $scope.services = [];
            return;
        }

        //MenuBarService.getServicesProvidersByService($scope.service, function (callback) {
        ServiceService.getServicesByName($scope.service, function (callback) {
            var data = callback.data;
            if (!data.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                $scope.services = [];
                var array = data.results;

                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.services.push({
                            name: iter.service
                        });
                    });
                }
            }
        }, $scope.error);
    };
  
    $scope.selectService = function(iter){
        console.log(iter);
        $scope.service = iter.name;
        $scope.services = [];

    };


    $scope.addService = function () {

        if ($scope.service != '') {

            ServiceProviderService.addService($scope.service, function (callback) {

                if (!callback.success) {
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.localStorage['token'] = null;
                    window.location.href = "index.html";
                } else {

                    window.localStorage['token'] = callback.token;
                    if (callback.isAlreadyStored) {
                        $scope.msg.type = 'ERROR'; 
                    } else {
                       $scope.msg.type = 'SUCCESS'; 
                    }

                    $scope.msg.msg = callback.mesage;
                    $scope.myServices = [];
                    $scope.getMyServices();
                }
            });
        }
    };

    $scope.getMyServices = function () {

        ServiceProviderService.getMyServices(function (callback) {
                var data = callback.data;
                if (!data.success) {
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.localStorage['token'] = null;
                    window.location.href = "index.html";
                } else {
                    var array = data.results;
                    if (array.length > 0){
                        array.forEach(function(iter){
                            $scope.myServices.push({
                                name: iter.service
                            });
                        });
                    }
                }
        }, $scope.error);
    };

    $scope.getMyServices();
    
    
    $scope.removeService = function(iter) {
        if (iter != null) {
             ServiceProviderService.removeService(iter.name, function (callback) {

                if (!callback.success) {
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.localStorage['token'] = null;
                    window.location.href = "index.html";
                } else {
                    window.localStorage['token'] = callback.token;
                    $scope.msg.type = 'SUCCESS'; 
                    
                    $scope.msg.msg = callback.mesage;
                    $scope.myServices = [];
                    $scope.getMyServices();
                }
        });
        }  
    };
    
     $scope.error = function(response) {
        console.log('error: '); 
    };

});