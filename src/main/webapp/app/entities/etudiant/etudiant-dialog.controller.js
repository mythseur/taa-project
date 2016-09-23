(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDialogController', EtudiantDialogController);

    EtudiantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Etudiant', 'Stage', 'Alternance', 'Enquete', 'EtudiantDiplome', 'DonneesEtudiant'];

    function EtudiantDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Etudiant, Stage, Alternance, Enquete, EtudiantDiplome, DonneesEtudiant) {
        var vm = this;

        vm.etudiant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stages = Stage.query();
        vm.alternances = Alternance.query();
        vm.enquetes = Enquete.query();
        vm.etudiantdiplomes = EtudiantDiplome.query();
        vm.donneesetudiants = DonneesEtudiant.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.etudiant.id !== null) {
                Etudiant.update(vm.etudiant, onSaveSuccess, onSaveError);
            } else {
                Etudiant.save(vm.etudiant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:etudiantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
