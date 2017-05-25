webApp.controller("homeController", ['$scope', '$routeParams', '$rootScope', 'userService', 'postService', 'imageUploadService', 'messageService', 'printService', function($scope, $routeParams, $rootScope, userService, postService, imageUploadService, messageService, printService){
    $scope.file = {};
    $scope.avatarFile = {};
    $scope.text = "";
    $scope.posts = [];
    $scope.names = ["activity", "etc"];
    $scope.printUrl = "";
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

    $scope.$on("$routeChangeSuccess", function () {
        var id = $routeParams["id"];
        if(id!=='undefined'){
            var promiseObj=userService.getUser(id);
            promiseObj.then(function(value) {
                    $scope.user = value;
                    $scope.message.receiverId = value.id;
                    var promiseObj=postService.getPosts(id);
                    promiseObj.then(function(value) {
                            $scope.posts = value;
                        },
                        function (value) {
                            //can be logging
                            console.dir(value);
                        }
                    );
                },
                function (value) {
                    //can be logging
                    console.dir(value);
                }
            );
        }
    });

    $scope.setNewFile = function (file) {
        $scope.file = file;
    };

    $scope.setNewAvatarFile = function (file) {
        $scope.avatarFile = file;
    };

    $scope.addPost = function () {
        var date = new Date();
        var month = date.getMonth() + 1;
        var sendDate = date.getFullYear() + "-" + month + "-" + date.getDate() + " " +
            date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        var promiseObj=imageUploadService.uploadFile($scope.file, $scope.text, sendDate, $rootScope.userInfo.id);
        promiseObj.then(function(value) {
                $scope.posts.push(value)
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.editProfile = function () {
        var promiseObj=imageUploadService.editUserInfo($scope.avatarFile, $scope.name, $scope.surname, $scope.country, $scope.city);
        promiseObj.then(function(value) {
                $rootScope.userInfo = value;
                var promiseObj=userService.getUser($scope.user.id);
                promiseObj.then(function(value) {
                        $scope.user = value;
                    },
                    function (value) {
                        //can be logging
                        console.dir(value);
                    }
                );
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.deletePost = function (post) {
        var index = $scope.posts.indexOf(post);
        var promiseObj=postService.deletePost(post.postProjection.id);
        promiseObj.then(function(value) {
                $scope.posts.splice(index, 1);
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.sendMessage = function () {
        var date = new Date();
        var month = date.getMonth() + 1;
        $scope.message.sendDate = date.getFullYear() + "-" + month + "-" + date.getDate() + " " +
            date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        console.dir($scope.message);
        messageService.send($scope.message);
        $scope.message.subject = '';
    };

    $scope.printStat = function () {
        var promiseObj=printService.printActivity($scope.selectedName, $scope.user.id);
        promiseObj.then(function(value) {
                $scope.printUrl = value.path;
            },
            function (value) {
                //can be logging
                console.dir(value);
            }
        );
    };

    $scope.closeModal = function () {
        $scope.printUrl = "";
        $scope.selectedName = "";
        commonModule.hidePrintModal();
    }
}]);