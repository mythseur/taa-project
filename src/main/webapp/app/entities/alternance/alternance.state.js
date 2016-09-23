(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('alternance', {
                parent: 'entity',
                url: '/alternance',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Alternances'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/alternance/alternances.html',
                        controller: 'AlternanceController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('alternance-detail', {
                parent: 'entity',
                url: '/alternance/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Alternance'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/alternance/alternance-detail.html',
                        controller: 'AlternanceDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Alternance', function ($stateParams, Alternance) {
                        return Alternance.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'alternance',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('alternance-detail.edit', {
                parent: 'alternance-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/alternance/alternance-dialog.html',
                        controller: 'AlternanceDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Alternance', function (Alternance) {
                                return Alternance.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('alternance.new', {
                parent: 'alternance',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/alternance/alternance-dialog.html',
                        controller: 'AlternanceDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    datedebut: null,
                                    datefin: null,
                                    sujet: null,
                                    service: null,
                                    details: null,
                                    jours: null,
                                    heures: null,
                                    versement: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('alternance', null, {reload: 'alternance'});
                    }, function () {
                        $state.go('alternance');
                    });
                }]
            })
            .state('alternance.edit', {
                parent: 'alternance',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/alternance/alternance-dialog.html',
                        controller: 'AlternanceDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Alternance', function (Alternance) {
                                return Alternance.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('alternance', null, {reload: 'alternance'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('alternance.delete', {
                parent: 'alternance',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/alternance/alternance-delete-dialog.html',
                        controller: 'AlternanceDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Alternance', function (Alternance) {
                                return Alternance.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('alternance', null, {reload: 'alternance'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
