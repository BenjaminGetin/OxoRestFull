# Projet Oxows API REST

Ce projet est une API REST permettant la création et la gestion de sondages.

## Technologies utilisées

- Java 17
- Spring Boot
- Jakarta Bean Validation

## Configuration requise

- Java 17
- Maven 3.8.1 ou supérieur

## Comment exécuter l'API

1. Cloner le dépôt git
2. Se rendre dans le répertoire du projet
3. Lancer l'application OxoWsApplication.java pour exécuter l'API. Elle se lancera sur votre serveur local au port 8080.

    ```
    OxoWsApplication.java
    ```
   Si vous souhaitez changer de port, voici la ligne de code à rentrer dans 'application.properties', qui se trouve dans le package 'ressources' :
    ```
    server.port=numéro de port
    ```

## Points d'entrée de l'API

- `GET /api/sondages/` : Récupère la liste des sondages ouverts
- `GET /api/sondages/{id}` : Récupère un sondage par son ID
- `POST /api/sondages/` : Ajoute un nouveau sondage
- `PUT /api/sondages/{id}` : Met à jour un sondage existant
- `DELETE /api/sondages/{id}` : Supprime un sondage existant
