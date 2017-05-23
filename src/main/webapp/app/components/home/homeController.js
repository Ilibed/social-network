webApp.controller("homeController", ['$scope', '$routeParams', '$rootScope', 'userService', 'postService', 'imageUploadService', function($scope, $routeParams, $rootScope, userService, postService, imageUploadService){
    $scope.file = {};
    $scope.text = "";
    $scope.posts = [];

    $scope.$on("$routeChangeSuccess", function () {
        var id = $routeParams["id"];
        if(id!=='undefined'){
            var promiseObj=userService.getUser(id);
            promiseObj.then(function(value) {
                    $scope.user = value;
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
}]);