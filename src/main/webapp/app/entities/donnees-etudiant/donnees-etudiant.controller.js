(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEtudiantController', DonneesEtudiantController);

    DonneesEtudiantController.$inject = ['$scope', '$state', 'DonneesEtudiant', 'DonneesEtudiantSearch'];

    function DonneesEtudiantController($scope, $state, DonneesEtudiant, DonneesEtudiantSearch) {
        var vm = this;

        vm.donneesEtudiants = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            DonneesEtudiant.query(function (result) {
                vm.donneesEtudiants = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DonneesEtudiantSearch.query({query: vm.searchQuery}, function (result) {
                vm.donneesEtudiants = result;
            });
        }
    }
})();
