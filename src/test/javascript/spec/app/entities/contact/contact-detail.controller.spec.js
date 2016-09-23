'use strict';

describe('Controller Tests', function () {

    describe('Contact Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContact, MockStage, MockEntreprise;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContact = jasmine.createSpy('MockContact');
            MockStage = jasmine.createSpy('MockStage');
            MockEntreprise = jasmine.createSpy('MockEntreprise');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Contact': MockContact,
                'Stage': MockStage,
                'Entreprise': MockEntreprise
            };
            createController = function () {
                $injector.get('$controller')("ContactDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'taaProjectApp:contactUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
