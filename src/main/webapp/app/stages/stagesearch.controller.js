(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageSearchController', StageController)
        .directive('etudiant', function () {
            return {
                link: function (scope, element, attrs) {
                    scope.u = attrs.urltable;
                },
                template: "<div ng-include='u'></div>"
            };
        });

    StageController.$inject =
        ['$scope', '$state', 'Stage', 'StageSearch', 'EtudiantSearch', 'EntrepriseSearch', 'StageEtudiant', 'StageEntreprise'];

    function StageController($scope, $state, Stage, StageSearch, EtudiantSearch, EntrepriseSearch, StageEtudiant, StageEntreprise) {
        var vm = this;
        vm.searchMode = null;


        vm.selectedEtudiant = null;
        vm.queryEtudiant = null;
        vm.etudiants = null;
        vm.searchEtudiant = function(){
            EtudiantSearch.query({query: vm.queryEtudiant}, function (result) {
                vm.etudiants = result;
                vm.selectedEtudiant = vm.etudiants[0];
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
                vm.selectedEntreprise = vm.entreprises[0];
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
    }
})();
