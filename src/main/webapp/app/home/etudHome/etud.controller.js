(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudHomeController', EtudHomeController);

    EtudHomeController.$inject = ['$scope', 'Principal', 'entity', 'LoginService', '$state'];

    function EtudHomeController($scope, Principal, entity, LoginService, $state) {
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
    }
})();
