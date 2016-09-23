(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('AlternanceDeleteController', AlternanceDeleteController);

    AlternanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alternance'];

    function AlternanceDeleteController($uibModalInstance, entity, Alternance) {
        var vm = this;

        vm.alternance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete(id) {
            Alternance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
