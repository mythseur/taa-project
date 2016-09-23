(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('donnees-entreprise', {
                parent: 'entity',
                url: '/donnees-entreprise',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DonneesEntreprises'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprises.html',
                        controller: 'DonneesEntrepriseController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('donnees-entreprise-detail', {
                parent: 'entity',
                url: '/donnees-entreprise/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DonneesEntreprise'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprise-detail.html',
                        controller: 'DonneesEntrepriseDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DonneesEntreprise', function ($stateParams, DonneesEntreprise) {
                        return DonneesEntreprise.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'donnees-entreprise',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('donnees-entreprise-detail.edit', {
                parent: 'donnees-entreprise-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprise-dialog.html',
                        controller: 'DonneesEntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DonneesEntreprise', function (DonneesEntreprise) {
                                return DonneesEntreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('donnees-entreprise.new', {
                parent: 'donnees-entreprise',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprise-dialog.html',
                        controller: 'DonneesEntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    datemodif: null,
                                    adresse: null,
                                    ville: null,
                                    codepostal: null,
                                    tel: null,
                                    url: null,
                                    commentaire: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('donnees-entreprise', null, {reload: 'donnees-entreprise'});
                    }, function () {
                        $state.go('donnees-entreprise');
                    });
                }]
            })
            .state('donnees-entreprise.edit', {
                parent: 'donnees-entreprise',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprise-dialog.html',
                        controller: 'DonneesEntrepriseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DonneesEntreprise', function (DonneesEntreprise) {
                                return DonneesEntreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('donnees-entreprise', null, {reload: 'donnees-entreprise'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('donnees-entreprise.delete', {
                parent: 'donnees-entreprise',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-entreprise/donnees-entreprise-delete-dialog.html',
                        controller: 'DonneesEntrepriseDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DonneesEntreprise', function (DonneesEntreprise) {
                                return DonneesEntreprise.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('donnees-entreprise', null, {reload: 'donnees-entreprise'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
