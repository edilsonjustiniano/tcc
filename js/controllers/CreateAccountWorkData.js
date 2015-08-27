//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('CreateAccountWorkDataService', function($http){
	
	this.getAllStates = function(success, error) {
		$http.get('http://localhost:8080/WebService/uf').
        then(success, error);
	};

	this.getAllCitiesByState = function(state, success, error) {
		$http.get('http://localhost:8080/WebService/city/cities/' + state).
        then(success, error);
	};

	this.createAccount = function(person, callback, error) {
		$http.post('http://localhost:8080/WebService/person/createaccount/workdata', JSON.stringify(person)).
		then(callback, error);
	};
});


TCCApp.controller('CreateAccountWorkData', function($scope, CreateAccountWorkDataService){
	
	//Create the attributes
	$scope.states = [];
	$scope.ufCompany = '';
	$scope.ufLives = '';
	$scope.citiesCompany = [];
	$scope.cityCompany = '';
	$scope.company = '';
	$scope.citiesLives = [];
	$scope.cityLives = '';
	$scope.district = '';
	$scope.address = '';

	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';


	$scope.getAllStates = function() {

		CreateAccountWorkDataService.getAllStates(function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.states.push({name: iter.name, abbreviation: iter.abbreviation});
			});
		}, $scope.error);
	};

	$scope.getAllStates(); //get All states

	$scope.getAllCitiesByStateForCompany = function() {
		CreateAccountWorkDataService.getAllCitiesByState($scope.ufCompany.trim(), function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.citiesCompany.push({name: iter.name});
			});
		}, $scope.error);
	};

	$scope.getAllCitiesByStateForLives = function() {
		CreateAccountWorkDataService.getAllCitiesByState($scope.ufLives.trim(), function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.citiesLives.push({name: iter.name});
			});
		}, $scope.error);
	};

	$scope.createAccount = function() {

		var person = {};
		var stateForCompany = {};
		var stateForLive = {};
		var livesIn = {};
		var worksIn = {};

		stateForLive.name = $scope.ufLives.trim();
		livesIn.name = $scope.cityLives.trim();
		livesIn.state = stateForLive;
		person.livesIn = livesIn;

		worksIn.name = $scope.company;
		var city = {};
		city.name = $scope.cityCompany.trim();
		stateForCompany.name = $scope.ufCompany.trim();
		city.state = stateForCompany;
		worksIn.locatedIn = city;
		person.worksIn = worksIn;
		
		person.district = $scope.district;
		person.address = $scope.address;
		
		/* Load the info about the session */
		if (window.localStorage['token'] == undefined || window.localStorage['token'] == null) {
			$scope.msg.type = 'ERROR';
			$scope.msg.msg = 'Sessão inválida!';
			return;
		}

		person.token = window.localStorage['token'];

		CreateAccountWorkDataService.createAccount(person, function(callback) {
			var data = callback.data; 
            if (!data.success) {
			 	$scope.msg.type = 'ERROR';
				$scope.msg.msg = data.mesage;
				return;		 
			} else {
				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = data.mesage;
				window.localStorage['token'] = data.token;
				window.location.href = 'create-account-photo.html';
			}

		}, $scope.error);
			
	};

    $scope.error = function(response) {
        console.log('error: '); 
    };

});
