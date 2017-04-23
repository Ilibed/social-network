webApp.controller("loginController", ['$scope', '$location', '$rootScope', 'loginService', function($scope, $location, $rootScope, loginService){
    $scope.loginUser = function () {
        var promiseObj = loginService.doLogin($scope.username, $scope.password);
        promiseObj.then(function(value) {
            var userPromiseObj = loginService.getUser();
            userPromiseObj.then(function (value) {
                $rootScope.userInfo = value;
                $rootScope.loggedInUser = true;
                $location.path('/');
            },
            function (value) {
                //something wrong server problem
            });
        },
        function (value) {
            $scope.username = "";
            $scope.password = "";
        });
    };
}]);