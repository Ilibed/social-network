/**
 * Created by gs on 22.05.2017.
 */
webApp.factory('postService',['$http', '$q', function ($http, $q) {
    var socket;
    var messageSuccessCallback;
    var messageErrorCallback;

    function showMessage(message) {
        var newMessage = JSON.parse(message);
        if (newMessage.id != -1){
            messageSuccessCallback(newMessage);
        }
        else {
            messageErrorCallback(newMessage);
        }
    }

    return{
        initSocket: function () {
            socket = new WebSocket("ws://localhost:8080/user");

            socket.onmessage = function(event) {
                var incomingMessage = event.data;
                showMessage(incomingMessage);
            };
        },
        setMessageCallbacks: function (successCallback, errorCallback) {
            messageSuccessCallback = successCallback;
            messageErrorCallback = errorCallback;
        },
        send: function (message) {
            var outMessage = JSON.stringify(message);
            socket.send(outMessage);
        },
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