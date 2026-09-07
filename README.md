# High School Manager

Application Android locale pour organiser les informations essentielles d'un établissement scolaire.

## Fonctionnalités

- tableau de bord ;
- gestion des enseignants ;
- suivi des notes ;
- organisation des emplois du temps ;
- stockage local avec Room ;
- interface en Kotlin et Jetpack Compose.

## Prérequis

- Android Studio récent ;
- JDK 11 ;
- Android SDK 36 ;
- un appareil ou émulateur Android 7.0 (API 24) ou plus récent.

## Installation

1. Clonez le dépôt.
2. Ouvrez-le dans Android Studio.
3. Laissez Gradle synchroniser les dépendances.
4. Lancez la configuration `app`.

Le fichier `.env` est facultatif dans l'état actuel du projet. Si une fonctionnalité Gemini est activée plus tard, copiez `.env.example` vers `.env` et gardez la vraie clé hors de Git.

## Signature d'une version release

Définissez `KEYSTORE_PATH`, `STORE_PASSWORD` et `KEY_PASSWORD` dans votre environnement. Ne publiez jamais le keystore ni ses mots de passe.

## Structure

- `app/src/main/java/com/example/data` : modèles, DAO, base de données et données initiales ;
- `app/src/main/java/com/example/ui` : écrans et composants Compose ;
- `app/src/main/java/com/example/ui/viewmodel` : état de l'application ;
- `app/src/main/res` : ressources Android.

## État du projet

Projet d'apprentissage en cours. Les données sont stockées localement et ne remplacent pas un système scolaire sécurisé en production.
