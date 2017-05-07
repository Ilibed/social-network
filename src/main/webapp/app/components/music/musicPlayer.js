/**
 * Created by gs on 29.04.2017.
 */
webApp.directive('musicPlayer', function($rootScope, ngAudio, musicLoadService) {
    return {
        restrict: 'AEC',
        replace: true,
        templateUrl: 'views/components/music/playerTemplate.html',
        link: function(scope, elem, attrs) {
            if ($rootScope.sounds == null){
                var promiseObj = musicLoadService.getSounds();
                promiseObj.then(function(value) {
                        $rootScope.sounds = value;
                        $rootScope.currentMusic = $rootScope.sounds[0];
                        $rootScope.currentMusicIndex = 0;

                        for (var i = 0; i < $rootScope.sounds.length; i++){
                            $rootScope.sounds[i].playing = false;
                            $rootScope.sounds[i].loaded = false;
                            $rootScope.sounds[i].sound = {};
                        }
                    },
                    function (value) {

                    }
                );
            }

            scope.file = {};

            var endedCallback = function () {
                scope.playNext();
            };

            scope.playSound = function (music, index) {
                if (music.loaded == false){
                    music.sound = ngAudio.load(music.path);
                    music.sound.setEndedCallback(endedCallback);
                    music.loaded = true;
                }

                if ($rootScope.currentMusic != music){
                    $rootScope.currentMusicIndex = index;
                    if ($rootScope.currentMusic.loaded){
                        $rootScope.currentMusic.sound.stop();
                        $rootScope.currentMusic.playing = false;
                    }

                    $rootScope.currentMusic = music;
                }

                if (!music.playing){
                    music.sound.play();
                }
                else {
                    music.sound.pause();
                }

                music.playing = !music.playing;
            };

            scope.muteVolume = function (music) {
                music.sound.muting = !music.sound.muting;
            };

            scope.changeVolume = function (music) {
                if (music.sound.muting){
                    music.sound.muting = false;
                }
            };

            scope.playNext = function () {
                scope.playSound($rootScope.sounds[($rootScope.currentMusicIndex + 1) % $rootScope.sounds.length], ($rootScope.currentMusicIndex + 1) % $rootScope.sounds.length) ;
            };

            scope.playPrev = function () {
                if ($rootScope.currentMusicIndex > 0){
                    scope.playSound($rootScope.sounds[($rootScope.currentMusicIndex - 1) % $rootScope.sounds.length], ($rootScope.currentMusicIndex - 1) % $rootScope.sounds.length) ;
                }
            };

            scope.repeatMusic = function (music) {
                if (music.sound.loop === true){
                    music.sound.loop = 0;
                }
                else {
                    music.sound.loop = true;
                }
            };

            scope.showMusicUpload = function () {
                commonModule.showMusicLoadModal();
            };

            scope.setNewFile = function (newFile) {
                scope.file = newFile;
            };

            scope.uploadMusic = function () {
                var promiseObj = musicLoadService.uploadFile(scope.file, scope.fileName);
                promiseObj.then(function(value) {
                        var newSound = value;
                        newSound.playing = false;
                        newSound.loaded = false;
                        newSound.sound = {};
                        $rootScope.sounds.push(newSound);
                    },
                    function (value) {

                    }
                );
            }
        }
    };
});