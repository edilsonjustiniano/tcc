//App
var TCCApp = angular.module('TCCApp', []);


TCCApp.service('CreateAccountPhotoService', function($http){
	
	this.uploadFile = function(fd, callback) {
		$http.post('http://localhost:8080/WebService/person/createAccountPhoto', fd, {
			withCredentials: true,
	        //headers: {'Content-Type': undefined },
	        transformRequest: angular.identity
		}).success(callback);
	};

});


TCCApp.controller('CreateAccountPhoto', function($scope, CreateAccountPhotoService){
	
	//Create the attributes
	$scope.photo = '';

	$scope.savePhoto = function() {
		console.log('teste');
	};

	$scope.uploadFile = function(files) {
	    var fd = new FormData();
	    //Take the first selected file
	    fd.append("file", files[0]);

	    CreateAccountPhotoService.uploadFile(fd, function(callback) {
	    	console.log('Sucesso callback');
	    });
	    // $http.post(uploadUrl, fd, {
	    //     withCredentials: true,
	    //     headers: {'Content-Type': undefined },
	    //     transformRequest: angular.identity
    	// });
	  	//.success( ...all right!... ).error( ..damn!... );

};

});
