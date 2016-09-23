(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('Stage', Stage);

    Stage.$inject = ['$resource', 'DateUtils'];

    function Stage($resource, DateUtils) {
        var resourceUrl = 'api/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datedebut = DateUtils.convertLocalDateFromServer(data.datedebut);
                        data.datefin = DateUtils.convertLocalDateFromServer(data.datefin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    data.datefin = DateUtils.convertLocalDateToServer(data.datefin);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    data.datefin = DateUtils.convertLocalDateToServer(data.datefin);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
