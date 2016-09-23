(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EtudiantDiplomeDetailController', EtudiantDiplomeDetailController);

    EtudiantDiplomeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EtudiantDiplome', 'Etudiant', 'Diplome'];

    function EtudiantDiplomeDetailController($scope, $rootScope, $stateParams, previousState, entity, EtudiantDiplome, Etudiant, Diplome) {
        var vm = this;

        vm.etudiantDiplome = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:etudiantDiplomeUpdate', function (event, result) {
            vm.etudiantDiplome = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
