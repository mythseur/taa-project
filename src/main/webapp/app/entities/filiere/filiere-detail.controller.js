(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('FiliereDetailController', FiliereDetailController);

    FiliereDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Filiere', 'Diplome', 'Enseignant'];

    function FiliereDetailController($scope, $rootScope, $stateParams, previousState, entity, Filiere, Diplome, Enseignant) {
        var vm = this;

        vm.filiere = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:filiereUpdate', function (event, result) {
            vm.filiere = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
