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
            })
            .state('etudiant.new.stage', {
                parent: 'stageCreate',
                url: '/newEtudiant',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                                       templateUrl: 'app/entities/etudiant/etudiant-dialog.html',
                                       controller: 'EtudiantDialogController',
                                       controllerAs: 'vm',
                                       backdrop: 'static',
                                       size: 'lg',
                                       resolve: {
                                           entity: function () {
                                               return {
                                                   iNe: null,
                                                   nom: null,
                                                   prenom: null,
                                                   sexe: null,
                                                   id: null
                                               };
                                           }
                                       }
                                   }).result.then(function () {
                        $state.go('stageCreate', null, {reload: 'stageCreate'});
                    }, function () {
                        $state.go('stageCreate');
                    });
                }]
            })
            .state('entreprise.new.stage', {
                parent: 'stageCreate',
                url: '/newEntreprise',
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
                        $state.go('stageCreate', null, {reload: 'stageCreate'});
                    }, function () {
                        $state.go('stageCreate');
                    });
                }]
            })
            .state('enseignant.new.stage', {
                parent: 'stageCreate',
                url: '/newEnseignant',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                                       templateUrl: 'app/entities/enseignant/enseignant-dialog.html',
                                       controller: 'EnseignantDialogController',
                                       controllerAs: 'vm',
                                       backdrop: 'static',
                                       size: 'lg',
                                       resolve: {
                                           entity: function () {
                                               return {
                                                   sesame: null,
                                                   nom: null,
                                                   prenom: null,
                                                   sexe: null,
                                                   adresse: null,
                                                   telpro: null,
                                                   actif: null,
                                                   id: null
                                               };
                                           }
                                       }
                                   }).result.then(function () {
                        $state.go('stageCreate', null, {reload: 'stageCreate'});
                    }, function () {
                        $state.go('stageCreate');
                    });
                }]
            })
            .state('contact.new.stage', {
                parent: 'stageCreate',
                url: '/newContact',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                                       templateUrl: 'app/entities/contact/contact-dialog.html',
                                       controller: 'ContactDialogController',
                                       controllerAs: 'vm',
                                       backdrop: 'static',
                                       size: 'lg',
                                       resolve: {
                                           entity: function () {
                                               return {
                                                   nom: null,
                                                   prenom: null,
                                                   role: null,
                                                   tel: null,
                                                   mail: null,
                                                   commentaire: null,
                                                   id: null
                                               };
                                           }
                                       }
                                   }).result.then(function () {
                        $state.go('stageCreate', null, {reload: 'stageCreate'});
                    }, function () {
                        $state.go('stageCreate');
                    });
                }]
            });
    }
})();
