'use strict';

describe('Controller Tests', function () {

    describe('Diplome Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiplome, MockEtudiantDiplome;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiplome = jasmine.createSpy('MockDiplome');
            MockEtudiantDiplome = jasmine.createSpy('MockEtudiantDiplome');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Diplome': MockDiplome,
                'EtudiantDiplome': MockEtudiantDiplome
            };
            createController = function () {
                $injector.get('$controller')("DiplomeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:diplomeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
