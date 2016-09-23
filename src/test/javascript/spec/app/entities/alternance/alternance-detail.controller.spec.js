'use strict';

describe('Controller Tests', function () {

    describe('Alternance Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlternance, MockEtudiant, MockEntreprise, MockContact;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlternance = jasmine.createSpy('MockAlternance');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockEntreprise = jasmine.createSpy('MockEntreprise');
            MockContact = jasmine.createSpy('MockContact');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Alternance': MockAlternance,
                'Etudiant': MockEtudiant,
                'Entreprise': MockEntreprise,
                'Contact': MockContact
            };
            createController = function () {
                $injector.get('$controller')("AlternanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:alternanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
