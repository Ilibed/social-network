/**
 * Created by gs on 07.05.2017.
 */
webApp.controller("detailedMessageController",['$scope', '$rootScope', 'messageService', function ($scope, $rootScope, messageService) {
    $scope.message = {
        'body' : '',
        'sender' : $rootScope.userInfo.username,
        'time' : ''
    };
    $scope.messageList = [];
    messageService.initSocket();
    
    var pushNewMessage = function (newMessage) {
        $scope.messageList.push(newMessage);
    };

    var handleErrorMessage = function (newMessage) {
        console.dir(newMessage);
    };
    
    messageService.setMessageCallbacks(pushNewMessage, handleErrorMessage);
    
    $scope.sendMessage = function () {
        var date = new Date();
        $scope.message.time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        messageService.send($scope.message);
        $scope.message.body = '';
    }
}]);