/**
 * Created by gs on 22.05.2017.
 */
webApp.factory('userService',['$http', '$q', function ($http, $q) {
    return{
        getUser: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: '/api//user/' + userId
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        getSimpleUser: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: '/api//user/simple/' + userId
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}]);