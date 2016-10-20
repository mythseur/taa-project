(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageCreateController', StageController);

    StageController.$inject = ['$scope', '$state', 'Stage', 'StageSearch', 'EtudiantSearch', 'EntrepriseSearch', 'EnseignantSearch', 'ContactSearch'];

    function StageController($scope, $state, Stage, StageSearch, EtudiantSearch, EntrepriseSearch, EnseignantSearch, ContactSearch) {
        var vm = this;


        vm.selectedEtudiant = null;
        vm.queryEtudiant = null;
        vm.etudiants = null;
        vm.searchEtudiant = function(){
            EtudiantSearch.query({query: vm.queryEtudiant}, function (result) {
                vm.etudiants = result;
                vm.stagesEtudiant = null;
                vm.selectedEtudiant = null;
                if(vm.etudiants != null && vm.etudiants.length > 0){
                    vm.selectEtudiant(vm.etudiants[0]);
                }
            });
        };
        vm.selectEtudiant = function(etud){
            vm.selectedEtudiant = etud;
        };


        vm.selectedEntreprise = null;
        vm.queryEntreprise = null;
        vm.entreprises = null;
        vm.searchEntreprise = function(){
            EntrepriseSearch.query({query: vm.queryEntreprise}, function (result) {
                vm.entreprises = result;
                vm.stagesEntreprise = null;
                vm.selectedEntreprise = null;
                if(vm.entreprises != null && vm.entreprises.length > 0){
                    vm.selectEntreprise(vm.entreprises[0]);
                }
            });
        };
        vm.selectEntreprise = function(etud){
            vm.selectedEntreprise = etud;
        };


        vm.selectedEncadrant = null;
        vm.queryEncadrant = null;
        vm.encadrants = null;
        vm.searchEncadrant = function(){
            ContactSearch.query({query: vm.queryEncadrant}, function (result) {
                vm.encadrants = result;
                vm.stagesEncadrant = null;
                vm.selectedEncadrant = null;
                if(vm.encadrants != null && vm.encadrants.length > 0){
                    vm.selectEncadrant(vm.encadrants[0]);
                }
            });
        };
        vm.selectEncadrant = function(etud){
            vm.selectedEncadrant = etud;
        };




        vm.selectedResponsable = null;
        vm.queryResponsable = null;
        vm.responsables = null;
        vm.searchResponsable = function(){
            ContactSearch.query({query: vm.queryResponsable}, function (result) {
                vm.responsables = result;
                vm.stagesResponsable = null;
                vm.selectedResponsable = null;
                if(vm.responsables != null && vm.responsables.length > 0){
                    vm.selectResponsable(vm.responsables[0]);
                }
            });
        };
        vm.selectResponsable = function(etud){
            vm.selectedResponsable = etud;
        };


        vm.selectedEnseignant = null;
        vm.queryEnseignant = null;
        vm.enseignants = null;
        vm.searchEnseignant = function(){
            EnseignantSearch.query({query: vm.queryEnseignant}, function (result) {
                vm.enseignants = result;
                vm.stagesEnseignant = null;
                vm.selectedEnseignant = null;
                if(vm.enseignants != null && vm.enseignants.length > 0){
                    vm.selectEnseignant(vm.enseignants[0]);
                }
            });
        };
        vm.selectEnseignant = function(etud){
            vm.selectedEnseignant = etud;
        };


        //Formulaire

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.datePickerOpenStatus.datedebut = false;
        vm.datePickerOpenStatus.datefin = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }


        vm.save = function(){
            vm.isSaving = true;
            vm.stage.etudiant = vm.selectedEtudiant;
            vm.stage.entreprise = vm.selectedEntreprise;
            vm.stage.referent = vm.selectedEnseignant;
            vm.stage.encadrant = vm.selectedEncadrant;
            vm.stage.responsable = vm.selectedResponsable;

            Stage.save(vm.stage, onSaveSuccess, onSaveError);
        };

        function onSaveSuccess(result) {
            $scope.$emit('taaProjectApp:stageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

    }
})();
