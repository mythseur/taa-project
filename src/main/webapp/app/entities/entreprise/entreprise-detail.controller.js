(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrepriseDetailController', EntrepriseDetailController);

    EntrepriseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entreprise', 'DonneesEntreprise', 'Contact', 'EntrepriseDernieresDonnees'];

    function EntrepriseDetailController($scope, $rootScope, $stateParams, previousState, entity, Entreprise, DonneesEntreprise, Contact, EntrepriseDernieresDonnees) {
        var vm = this;

        vm.entreprise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:entrepriseUpdate', function (event, result) {
            vm.entreprise = result;
        });
        $scope.$on('$destroy', unsubscribe);

        EntrepriseDernieresDonnees.get(
            {id: vm.entreprise.id},
            function (data) {
                vm.adresse = data.adresse;
                vm.ville = data.ville;
                vm.codepostal = data.codepostal;
                vm.tel = data.tel;
                vm.url = data.url;
                vm.commentaire = data.commentaire;
            }
        );

    }
})();
