/**
 * Created by gs on 07.05.2017.
 */
webApp.controller("mainMessageController",['$scope', '$rootScope', '$location', 'messageService', 'userService', function ($scope, $rootScope, $location, messageService, userService) {
    $scope.$on("$routeChangeSuccess", function () {
        if (!$rootScope.listInit){
            $rootScope.listInit = true;
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
        if (userId in Object.keys($rootScope.messageList)){
            $rootScope.messageList[userId].messageList.push(newMessage);
        }
        else {
            $rootScope.messageList[userId] = {
                messageList: [],
                simpleUser: {}
            };
            $rootScope.messageList[userId].messageList.push(newMessage);
            var promiseObj=userService.getSimpleUser(userId);
            promiseObj.then(function(value) {
                    $rootScope.messageList[userId].simpleUser = value;
                },
                function (value) {
                    //can be logging
                    console.dir(value);
                }
            );
        }

    };

    var handleErrorMessage = function (newMessage) {
        console.dir(newMessage);
    };

    $scope.viewDetailedMessage = function (lastMessage) {
        var userId = lastMessage.receiverId == $rootScope.userInfo.id ? lastMessage.senderId : lastMessage.receiverId;
        $location.path("messages/" + userId);
    }
}]);