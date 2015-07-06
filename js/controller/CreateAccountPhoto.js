//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.controller('CreateAccountPhoto', function($scope){
	
	//Create the attributes
	$scope.name = '';
	$scope.email = '';
	$scope.password = '';
	$scope.confirmPassword = '';
	$scope.typeOfAccount = 'SERVICE_PROVIDER';
	$scope.typeOfPerson;
	$scope.cnpj = '';
	$scope.cpf = '';
	$scope.gender = 'F';

});
