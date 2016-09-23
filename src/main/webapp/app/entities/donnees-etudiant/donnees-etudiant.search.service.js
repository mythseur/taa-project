(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('DonneesEtudiantSearch', DonneesEtudiantSearch);

    DonneesEtudiantSearch.$inject = ['$resource'];

    function DonneesEtudiantSearch($resource) {
        var resourceUrl = 'api/_search/donnees-etudiants/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
