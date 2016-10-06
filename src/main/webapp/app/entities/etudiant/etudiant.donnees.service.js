(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('EtudiantDernieresDonnees', EtudiantDernieresDonnees);

    EtudiantDernieresDonnees.$inject = ['$resource'];

    function EtudiantDernieresDonnees($resource) {
        var resourceUrl = 'api/donnees-etudiants/etudiant/:id';

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
            'update': {method: 'PUT'}
        });
    }
})();
