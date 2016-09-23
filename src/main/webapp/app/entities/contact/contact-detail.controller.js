(function () {
    'use strict';

    angular
        .module('taaProjectApp')
        .controller('ContactDetailController', ContactDetailController);

    ContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contact', 'Stage', 'Entreprise'];

    function ContactDetailController($scope, $rootScope, $stateParams, previousState, entity, Contact, Stage, Entreprise) {
        var vm = this;

        vm.contact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaProjectApp:contactUpdate', function (event, result) {
            vm.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
