(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('FiliereDialogController', FiliereDialogController);

    FiliereDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Filiere', 'Diplome', 'Enseignant'];

    function FiliereDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Filiere, Diplome, Enseignant) {
        var vm = this;

        vm.filiere = entity;
        vm.clear = clear;
        vm.save = save;
        vm.diplomes = Diplome.query();
        vm.enseignants = Enseignant.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.filiere.id !== null) {
                Filiere.update(vm.filiere, onSaveSuccess, onSaveError);
            } else {
                Filiere.save(vm.filiere, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:filiereUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
