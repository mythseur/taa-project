(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('diplome', {
                parent: 'entity',
                url: '/diplome',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Diplomes'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/diplome/diplomes.html',
                        controller: 'DiplomeController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('diplome-detail', {
                parent: 'entity',
                url: '/diplome/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Diplome'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/diplome/diplome-detail.html',
                        controller: 'DiplomeDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Diplome', function ($stateParams, Diplome) {
                        return Diplome.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'diplome',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('diplome-detail.edit', {
                parent: 'diplome-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/diplome/diplome-dialog.html',
                        controller: 'DiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Diplome', function (Diplome) {
                                return Diplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('diplome.new', {
                parent: 'diplome',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/diplome/diplome-dialog.html',
                        controller: 'DiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    libelle: null,
                                    duree: null,
                                    libellecourt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('diplome', null, {reload: 'diplome'});
                    }, function () {
                        $state.go('diplome');
                    });
                }]
            })
            .state('diplome.edit', {
                parent: 'diplome',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/diplome/diplome-dialog.html',
                        controller: 'DiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Diplome', function (Diplome) {
                                return Diplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('diplome', null, {reload: 'diplome'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('diplome.delete', {
                parent: 'diplome',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/diplome/diplome-delete-dialog.html',
                        controller: 'DiplomeDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Diplome', function (Diplome) {
                                return Diplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('diplome', null, {reload: 'diplome'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
