app.service('RatingService', function($http){

    this.getMyLastestRatings = function(callback, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/rating/mylastestratings/' + token).
		then(callback, error);
	};
    
    this.saveRating = function(serviceProvider, note, comments, success, error) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/rating/save', {provider: serviceProvider.email, service: serviceProvider.service, note: note, comments: comments, token: token}).
		then(success, error);
    };
});