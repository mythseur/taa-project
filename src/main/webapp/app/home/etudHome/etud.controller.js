(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudHomeController', EtudHomeController);

    EtudHomeController.$inject = ['$scope', '$rootScope', 'Principal', 'entity', 'LoginService', '$state', 'StageEtudiant'];

    function EtudHomeController($scope, $rootScope, Principal, entity, LoginService, $state, StageEtudiant) {
        var vm = this;

        vm.stages = [];
        vm.etudiant = entity;
        vm.toogle=true;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.getButtonText = getButtonText;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();
        getStages();

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
        });
        $scope.$on('$destroy', unsubscribe);

        function getStages() {
            StageEtudiant.get({id : vm.etudiant.id},
            function (data) {
                vm.stages = data;
            });
        }

        function getButtonText() {
            if(vm.toogle)
                return "Afficher les stages";
            else
                return "Afficher les informations";
        }
    }
})();
