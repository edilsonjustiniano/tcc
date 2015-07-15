//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.service('ServiceService', function($http){
	
	this.service = function(callback) {
		$http.post('http://localhost:8080/WebService/service/getServices').
		success(callback);
	};
});


TCCApp.controller('ServiceController', function($scope, ServiceService) {

	//UF Fields
	$scope.nome = '';
	$scope.services =[];
	$scope.services [0] = {name:"pintor"};
	$scope.services [1] = {name:"jardineiro"};
	$scope.services [2] = {name:"pintor"};
	
	$scope.msg = {}; /* Error or success mesage */
	$scope.msg.type = '';
	$scope.msg.msg = '';
	
	$scope.getServices = function() {

		ServiceService.service(function (callback){
			if (!callback.success) {
				$scope.msg.type = 'ERROR';
				$scope.msg.msg = callback.mesage;
				$scope.loading = false;
			} else {
				var array = callback.data;
				for (var i = 0; i < array.length; i++) {
					$scope.services[i] = {name: array[i].toString()};
				};
			}
		});	
	};

	$scope.getServices();
});