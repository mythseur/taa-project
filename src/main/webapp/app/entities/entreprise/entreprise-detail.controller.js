(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('EntrepriseDetailController', EntrepriseDetailController);

    EntrepriseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entreprise'];

    function EntrepriseDetailController($scope, $rootScope, $stateParams, previousState, entity) {
        var vm = this;

        vm.entreprise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:entrepriseUpdate', function (event, result) {
            vm.entreprise = result;
        });
        $scope.$on('$destroy', unsubscribe);


    }
})();
