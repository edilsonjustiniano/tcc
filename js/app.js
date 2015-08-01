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

	$routeProvider.when("/partner-profile/:partner?", {
		controller : "PartnerProfileController",
		templateUrl: "views/partner-profile.html"
	});
    
    $routeProvider.when("/service-provider-profile/:provider?", {
		controller : "ServiceProviderProfileController",
		templateUrl: "views/service-provider-profile.html"
	});
    
    $routeProvider.when("/search-service-providers", {
		controller : "SearchServiceProviderController",
		templateUrl: "views/search-service-providers.html"
	});
    
	$routeProvider.otherwise( {redirectTo: "/home"});

});