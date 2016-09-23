(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('AlternanceController', AlternanceController);

    AlternanceController.$inject = ['$scope', '$state', 'Alternance', 'AlternanceSearch'];

    function AlternanceController($scope, $state, Alternance, AlternanceSearch) {
        var vm = this;

        vm.alternances = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Alternance.query(function (result) {
                vm.alternances = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AlternanceSearch.query({query: vm.searchQuery}, function (result) {
                vm.alternances = result;
            });
        }
    }
})();
