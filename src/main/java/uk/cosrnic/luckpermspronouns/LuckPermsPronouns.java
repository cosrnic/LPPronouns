package uk.cosrnic.luckpermspronouns;

import co.aikar.commands.PaperCommandManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.cosrnic.luckpermspronouns.commands.PronounCommand;

public final class LuckPermsPronouns extends JavaPlugin {

    public LuckPerms api;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                api = provider.getProvider();
            }
        } catch (Exception e) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().severe("Cant find luckperms, is it installed?");
        }


        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new PronounCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
