(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEtudiantDeleteController', DonneesEtudiantDeleteController);

    DonneesEtudiantDeleteController.$inject = ['$uibModalInstance', 'entity', 'DonneesEtudiant'];

    function DonneesEtudiantDeleteController($uibModalInstance, entity, DonneesEtudiant) {
        var vm = this;

        vm.donneesEtudiant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete(id) {
            DonneesEtudiant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
