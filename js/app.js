var app = angular.module("TCCApp", ['ngRoute']);

app.config(function($routeProvider) {
	
	$routeProvider.when("/home", {
		controller : "HomeController",
		templateUrl: "views/home.html"
	});

	$routeProvider.when("/addService", {
		controller : "AddServiceController",
		templateUrl: "views/addService.html"
	});

	$routeProvider.otherwise( {redirectTo: "/home"});

});