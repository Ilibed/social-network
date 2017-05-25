/**
 * Created by gs on 25.05.2017.
 */
webApp.factory('printService',['$http', '$q', function ($http, $q) {
    return{
        printActivity: function (printName, userId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/api/print/' + printName + "/" + userId,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}]);