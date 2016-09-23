'use strict';

describe('Controller Tests', function () {

    describe('EtudiantDiplome Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEtudiantDiplome, MockEtudiant, MockDiplome;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEtudiantDiplome = jasmine.createSpy('MockEtudiantDiplome');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockDiplome = jasmine.createSpy('MockDiplome');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EtudiantDiplome': MockEtudiantDiplome,
                'Etudiant': MockEtudiant,
                'Diplome': MockDiplome
            };
            createController = function () {
                $injector.get('$controller')("EtudiantDiplomeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:etudiantDiplomeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
