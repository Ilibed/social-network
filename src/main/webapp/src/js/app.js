var app = angular.module('socialNetworkApp', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main/main.html',
            controller: 'mainController'
        })
        .when('/login', {
            templateUrl: 'views/login/login.html',
            controller: 'loginController'
        })
        .when('/info', {
            templateUrl: 'views/info/info.html',
            controller: 'infoController'
        })
});