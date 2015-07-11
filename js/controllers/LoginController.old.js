//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('LoginService', function($http){
	
	var states = {};

	this.getUFs = function(data) {
		var url = 'http://localhost:8080/WebService/clientes/listarUF';

		// $http.get('http://localhost:8080/RestFull/clientes/listarTodos').success(function(data) {
		// 	console.log('data:' + data);
		// }).
		$http.get('http://localhost:8080/WebService/clientes/listarUF').success(data);
		// error(function(data) {
		// 	console.log('error: ' + data);			
		// })
	};

	this.getClientes = function(data) {
		// var url = 'http://localhost:8080/RestFull/clientes/listarTodos';

		// $http.get('http://localhost:8080/RestFull/clientes/listarTodos').success(function(data) {
		// 	console.log('data:' + data);
		// }).
		$http.get('http://localhost:8080/WebService/clientes/listarTodos').success(data);
		// error(function(data) {
		// 	console.log('error: ' + data);			
		// })
	};

	/** COLOCAR O .gitignore antes de fazer os commits da parte escrita **/
	this.addState = function(id, name, abbreviation) {
		$http.post('http://localhost:8080/WebService/clientes/addUF', {id: id, name: name, abbreviation: abbreviation}).
		  success(function(data, status, headers, config) {
		    // this callback will be called asynchronously
		    // when the response is available
		    console.log('Success');
		  }).
		  error(function(data, status, headers, config) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		    console.log('error');
		  });
		/*
		$http({
		   method: 'POST',
		   url: 'http://localhost:8080/RestFull/clientes/addUF',
		   headers: {
		       ContentType: 'application/json'
		   },
		   data: {
		       id : id,
		       name: name,
		       abbreviation: abbreviation
		   }
		});
		*/
	};
});


TCCApp.controller('LoginController', function LoginController($scope, LoginService) {

	//UF Fields
	$scope.id = '';
	$scope.name = '';
	$scope.abbreviation = '';

	$scope.clientes = {};
	$scope.states = [];

	$scope.Login = function() {
		console.log('teste');
		LoginService.getUFs(function(data){
			//return {"cliente":[{"id":"1","name":"Edilson"},{"id":"2","name":"Josy"}]}
			//$scope.states = data.data; //cliente is the root element in JSON return
			var array = data.data;
			array.forEach(function(iter){
				$scope.states.push({id: iter[0], name: iter[1], abbreviation: iter[2]});
				// LoginService.addStateIntoCollection(iter[0], iter[1], iter[2]);
				console.log('iter: ' + iter);
			});
			// console.log(data);
			// for (var i = 0; i < data.length; i++) {
			// 	$scope
			// };
		});	
	};


	//Get All Clientes
	$scope.addState = function() {

		LoginService.addState($scope.id, $scope.name, $scope.abbreviation);
		//Reset form
		$scope.id = '';
		$scope.name = '';
		$scope.abbreviation = '';
	};

	$scope.GetAllClients = function() {
		LoginService.getClientes(function(data){
			//return {"cliente":[{"id":"1","name":"Edilson"},{"id":"2","name":"Josy"}]}
			$scope.clientes = data.cliente; //cliente is the root element in JSON return
		});
	};
	

});