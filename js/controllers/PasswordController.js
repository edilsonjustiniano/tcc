app.controller('PasswordController', ['$scope', 'SessionService', 'CityService', 'AccountService',
 function ($scope, SessionService, CityService, AccountService) {

	$scope.msg = {};
	$scope.msg.msg = '';
	$scope.msg.type = '';
    $scope.user = {};
	
    $scope.changePassword = function() {
        if ($scope.user.newPassword !== $scope.user.repeatedPassword) {
            $scope.msg.type = 'ERROR';
            $scope.msg.msg = 'Campos nova senha e repita a senha devem ser iguais';
            return;
        } 
        
        AccountService.changePassword(function(callback) {
            var data = callback.data;
            if (data.success) {
                $scope.msg.type = 'SUCCESS';
                $scope.msg.msg = 'Successo ao editar senha';
                window.localStorage['token'] = data.token;
            } else {
                $scope.msg.type = 'ERROR';
                $scope.msg.msg = data.message;
            }
        }, $scope.error, $scope.user);
    };
     
    $scope.error = function (error){
		console.log(error);

	};

}]);