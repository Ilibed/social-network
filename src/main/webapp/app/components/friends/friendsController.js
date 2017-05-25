webApp.controller('friendsController', ['$scope', '$rootScope', 'friendService', 'messageService', function($scope, $rootScope, friendService, messageService){
    $scope.friends = [];
    $scope.peoples = [];
    $scope.user = {};
    $scope.message = {
        'subject' : '',
        'senderId' : $rootScope.userInfo.id,
        'receiverId' : {},
        'sendDate' : ''
    };
    messageService.initSocket();
    if (!$rootScope.listInit){
        messageService.setMessageCallbacks(function (newMessage) {

            },
            function (newMessage) {

            });
    }

    var promiseObj=friendService.getFriends();
    promiseObj.then(function(value) {
            $scope.friends = value;
        },
        function (value) {
            //can be logging
            console.dir(value);
        }
    );

    var promisedObj=friendService.getAllPeoples();
    promisedObj.then(function(value) {
            $scope.peoples = value;
        },
        function (value) {
            //can be logging
            console.dir(value);
        }
    );

    $scope.addToFriend = function (user) {
        var index = $scope.peoples.indexOf(user);
        var promiseObj=friendService.addToFriend(user.id);
        promiseObj.then(function(value) {
            console.dir(value);
                $scope.friends.push(value);
                $scope.peoples.splice(index, 1);
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.removeFriend = function (user) {
        var index = $scope.friends.indexOf(user);
        var promiseObj=friendService.removeFromFriend(user.id);
        promiseObj.then(function(value) {
                $scope.friends.splice(index, 1);
                $scope.peoples.push(user)
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.showNewMessage = function (user) {
        $scope.user = user;
        commonModule.showMessageModal();
    };

    $scope.sendMessage = function () {
        var date = new Date();
        var month = date.getMonth() + 1;
        $scope.message.sendDate = date.getFullYear() + "-" + month + "-" + date.getDate() + " " +
            date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        $scope.message.receiverId = $scope.user.id;
        console.dir($scope.message);
        messageService.send($scope.message);
        $scope.message.subject = '';
    }
}]);