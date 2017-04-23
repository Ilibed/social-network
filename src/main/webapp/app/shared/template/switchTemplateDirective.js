webApp.directive('switchTemplate', function($rootScope) {
    return {
        restrict: 'C',
        replace: true,
        templateUrl: function (elem, attrs) {
            if ($rootScope.loggedInUser) {
                return 'views/shared/template/logged.html';
            } else {
                return 'views/shared/template/unlogged.html';
            }
        }
    };
});