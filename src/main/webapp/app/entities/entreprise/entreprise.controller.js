(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrepriseController', EntrepriseController);

    EntrepriseController.$inject = ['$scope', '$state', 'Entreprise', 'EntrepriseSearch'];

    function EntrepriseController($scope, $state, Entreprise, EntrepriseSearch) {
        var vm = this;

        vm.entreprises = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Entreprise.query(function (result) {
                vm.entreprises = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EntrepriseSearch.query({query: vm.searchQuery}, function (result) {
                vm.entreprises = result;
            });
        }
    }
})();
