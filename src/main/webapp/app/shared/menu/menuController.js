/**
 * Created by gs on 07.05.2017.
 */
webApp.controller("menuController",['$scope', function ($scope) {
    $scope.showMusic = false;
    $scope.changeShow = function () {
        if ($scope.showMusic){
            commonModule.hideMusicDropdown();
        }
        else {
            commonModule.showMusicDropdown();
        }

        $scope.showMusic = !$scope.showMusic;
    }
}]);