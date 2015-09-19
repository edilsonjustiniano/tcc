app.controller('ConfigController', ['$scope', 'SessionService', 'CityService', 'AccountService',
 function ($scope, SessionService, CityService, AccountService) {

	$scope.msg = {};
	$scope.msg.msg = '';
	$scope.msg.type = '';
	$scope.typeOfAccountTemp = undefined;
	$scope.states = [];
	$scope.citiesCompany = [];
	$scope.citiesLives = [];

	$scope.loadData = function() {

		SessionService.getUserInfoFromSession(function(callback){
			console.log(callback);
			var data = callback.data;

			if(data.success){
				var userData = data.results[0];
				$scope.user = {
					name: userData.name,
					email: userData.email,
					photo: userData.photo == null ? userData.photo = 'image/user-profile.png': userData.photo = userData.photo,
					typeOfAccount: userData.typeOfAccount,
					typeOfPerson:userData.typeOfPerson,
					address: userData.address,
					district: userData.district,
					cpf: userData.cpf,
					cnpj: userData.cnpj,
					cityLives: userData.cityLives,
					ufLives: userData.ufLives,
					cityUF: userData.cityUF,
					cityWork: userData.cityWork,
					ufWork: userData.ufWork,
					company: userData.company
				};

				$scope.typeOfAccountTemp = userData.typeOfAccount;
				window.localStorage['token'] = data.token;

				$scope.getAllCitiesByStateForCompany();
				/* city by state */
				$scope.citiesCompany.forEach(function(city) {
					if (city.name == $scope.user.cityWork) {
						$scope.user.cityWork = city;
					}
				});

				$scope.getAllCitiesByStateForLives();
				/* city by state */
				$scope.citiesLives.forEach(function(city) {
					if (city.name == $scope.user.cityLives) {
						$scope.user.cityLives = city;
					}
				});

			} else{

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";

			}
		}, $scope.error);

	};

	$scope.loadData();

	$scope.getAllStates = function() {

		CityService.getAllStates(function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.states.push({name: iter.name, abbreviation: iter.abbreviation});
			});
		}, $scope.error);
	};

	$scope.getAllStates(); //get All states

	/* city by state */
	$scope.states.forEach(function(uf) {
		if (uf.name == $scope.user.ufWork) {
			$scope.user.ufWork = uf;
		}

		if (uf.name == $scope.user.ufLives) {
			$scope.user.ufLives = uf;
		}

	});

	$scope.getAllCitiesByStateForCompany = function() {
		CityService.getAllCitiesByState($scope.user.ufWork.trim(), function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.citiesCompany.push({name: iter.name});
			});
		}, $scope.error);
	};
	
	$scope.getAllCitiesByStateForLives = function() {
		CityService.getAllCitiesByState($scope.user.ufLives.trim(), function(callback) {
			var array = callback.data.results;
			array.forEach(function(iter){
				$scope.citiesLives.push({name: iter.name});
			});
		}, $scope.error);
	};

	$scope.save = function(){
		AccountService.editAccount(function (callback){
			var data = callback.data;
			if(data.success){
				$scope.msg.type = "SUCCESS";
				window.localStorage['token'] = data.token;
			}else{
				$scope.msg.type = "ERROR";
				
			}

			$scope.msg.msg = data.mesage;

		},$scope.error, $scope.user);
	};

	$scope.error = function (error){
		console.log(error);

	};


}]);