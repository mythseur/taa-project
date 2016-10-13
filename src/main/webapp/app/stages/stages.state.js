(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('stages', {
                abstract: true,
                parent: 'app'
            })
            .state('stageCreate', {
                parent: 'stages',
                url: '/stage_create',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Page de création d\'un stages'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/stages/stagecreate.html',
                        controller: 'StageCreateController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('stageSearch', {
                parent: 'stages',
                url: '/stage_search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Page de recherche de stages'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/stages/stagesearch.html',
                        controller: 'StageSearchController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            });
    }
})();
