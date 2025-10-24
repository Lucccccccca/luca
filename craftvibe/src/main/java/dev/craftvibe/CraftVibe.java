package dev.craftvibe;

import dev.craftvibe.afk.AfkManager;
import dev.craftvibe.config.ConfigManager;
import dev.craftvibe.spawn.SpawnManager;
import dev.craftvibe.perms.PermissionManager;
import dev.craftvibe.perms.PermGui;
import dev.craftvibe.teams.TeamGui;
import dev.craftvibe.teams.TeamManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import static net.minecraft.server.command.CommandManager.literal;

public class CraftVibe implements ModInitializer {
    public static final String MODID = "craftvibe";

    public static MinecraftServer SERVER;

    @Override
    public void onInitialize() {
        ConfigManager.load();
        PermissionManager.init();
        TeamManager.init();
        AfkManager.init();
        SpawnManager.init();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);

        // Commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, env) -> {
            dispatcher.register(literal("afk").executes(ctx -> AfkManager.toggleAfk(ctx.getSource().getPlayer())));
            dispatcher.register(literal("spawn").executes(ctx -> SpawnManager.teleportToSpawn(ctx.getSource().getPlayer())));
            dispatcher.register(literal("teamgui").executes(ctx -> TeamGui.open(ctx.getSource().getPlayer())));
            dispatcher.register(literal("perms").executes(ctx -> PermGui.open(ctx.getSource().getPlayer())));
        });

        // Elytra-on-spawn & first join tag
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            SpawnManager.onJoin(handler.getPlayer());
        });
    }
}
