(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEntrepriseController', DonneesEntrepriseController);

    DonneesEntrepriseController.$inject = ['$scope', '$state', 'DonneesEntreprise', 'DonneesEntrepriseSearch'];

    function DonneesEntrepriseController($scope, $state, DonneesEntreprise, DonneesEntrepriseSearch) {
        var vm = this;

        vm.donneesEntreprises = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            DonneesEntreprise.query(function (result) {
                vm.donneesEntreprises = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DonneesEntrepriseSearch.query({query: vm.searchQuery}, function (result) {
                vm.donneesEntreprises = result;
            });
        }
    }
})();
