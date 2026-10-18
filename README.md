# Exercice_Evaluation

Projet Maven multi-modules réalisé avec Java, Hibernate (JPA) et MySQL.
Il regroupe trois applications de gestion, une par exercice.

## Technologies

- Java 17
- Maven
- Hibernate ORM 6.4
- MySQL 8
- Annotations JPA (Jakarta Persistence)

## Structure du projet

```
Exercice_Evaluation
├── pom.xml
├── exercice1        Gestion de stock
├── exercice2        Gestion de projets
└── exercice3        Gestion de l'état civil
```

Chaque module contient la même organisation :

| Package | Rôle |
|---|---|
| `ma.projet.classes` (ou `ma.projet.beans` pour l'exercice 3) | Entités JPA |
| `ma.projet.dao` | Interface générique `IDao` |
| `ma.projet.service` | Services (CRUD + méthodes métier) |
| `ma.projet.util` | `HibernateUtil` (lit `application.properties`) |
| `ma.projet.test` | Classe `Main` de test |

## Exercice 1 : Gestion de stock

Entités : `Categorie`, `Produit`, `Commande`, `LigneCommandeProduit`

Services : `CategorieService`, `ProduitService`, `CommandeService`, `LigneCommandeService`

Fonctionnalités de `ProduitService` :

- Afficher les produits par catégorie
- Afficher les produits commandés entre deux dates
- Afficher les produits d'une commande donnée
- Afficher les produits dont le prix est supérieur à 100 DH (requête nommée `Produit.prixSuperieurA`)

Base de données : `gestion_stock`

## Exercice 2 : Gestion de projets

Entités : `Employe`, `Projet`, `Tache`, `EmployeTache`

Services : `EmployeService`, `ProjetService`, `TacheService`, `EmployeTacheService`

Fonctionnalités :

- `EmployeService` : tâches réalisées par un employé, projets gérés par un employé
- `ProjetService` : tâches planifiées d'un projet, tâches réalisées avec les dates réelles
- `TacheService` : tâches dont le prix est supérieur à 1000 DH (requête nommée `Tache.prixSuperieurA`), tâches réalisées entre deux dates

Base de données : `gestion_projets`

## Exercice 3 : Gestion de l'état civil

Entités : `Personne`, `Homme`, `Femme`, `Mariage`

Services : `HommeService`, `FemmeService`, `MariageService`

Fonctionnalités :

- `HommeService` : épouses d'un homme entre deux dates, nombre d'hommes mariés à quatre femmes entre deux dates (API Criteria), mariages d'un homme avec tous les détails
- `FemmeService` : nombre d'enfants d'une femme entre deux dates (requête native nommée), femmes mariées au moins deux fois (requête nommée), femme la plus âgée

Base de données : `gestion_etat_civil`

## Configuration

Chaque module possède son fichier `src/main/resources/application.properties` :

```properties
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.connection.url=jdbc:mysql://localhost:3306/gestion_stock?createDatabaseIfNotExist=true&serverTimezone=UTC
hibernate.connection.username=root
hibernate.connection.password=
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.hbm2ddl.auto=create
```

Modifier `username` et `password` selon votre installation MySQL.
La base de données est créée automatiquement au premier lancement.
Avec `hbm2ddl.auto=create`, les tables sont recréées à chaque exécution.

## Exécution

1. Démarrer le serveur MySQL.
2. Ouvrir le dossier `Exercice_Evaluation` comme projet Maven dans l'IDE (IntelliJ IDEA ou Eclipse).
3. Laisser Maven télécharger les dépendances.
4. Lancer la classe `ma.projet.test.Main` du module voulu :
   - `exercice1` pour la gestion de stock
   - `exercice2` pour la gestion de projets
   - `exercice3` pour la gestion de l'état civil

Ou en ligne de commande :

```
mvn clean compile
```

## Captures d'écran

### Exercice 1 : Gestion de stock

![EVA1](C:/Users/hp/Pictures/Screenshots/EVA1.png)

![EVA2](C:/Users/hp/Pictures/Screenshots/EVA2.png)

### Exercice 2 : Gestion de projets

![EVA3](C:/Users/hp/Pictures/Screenshots/EVA3.png)

![EVA4](C:/Users/hp/Pictures/Screenshots/EVA4.png)

![EVA5](C:/Users/hp/Pictures/Screenshots/EVA5.png)

### Exercice 3 : Gestion de l'état civil

![EVA6](C:/Users/hp/Pictures/Screenshots/EVA6.png)

![EVA7](C:/Users/hp/Pictures/Screenshots/EVA7.png)

## Auteur
OUADAY Sara
