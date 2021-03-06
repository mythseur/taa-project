(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDetailController', EtudiantDetailController);

    EtudiantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity'];

    function EtudiantDetailController($scope, $rootScope, $stateParams, previousState, entity) {
        var vm = this;

        vm.etudiant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:etudiantUpdate', function (event, result) {
            vm.etudiant = result;

        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
