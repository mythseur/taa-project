(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDiplomeDialogController', EtudiantDiplomeDialogController);

    EtudiantDiplomeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EtudiantDiplome', 'Etudiant', 'Diplome'];

    function EtudiantDiplomeDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, EtudiantDiplome, Etudiant, Diplome) {
        var vm = this;

        vm.etudiantDiplome = entity;
        vm.clear = clear;
        vm.save = save;
        vm.etudiants = Etudiant.query();
        vm.diplomes = Diplome.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.etudiantDiplome.id !== null) {
                EtudiantDiplome.update(vm.etudiantDiplome, onSaveSuccess, onSaveError);
            } else {
                EtudiantDiplome.save(vm.etudiantDiplome, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:etudiantDiplomeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
