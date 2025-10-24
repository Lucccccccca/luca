package dev.craftvibe.teams;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TeamGui {
    public static int open(ServerPlayerEntity p){
        // TODO: Implement proper inventory GUI
        p.sendMessage(Text.literal("[Teams] Placeholder GUI geöffnet."));
        return 1;
    }
}
