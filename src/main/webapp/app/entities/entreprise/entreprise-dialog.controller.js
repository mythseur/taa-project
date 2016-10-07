(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrepriseDialogController', EntrepriseDialogController);

    EntrepriseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entreprise', 'DonneesEntreprise', 'Contact', 'EntrepriseDernieresDonnees'];

    function EntrepriseDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Entreprise, DonneesEntreprise, Contact, EntrepriseDernieresDonnees) {
        var vm = this;

        vm.entreprise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contacts = Contact.query();

        if(vm.entreprise.id != null) {
            EntrepriseDernieresDonnees.get(
                {id: vm.entreprise.id},
                function (data) {
                    vm.donneesentreprise = data;
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
            if (vm.entreprise.id !== null) {
                Entreprise.update(vm.entreprise, saveDonnees, onSaveError);
            } else {
                Entreprise.save(vm.entreprise, saveDonnees, onSaveError);
            }
        }

        function saveDonnees(result){
            vm.donneesentreprise.datemodif = null;
            vm.donneesentreprise.id = null;
            vm.donneesentreprise.entreprise = result;
            DonneesEntreprise.save(vm.donneesentreprise, onSaveSuccess, onSaveError);
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
