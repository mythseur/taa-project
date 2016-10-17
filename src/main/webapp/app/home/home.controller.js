(function() {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('HomeController', HomeController)
        .directive('tableres', function () {
            return {
                link: function(scope, element, attrs)
                {
                    scope.u = attrs.urltable;
                },
                template: "<div ng-include='u'></div>"
            };
        });

    HomeController.$inject = ['$scope', 'Principal', 'Etudiant', 'EtudiantSearch','StageSearch', 'EntrepriseSearch', 'LoginService', '$state'];

    function HomeController ($scope, Principal, Etudiant, EtudiantSearch, StageSearch, EntrepriseSearch, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        vm.Result = [];
        vm.search = search;
        vm.select = select;
        vm.loadAll = loadAll;
        vm.typeList = [
            {s:"Etudiant", f:EtudiantSearch, t:'app/home/tableEtudiant.html' },
            {s:"Entreprise", f:EntrepriseSearch, t:'app/home/tableEntreprise.html'},
            {s:"Stage", f:StageSearch, t:'app/home/tableStage.html'}
        ];
        vm.type = vm.typeList[0];

        function loadAll() {
            Etudiant.query(function (result) {
                vm.Etudiants = result;
            });
        }


        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

                if (account.authorities[0] == 'ROLE_ETUDIANT') {
                    console.log("Je passe");
                    $state.go('home.etud', {id: account.login});
                }
                else if (account.authorities[0] == 'ROLE_ENTREPRISE') {
                    console.log("Je passe");
                    $state.go('home.entre', {id: account.login});
                }

            });
        }
        function register () {
            $state.go('register');
        }

        function search() {
            if (!vm.searchQuery) {
                return;
            }
            vm.type.f.query({query: vm.searchQuery}, function (result) {
                vm.Result = result;
            });
        }

        function select(type) {
            vm.type = type;
            vm.Result = [];
        }
    }
})();
