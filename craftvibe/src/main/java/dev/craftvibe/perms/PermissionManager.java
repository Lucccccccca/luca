package dev.craftvibe.perms;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class PermissionManager {
    private static final Map<UUID, Set<String>> PERMS = new HashMap<>();

    public static void init() {}

    public static boolean has(ServerPlayerEntity p, String node) {
        return PERMS.getOrDefault(p.getUuid(), Collections.emptySet()).contains(node);
    }

    public static void grant(UUID uuid, String node) {
        PERMS.computeIfAbsent(uuid, k -> new HashSet<>()).add(node);
    }

    public static void revoke(UUID uuid, String node) {
        PERMS.computeIfAbsent(uuid, k -> new HashSet<>()).remove(node);
    }

    public static Set<String> get(UUID uuid){
        return PERMS.getOrDefault(uuid, Collections.emptySet());
    }
}
