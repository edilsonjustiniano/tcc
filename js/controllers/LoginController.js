//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('LoginService', function ($http) {

    this.login = function (person, callback, error) {
        //$http.post('http://localhost:8080/WebService/session/login', JSON.stringify(person)).
        //success(callback);
        $http.post('http://localhost:8080/WebService/session/login', JSON.stringify(person))
                .then(callback, error);
    };
});


TCCApp.controller('LoginController', function ($scope, LoginService) {

    //UF Fields
    $scope.email = '';
    $scope.password = '';
    $scope.loading = true;
    $scope.msg = {}; /* Error or success mesage */
    $scope.msg.type = '';
    $scope.msg.msg = '';

    $scope.login = function () {

        /* show loading image But it is not Work. It seems it wait for the finish */
        //$scope.loading = false;

        var person = {};
        person.email = $scope.email;
        person.password = $scope.password;

        LoginService.login(person, function (callback) {
            var data = callback.data;
            if (!data.success) {
                $scope.msg.type = 'ERROR';
                $scope.msg.msg = data.mesage;
                $scope.loading = true;
            } else {
                $scope.msg.type = 'SUCCESS';
                $scope.msg.type = data.mesage;
                window.localStorage['token'] = data.token;
                window.location.href = 'home.html#/home';
            }
        }, $scope.error);
    };
                           
    $scope.error = function(response) {
        console.log('error: '); 
    };
});