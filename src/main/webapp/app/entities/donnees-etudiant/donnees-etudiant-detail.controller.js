(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEtudiantDetailController', DonneesEtudiantDetailController);

    DonneesEtudiantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DonneesEtudiant', 'Etudiant'];

    function DonneesEtudiantDetailController($scope, $rootScope, $stateParams, previousState, entity, DonneesEtudiant, Etudiant) {
        var vm = this;

        vm.donneesEtudiant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:donneesEtudiantUpdate', function (event, result) {
            vm.donneesEtudiant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
