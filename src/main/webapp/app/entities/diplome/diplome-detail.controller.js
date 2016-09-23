(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('DiplomeDetailController', DiplomeDetailController);

    DiplomeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Diplome', 'EtudiantDiplome'];

    function DiplomeDetailController($scope, $rootScope, $stateParams, previousState, entity, Diplome, EtudiantDiplome) {
        var vm = this;

        vm.diplome = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:diplomeUpdate', function (event, result) {
            vm.diplome = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
