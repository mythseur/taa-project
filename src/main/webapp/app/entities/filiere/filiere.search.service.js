(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('FiliereSearch', FiliereSearch);

    FiliereSearch.$inject = ['$resource'];

    function FiliereSearch($resource) {
        var resourceUrl = 'api/_search/filieres/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
