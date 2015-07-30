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
    $scope.evaluateNote = '';
    $scope.comments = '';

    $scope.msg = {}; /* Error or success mesage */
    $scope.msg.type = '';
    $scope.msg.msg = '';

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
                    });
                }
            }
        });
    };

    $scope.getServiceProviderData();


    /* save evaluate */
    $scope.saveEvaluate = function () {
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
});