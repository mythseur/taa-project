(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home.entre', {
            parent: 'app',
            url: '/homentre/{id}',
            data: {
                authorities: ['ROLE_ENTREPRISE']
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/entreprhome/entr.html',
                    controller: 'EntrHomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Entreprise', function ($stateParams, Entreprise) {
                    return Entreprise.get({id: $stateParams.id}).$promise;
                }]
            }
        });
    }
})();
