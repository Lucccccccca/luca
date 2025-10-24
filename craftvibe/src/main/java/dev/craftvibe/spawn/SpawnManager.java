package dev.craftvibe.spawn;

import dev.craftvibe.config.ConfigManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class SpawnManager {
    private static final Set<UUID> TEMP_ELYTRA = new HashSet<>();
    private static final Map<UUID, Integer> TIME_LEFT = new HashMap<>();

    public static void init() {
        // Countdown tick
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            Iterator<Map.Entry<UUID, Integer>> it = TIME_LEFT.entrySet().iterator();
            while (it.hasNext()) {
                var e = it.next();
                int t = e.getValue() - 1;
                if (t <= 0) {
                    var uuid = e.getKey();
                    ServerPlayerEntity p = server.getPlayerManager().getPlayer(uuid);
                    if (p != null) removeElytra(p);
                    it.remove();
                } else {
                    e.setValue(t);
                }
            }
        });
    }

    public static void onJoin(ServerPlayerEntity p) {
        giveTempElytra(p);
        teleportToSpawn(p);
        TIME_LEFT.put(p.getUuid(), ConfigManager.CONFIG.elytraTimeoutSeconds * 20);
    }

    public static int teleportToSpawn(ServerPlayerEntity p) {
        MinecraftServer server = p.getServer();
        if (server == null) return 0;
        ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
        if (world == null) return 0;
        BlockPos pos = BlockPos.ofFloored(ConfigManager.CONFIG.spawnX, ConfigManager.CONFIG.spawnY, ConfigManager.CONFIG.spawnZ);
        p.teleport(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, p.getYaw(), p.getPitch());
        p.sendMessage(Text.literal("Willkommen auf der Spawn-Insel! Gleite runter – Elytra verschwindet unten."));
        return 1;
    }

    private static void giveTempElytra(ServerPlayerEntity p) {
        ItemStack elytra = new ItemStack(Items.ELYTRA);
        p.getInventory().armor.set(2, elytra); // Chest slot
        TEMP_ELYTRA.add(p.getUuid());
    }

    private static void removeElytra(ServerPlayerEntity p) {
        if (TEMP_ELYTRA.remove(p.getUuid())) {
            ItemStack chest = p.getInventory().armor.get(2);
            if (!chest.isEmpty() && chest.isOf(Items.ELYTRA)) {
                p.getInventory().armor.set(2, ItemStack.EMPTY);
                p.sendMessage(Text.literal("Elytra entfernt."));
            }
        }
    }
}
