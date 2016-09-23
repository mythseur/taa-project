(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('StageDetailController', StageDetailController);

    StageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stage', 'Etudiant', 'Enseignant', 'Entreprise', 'Contact'];

    function StageDetailController($scope, $rootScope, $stateParams, previousState, entity, Stage, Etudiant, Enseignant, Entreprise, Contact) {
        var vm = this;

        vm.stage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:stageUpdate', function (event, result) {
            vm.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
