var webApp = angular.module('socialNetworkApp', ["ngRoute"]);
webApp.config(function ($routeProvider) {
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

    $routeProvider.otherwise({redirectTo: '/'});
}).run(function($rootScope, $location) {
    $rootScope.loggedInUser = false;

    $rootScope.$on("$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
            if (!((next.templateUrl == "components/registry/view.html")
                || (next.templateUrl == "views/components/login/login.html"))) {
                $location.path("/");
            }
        }
        else {
            if (next.templateUrl == "views/components/login/login.html") {
                $location.path("/");
            }
        }
    });
});