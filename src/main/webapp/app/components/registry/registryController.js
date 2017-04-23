webApp.controller('registryController', ['$scope', '$rootScope', '$location', 'registerService', control]);

function control($scope, $rootScope, $location, registerService) {
    $scope.register = function () {
        var user = registerService.createRegisterInf($scope);
        var promiseObj=registerService.doRegister(user);
        promiseObj.then(function(value) {
            if (value.id < 0){
                registerService.performScope($scope, value);
            }
            else {
                $rootScope.loggedInUser = true;
                $rootScope.userInfo = value;
                $location.path("#/");
            }
        });
    };
}