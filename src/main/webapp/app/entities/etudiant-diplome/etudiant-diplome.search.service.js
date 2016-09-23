(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('EtudiantDiplomeSearch', EtudiantDiplomeSearch);

    EtudiantDiplomeSearch.$inject = ['$resource'];

    function EtudiantDiplomeSearch($resource) {
        var resourceUrl = 'api/_search/etudiant-diplomes/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
