app.service('RatingService', function($http){

    this.getMyLastestRatings = function(callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/rating/myLastestRatings', {token: token}).
		success(callback);
	};
});