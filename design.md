La partie back-end du projet suit la structure suivante (exemple avec Etudiant) :

![Alt text](http://g.gravizo.com/g?
@startuml
class EtudiantResource;
interface EtudiantService;
class EtudiantServiceImpl;
interface EtudiantRepository;
EtudiantResource -- "1" EtudiantService;
EtudiantServiceImpl -- "1" EtudiantRepository;
EtudiantService <|-down- EtudiantServiceImpl;
@enduml
)

La classe `EtudiantResource` définit le service REST concernant les étudiants. Elle utilise l'interface `EtudiantService` pour la gestion des étudiants (récupérer, sauvegarder, supprimer un étudiant). C'est dans `EtudiantResource` qu'est gérée la création d'un nouvel utilisateur pour l'étudiant.  
L'interface `EtudiantService` est implémentée par `EtudiantServiceImpl`. Cette classe utilise `EtudiantRepository` afin d'accéder aux données des étudiants de la base.  
`EtudiantRepository` est une interface Spring Data JPA dans laquelle se trouvent les requêtes nécessaires à la manipulation des données de la base.


Le modèle utilisé suit le diagramme suivant :

![alt text](http://i.imgur.com/jzkYw5d.png "Diagramme du modèle")

Les deux entités `Etudiant` et `Entreprise` ont des données séparées : `DonneesEtudiant` et `DonneesEntreprise`. Elles sont utilisées pour pouvoir récupérer les données d'une entité à une certaine date : la date de début d'un stage par exemple. Cependant, celà est transparent à l'utilisation de l'API.

Lorsque l'on récupère un étudiant, on obtient toutes ses informations, conformément à ses dernières données disponibles. Par contre, lorsque l'on récupère un stage, on obtient l'étudiant concerné ainsi que ses informations à la date de début du stage.  
La récupération des bonnes données est faite dans `EtudiantResource` et utilise les interfaces `EtudiantService` et `DonneesEtudiantService`. L'objet réellement récupéré est de la classe `EtudiantIHM` qui regroupe les informations des classes `Etudiant` et `DonneesEtudiant`.
