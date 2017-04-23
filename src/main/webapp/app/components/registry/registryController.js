webApp.controller('registryController', ['$scope', '$rootScope', '$location', 'registerService', control]);

function control($scope, $rootScope, $location, registerService) {
    $scope.register = function () {
        var user = registerService.createRegisterInf($scope);
        var promiseObj=registerService.doRegister(user);
        promiseObj.then(function(value) {
                $rootScope.loggedInUser = true;
                $rootScope.userInfo = value;
                $location.path("/");
            },
            function (value) {
                registerService.performScope($scope, value);
        });
    };
}