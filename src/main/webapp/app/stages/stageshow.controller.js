(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageShowController', StageController);

    StageController.$inject = ['$scope', '$state', '$filter', 'Stage', 'entity', 'Etudiant', 'Entreprise'];

    function StageController($scope, $state, $filter, Stage, entity, Etudiant, Entreprise) {
        var vm = this;

        vm.stage = entity;
        vm.etudiant = vm.stage.etudiant;
        vm.entreprise = vm.stage.entreprise;
        vm.referent = vm.stage.referent;
        vm.responsable = vm.stage.responsable;
        vm.encadrant = vm.stage.encadrant;

        if(vm.etudiant != null) {
            Etudiant.get(
                {"id": vm.etudiant.id},
                function (data) {
                    vm.dernieredonneesetudiant = data;
                });
        }

        if(vm.entreprise != null){
            Entreprise.get(
                {"id": vm.entreprise.id},
                function(data){
                    vm.dernieredonneesentreprise = data;
                });
        }

        vm.exists = function(data){
            return data !== undefined && data != null && data != '';
        }
    }
})();
