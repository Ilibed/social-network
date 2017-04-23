webApp.factory('registerService',['$http', '$q', '$filter', register]);

function register($http, $q, $filter) {
    return{
        doRegister: function (regInf) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/registry',
                headers: {
                    'Content-Type': 'json'
                },
                data: regInf
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        createRegisterInf: function ($scope) {
            var regInf = {};
            regInf.userName = $scope.username;
            regInf.password = $scope.password;
            regInf.name = $scope.name;
            regInf.surname = $scope.surname;
            regInf.email = $scope.email;
            regInf.sex = $scope.sex;
            regInf.birthday = $filter('date')($scope.birthday, "yyyy-MM-dd");

            return regInf;
        },
        performScope: function ($scope, value) {
            $scope.username = value.username;
            $scope.password = "";
            $scope.repeatPassword = "";
            $scope.name = value.name;
            $scope.surname = value.name;
            $scope.email = value.email;
        }
    }
}