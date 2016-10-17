(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home.etud', {
            parent: 'app',
            url: '/hometud/{id}',
            data: {
                authorities: ['ROLE_ETUDIANT']
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/etudHome/etud.html',
                    controller: 'EtudHomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EtudiantIne', function ($stateParams, EtudiantIne) {
                    return EtudiantIne.get({ine: $stateParams.id}).$promise;
                }]
            }
        });
    }
})();
