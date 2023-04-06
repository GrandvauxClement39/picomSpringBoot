# Picom - API de gestion de publicités pour les arrêts de bus

## Description
Picom est une API dédiée à la gestion d'un système de publicité sur des arrêts de bus. Elle offre des fonctionnalités permettant de créer, lire, mettre à jour et supprimer des publicités, ainsi que de gérer les arrêts de bus associés à ces publicités. L'API est développée en utilisant Spring Boot et utilise Maven comme gestionnaire de dépendances.

## Prérequis
* Java JDK 17 ou une version ultérieure

* Maven 3.6.3 ou une version ultérieure

* Un serveur de base de données MySQL en cours d'exécution avec les configurations appropriées (ou un autre serveur de base de données pris en charge par le connecteur JDBC de MySQL)

* Un environnement de développement Java compatible, tel qu'Eclipse, IntelliJ IDEA, ou tout autre IDE de votre choix

* Une connaissance de base des concepts de Spring Framework et de Spring Boot

Assurez-vous que ces prérequis sont satisfaits avant d'essayer de construire, déployer ou exécuter le projet PicomSpring.

## Installation
Pour installer cette application, commencez par cloner ce dépôt de code :

```bash
    git clone https://github.com/GrandvauxClement39/picomSpringBoot.git
```
Naviguer vers le répertoire du projet dans votre environnement de développement.

Compiler le code en utilisant la commande suivante :
```bash
    mvn clean install
```

Exécuter l'API en utilisant la commande suivante :
```bash
    mvn spring-boot:run
```

## Configuration
Aucune configuration supplémentaire n'est requise pour exécuter l'API Picom. Cependant, vous pouvez modifier les fichiers de configuration Spring Boot appropriés (par exemple, application.properties ou application.yml) pour adapter les paramètres de l'API à votre environnement spécifique.

## Utilisation
Une fois que l'API Picom est en cours d'exécution, vous pouvez accéder à la documentation Swagger UI en naviguant vers la route /swagger-ui/index.html dans votre navigateur. La documentation Swagger UI offre une interface conviviale pour explorer les endpoints exposés par l'API, ainsi que pour tester les requêtes et les réponses. Vous pouvez également utiliser des outils de requêtes API tels que curl, Postman, ou tout autre client REST pour interagir avec l'API Picom.

## Documentation
La documentation complète de l'API Picom est disponible dans la documentation Swagger UI intégrée à l'API. Vous pouvez y accéder en naviguant vers la route /swagger-ui/index.html de l'API en cours d'exécution.

