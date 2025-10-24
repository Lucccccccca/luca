package dev.craftvibe.perms;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PermGui {
    public static int open(ServerPlayerEntity p){
        // TODO: replace with real ScreenHandler; placeholder chat GUI
        p.sendMessage(Text.literal("[Perms] Placeholder GUI geöffnet."));
        return 1;
    }
}
