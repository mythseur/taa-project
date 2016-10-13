(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('StageEntreprise', StageEntreprise);

    StageEntreprise.$inject = ['$resource'];

    function StageEntreprise($resource) {
        var resourceUrl = 'api/stages/entreprise/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
