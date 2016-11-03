(function () {
    'use strict';
    angular
        .module('taaProjectApp');
    // .factory('EntrepriseAnciennesDonnees', EntrepriseAnciennesDonnees)
    // .factory('EntrepriseDernieresDonnees', EntrepriseDernieresDonnees);

    EntrepriseDernieresDonnees.$inject = ['$resource'];
    EntrepriseAnciennesDonnees.$inject = ['$resource'];

    function EntrepriseDernieresDonnees($resource) {
        var resourceUrl = 'api/donnees-entreprises/entreprise/:id';

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

    function EntrepriseAnciennesDonnees($resource) {
        var resourceUrl = 'api/donnees-entreprises/entreprisedate/:id/:date';

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
