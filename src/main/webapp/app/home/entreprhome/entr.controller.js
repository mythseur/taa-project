(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrHomeController', EntrHomeController);
    // .directive('tableRes', function () {
    //     return {
    //         link: function(scope, element, attrs)
    //         {
    //             scope.u = attrs.urltable;
    //         },
    //         template: "<div ng-include='u'></div>"
    //     };
    // });

    EntrHomeController.$inject = ['$scope', 'Principal', 'LoginService', 'entity', 'EntrepriseDernieresDonnees', '$state'];

    function EntrHomeController($scope, Principal, LoginService, entity, EntrepriseDernieresDonnees, $state) {
        var vm = this;

        vm.entreprise = entity;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        if (vm.entreprise.id != null) {
            chargerDonnees();
        }

        function chargerDonnees() {
            EntrepriseDernieresDonnees.get(
                {id: vm.entreprise.id},
                function (data) {
                    vm.adresse = data.adresse;
                    vm.ville = data.ville;
                    vm.codepostal = data.codepostal;
                    vm.tel = data.tel;
                    vm.url = data.url;
                    vm.mail = data.mail;
                    vm.commentaire = data.commentaire;
                }
            );
        }

        // vm.Result = [];
        // vm.search = search;
        // vm.select = select;
        // vm.loadAll = loadAll;
        // vm.typeList = [
        //     {s:"Etudiant", f:EtudiantSearch, t:'app/home/tableEtudiant.html' },
        //     {s:"Entreprise", f:EntrepriseSearch, t:'app/home/tableEntreprise.html'},
        //     {s:"Stage", f:StageSearch, t:'app/home/tableStage.html'}
        // ];
        // vm.type = vm.typeList[0];
        //
        // function loadAll() {
        //     Etudiant.query(function (result) {
        //         vm.Etudiants = result;
        //     });
        // }


        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        //     function search() {
        //         if (!vm.searchQuery) {
        //             return;
        //         }
        //         vm.type.f.query({query: vm.searchQuery}, function (result) {
        //             vm.Result = result;
        //         });
        //     }
        //
        //     function select(type) {
        //         vm.type = type;
        //         vm.Result = [];
        //     }
    }
})();
