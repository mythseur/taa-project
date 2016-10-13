(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home.etud', {
            parent: 'app',
            url: '/{id}',
            data: {
                authorities: []
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
                    return EtudiantIne.get({ine: $stateParams.id}).promise;
                }]
            }
        });
    }
})();
