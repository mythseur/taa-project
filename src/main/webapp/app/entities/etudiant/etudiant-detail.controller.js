(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDetailController', EtudiantDetailController);

    EtudiantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Etudiant', 'Stage', 'Alternance', 'Enquete', 'EtudiantDiplome', 'DonneesEtudiant', 'EtudiantDernieresDonnees'];

    function EtudiantDetailController($scope, $rootScope, $stateParams, previousState, entity, Etudiant, Stage, Alternance, Enquete, EtudiantDiplome, DonneesEtudiant, EtudiantDernieresDonnees) {
        var vm = this;

        vm.etudiant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:etudiantUpdate', function (event, result) {
            vm.etudiant = result;

            if(vm.etudiant.id != null) {
                chargerDonnees();
            }
        });
        $scope.$on('$destroy', unsubscribe);

        if(vm.etudiant.id != null) {
            chargerDonnees();
        }

        function chargerDonnees(){
            EtudiantDernieresDonnees.get(
                {id: vm.etudiant.id},
                function (data) {
                    vm.adresse = (data.adresse);
                    vm.ville = (data.ville);
                    vm.codepostal = (data.codepostal);
                    vm.tel = (data.telperso);
                    vm.mobile = (data.telmobile);
                    vm.mail = (data.mail);
                }
            );
        }
    }
})();
