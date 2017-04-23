webApp.directive('langSelector', function($translate) {
    return {
        restrict: 'C',
        replace: true,
        templateUrl: 'views/shared/lang/lang-selector.html',
        link: function(scope, elem, attrs) {
            scope.setLocale = function(locale) {
                $translate.use(locale);
            };
        }
    };
});