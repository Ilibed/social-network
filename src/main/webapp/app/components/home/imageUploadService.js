/**
 * Created by gs on 22.05.2017.
 */
webApp.factory('imageUploadService',['$q', '$http', 'Upload', function ($q, $http, Upload) {
    return{
        uploadFile: function (file, text, creationDate, userId) {
            var deferred = $q.defer();
            Upload.upload({
                url: '/api/post/save/file',
                fields: {
                    'text': text,
                    'creationDate': creationDate,
                    'userId': userId
                },
                file: file
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                }
            );

            return deferred.promise;
        },
        editUserInfo: function (file, name, surname, country, city) {
            var deferred = $q.defer();
            Upload.upload({
                url: '/api/user/update',
                fields: {
                    'name': name,
                    'surname': surname,
                    'country': country,
                    'city': city
                },
                file: file
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                }
            );

            return deferred.promise;
        },
        getPosts: function () {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/login',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
                },
                data: 'username=' + username + '&password=' + password
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