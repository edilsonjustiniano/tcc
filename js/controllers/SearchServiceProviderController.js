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
                            if ($scope.serviceProviders[i].email == iter[1]) {
                                isExist = true;
                                break;
                            }

                        }

                        if (!isExist) {
                            $scope.serviceProviders.push({
                                name: iter[0],
                                email: iter[1],
                                service: iter[2],
                                howManyPersonsEvaluate: iter[3],
                                average: iter != null ? iter[4].toFixed(2) : 0
                            });
                        }
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

    $scope.getProviderDetails = function (provider, div) {
        console.log("Service Provider Selected: " + provider.name + " | " + provider.service);
        console.log(div);
    };

});