webApp.controller("loginController", ['$scope', '$location', 'loginService', function($scope, $location, loginService){
    $scope.loginUser = function () {
        var promiseObj = loginService.doLogin($scope.username, $scope.password);
        promiseObj.then(function(value) {
            $rootScope.userInfo = value;
            $rootScope.loggedInUser = true;
            $location.path('/');
        },
        function (value) {
            $scope.username = "";
            $scope.password = "";
        });
    };
}]);