(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('EnseignantSearch', EnseignantSearch);

    EnseignantSearch.$inject = ['$resource'];

    function EnseignantSearch($resource) {
        var resourceUrl = 'api/_search/enseignants/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
