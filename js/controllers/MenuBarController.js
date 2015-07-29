//App
//var TCCApp = angular.module('TCCApp', []); //Usando Rotas, portanto não há mais a necessidade desta declaração

app.service('MenuBarService', function ($http) {

    this.getAllPartnerRequest = function (callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/partner/getAllPartnerRequest', {
            token: token
        }).
        success(callback);
    };

    this.acceptPartnerRequest = function (partner, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/partner/acceptPartnerRequest', {
            partner: partner.requestFromEmail,
            token: token
        }).
        success(callback);
    };

    this.rejectPartnerRequest = function (partner, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/partner/rejectPartnerRequest', {
            partner: partner.requestFromEmail,
            token: token
        }).
        success(callback);
    };

    this.getServicesProvidersByService = function (service, callback) {
        var token = window.localStorage['token'];
        $http.post('http://localhost:8080/WebService/serviceProvider/getServiceProvidersByService', {
            service: service,
            token: token
        }).
        success(callback);
    };
});


app.controller('MenuBarController', function ($scope, MenuBarService, SessionService, ServiceProviderService) {

    $scope.name = '';
    $scope.typeOfAccount = '';
    $scope.partnerRequests = [];
    $scope.msg = {}; /* Error or success mesage */
    $scope.msg.type = '';
    $scope.msg.msg = '';

    //    $scope.services = [];
    //    $scope.service = '';
    //    $scope.services.push({name: "Pedreiro"});
    //    $scope.services.push({name: "Pintor"});
    //    $scope.services.push({name: "Babá"});
    //    $scope.services.push({name: "Doméstica"});
    $scope.servicesProviders = [];
    $scope.service = '';


    $scope.getUserInfoFromSession = function () {

        SessionService.getUserInfoFromSession(function (callback) {
            if (!callback.success) { /* Ivalid session or expired session */
                $scope.msg.type = 'ERROR';
                $scope.msg.msg = callback.mesage;
                window.localStorage['token'] = null;
                window.location.href = 'index.html';
            } else {
                //get data from return and fill the components according to type of account
                window.localStorage['token'] = callback.token;
                $scope.name = callback.data[0][0]; //name
                var arrayNames = $scope.name.split(" "); //Show only the first name
                $scope.name = arrayNames[0];
                //$scope.email = callback.data[0][1]; //email (It works)
                //$scope.typeOfPerson = callback.data[0][2]; //typeOfPerson (It works)
                $scope.typeOfAccount = callback.data[0][3]; //typeOfAccount
                window.sessionStorage.setItem('typeOfAccount', $scope.typeOfAccount);
            }
        });
    };

    $scope.logout = function () {
        sessionStorage.setItem('typeOfAccount', null);
        //window.localStorage['typeOfAccount'] = null;
        window.localStorage['token'] = null;
        window.location.href = "index.html";
    };


    $scope.getUserInfoFromSession();


    /* Get all request for partners */
    $scope.getAllPartnerRequest = function () {

        /* Only contractor user can perform this query */
        if (window.sessionStorage.getItem('typeOfAccount') == 'SERVICE_PROVIDER') {
            return;
        }

        MenuBarService.getAllPartnerRequest(function (callback) {

            if (!callback.success) { /* Invalid session or expired session */

                window.sessionStorage.setItem('typeOfAccount', undefined);
                window.localStorage['token'] = null;
                window.location.href = "index.html";

            } else {

                window.localStorage['token'] = callback.token;
                var array = callback.data;
                array.forEach(function (iter) {
                    $scope.partnerRequests.push({
                        requestFromName: iter[0],
                        requestFromEmail: iter[1]
                    });
                });

            }

        });
    };

    $scope.getAllPartnerRequest();


    /* Accept the partner request */
    $scope.acceptPartnerRequest = function (partner) {

        MenuBarService.acceptPartnerRequest(partner, function (callback) {
            if (!callback.success) { /* Invalid session or expired session */

                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";

            } else {

                window.localStorage['token'] = callback.token;
                $scope.partnerRequests = [];
                $scope.getAllPartnerRequest();
            }
        });
    };



    /* Reject the partner request */
    $scope.rejectPartnerRequest = function (partner) {

        MenuBarService.rejectPartnerRequest(partner, function (callback) {
            if (!callback.success) { /* Invalid session or expired session */

                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";

            } else {

                window.localStorage['token'] = callback.token;
                $scope.partnerRequests = [];
                $scope.getAllPartnerRequest();
            }
        });
    };


    $scope.getServicesProvidersByService = function () {
        if ($scope.service.length < 3) {
            return;
        }

        MenuBarService.getServicesProvidersByService($scope.service, function (callback) {
            if (!callback.success) {
                window.sessionStorage.setItem('typeOfAccount', null);
                window.localStorage['token'] = null;
                window.location.href = "index.html";
            } else {
                window.localStorage['token'] = callback.token;
                $scope.servicesProviders = [];
                var array = callback.data;
                if (array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.servicesProviders.push({
                            name: iter[0],
                            email: iter[1],
                            service: iter[2]
                        });
                    });
                }
            }
        });


    };

    /* Load all options */
    $scope.selectService = function (person) {
        
        if (person == null) {
			return;
		}
        
        var encodedData = ServiceProviderService.encodeEmail(person);
        console.log('Service : ' + person.email + "|" + person.service);
        console.log('Encoded Data: ' + encodedData);
        
		window.location.href = "#/service-provider-profile/" + encodedData;
    };
});