app.controller('HomeController', 
               function ($scope, PartnerService, SessionService, ServiceProviderService, FeedsService, ReportService) {

    $scope.feeds = []; //mix of the both feeds partnership and ratings
    $scope.myServices = [];
    $scope.dicas = [];
    $scope.loading = true;
    $scope.showGraphics = false;
    
    $scope.dicas.push({
        dica: 'Cadastre todos os serviços que você realiza!'
    }, {
        dica: 'Quanto mais serviços fornecer maiores serão suas oportunidades!'
    }, {
        dica: 'Capriche nos trabalhos! Afinal, são eles que vão lhe garantir melhores oportunidades!'
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
            $scope.loading = false;
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
    
    
    $scope.reloadGraph = function(service) {
        if (service) {
            $scope.showGraphics = true;
            $scope.selectedService = service.name;
            $scope.lastEvaluate(service.name, 20);   
        }
    }
    
    /** Graficos **/
    $scope.lastEvaluate = function(service, limit){ 
        ReportService.lastEvaluate(service, limit, function (callback){

                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    var notes = [0, 0, 0, 0];
                    var evaluates = [0, 0, 0, 0];
                    var results = [];
                    
                    for (var i = 0; i < array.length; i++) {
                        if(i < 5){
                            notes[0] += array[i].note;
                            evaluates[0] += 1;
                        }
                        if (i < 10) {
                            notes[1] += array[i].note;
                            evaluates[1] += 1;
                        }
                        if(i < 15){
                            notes[2] += array[i].note;
                            evaluates[2] += 1;
                        }
                        if(i < 20){
                            notes[3] += array[i].note;
                            evaluates[3] += 1;
                        }
                    }

                    for (var i = 0; i < notes.length; i++) {
                        if (notes[i] > 0) {
                            results[i] = notes[i] / evaluates[i];
                        } else {
                            results[i] = 0;
                        }
                    }
                    
                    $scope.lineChartData.datasets[0].data = results;
                    $scope.lastEvaluateInMyCity(service, limit);
                }

            }, $scope.error)
    };

    $scope.lastEvaluateInMyCity = function(service, limit){ 
        ReportService.lastEvaluateInMyCity(service, limit, function (callback){

                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    var notes = [0, 0, 0, 0];
                    var evaluates = [0, 0, 0, 0];
                    var results = [];
                    
                    for (var i = 0; i < array.length; i++) {
                        if(i < 5){
                            notes[0] += array[i].note;
                            evaluates[0] += 1;
                        }
                        if (i < 10) {
                            notes[1] += array[i].note;
                            evaluates[1] += 1;
                        }
                        if(i < 15){
                            notes[2] += array[i].note;
                            evaluates[2] += 1;
                        }
                        if(i < 20){
                            notes[3] += array[i].note;
                            evaluates[3] += 1;
                        }
                    }

                    for (var i = 0; i < notes.length; i++) {
                        if (notes[i] > 0) {
                            results[i] = notes[i] / evaluates[i];
                        } else {
                            results[i] = 0;
                        }
                    }
                    
                    $scope.lineChartData.datasets[1].data = results;
                    $scope.loadGraph();
                }

            }, $scope.error)
    };
    
    $scope.lineChartData = {
            labels : ["5 últimas","10 últimas","15 últimas","20 últimas"],
            datasets : [
                {
                    label: "Minhas avaliações como " + $scope.selectedService,
                    fillColor : "rgba(166,246,166,0.2)",
                    strokeColor : "rgba(61,213,61,1)",
                    pointColor : "rgba(61,213,61,1)",
                    pointStrokeColor : "#fff",
                    pointHighlightFill : "#fff",
                    pointHighlightStroke : "rgba(41,157,41,1)",
                    data : [
                        //filled by lastEvaluateOfServiceProvider
                    ]   
                },
                {
                    label: "As avaliações de " + $scope.selectedService + " em minha cidade",
                    fillColor : "rgba(98,168,248,0.2)",
                    strokeColor : "rgba(25,123,235,1)",
                    pointColor : "rgba(25,123,235,1)",
                    pointStrokeColor : "#fff",
                    pointHighlightFill : "#fff",
                    pointHighlightStroke : "rgba(33,97,170,1)",
                    data : [
                        //filled by lastEvaluateOfServiceInNetwork
                    ]
                }
            ]

        };

    $scope.loadGraph = function(){
        var ctx = document.getElementById("canvas").getContext("2d");
        window.myLine = new Chart(ctx).Line($scope.lineChartData, {
            responsive: true
        });
    };

});