(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('AlternanceDialogController', AlternanceDialogController);

    AlternanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alternance', 'Etudiant', 'Entreprise', 'Contact'];

    function AlternanceDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Alternance, Etudiant, Entreprise, Contact) {
        var vm = this;

        vm.alternance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.etudiants = Etudiant.query();
        vm.entreprises = Entreprise.query();
        vm.contacts = Contact.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.alternance.id !== null) {
                Alternance.update(vm.alternance, onSaveSuccess, onSaveError);
            } else {
                Alternance.save(vm.alternance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:alternanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datedebut = false;
        vm.datePickerOpenStatus.datefin = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
