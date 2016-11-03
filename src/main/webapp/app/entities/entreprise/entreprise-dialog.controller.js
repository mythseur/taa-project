(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrepriseDialogController', EntrepriseDialogController);

    EntrepriseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entreprise', 'Contact'];

    function EntrepriseDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Entreprise, Contact) {
        var vm = this;

        vm.entreprise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contacts = Contact.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.entreprise.id !== null) {
                Entreprise.update(vm.entreprise, onSaveSuccess, onSaveError);
            } else {
                Entreprise.save(vm.entreprise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:entrepriseUpdate', result.entreprise);
            $uibModalInstance.close(result.entreprise);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
