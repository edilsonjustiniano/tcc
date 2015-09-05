app.controller('ConfigController', ['$scope' ,'SessionService', function ($scope, SessionService) {

	$scope.msg = {};
	$scope.msg.msg = '';
	$scope.msg.type = '';
	$scope.typeOfAccountTemp = undefined;

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
					cityUF: userData.cityUF,
					cityWork: userData.cityWork,
					ufWork: userData.ufWork,
					company: userData.company
				};

				$scope.typeOfAccountTemp = userData.typeOfAccount;
				window.localStorage['token'] = data.token;
			} else{

				window.sessionStorage.setItem('typeOfAccount', null);
				window.localStorage['token'] = null;
				window.location.href = "index.html";

			}
		}, $scope.error);

	};

	$scope.loadData();


	$scope.error = function (error){
		console.log(error);

	};


}]);