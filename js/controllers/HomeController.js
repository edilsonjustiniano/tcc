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
            var data = callback.data;
            if (!data.success) { /* Ivalid session or expired session */
                $scope.msg.type = 'ERROR';
                $scope.msg.msg = data.mesage;
                window.localStorage['token'] = null;
                window.location.href = 'index.html';
            } else {
                //get data from return and fill the components according to type of account
                window.localStorage['token'] = data.token;
                var userData = data.results[0];
                $scope.name = userData.name; //name
                //$scope.email = callback.data[0][1]; //email (It works)
                //$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
                $scope.typeOfAccount = userData.typeOfAccount; //typeOfAccount
                window.sessionStorage.setItem('typeOfAccount', $scope.typeOfAccount);
                $scope.getServicesProvideByMe();
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


    $scope.getFeedLastestPartnership = function () {
        FeedsService.getFeedLastestPartnership(function (callback) {
            var data = callback.data;
            if (!data.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                var array = data.results;
                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.feeds.push({
                            partner: iter.partnerName,
                            partnerEmail: iter.partnerEmail,
                            user: iter.userName,
                            userEmail: iter.userEmail,
                            receivedIn: iter.partnerASince,
                            answeredIn: iter.partnerBSince,
                            isRating: iter.rating
                        });
                    });
                }

            }
        }, $scope.error);
    };

    $scope.getFeedLastestPartnership();


    $scope.getFeedLastestRatings = function () {
        FeedsService.getFeedLastestRatings(function (callback) {
            var data = callback.data;
            if (!data.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                var array = data.results;
                if (array != null && array.length > 0) {
                    array.forEach(function (iter) {

                        var dateSplit = iter.date.split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];

                        $scope.feeds.push({
                            serviceProvider: iter.serviceProviderName,
                            serviceProviderEmail: iter.serviceProviderEmail,
                            service: iter.service,
                            note: iter.note,
                            comments: iter.comments,
                            date: day + '/' + month + '/' + year, //date
                            user: iter.partnerName,
                            userEmail: iter.partnerEmail,
                            isRating: iter.rating
                        });
                    });
                }
            }
        }, $scope.error);
    };

    $scope.getFeedLastestRatings();


    /* For Service Providers (get the service whose it provide) */
    $scope.getServicesProvideByMe = function () {

        if ($scope.typeOfAccount && $scope.typeOfAccount != 'CONTRACTOR') {
            ServiceProviderService.getMyServices(function (callback) {
                var data = callback.data;
                if (!data.success) {
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.localStorage['token'] = null;
                    window.location.href = "index.html";
                } else {
                    var array = data.results;
                    if (array.length > 0) {
                        array.forEach(function (iter) {
                            $scope.myServices.push({
                                name: iter.service
                            });
                        });
                    }
                }
            }, $scope.error);
        }
    };

    $scope.getServicesProvideByMe();
    
    $scope.error = function(response) {
        console.log('error: '); 
    };

});