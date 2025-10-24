package dev.craftvibe.afk;

import dev.craftvibe.config.ConfigManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkManager {
    private static final Map<UUID, PlayerState> STATES = new HashMap<>();

    public static void init() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
                var st = STATES.computeIfAbsent(p.getUuid(), k -> new PlayerState());
                double dx = p.getX() - st.lastX;
                double dy = p.getY() - st.lastY;
                double dz = p.getZ() - st.lastZ;

                boolean moved = Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > ConfigManager.CONFIG.afkMoveThreshold;
                if (st.afk && moved) {
                    st.afk = false;
                    p.changeGameMode(GameMode.SURVIVAL);
                }
                st.lastX = p.getX();
                st.lastY = p.getY();
                st.lastZ = p.getZ();
            }
        });
    }

    public static int toggleAfk(ServerPlayerEntity p) {
        var st = STATES.computeIfAbsent(p.getUuid(), k -> new PlayerState());
        st.afk = !st.afk;
        p.changeGameMode(st.afk ? GameMode.SPECTATOR : GameMode.SURVIVAL);
        return 1;
    }

    private static class PlayerState {
        boolean afk = false;
        double lastX, lastY, lastZ;
    }
}
