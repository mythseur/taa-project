(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('EtudiantDiplome', EtudiantDiplome);

    EtudiantDiplome.$inject = ['$resource'];

    function EtudiantDiplome($resource) {
        var resourceUrl = 'api/etudiant-diplomes/:id';

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
