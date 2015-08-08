app.controller('SearchServiceProviderController', function ($scope, ServiceProviderService) {
    
    $scope.services = [];
    $scope.service = '';
    $scope.serviceProviders = [];
    
    $scope.getServicesByService = function () {
        if ($scope.service.length < 3) {
            $scope.services = [];
            $scope.serviceProviders = [];
            return;
        }

        //MenuBarService.getServicesProvidersByService($scope.service, function (callback) {
        ServiceProviderService.getServicesByName($scope.service, function (callback) {

            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                $scope.services = [];
                var array = callback.data;

                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.services.push({
                            name: iter[0]
                        });
                    });
                }
            }
        });
    };

    $scope.selectService = function (service) {

        if (service == null) {
            return;
        }

        ServiceProviderService.getServicesProvidersByService(service.name, function (callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                window.localStorage['token'] = callback.token;
                $scope.serviceProviders = [];
                var array = callback.data;

                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.serviceProviders.push({
                            name: iter[0],
                            email: iter[1],
                            service: iter[2],
                            howManyPeopleEvaluate: iter[3],
                            average: iter[4]
                        });
                    });
                }
            }

            console.log($scope.serviceProviders.length);
            $scope.service = service.name;
            $scope.services = [];
        });
    };


    $scope.selectServiceProvider = function (provider) {
        console.log("Service Provider Selected: " + provider.name + " | " + provider.service);
        var encodedData = ServiceProviderService.encodeEmail(provider);
        console.log('Service : ' + provider.email + "|" + provider.service);
        console.log('Encoded Data: ' + encodedData);

        window.location.href = "#/service-provider-profile/" + encodedData;
        /* Reset fields and options */
        $scope.service = '';
        $scope.services = [];
        $scope.serviceProviders = [];
    };
});