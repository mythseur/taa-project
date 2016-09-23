(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEntrepriseDetailController', DonneesEntrepriseDetailController);

    DonneesEntrepriseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DonneesEntreprise', 'Entreprise'];

    function DonneesEntrepriseDetailController($scope, $rootScope, $stateParams, previousState, entity, DonneesEntreprise, Entreprise) {
        var vm = this;

        vm.donneesEntreprise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:donneesEntrepriseUpdate', function (event, result) {
            vm.donneesEntreprise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
