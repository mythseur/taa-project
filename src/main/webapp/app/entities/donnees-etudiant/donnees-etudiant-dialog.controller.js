(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEtudiantDialogController', DonneesEtudiantDialogController);

    DonneesEtudiantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DonneesEtudiant', 'Etudiant'];

    function DonneesEtudiantDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, DonneesEtudiant, Etudiant) {
        var vm = this;

        vm.donneesEtudiant = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.etudiants = Etudiant.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.donneesEtudiant.id !== null) {
                DonneesEtudiant.update(vm.donneesEtudiant, onSaveSuccess, onSaveError);
            } else {
                DonneesEtudiant.save(vm.donneesEtudiant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:donneesEtudiantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datemodif = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
