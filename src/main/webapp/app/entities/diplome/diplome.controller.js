(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DiplomeController', DiplomeController);

    DiplomeController.$inject = ['$scope', '$state', 'Diplome', 'DiplomeSearch'];

    function DiplomeController($scope, $state, Diplome, DiplomeSearch) {
        var vm = this;

        vm.diplomes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Diplome.query(function (result) {
                vm.diplomes = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DiplomeSearch.query({query: vm.searchQuery}, function (result) {
                vm.diplomes = result;
            });
        }
    }
})();
