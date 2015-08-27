app.controller('SearchServiceProviderController', function ($scope, ServiceProviderService, ServiceService) {

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
        });
    };

    $scope.selectService = function (service) {

        if (service == null) {
            return;
        }

        ServiceProviderService.getServicesProvidersByService(service.name, function (callback) {
            var data = callback.data;
            if (!data.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                window.localStorage['token'] = data.token;
                $scope.serviceProviders = [];
                var array = data.results;

                /* A consulta foi alterada para ser apenas uma, sendo que, a order vai definir como foram localizados,
                 * exemplo: 1 - Na rede de parceiros do usuário; 2 - Na empresa onde o usuário trabalha;
                 * 3 - Na cidade onde o usuário vive; E só na página de perfil dele é que eu irei fazer a comparação
                 * como no app vivino que o Márcio apresentou
                 */
                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {

                        /* Check before if there is the same person added before */
                        var isExist = false;
                        for (var i = 0; i < $scope.serviceProviders.length; i++) {
                            if ($scope.serviceProviders[i].email == iter.serviceProviderEmail) {
                                isExist = true;
                                break;
                            }

                        }

                        if (!isExist) {
                            $scope.serviceProviders.push({
                                name: iter.serviceProviderName,
                                email: iter.serviceProviderEmail,
                                service: iter.service,
                                howManyPersonsEvaluate: iter.total,
                                average: iter != null ? iter.media.toFixed(2) : 0
                            });
                        }
                    });
                }
            }

            console.log($scope.serviceProviders.length);
            $scope.service = service.name;
            $scope.services = [];
        }, $scope.error);
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

    $scope.getProviderDetails = function (provider, div) {
        console.log("Service Provider Selected: " + provider.name + " | " + provider.service);
        console.log(div);
    };

     $scope.error = function(response) {
        console.log('error: '); 
    };
});