webApp.directive('switchTemplate', function() {
    return {
        restrict: 'C',
        replace: true,
        templateUrl: function (elem, attrs) {
            if (attrs.mode) {
                return 'views/shared/template/logged.html';
            } else {
                return 'views/shared/template/unlogged.html';
            }
        },
        scope: {
            mode: "="
        }
    };
});