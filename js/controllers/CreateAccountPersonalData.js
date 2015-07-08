//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('CreateAccountService', function($http){
	
	/* function to check CPF */
	this.isValidCPF = function(cpf) {
		var Soma; 
		var Resto; 
		Soma = 0; 

		if (cpf == "00000000000") 
			return false;

		for (i=1; i<=9; i++) 
			Soma = Soma + parseInt(cpf.substring(i-1, i)) * (11 - i);

		Resto = (Soma * 10) % 11;

		if ((Resto == 10) || (Resto == 11)) 
		 	Resto = 0; 

		if (Resto != parseInt(cpf.substring(9, 10)) ) 
			return false; 

		Soma = 0; 
		for (i = 1; i <= 10; i++) 
			Soma = Soma + parseInt(cpf.substring(i-1, i)) * (12 - i); 

		Resto = (Soma * 10) % 11; 

		if ((Resto == 10) || (Resto == 11)) 
			Resto = 0; 
		
		if (Resto != parseInt(cpf.substring(10, 11) ) ) 
			return false; 

		return true;
	};

	/* function to check CNPJ */
	this.isValidCNPJ = function(cnpj) {

		cnpj = cnpj.replace(/[^\d]+/g,'');

    	if(cnpj == '') return false;

    	if (cnpj.length != 14)
        	return false;

    	// LINHA 10 - Elimina CNPJs invalidos conhecidos
    	if (cnpj == "00000000000000" || 
        	cnpj == "11111111111111" || 
        	cnpj == "22222222222222" || 
        	cnpj == "33333333333333" || 
        	cnpj == "44444444444444" || 
        	cnpj == "55555555555555" || 
        	cnpj == "66666666666666" || 
        	cnpj == "77777777777777" || 
        	cnpj == "88888888888888" || 
        	cnpj == "99999999999999")
        	return false; // LINHA 21

    	// Valida DVs LINHA 23 -
    	tamanho = cnpj.length - 2
    	numeros = cnpj.substring(0,tamanho);
    	digitos = cnpj.substring(tamanho);
    	soma = 0;
    	pos = tamanho - 7;
    	for (i = tamanho; i >= 1; i--) {
    	  soma += numeros.charAt(tamanho - i) * pos--;
    	  if (pos < 2)
    	        pos = 9;
    	}
    	resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    	if (resultado != digitos.charAt(0))
    	    return false;

    	tamanho = tamanho + 1;
    	numeros = cnpj.substring(0,tamanho);
    	soma = 0;
    	pos = tamanho - 7;
    	for (i = tamanho; i >= 1; i--) {
    	  soma += numeros.charAt(tamanho - i) * pos--;
    	  if (pos < 2)
    	        pos = 9;
    	}
    	resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    	if (resultado != digitos.charAt(1))
    	      return false;

    	return true;
	};

	this.createAccount = function(person, callback) {

		$http.post('http://localhost:8080/WebService/person/createAccountPersonalData', JSON.stringify(person)).
		success(callback);
		// error(function(data, status, headers, config) {
		//     // called asynchronously if an error occurs
		//     // or server returns response with an error status.
		//     console.log('error');
		//     //return undefined;
		// });
	};

});

TCCApp.controller('CreateAccountPersonalData', function($scope, CreateAccountService){
	
	//Create the attributes
	$scope.name = '';
	$scope.email = '';
	$scope.password = '';
	$scope.confirmPassword = '';
	$scope.typeOfAccount = 'SERVICE_PROVIDER';
	$scope.typeOfPerson = 'PHISIC';
	$scope.cnpj = '';
	$scope.cpf = '';
	$scope.gender = 'F';
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';

	/* Function to create a new account */
	$scope.createAccount = function() {

		/* Check if the password and confirm password have the same content */
		if ($scope.password != $scope.confirmPassword) {
			$scope.msg.type = 'ERROR';
			$scope.msg.msg = 'O campo senha e confirmar senha devem possuir o mesmo conteúdo!';
			return;
		}

		var person = new Person();
		/* Check if the type of Person is equal to PHISIC, In this case, we need to validate the CPF */
		if ($scope.typeOfPerson == 'PHISIC') {
			
			/* Check if the CPF is valid */
			if ($scope.cpf == '') {
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = 'Por favor, informe um CPF antes de prosseguir!';
				return;
			}

			// if (!CreateAccountService.isValidCPF($scope.cpf)) {
			// 	$scope.msg.type = 'ERROR';
			// 	$scope.msg.msg = 'CPF inválido, por favor verifique o CPF informado!';
			// 	return;
			
			// } else {
				/* Continue with the new cadastre */
				person.setCPF($scope.cpf);
				person.setGender($scope.gender);
			// }
		
		} else { /* Juridic Person */

			/* Check if CNPJ is valid */
			if ($scope.cnpj == '') {
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = 'Por favor, informe um CNPJ antes de prosseguir!';
				return;
			}
			
			// if (!CreateAccountService.isValidCNPJ($scope.cnpj)) {
			// 	$scope.msg.type = 'ERROR';
			// 	$scope.msg.msg = 'CNPJ inválido, por favor verifique o CNPJ informado!';
			// 	return;
			
			// } else {
				/* Continue with the new cadastre */
				person.setCNPJ($scope.cnpj);
			// }
		}

		person.setName($scope.name);
		person.setEmail($scope.email);
		person.setPassword($scope.password);
		person.setTypeOfAccount($scope.typeOfAccount);
		person.setTypeOfPerson($scope.typeOfPerson);

	
		var data = CreateAccountService.createAccount(person, function(callback) {
			/*
			$scope.name = '';
			$scope.email = '';
			$scope.password = '';
			$scope.confirmPassword = '';
			$scope.typeOfAccount = 'SERVICE_PROVIDER';
			$scope.typeOfPerson = 'PHISIC';
			$scope.cnpj = '';
			$scope.cpf = '';
			$scope.gender = 'F';
			*/
			/* Redirect to page create account work data */
			if (!callback.data.success) {
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.data.mesage;
			} else {
				$scope.msg.type = 'SUCCESS';
				$scope.msg.msg = callback.data.mesage;
			}
		}); 
		

			//return {"cliente":[{"id":"1","name":"Edilson"},{"id":"2","name":"Josy"}]}
			//$scope.clientes = data.cliente; //cliente is the root element in JSON return
		// });
	};
});
