package fun.aqurik.phantomVoteSkip;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class PhantomVoteSkip extends JavaPlugin implements Listener {
    public int votes = 0;

    public ArrayList<String> voted = new ArrayList<>(List.of());

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("vote").setExecutor(new VoteCommand(this));
        getLogger().info("Йоу йоу йоу я живой");
    }

    @Override
    public void onDisable() {
        getLogger().info("Выключаюсь лол");
    }


}

