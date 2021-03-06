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
    
    $routeProvider.when("/upload-photo/:photo?", {
		controller : "UploadController",
		templateUrl: "views/upload.html"
	});

	$routeProvider.when("/service", {
		controller : "ServiceController",
		templateUrl: "views/service.html"
	});

	$routeProvider.when("/config", {
		controller : "ConfigController",
		templateUrl : "views/config.html"
	});
    
    $routeProvider.when("/change-password", {
		controller : "PasswordController",
		templateUrl : "views/change-password.html"
	});
    
	//$routeProvider.otherwise( {redirectTo: "/home"});

});