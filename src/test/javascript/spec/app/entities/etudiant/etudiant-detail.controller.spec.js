'use strict';

describe('Controller Tests', function () {

    describe('Etudiant Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEtudiant, MockStage, MockAlternance, MockEnquete, MockEtudiantDiplome, MockDonneesEtudiant;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockStage = jasmine.createSpy('MockStage');
            MockAlternance = jasmine.createSpy('MockAlternance');
            MockEnquete = jasmine.createSpy('MockEnquete');
            MockEtudiantDiplome = jasmine.createSpy('MockEtudiantDiplome');
            MockDonneesEtudiant = jasmine.createSpy('MockDonneesEtudiant');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Etudiant': MockEtudiant,
                'Stage': MockStage,
                'Alternance': MockAlternance,
                'Enquete': MockEnquete,
                'EtudiantDiplome': MockEtudiantDiplome,
                'DonneesEtudiant': MockDonneesEtudiant
            };
            createController = function () {
                $injector.get('$controller')("EtudiantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:etudiantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
