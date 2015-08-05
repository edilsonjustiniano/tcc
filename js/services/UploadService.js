app.service('UploadService', function($http){
	
	this.setPhotoToUser = function(photo, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/person/setPhoto', {photo: photo, token: token}).
		success(callback);
	};
});