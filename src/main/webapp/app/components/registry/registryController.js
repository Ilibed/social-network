webApp.controller('registryController', ['$scope', '$rootScope', '$location', 'registerService', 'loginService', control]);

function control($scope, $rootScope, $location, registerService, loginService) {
    $scope.register = function () {
        var user = registerService.createRegisterInf($scope);
        var promiseObj=registerService.doRegister(user);
        promiseObj.then(function(value) {
                var promiseObj = loginService.doLogin($scope.email, $scope.password);
                promiseObj.then(function(value) {
                        var userPromiseObj = loginService.getUser();
                        userPromiseObj.then(function (value) {
                                $rootScope.userInfo = value;
                                $rootScope.loggedInUser = true;
                                $location.path('/user/' + $rootScope.userInfo.id);
                            },
                            function (value) {
                                //something wrong server problem
                            });
                    },
                    function (value) {
                        $scope.username = "";
                        $scope.password = "";
                    });
            },
            function (value) {
                registerService.performScope($scope, value);
        });
    };
}