'use strict';

describe('Controller Tests', function () {

    describe('Stage Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStage, MockEtudiant, MockEnseignant, MockEntreprise, MockContact;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStage = jasmine.createSpy('MockStage');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockEnseignant = jasmine.createSpy('MockEnseignant');
            MockEntreprise = jasmine.createSpy('MockEntreprise');
            MockContact = jasmine.createSpy('MockContact');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stage': MockStage,
                'Etudiant': MockEtudiant,
                'Enseignant': MockEnseignant,
                'Entreprise': MockEntreprise,
                'Contact': MockContact
            };
            createController = function () {
                $injector.get('$controller')("StageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:stageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
