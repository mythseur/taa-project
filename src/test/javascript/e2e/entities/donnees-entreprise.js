'use strict';

describe('DonneesEntreprise e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load DonneesEntreprises', function () {
        entityMenu.click();
        element.all(by.css('[ui-sref="donnees-entreprise"]')).first().click().then(function () {
            expect(element.all(by.css('h2')).first().getText()).toMatch(/Donnees Entreprises/);
        });
    });

    it('should load create DonneesEntreprise dialog', function () {
        element(by.css('[ui-sref="donnees-entreprise.new"]')).click().then(function () {
            expect(element(by.css('h4.modal-title')).getText()).toMatch(/Create or edit a Donnees Entreprise/);
            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
