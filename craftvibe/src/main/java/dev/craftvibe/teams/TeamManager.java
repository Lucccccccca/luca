package dev.craftvibe.teams;

import net.minecraft.server.network.ServerPlayerEntity;
import java.util.*;

public class TeamManager {
    private static final Map<String, Set<UUID>> TEAMS = new HashMap<>();

    public static void init() {}

    public static void join(ServerPlayerEntity p, String team){
        TEAMS.computeIfAbsent(team, k -> new HashSet<>()).add(p.getUuid());
    }

    public static void leave(ServerPlayerEntity p, String team){
        var set = TEAMS.get(team);
        if(set!=null) set.remove(p.getUuid());
    }

    public static Optional<String> getTeam(ServerPlayerEntity p){
        for (var e : TEAMS.entrySet()) {
            if(e.getValue().contains(p.getUuid())) return Optional.of(e.getKey());
        }
        return Optional.empty();
    }
}
