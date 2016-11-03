(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrHomeController', EntrHomeController);

    EntrHomeController.$inject = ['$scope', 'Principal', 'LoginService', 'entity', '$state'];

    function EntrHomeController($scope, Principal, LoginService, entity, $state) {
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
