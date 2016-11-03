(function () {
    'use strict';
    angular
        .module('taaProjectApp');
    // .factory('EtudiantDernieresDonnees', EtudiantDernieresDonnees)
    // .factory('EtudiantAnciennesDonnees', EtudiantAnciennesDonnees);

    EtudiantDernieresDonnees.$inject = ['$resource'];
    EtudiantAnciennesDonnees.$inject = ['$resource'];

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
    function EtudiantAnciennesDonnees($resource) {
        var resourceUrl = 'api/donnees-etudiants/etudiantdate/:id/:date';

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
