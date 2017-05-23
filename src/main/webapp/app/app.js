var webApp = angular.module('socialNetworkApp', ["ngRoute", "ngAudio", "ngCookies", "pascalprecht.translate", "ngFileUpload"]);
webApp.config(function ($routeProvider, $translateProvider) {
    $routeProvider.when('/user/:id',
        {
            templateUrl: 'views/components/home/home.html',
            controller: 'homeController'
        });
    $routeProvider.when('/login',
        {
            templateUrl: 'views/components/login/login.html',
            controller: 'loginController'
        });
    $routeProvider.when('/registry',
        {
            templateUrl: 'views/components/registry/registry.html',
            controller: 'registryController'
        });
    $routeProvider.when('/music',
        {
            templateUrl: 'views/components/music/music.html',
            controller: 'musicController'
        }
    );
    $routeProvider.when('/messages',
        {
            templateUrl: 'views/components/message/mainMessage.html',
            controller: 'mainMessageController'
        }
    );
    $routeProvider.when('/messages/:id',
        {
            templateUrl: 'views/components/message/detailedMessage.html',
            controller: 'detailedMessageController'
        }
    );

    $routeProvider.otherwise({redirectTo: '/login'});

    $translateProvider.useCookieStorage();
    $translateProvider.useStaticFilesLoader({
        prefix: 'assets/i18n/',
        suffix: '.json'
    });
    $translateProvider.preferredLanguage('en');
}).run(function($rootScope, $location, loginService) {
    $rootScope.loggedInUser = false;

    var userPromiseObj = loginService.getUser();
    userPromiseObj.then(function (value) {
            $rootScope.userInfo = value;
            $rootScope.loggedInUser = true;
        },
        function (value) {
            //something wrong server problem
        });


    $rootScope.$on("$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
            if (!((next.templateUrl == "views/components/registry/registry.html")
                || (next.templateUrl == "views/components/login/login.html")
                || (next.templateUrl == "views/components/music/music.html"))) {
                $location.path("/login");
            }
        }
        else {
            if (next.templateUrl == "views/components/login/login.html") {
                $location.path("/");
            }
        }
    });
});