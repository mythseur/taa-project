(function() {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('HomeController', HomeController);

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
            {s:"Etudiant", f:EtudiantSearch},
            {s:"Entreprise", f:EntrepriseSearch},
            {s:"Stage", f:StageSearch}
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
            });
        }
        function register () {
            $state.go('register');
        }

        function search() {
            if (!vm.searchQuery) {
                return ;
            }
            vm.type.f.query({query: vm.searchQuery}, function (result) {
                vm.Result = result;
                console.log(vm.Result);
            });
        }

        function select(type) {
            vm.type = type;
        }
    }
})();
