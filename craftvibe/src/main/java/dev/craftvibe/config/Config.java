package dev.craftvibe.config;

public class Config {
    public double spawnX = 0.5;
    public double spawnY = 200.0; // schwebende Insel
    public double spawnZ = 0.5;
    public String spawnWorld = "minecraft:overworld";

    public int elytraTimeoutSeconds = 120; // Elytra verschwindet nach 2 Min
    public int elytraMinYToConsume = 120;   // unterhalb dieser Y wird Elytra entfernt, sobald Boden berührt

    public int afkMoveThreshold = 1; // minimale Bewegung, um AFK zu beenden
}
