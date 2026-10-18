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

## Captures d'écran

### Exercice 1 : Gestion de stock

<img width="458" height="283" alt="EVA1" src="https://github.com/user-attachments/assets/b741916d-292f-4eef-ab6f-e14796c4616b" />


<img width="377" height="359" alt="EVA2" src="https://github.com/user-attachments/assets/76c240d3-ff56-472c-8198-a89e966636f7" />

### Exercice 2 : Gestion de projets

<img width="478" height="325" alt="EVA3" src="https://github.com/user-attachments/assets/bdff254c-478e-49ae-80bf-5733fc714a9f" />

<img width="609" height="367" alt="EVA4" src="https://github.com/user-attachments/assets/d25539ef-ac6b-4ac7-8c52-1c8c02fa16d8" />

<img width="395" height="106" alt="EVA5" src="https://github.com/user-attachments/assets/4d724a42-ea03-42bf-878c-06a7f90c5cad" />

### Exercice 3 : Gestion de l'état civil
<img width="470" height="350" alt="EVA6" src="https://github.com/user-attachments/assets/9654d325-1282-4e75-9c4d-d279a5befdb9" />

<img width="611" height="154" alt="EVA7" src="https://github.com/user-attachments/assets/290be351-7917-4074-85bf-cfaee2642bc7" />

## Auteur
OUADAY Sara
