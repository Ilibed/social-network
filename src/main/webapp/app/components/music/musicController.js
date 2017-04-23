webApp.controller("musicController",['$scope', 'ngAudio', function ($scope, ngAudio) {
    $scope.sounds = [
        {
            name : 'Big city life',
            playing : false,
            sound : ngAudio.load("assets/muz/Eminem – Big city life.mp3")
        },
        {
            name : 'Bitch',
            playing : false,
            sound : ngAudio.load("assets/muz/Eminem – Bitch (feat. D12 & Dina Ray).mp3")
        },
        {
            name : 'Brainless',
            playing : false,
            sound : ngAudio.load("assets/muz/Eminem – Brainless.mp3")
        }
    ];
    $scope.currentMusic = $scope.sounds[0];
    $scope.currentMusicIndex = 0;

    var endedCallback = function () {
        $scope.playNext();
    };

    for (var i = 0; i < $scope.sounds.length; i++){
        $scope.sounds[i].sound.setEndedCallback(endedCallback);
    }

    $scope.playSound = function (music, index) {
        if ($scope.currentMusic != music){
            $scope.currentMusicIndex = index;
            if ("sound" in $scope.currentMusic){
                $scope.currentMusic.sound.stop();
                $scope.currentMusic.playing = false;
            }

            $scope.currentMusic = music;
        }

        if (!music.playing){
            music.sound.play();
        }
        else {
            music.sound.pause();
        }

        music.playing = !music.playing;
    };

    $scope.muteVolume = function (music) {
        music.sound.muting = !music.sound.muting;
    };

    $scope.changeVolume = function (music) {
        if (music.sound.muting){
            music.sound.muting = false;
        }
    };

    $scope.playNext = function () {
        $scope.playSound($scope.sounds[($scope.currentMusicIndex + 1) % $scope.sounds.length], ($scope.currentMusicIndex + 1) % $scope.sounds.length) ;
    };

    $scope.playPrev = function () {
        if ($scope.currentMusicIndex > 0){
            $scope.playSound($scope.sounds[($scope.currentMusicIndex - 1) % $scope.sounds.length], ($scope.currentMusicIndex - 1) % $scope.sounds.length) ;
        }
    };

    $scope.repeatMusic = function (music) {
        if (music.sound.loop === true){
            music.sound.loop = 0;
        }
        else {
            music.sound.loop = true;
        }
    }
}]);

