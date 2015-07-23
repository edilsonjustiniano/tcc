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
    
//    $routeProvider.when("/upload", {
//		controller : "UploadController",
//		templateUrl: "views/upload.html"
//	});
	$routeProvider.otherwise( {redirectTo: "/home"});

});