'use strict';

describe('Controller Tests', function () {

    describe('DonneesEtudiant Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDonneesEtudiant, MockEtudiant;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDonneesEtudiant = jasmine.createSpy('MockDonneesEtudiant');
            MockEtudiant = jasmine.createSpy('MockEtudiant');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DonneesEtudiant': MockDonneesEtudiant,
                'Etudiant': MockEtudiant
            };
            createController = function () {
                $injector.get('$controller')("DonneesEtudiantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:donneesEtudiantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
