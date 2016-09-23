(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('StageSearch', StageSearch);

    StageSearch.$inject = ['$resource'];

    function StageSearch($resource) {
        var resourceUrl = 'api/_search/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
