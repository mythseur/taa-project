(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDialogController', EtudiantDialogController);

    EtudiantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Etudiant', 'Stage', 'Alternance', 'Enquete', 'EtudiantDiplome', 'DonneesEtudiant', 'EtudiantDernieresDonnees'];

    function EtudiantDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Etudiant, Stage, Alternance, Enquete, EtudiantDiplome, DonneesEtudiant, EtudiantDernieresDonnees) {
        var vm = this;

        vm.etudiant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stages = Stage.query();
        vm.alternances = Alternance.query();
        vm.enquetes = Enquete.query();
        vm.etudiantdiplomes = EtudiantDiplome.query();

        if(vm.etudiant.id != null) {
            EtudiantDernieresDonnees.get(
                {id: vm.etudiant.id},
                function (data) {
                    vm.donneesetudiant = data;
                });
        }

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;

            if (vm.etudiant.id !== null) {
                Etudiant.update(vm.etudiant, saveDonnees, onSaveError);
            } else {
                Etudiant.save(vm.etudiant, saveDonnees, onSaveError);
            }
        }

        function saveDonnees(result){
            vm.donneesetudiant.datemodif = null;
            vm.donneesetudiant.id = null;
            vm.donneesetudiant.etudiant = result;
            DonneesEtudiant.save(vm.donneesetudiant, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:etudiantUpdate', result.etudiant);

            $uibModalInstance.close(result.etudiant);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        $scope.check = function(){

        }


    }
})();
