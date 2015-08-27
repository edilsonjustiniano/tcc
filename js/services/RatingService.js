app.service('RatingService', function($http){

    this.getMyLastestRatings = function(callback, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/rating/mylastestratings/' + token).
		then(callback, error);
	};
});