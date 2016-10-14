(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudHomeController', EtudHomeController);

    EtudHomeController.$inject = ['$scope', '$rootScope', 'Principal', 'entity', 'LoginService', '$state', 'EtudiantDernieresDonnees'];

    function EtudHomeController($scope, $rootScope, Principal, entity, LoginService, $state, EtudiantDernieresDonnees) {
        var vm = this;

        vm.etudiant = entity;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        var unsubscribe = $rootScope.$on('taaProjectApp:etudiantUpdate', function (event, result) {
            vm.etudiant = result;

            if (vm.etudiant.id != null) {
                chargerDonnees();
            }
        });
        $scope.$on('$destroy', unsubscribe);

        if (vm.etudiant.id != null) {
            chargerDonnees();
        }

        function chargerDonnees() {
            EtudiantDernieresDonnees.get(
                {id: vm.etudiant.id},
                function (data) {
                    vm.adresse = (data.adresse);
                    vm.ville = (data.ville);
                    vm.codepostal = (data.codepostal);
                    vm.tel = (data.telperso);
                    vm.mobile = (data.telmobile);
                    vm.mail = (data.mail);
                }
            );
        }
    }
})();
