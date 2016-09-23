(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('entreprise', {
                parent: 'entity',
                url: '/entreprise',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Entreprises'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/entreprise/entreprises.html',
                        controller: 'EntrepriseController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('entreprise-detail', {
                parent: 'entity',
                url: '/entreprise/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Entreprise'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/entreprise/entreprise-detail.html',
                        controller: 'EntrepriseDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Entreprise', function ($stateParams, Entreprise) {
                        return Entreprise.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'entreprise',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('entreprise-detail.edit', {
                parent: 'entreprise-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/entreprise/entreprise-dialog.html',
                        controller: 'EntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Entreprise', function (Entreprise) {
                                return Entreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('entreprise.new', {
                parent: 'entreprise',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/entreprise/entreprise-dialog.html',
                        controller: 'EntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom: null,
                                    siret: null,
                                    effectif: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('entreprise', null, {reload: 'entreprise'});
                    }, function () {
                        $state.go('entreprise');
                    });
                }]
            })
            .state('entreprise.edit', {
                parent: 'entreprise',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/entreprise/entreprise-dialog.html',
                        controller: 'EntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Entreprise', function (Entreprise) {
                                return Entreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('entreprise', null, {reload: 'entreprise'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('entreprise.delete', {
                parent: 'entreprise',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/entreprise/entreprise-delete-dialog.html',
                        controller: 'EntrepriseDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Entreprise', function (Entreprise) {
                                return Entreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('entreprise', null, {reload: 'entreprise'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
