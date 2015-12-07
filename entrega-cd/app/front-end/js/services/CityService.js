app.service('CityService', ['$http', function($http){
    
    this.getAllStates = function(success, error) {
        $http.get('http://localhost:8080/WebService/uf').
        then(success, error);
    };

    this.getAllCitiesByState = function(state, success, error) {
        $http.get('http://localhost:8080/WebService/city/cities/' + state).
        then(success, error);
    };
}]);