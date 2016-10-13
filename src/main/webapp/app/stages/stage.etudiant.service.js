(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('StageEtudiant', StageEtudiant);

    StageEtudiant.$inject = ['$resource'];

    function StageEtudiant($resource) {
        var resourceUrl = 'api/stages/etudiant/:id';

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
