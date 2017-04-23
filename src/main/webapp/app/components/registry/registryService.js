webApp.factory('registerService',['$http', '$q', '$filter', register]);

function register($http, $q, $filter) {
    return{
        doRegister: function (regInf) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/api/user',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: regInf
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.data);
                });

            return deferred.promise;
        },
        createRegisterInf: function ($scope) {
            var regInf = {};
            regInf.password = $scope.password;
            regInf.firstName = $scope.name;
            regInf.lastName = $scope.surname;
            regInf.email = $scope.email;
            regInf.country = $scope.country;
            regInf.city = $scope.city;
            regInf.birthday = $filter('date')($scope.birthday, "yyyy-MM-dd");

            return regInf;
        },
        performScope: function ($scope, value) {
            $scope.password = "";
            $scope.repeatPassword = "";
            $scope.name = value.firstName;
            $scope.surname = value.lastName;
            $scope.email = value.email;
            $scope.country = value.country;
            $scope.city = value.city;
        }
    }
}