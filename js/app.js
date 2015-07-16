var app = angular.module("TCCApp", ['ngRoute']);

app.config(function($routeProvider) {
	
	$routeProvider.when("/home", {
		controller : "HomeController",
		templateUrl: "views/home.html"
	});

	$routeProvider.when("/partner-network", {
		controller : "PartnerNetworkController",
		templateUrl: "views/partner-network.html"
	});

	$routeProvider.otherwise( {redirectTo: "/home"});

});