/**
 * Created by gs on 07.05.2017.
 */
webApp.controller("mainMessageController",['$scope', '$rootScope', '$location', 'messageService', function ($scope, $rootScope, $location, messageService) {
    $scope.$on("$routeChangeSuccess", function () {
        if ($rootScope.messageList == null){
            messageService.initSocket();
            messageService.setMessageCallbacks(pushNewMessage, handleErrorMessage);
            var promiseObj=messageService.getAllMessagesForUser($rootScope.userInfo.id);
            promiseObj.then(function(value) {
                    $rootScope.messageList = value;
                    console.dir($rootScope.messageList);
                },
                function (value) {
                    //can be logging
                    console.dir(value);
                }
            );
        }
    });

    var pushNewMessage = function (newMessage) {
        var userId = newMessage.receiverId == $rootScope.userInfo.id ? newMessage.senderId : newMessage.receiverId;
        $rootScope.messageList[userId].messageList.push(newMessage);
    };

    var handleErrorMessage = function (newMessage) {
        console.dir(newMessage);
    };

    $scope.viewDetailedMessage = function (lastMessage) {
        var userId = lastMessage.receiverId == $rootScope.userInfo.id ? lastMessage.senderId : lastMessage.receiverId;
        $location.path("messages/" + userId);
    }
}]);