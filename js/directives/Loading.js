/*angular.module('directive.loading', [])
    .directive('loading',   ['$http' ,function ($http) {
        return {
            restrict: 'A',
            link: function (scope, elm, attrs) {
                
                scope.isLoading = function () {
                    return $http.pendingRequests.length > 0;
                };

                scope.$watch(scope.isLoading, function (v) {
                    if(v) {
                        elm.show();
                    } else {
                        elm.hide();
                    }
                });
            }
        };

    }]);*/
app.directive('loading', function () {
      return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading"><img src="./image/icon/ajax-loader.gif"/></div>',
        link: function (scope, element, attr) {
              scope.$watch('loading', function (val) {
                  if (val)
                      $(element).show();
                  else
                      $(element).hide();
              });
        }
      }
  });