//App
var TCCApp = angular.module('TCCApp', []);

TCCApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

TCCApp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);

TCCApp.controller('CreateAccountPhoto', ['$scope', 'fileUpload', function($scope, fileUpload){
    
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "http://localhost:8080/WebService/person/savePhoto";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
    
}]);

/*
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
*/


