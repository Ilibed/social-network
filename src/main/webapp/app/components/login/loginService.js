webApp.factory('loginService',['$http', '$q', function ($http, $q) {
    return{
        doLogin: function (username, password) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/login',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
                },
                data: 'username=' + username + '&password=' + password
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}]);

