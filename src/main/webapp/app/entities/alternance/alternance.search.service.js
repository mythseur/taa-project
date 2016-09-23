(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .factory('AlternanceSearch', AlternanceSearch);

    AlternanceSearch.$inject = ['$resource'];

    function AlternanceSearch($resource) {
        var resourceUrl = 'api/_search/alternances/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true}
        });
    }
})();
