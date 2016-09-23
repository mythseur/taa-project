(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('EnqueteSearch', EnqueteSearch);

    EnqueteSearch.$inject = ['$resource'];

    function EnqueteSearch($resource) {
        var resourceUrl = 'api/_search/enquetes/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
