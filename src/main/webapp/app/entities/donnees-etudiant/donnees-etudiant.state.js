(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('donnees-etudiant', {
                parent: 'entity',
                url: '/donnees-etudiant',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DonneesEtudiants'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiants.html',
                        controller: 'DonneesEtudiantController',
                        controllerAs: 'vm'
                }
                },
                resolve: {}
            })
            .state('donnees-etudiant-detail', {
                parent: 'entity',
                url: '/donnees-etudiant/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DonneesEtudiant'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiant-detail.html',
                        controller: 'DonneesEtudiantDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DonneesEtudiant', function ($stateParams, DonneesEtudiant) {
                        return DonneesEtudiant.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'donnees-etudiant',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                }]
                }
            })
            .state('donnees-etudiant-detail.edit', {
                parent: 'donnees-etudiant-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiant-dialog.html',
                        controller: 'DonneesEtudiantDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DonneesEtudiant', function (DonneesEtudiant) {
                                return DonneesEtudiant.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('donnees-etudiant.new', {
                parent: 'donnees-etudiant',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiant-dialog.html',
                        controller: 'DonneesEtudiantDialogController',
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
                                    telperso: null,
                                    telmobile: null,
                                    mail: null,
                                    id: null
                                };
                        }
                        }
                    }).result.then(function () {
                        $state.go('donnees-etudiant', null, {reload: 'donnees-etudiant'});
                    }, function () {
                        $state.go('donnees-etudiant');
                    });
                }]
            })
            .state('donnees-etudiant.edit', {
                parent: 'donnees-etudiant',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiant-dialog.html',
                        controller: 'DonneesEtudiantDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DonneesEtudiant', function (DonneesEtudiant) {
                                return DonneesEtudiant.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('donnees-etudiant', null, {reload: 'donnees-etudiant'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('donnees-etudiant.delete', {
                parent: 'donnees-etudiant',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/donnees-etudiant/donnees-etudiant-delete-dialog.html',
                        controller: 'DonneesEtudiantDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DonneesEtudiant', function (DonneesEtudiant) {
                                return DonneesEtudiant.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('donnees-etudiant', null, {reload: 'donnees-etudiant'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
