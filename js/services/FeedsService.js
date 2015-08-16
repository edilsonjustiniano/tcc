app.service('FeedsService', function($http){
    
    this.getFeedLastPartnership = function(callback) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/feed/lastPartnership', {token: token}).
		success(callback);
    };
    
    this.getFeedLastestRatings = function(callback) {
        var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/feed/lastestRatings', {token: token}).
		success(callback);
    };
});