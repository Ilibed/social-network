/**
 * Created by gs on 06.05.2017.
 */
webApp.directive('shortMusicPlayer', function($rootScope, ngAudio) {
    return {
        restrict: 'AEC',
        replace: true,
        templateUrl: 'views/components/music/shortPlayerTemplate.html',
        link: function(scope, elem, attrs) {
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

            scope.playNext = function () {
                scope.playSound($rootScope.sounds[($rootScope.currentMusicIndex + 1) % $rootScope.sounds.length], ($rootScope.currentMusicIndex + 1) % $rootScope.sounds.length) ;
            };

            scope.playPrev = function () {
                if ($rootScope.currentMusicIndex > 0){
                    scope.playSound($rootScope.sounds[($rootScope.currentMusicIndex - 1) % $rootScope.sounds.length], ($rootScope.currentMusicIndex - 1) % $rootScope.sounds.length) ;
                }
            };
        }
    };
});