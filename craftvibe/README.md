# CraftVibe (Fabric 1.21.1)

Server-Mod mit Spawn-Insel (Elytra verschwindet), /afk -> Spectator, Teams, Permissions-Placeholder mit GUI-Stub, Custom Rezepte & Advancements.

> **Wichtig**: Du wolltest 1.21.10 – Fabric ist derzeit bei 1.21.1 APIs. Sobald 1.21.10-Fabric/Loader stabil ist, in `build.gradle` & `fabric.mod.json` Versionen anpassen.

## Commands
- `/spawn` – Teleport zur Spawn Insel
- `/afk` – toggelt Spectator/Survival bei Bewegung
- `/teamgui` – Platzhalter Team-GUI
- `/perms` – Platzhalter Perms-GUI

## Build
```bash
cd craftvibe
./gradlew build
```
JAR: `craftvibe/build/libs/craftvibe-1.0.0.jar`

## TODO
- Inventar-GUI für Teams & Perms
- Elytra-Entfernung beim Boden/MinY erreichen hooken (Collision/OnGround)
- Advancements/Rezepte erweitern
- Permissions persistent speichern
