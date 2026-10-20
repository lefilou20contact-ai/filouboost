# FilouBoost

Mod client Fabric pour **Minecraft 26.3** : optimisations de performance cote client,
pense pour fonctionner avec les meilleurs mods d'optimisation.

## Fonctionnalites

- Limite les FPS quand la fenetre du jeu n'est pas active (configurable dans `config/filouboost.json`).

## Mods recommandes (a installer a cote, depuis leurs pages officielles)

Sodium, Lithium, FerriteCore, ModernFix, ImmediatelyFast, Entity Culling.
FilouBoost n'embarque ni ne copie le code d'aucun de ces mods ; chacun appartient a ses auteurs.

## Compiler

Prerequis : JDK 25 et Gradle 9.6+.

    gradle wrapper --gradle-version 9.6.0   # une seule fois, puis commiter les fichiers generes
    ./gradlew build

Le jar se trouve dans `build/libs/`. Sur GitHub, le workflow `build` compile a chaque push,
et le workflow `release` publie le jar quand tu pousses un tag `vX.Y.Z`.

## Licence

MIT, voir `LICENSE`.
