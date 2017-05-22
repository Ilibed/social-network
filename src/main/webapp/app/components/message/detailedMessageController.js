/**
 * Created by gs on 07.05.2017.
 */
webApp.controller("detailedMessageController",['$scope', '$rootScope', '$routeParams', 'messageService', function ($scope, $rootScope, $routeParams, messageService) {
    $scope.message = {
        'subject' : '',
        'senderId' : $rootScope.userInfo.id,
        'receiverId' : {},
        'sendDate' : ''
    };
    $scope.userId = {};
    $scope.messages = [];

    $scope.$on("$routeChangeSuccess", function () {
        var id = $routeParams["id"];
        if(id!=='undefined'){
            $scope.userId = id;
            $scope.message.receiverId = id;
        }
    });
    
    $scope.sendMessage = function () {
        var date = new Date();
        var month = date.getMonth() + 1;
        $scope.message.sendDate = date.getFullYear() + "-" + month + "-" + date.getDate() + " " +
            date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        console.dir($scope.message);
        messageService.send($scope.message);
        $scope.message.subject = '';
    }
}]);