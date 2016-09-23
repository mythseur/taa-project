(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('DonneesEntrepriseSearch', DonneesEntrepriseSearch);

    DonneesEntrepriseSearch.$inject = ['$resource'];

    function DonneesEntrepriseSearch($resource) {
        var resourceUrl = 'api/_search/donnees-entreprises/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
