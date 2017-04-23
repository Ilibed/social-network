var webApp = angular.module('socialNetworkApp', ["ngRoute", "ngCookies", "pascalprecht.translate"]);
webApp.config(function ($routeProvider, $translateProvider) {
    $routeProvider.when('/',
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

    $routeProvider.otherwise({redirectTo: '/'});

    $translateProvider.useCookieStorage();
    $translateProvider.useStaticFilesLoader({
        prefix: 'assets/i18n/',
        suffix: '.json'
    });
    $translateProvider.preferredLanguage('en');
}).run(function($rootScope, $location) {
    $rootScope.loggedInUser = false;

    $rootScope.$on("$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
            if (!((next.templateUrl == "views/components/registry/registry.html")
                || (next.templateUrl == "views/components/login/login.html"))) {
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