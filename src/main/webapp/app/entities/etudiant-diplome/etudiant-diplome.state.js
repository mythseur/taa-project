(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('etudiant-diplome', {
                parent: 'entity',
                url: '/etudiant-diplome',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'EtudiantDiplomes'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplomes.html',
                        controller: 'EtudiantDiplomeController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('etudiant-diplome-detail', {
                parent: 'entity',
                url: '/etudiant-diplome/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'EtudiantDiplome'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplome-detail.html',
                        controller: 'EtudiantDiplomeDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'EtudiantDiplome', function ($stateParams, EtudiantDiplome) {
                        return EtudiantDiplome.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'etudiant-diplome',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('etudiant-diplome-detail.edit', {
                parent: 'etudiant-diplome-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplome-dialog.html',
                        controller: 'EtudiantDiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['EtudiantDiplome', function (EtudiantDiplome) {
                                return EtudiantDiplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('etudiant-diplome.new', {
                parent: 'etudiant-diplome',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplome-dialog.html',
                        controller: 'EtudiantDiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    annee: null,
                                    note: null,
                                    mention: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('etudiant-diplome', null, {reload: 'etudiant-diplome'});
                    }, function () {
                        $state.go('etudiant-diplome');
                    });
                }]
            })
            .state('etudiant-diplome.edit', {
                parent: 'etudiant-diplome',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplome-dialog.html',
                        controller: 'EtudiantDiplomeDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['EtudiantDiplome', function (EtudiantDiplome) {
                                return EtudiantDiplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('etudiant-diplome', null, {reload: 'etudiant-diplome'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('etudiant-diplome.delete', {
                parent: 'etudiant-diplome',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/etudiant-diplome/etudiant-diplome-delete-dialog.html',
                        controller: 'EtudiantDiplomeDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['EtudiantDiplome', function (EtudiantDiplome) {
                                return EtudiantDiplome.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('etudiant-diplome', null, {reload: 'etudiant-diplome'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
