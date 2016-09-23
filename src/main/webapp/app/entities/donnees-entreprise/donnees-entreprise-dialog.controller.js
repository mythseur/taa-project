(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEntrepriseDialogController', DonneesEntrepriseDialogController);

    DonneesEntrepriseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DonneesEntreprise', 'Entreprise'];

    function DonneesEntrepriseDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, DonneesEntreprise, Entreprise) {
        var vm = this;

        vm.donneesEntreprise = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.entreprises = Entreprise.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.donneesEntreprise.id !== null) {
                DonneesEntreprise.update(vm.donneesEntreprise, onSaveSuccess, onSaveError);
            } else {
                DonneesEntreprise.save(vm.donneesEntreprise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:donneesEntrepriseUpdate', result);
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
