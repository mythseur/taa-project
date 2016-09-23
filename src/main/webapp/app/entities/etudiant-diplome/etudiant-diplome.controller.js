(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDiplomeController', EtudiantDiplomeController);

    EtudiantDiplomeController.$inject = ['$scope', '$state', 'EtudiantDiplome', 'EtudiantDiplomeSearch'];

    function EtudiantDiplomeController($scope, $state, EtudiantDiplome, EtudiantDiplomeSearch) {
        var vm = this;

        vm.etudiantDiplomes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EtudiantDiplome.query(function (result) {
                vm.etudiantDiplomes = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EtudiantDiplomeSearch.query({query: vm.searchQuery}, function (result) {
                vm.etudiantDiplomes = result;
            });
        }
    }
})();
