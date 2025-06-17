package fun.aqurik.phantomVoteSkip;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class VoteCommand implements CommandExecutor {

    private final PhantomVoteSkip plugin;
    public VoteCommand(PhantomVoteSkip plugin) {
        this.plugin = plugin;
    }


    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int req = getServer().getOnlinePlayers().size() / 2;
        if (req <= 1){ req = 1; }


        Player p = (Player) sender;


        if (p.hasPermission("PVS.vote")) {
            if (p.getWorld().getTime() >= 12000 && p.getWorld().getTime() <= 23000) {
                if (!plugin.voted.contains(p.getName().toString())) {
                    if(cooldowns.containsKey(p.getName())) {
                        long left = ((cooldowns.get(p.getName())/1000)+25) - (System.currentTimeMillis()/1000);
                        if(left>0) {
                            p.sendMessage("§7[PVS] §aПодождите "+ left +"сек. Перед использованием команд!");
                            return true;
                        }
                    }
                    cooldowns.put(p.getName(), System.currentTimeMillis());

                    plugin.voted.add(p.getName().toString());

                    if (plugin.votes == req - 1) {
                        plugin.votes += 1;
                        Bukkit.broadcastMessage("§a§l==========================");
                        Bukkit.broadcastMessage("§x§0§0§F§F§C§7§lP§x§0§5§E§F§C§B§lH§x§0§B§D§F§C§E§lA§x§1§0§C§F§D§2§lN§x§1§6§B§F§D§5§lT§x§1§B§A§F§D§9§lO§x§2§0§9§F§D§C§lM §x§2§B§8§0§E§3§lV§x§3§0§7§0§E§7§lO§x§3§6§6§0§E§A§lT§x§3§B§5§0§E§E§lE §x§4§6§3§0§F§5§lS§x§4§B§2§0§F§8§lK§x§5§1§1§0§F§C§lI§x§5§6§0§0§F§F§lP" + "\n§с§lУдаляем фантомов...");
                        Bukkit.broadcastMessage("§a§l==========================");
                        plugin.voted.clear();
                        plugin.votes = 0;
                        p.getWorld().getEntitiesByClass(Phantom.class).forEach(c -> c.setHealth(0));
                        return true;
                    } else {
                        plugin.votes += 1;
                    }
                    Bukkit.broadcastMessage("§a§l==========================");
                    Bukkit.broadcastMessage("§x§0§0§F§F§C§7§lP§x§0§4§F§4§C§9§lH§x§0§7§E§A§C§C§lA§x§0§B§D§F§C§E§lN§x§0§E§D§5§D§0§lT§x§1§2§C§A§D§3§lO§x§1§6§B§F§D§5§lM §x§1§D§A§A§D§A§lV§x§2§0§9§F§D§C§lO§x§2§4§9§5§D§E§lT§x§2§7§8§A§E§1§lE §x§2§F§7§5§E§5§lS§x§3§2§6§A§E§8§lK§x§3§6§6§0§E§A§lI§x§3§9§5§5§E§C§lP §x§4§1§4§0§F§1§l+§x§4§4§3§5§F§3§l1 §x§4§B§2§0§F§8§lV§x§4§F§1§5§F§A§lO§x§5§2§0§B§F§D§lT§x§5§6§0§0§F§F§lE\n" + "§3§l" + plugin.votes + "/" + req + " Нужно для удаления фантомов!" + "\n§b§lПроголосовать - /vote");
                    Bukkit.broadcastMessage("§a§l==========================");
                } else {
                    p.sendMessage("§7[PVS] §aВы уже голосовали!");
                }


            } else {
                p.sendMessage("§7[PVS] §aНа сервере день!");

            }
        }else{
            p.sendMessage("§7[PVS] §aНету прав: §cPVS.vote");
        }


        return true;
    }
}