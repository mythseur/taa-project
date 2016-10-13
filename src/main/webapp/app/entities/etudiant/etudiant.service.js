(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('Etudiant', Etudiant)
        .factory('EtudiantIne', EtudiantIne);

    Etudiant.$inject = ['$resource'];
    EtudiantIne.$inject = ['$resource'];

    function Etudiant($resource) {
        var resourceUrl = 'api/etudiants/:id';

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

    function EtudiantIne($resource)
    {
        var resourceUrl = 'api/etudiants/ine/:ine';

        return $resource( resourceUrl, {}, {
            'get' :{
                method: 'GET',
                transformResponse: function(data) {
                    if(data){
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
