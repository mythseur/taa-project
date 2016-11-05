# TAAProject

##Installation

###Prérequis
Avoir Docker et Docker Compose dans leur dernière versions installés.

###Installation et lancement de l'application
* Clonez le projet puis rendez vous dans le dossier [src/main/docker](https://github.com/mythseur/taa-project/tree/master/src/main/docker)
* Assurez-vous d'être connectés au Web
* Lancer les container docker avec la commande : 
    `docker-compose -f app.yml up`
* Le premier lancement est assez long pendant le téléchargement des images dockers
* Une fois le projet démarré rendez-vous à l'adresse web indiquée en utilisant le login et mot de passe admin/admin

##Quelques informations
* Le projet implémente dans une version basique la gestion des stages de l'ISTIC
* La création d'un étudiant ou d'une entreprise entraîne la création d'un utilisateur avec l'envoi d'un lien pour activer le compte à l'adresse mail indiquée

[JHipster]: https://jhipster.github.io/
[Gatling]: http://gatling.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
