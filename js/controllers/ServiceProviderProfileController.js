app.controller('ServiceProviderProfileController', 
    function ($scope, $routeParams, ServiceProviderService, RatingService, ReportService) {

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

    $scope.showAddRating = false;
    $scope.showAddMesage = false;
    
    $scope.loading = true;

    $scope.reportResults = [2, 5, 4, 1];
    
    $scope.getServiceProviderData = function () {

        ServiceProviderService.getServiceProviderData($scope.serviceProvider, function (callback) {
            var data = callback.data;
            if (!data.success) { /* Invalid Session or Expired */
                window.localStorage['token'] = null;
                window.sessionStorage.setItem('typeOfAccount', null);
                window.location.href = 'index.html';
            } else {
                var array = data.results;
                if (array.length > 0) {
                    array.forEach(function (iter) {
                        $scope.serviceProvider.name = iter.serviceProviderName; //name
                        $scope.serviceProvider.photo = iter.photo == null ? iter.photo = 'image/user-profile.png' : iter.photo = iter.photo;
                        $scope.serviceProvider.gender = iter.gender == "F" ? "Feminino" : "Masculino";
                        $scope.serviceProvider.worksIn = iter.worksIn;
                        $scope.serviceProvider.livesIn = iter.livesIn;
                        
                    });
                }
            }
        }, $scope.error);
    };

    $scope.getServiceProviderData();


    /* Get evaluate rating for my network partners */
    $scope.getServiceProviderRatingInMyNetworkPartners = function() {
        if ($scope.serviceProvider != null && $scope.serviceProvider != undefined) {
            ServiceProviderService.getServiceProviderRatingInMyNetworkPartners($scope.serviceProvider, function(callback) {
                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    array.forEach(function(iter) {
                        var dateSplit = iter.date.split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        
                        $scope.ratingInMyNetwork.push({
                            partner : iter.partner, //partner name
                            note    : iter.note, //note
                            comments: iter.comments, //comments
                            date    : day + '/' + month + '/' + year //date
                        });
                        $scope.personsWhoseRateitInMyNetwork += iter.partner + '\n';
                    });
                    window.localStorage['token'] = data.token;
                    
                    $scope.averageInMyNetwork = ServiceProviderService.calculateAverage($scope.ratingInMyNetwork);
                    $scope.percentInMyNetwork = ServiceProviderService.calculatePercentage($scope.averageInMyNetwork);
                }
            }, $scope.error);
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
                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    array.forEach(function(iter) {
                        var dateSplit = iter.date.split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        $scope.ratingInMyCompany.push({
                            partner : iter.partner, //partner name
                            note    : iter.note, //note
                            comments: iter.comments, //comments
                            date    : day + '/' + month + '/' + year  //date
                        });
                        $scope.personsWhoseRateitInMyCompany += iter.partner + '\n';
                    });
                    window.localStorage['token'] = data.token;
                    
                    $scope.averageInMyCompany = ServiceProviderService.calculateAverage($scope.ratingInMyCompany);
                    $scope.percentInMyCompany = ServiceProviderService.calculatePercentage($scope.averageInMyCompany);
                }
            }, $scope.error);
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
                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    array.forEach(function(iter) {
                        var dateSplit = iter.date.split(' ');
                        dateSplit = dateSplit[0];
                        dateSplit = dateSplit.split('-'); //[2015] [08] [11]
                        var year = dateSplit[0];
                        var month = Number.parseInt(dateSplit[1]);
                        var day = dateSplit[2];
                        
                        $scope.ratingInMyCity.push({
                            partner : iter.partner, //partner name
                            note    : iter.note, //note
                            comments: iter.comments, //comments
                            date    : day + '/' + month + '/' + year  //date
                        });
                        $scope.personsWhoseRateitInMyCity += iter.partner + '\n';
                    });
                    window.localStorage['token'] = data.token;
                    
                    $scope.averageInMyCity = ServiceProviderService.calculateAverage($scope.ratingInMyCity);
                    $scope.percentInMyCity = ServiceProviderService.calculatePercentage($scope.averageInMyCity);
                }
                
                $scope.loading = false;
                
            }, $scope.error);
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
    $scope.saveRating = function () {
        
        if ($scope.evaluateNote == undefined || $scope.evaluateNote == null) {
            $scope.msg.type = 'ERROR';
            $scope.msg.msg = 'Selecione uma nota para prosseguir';
            return;
        }
        
        RatingService.saveRating($scope.serviceProvider, $scope.evaluateNote, $scope.comments, function (callback) {
            var data = callback.data;
            if (!data.success) { /* Invalid Session or Expired */
                window.localStorage['token'] = null;
                window.sessionStorage.setItem('typeOfAccount', null);
                window.location.href = 'index.html';
            } else {
                if (!data.isNewEvaluate) {
                    $scope.msg.type = 'ERROR';
                    $scope.msg.msg = data.mesage;
                } else {
                    $scope.msg.type = 'SUCCESS';
                    $scope.msg.msg = data.mesage;
                }
                window.localStorage['token'] = data.token;
            }
        });
    };
    
    /* open evaluate form */
    $scope.evaluateButtonClick = function() {
        $scope.showAddRating = true;
        $scope.showAddMesage = false;
    };
    
    /* open mesage form */
    $scope.mesageButtonClick = function() {
        $scope.showAddRating = false;
        $scope.showAddMesage = true;
    };
    
     $scope.error = function(response) {
        console.log('error: '); 
    };

    $scope.lastEvaluateOfServiceProvider = function(limit){ 
        ReportService.lastEvaluateOfServiceProvider( 
            $scope.serviceProvider.email, 
            $scope.serviceProvider.service, 
            20, function (callback){

                var data = callback.data;
                if (!data.success) {
                    window.localStorage['token'] = null;
                    window.sessionStorage.setItem('typeOfAccount', null);
                    window.location.href = 'index.html';
                } else {
                    var array = data.results;
                    var notes = [0, 0, 0, 0];
                    var evaluates = [0, 0, 0, 0];
                    
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
                            $scope.reportResults[i] = notes[i] / evaluates[i];
                        } else {
                            $scope.reportResults[i] = 0;
                        }
                    }
                    
                    $scope.lineChartData.datasets[0].data = $scope.reportResults;
                    $scope.lastEvaluateOfServiceInNetwork();
                    //$scope.loadGraph();
                }

            }, $scope.error)
    };

    $scope.lastEvaluateOfServiceInNetwork = function(){ 
        ReportService.lastEvaluateOfServiceInNetwork( 
            $scope.serviceProvider.email, 
            $scope.serviceProvider.service, 
            20, function (callback){

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
                    label: "Avaliações de " + $scope.serviceProvider.name,
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
                    label: "B",
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

    $scope.lastEvaluateOfServiceProvider(); //Load data to fill graphic
        
    $scope.loadGraph = function(){
        var ctx = document.getElementById("canvas").getContext("2d");
        window.myLine = new Chart(ctx).Line($scope.lineChartData, {
            responsive: true
        });
    };
});