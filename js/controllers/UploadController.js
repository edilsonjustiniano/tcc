app.controller('UploadController', function ($scope, $routeParams, UploadService) {
    
    $scope.photoName = $routeParams.photo;
    console.log($scope.photoName);
    
    $scope.setPhoto = function() {
        
        UploadService.setPhotoToUser($scope.photoName, function(callback) {
            if(!callback.success) {
                window.localStorage['token'] = null;
				window.sessionStorage.setItem('typeOfAccount', null);
				window.location.href = 'index.html';
            } else {
                window.localStorage['token'] = callback.token;
                window.location.href = "#/home";
            }
        });
    };
    
    $scope.setPhoto();
});