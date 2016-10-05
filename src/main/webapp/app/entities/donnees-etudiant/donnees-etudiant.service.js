(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('DonneesEtudiant', DonneesEtudiant);

    DonneesEtudiant.$inject = ['$resource', 'DateUtils'];

    function DonneesEtudiant($resource, DateUtils) {
        var resourceUrl = 'api/donnees-etudiants/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    }
})();
