/**
 * Created by gs on 07.05.2017.
 */
webApp.factory('messageService',['$http', '$q', function ($http, $q) {
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
            socket = new WebSocket("ws://localhost:8080/message");

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
        getAllMessagesForUser: function (userId) {
            var deferred = $q.defer();
            $http({
                method: 'GET', 
                url: '/api/get/messages',
                params: {userId: userId}
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