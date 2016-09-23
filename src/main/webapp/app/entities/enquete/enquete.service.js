(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('Enquete', Enquete);

    Enquete.$inject = ['$resource', 'DateUtils'];

    function Enquete($resource, DateUtils) {
        var resourceUrl = 'api/enquetes/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertLocalDateFromServer(data.date);
                        data.datedebut = DateUtils.convertLocalDateFromServer(data.datedebut);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
