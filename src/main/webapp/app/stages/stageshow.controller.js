(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageShowController', StageController);

    StageController.$inject = ['$scope', '$state', '$filter', 'Stage', 'entity', 'EtudiantDernieresDonnees', 'EntrepriseDernieresDonnees', 'EtudiantAnciennesDonnees', 'EntrepriseAnciennesDonnees'];

    function StageController($scope, $state, $filter, Stage, entity, EtudiantDernieresDonnees, EntrepriseDernieresDonnees, EtudiantAnciennesDonnees, EntrepriseAnciennesDonnees) {
        var vm = this;

        vm.stage = entity;
        vm.etudiant = vm.stage.etudiant;
        vm.entreprise = vm.stage.entreprise;
        vm.referent = vm.stage.referent;
        vm.responsable = vm.stage.responsable;
        vm.encadrant = vm.stage.encadrant;

        vm.dernieredonneesetudiant = {};
        vm.anciennedonneesetudiant = {};
        vm.dernieredonneesentreprise = {};
        vm.anciennedonneesentreprise = {};
        chargerDonnees();

        function chargerDonnees(){

            EtudiantDernieresDonnees.get(
                {id: vm.etudiant.id},
                function (data) {
                    vm.dernieredonneesetudiant.adresse = (data.adresse);
                    vm.dernieredonneesetudiant.ville = (data.ville);
                    vm.dernieredonneesetudiant.codepostal = (data.codepostal);
                    vm.dernieredonneesetudiant.tel = (data.telperso);
                    vm.dernieredonneesetudiant.mobile = (data.telmobile);
                    vm.dernieredonneesetudiant.mail = (data.mail);
                }
            );

            EtudiantAnciennesDonnees.get(
                {id: vm.etudiant.id,
                    date: $filter('date')(vm.stage.datedebut, 'yyyy-MM-dd')},
                function (data) {
                    vm.anciennedonneesetudiant.adresse = (data.adresse);
                    vm.anciennedonneesetudiant.ville = (data.ville);
                    vm.anciennedonneesetudiant.codepostal = (data.codepostal);
                    vm.anciennedonneesetudiant.tel = (data.telperso);
                    vm.anciennedonneesetudiant.mobile = (data.telmobile);
                    vm.anciennedonneesetudiant.mail = (data.mail);
                }
            );

            EntrepriseDernieresDonnees.get(
                {id: vm.entreprise.id},
                function (data) {
                    vm.dernieredonneesentreprise.adresse = data.adresse;
                    vm.dernieredonneesentreprise.ville = data.ville;
                    vm.dernieredonneesentreprise.codepostal = data.codepostal;
                    vm.dernieredonneesentreprise.tel = data.tel;
                    vm.dernieredonneesentreprise.url = data.url;
                    vm.dernieredonneesentreprise.mail = data.mail;
                    vm.dernieredonneesentreprise.commentaire = data.commentaire;
                }
            );

            EntrepriseAnciennesDonnees.get(
                {id: vm.entreprise.id,
                    date: $filter('date')(vm.stage.datedebut, 'yyyy-MM-dd')},
                function (data) {
                    vm.anciennedonneesentreprise.adresse = data.adresse;
                    vm.anciennedonneesentreprise.ville = data.ville;
                    vm.anciennedonneesentreprise.codepostal = data.codepostal;
                    vm.anciennedonneesentreprise.tel = data.tel;
                    vm.anciennedonneesentreprise.url = data.url;
                    vm.anciennedonneesentreprise.mail = data.mail;
                    vm.anciennedonneesentreprise.commentaire = data.commentaire;
                }
            );
        }

        vm.exists = function(data){
            return data !== undefined && data != null && data != '';
        }
    }
})();
