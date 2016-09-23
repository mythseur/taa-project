(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DonneesEntrepriseDeleteController', DonneesEntrepriseDeleteController);

    DonneesEntrepriseDeleteController.$inject = ['$uibModalInstance', 'entity', 'DonneesEntreprise'];

    function DonneesEntrepriseDeleteController($uibModalInstance, entity, DonneesEntreprise) {
        var vm = this;

        vm.donneesEntreprise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete(id) {
            DonneesEntreprise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
