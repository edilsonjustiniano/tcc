//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('CreateAccountWorkDataService', function($http){
	
	this.getAllStates = function(data) {
		$http.get('http://localhost:8080/WebService/state/getAllStates').success(data);
	};

	this.getAllCitiesByState = function(state, data) {
		$http.get('http://localhost:8080/WebService/city/getAllCitiesByState/' + state).success(data);
	};

	this.createAccount = function(person, callback) {
		$http.post('http://localhost:8080/WebService/person/createAccountWorkData', JSON.stringify(person)).
		success(callback);
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

		CreateAccountWorkDataService.getAllStates(function(data){
			var array = data.data;
			array.forEach(function(iter){
				$scope.states.push({name: iter[0], abbreviation: iter[1]});
			});
		});
	};

	$scope.getAllStates(); //get All states

	$scope.getAllCitiesByStateForCompany = function() {
		CreateAccountWorkDataService.getAllCitiesByState($scope.ufCompany.trim(), function(data) {
			var array = data.data;
			array.forEach(function(iter){
				$scope.citiesCompany.push({name: iter[0]});
			});
		});
	};

	$scope.getAllCitiesByStateForLives = function() {
		CreateAccountWorkDataService.getAllCitiesByState($scope.ufLives.trim(), function(data) {
			var array = data.data;
			array.forEach(function(iter){
				$scope.citiesLives.push({name: iter[0]});
			});
		});
	};

	$scope.createAccount = function() {

		var person = new Person();
		var stateForCompany = new State();
		var stateForLive = new State();
		var livesIn = new City();
		var worksIn = new Company();

		stateForLive.setName($scope.ufLives.trim());
		livesIn.setName($scope.cityLives.trim());
		livesIn.setState(stateForLive);
		person.setLivesIn(livesIn);

		worksIn.setName($scope.company);
		var city = new City();
		city.setName($scope.cityCompany.trim());
		stateForCompany.setName($scope.ufCompany.trim());
		city.setState(stateForCompany);
		worksIn.setLocatedIn(city);
		person.setWorksIn(worksIn);
		
		person.setDistrict($scope.district);
		person.setAddress($scope.address);
		
		/* Load the info about the session */
		if (window.localStorage['token'] == undefined || window.localStorage['token'] == null) {
			$scope.msg.type = 'ERROR';
			$scope.msg.msg = 'Sessão inválida!';
			return;
		}

		person.setToken(window.localStorage['token']);

		CreateAccountWorkDataService.createAccount(person, function(callback) {
			 console.log('callback' + callback);
			 if (!callback.success) {
			 	$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				return;		 
			} else {
				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.mesage;
				window.localStorage['token'] = callback.token;
				window.location.href = 'create-account-photo.html';
			}

		});
			
	};


});
