(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('EntrepriseDernieresDonnees', EntrepriseDernieresDonnees);

    EntrepriseDernieresDonnees.$inject = ['$resource'];

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
})();
