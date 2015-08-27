app.service('FeedsService', function($http){
    
    this.getFeedLastestPartnership = function(callback, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/feed/lastestpartnership/' + token).
		then(callback, error);
    };
    
    this.getFeedLastestRatings = function(callback, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/feed/lastestratings/' + token).
		then(callback, error);
    };
});