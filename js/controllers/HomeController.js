app.controller('HomeController', function ($scope, PartnerService, SessionService, ServiceProviderService, FeedsService) {

    $scope.typeOfAccount = '';
    $scope.feeds = []; //mix of the both feeds partnership and ratings
    $scope.myServices = [];
    $scope.dicas = [];
    $scope.dicas.push({
        dica: 'Cadastre todos os serviços que você realiza!'
    }, {
        dica: 'Quanto mais serviços forncecer maiores serão suas oportunidades!'
    }, {
        dica: 'Capriche nos trabalhos! Afinal, são eles que vão te garantir melhores oportunidades!'
    }, {
        dica: 'Se torne um(a) profissional qualificado(a)!'
    });


    $scope.getTypeOfAccount = function () {
        SessionService.getTypeOfAccount(function (callback) {
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


    /* Open Partner Profile */
    $scope.openPartnerProfile = function (email, name) {
        var partner = {};
        partner.email = email;
        partner.name = name;

        // Encode the String
        var encodedString = PartnerService.encodePartnerEmail(partner); //btoa(partner.email + "|" + partner.name);

        window.location.href = "home.html#/partner-profile/" + encodedString;
    };

    //var encodedString = btoa(person.email + "|" + person.service);
    /* Open service Provider profile */
    $scope.openServiceProviderProfile = function (email, service) {
        var serviceProvider = {};
        serviceProvider.email = email;
        serviceProvider.service = service;

        var encodedData = ServiceProviderService.encodeEmail(serviceProvider);
        console.log('Service : ' + serviceProvider.email + "|" + serviceProvider.service);
        console.log('Encoded Data: ' + encodedData);

        window.location.href = "#/service-provider-profile/" + encodedData;
    };


    $scope.getFeedLastPartnership = function () {
        FeedsService.getFeedLastPartnership(function (callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                var array = callback.data;
                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.feeds.push({
                            partner: iter[0],
                            partnerEmail: iter[1],
                            user: iter[2],
                            userEmail: iter[3],
                            receivedIn: iter[4],
                            answeredIn: iter[5],
                            isRating: iter[8]
                        });
                    });
                }

            }
        });
    };

    $scope.getFeedLastPartnership();


    $scope.getFeedLastestRatings = function () {
        FeedsService.getFeedLastestRatings(function (callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                var array = callback.data;
                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {

                        var dateSplit = iter[5].split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];

                        $scope.feeds.push({
                            serviceProvider: iter[0],
                            serviceProviderEmail: iter[1],
                            service: iter[2],
                            note: iter[3],
                            comments: iter[4],
                            date: day + '/' + month + '/' + year, //date
                            user: iter[6],
                            userEmail: iter[7],
                            isRating: iter[8]
                        });
                    });
                }
            }
        });
    };

    $scope.getFeedLastestRatings();


    /* For Service Providers (get the service whose it provide) */
    $scope.getServicesProvideByMe = function () {

        if ($scope.typeOfAccount != 'CONTRACTOR') {
            ServiceProviderService.getMyServices(function (callback) {

                if (!callback.success) {
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.localStorage['token'] = null;
                    window.location.href = "index.html";
                } else {
                    var array = callback.data;
                    if (array.length > 0) {
                        array.forEach(function (iter) {
                            $scope.myServices.push({
                                name: iter[0]
                            });
                        });
                    }
                }
            });
        }
    };

    $scope.getServicesProvideByMe();

});