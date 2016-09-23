(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('AlternanceDetailController', AlternanceDetailController);

    AlternanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alternance', 'Etudiant', 'Entreprise', 'Contact'];

    function AlternanceDetailController($scope, $rootScope, $stateParams, previousState, entity, Alternance, Etudiant, Entreprise, Contact) {
        var vm = this;

        vm.alternance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:alternanceUpdate', function (event, result) {
            vm.alternance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
