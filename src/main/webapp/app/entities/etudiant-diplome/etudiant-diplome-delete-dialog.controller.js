(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDiplomeDeleteController', EtudiantDiplomeDeleteController);

    EtudiantDiplomeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EtudiantDiplome'];

    function EtudiantDiplomeDeleteController($uibModalInstance, entity, EtudiantDiplome) {
        var vm = this;

        vm.etudiantDiplome = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete(id) {
            EtudiantDiplome.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
