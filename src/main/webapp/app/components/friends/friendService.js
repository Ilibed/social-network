/**
 * Created by gs on 24.05.2017.
 */
webApp.factory('friendService',['$http', '$q', function ($http, $q) {
    return{
        getFriends: function () {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: '/api/get/friends'
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        getAllPeoples: function () {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: '/api/get/users'
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        addToFriend: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/api/friend/add/' + userId,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        removeFromFriend: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/api/friend/remove/' + userId,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            then(function(response) {
                    deferred.resolve(response.headers);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}]);