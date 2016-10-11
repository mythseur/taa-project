(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('DonneesEntreprise', DonneesEntreprise);

    DonneesEntreprise.$inject = ['$resource', 'DateUtils'];

    function DonneesEntreprise($resource, DateUtils) {
        var resourceUrl = 'api/donnees-entreprises/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datemodif = DateUtils.convertDateTimeFromServer(data.datemodif);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    }
})();
