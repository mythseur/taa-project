(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageSearchController', StageController);

    StageController.$inject =
        ['$scope', '$state', 'Stage', 'StageSearch', 'EtudiantSearch', 'EntrepriseSearch', 'EnseignantSearch', 'ContactSearch', 'StageEtudiant', 'StageEntreprise', 'StageEnseignant', 'StageContact'];

    function StageController($scope, $state, Stage, StageSearch, EtudiantSearch, EntrepriseSearch, EnseignantSearch, ContactSearch, StageEtudiant, StageEntreprise, StageEnseignant, StageContact) {
        var vm = this;
        vm.searchMode = null;


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
            StageEtudiant.get(
                {id: vm.selectedEtudiant.id},
                function (data) {
                    vm.stagesEtudiant = data;
                }
            );
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
        vm.selectEntreprise = function(entrep){
            vm.selectedEntreprise = entrep;
            StageEntreprise.get(
                {id: vm.selectedEntreprise.id},
                function (data) {
                    vm.stagesEntreprise = data;
                }
            );
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
        vm.selectEnseignant = function(ens){
            vm.selectedEnseignant = ens;
            StageEnseignant.get(
                {id: vm.selectedEnseignant.id},
                function (data) {
                    vm.stagesEnseignant = data;
                }
            );
        };


        vm.selectedContact = null;
        vm.queryContact = null;
        vm.contacts = null;
        vm.searchContact = function(){
            ContactSearch.query({query: vm.queryContact}, function (result) {
                vm.contacts = result;
                vm.stagesContact = null;
                vm.selectedContact = null;
                if(vm.contacts != null && vm.contacts.length > 0){
                    vm.selectContact(vm.contacts[0]);
                }
            });
        };
        vm.selectContact = function(cont){
            vm.selectedContact = cont;
            StageContact.get(
                {id: vm.selectedContact.id},
                function (data) {
                    vm.stagesContact = data;
                }
            );
        };
    }
})();
