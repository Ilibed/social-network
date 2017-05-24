/**
 * Created by gs on 22.05.2017.
 */
webApp.factory('postService',['$http', '$q', function ($http, $q) {
    return{
        getPosts: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: '/api/get/posts',
                params: {userId: userId}
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        deletePost: function (postId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/api/delete/post/' + postId,
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