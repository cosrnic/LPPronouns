package uk.cosrnic.luckpermspronouns.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.entity.Player;
import uk.cosrnic.luckpermspronouns.LuckPermsPronouns;

@CommandAlias("pronouns")
public class PronounCommand extends BaseCommand {

    private LuckPermsPronouns plugin;

    public PronounCommand(LuckPermsPronouns plugin) {
        this.plugin = plugin;
    }

    @Default
    @Syntax("[pronouns]")
    @Description("Set your pronouns")
    public void command(Player player, String pronouns) {

        if (pronouns.contains("[")) pronouns = pronouns.replace("[", "");
        if (pronouns.contains("]")) pronouns = pronouns.replace("]", "");

        if (pronouns.contains("\"")) pronouns = pronouns.replace("\"", "");

        User user = plugin.api.getPlayerAdapter(Player.class).getUser(player);


        SuffixNode node = SuffixNode.builder(" &7[" + pronouns + "]", 1000).build();


        if (user.getCachedData().getMetaData().getSuffix() != null) {
            user.data().clear(NodeType.SUFFIX::matches);
        }

        user.data().add(node);

        plugin.api.getUserManager().saveUser(user);

        player.sendRichMessage("<green>Successfully set your pronouns to </green><yellow>" + pronouns + "</yellow>");

    }


    @Subcommand("remove")
    @Syntax("[Player]")
    @CommandPermission("lppronouns.admin")
    @Description("remove a players pronouns")
    public void removePronouns(Player player, OnlinePlayer onlinePlayer) {

        User user = plugin.api.getPlayerAdapter(Player.class).getUser(onlinePlayer.getPlayer());

        if (user.getCachedData().getMetaData().getSuffix() != null) {
            user.data().clear(NodeType.SUFFIX::matches);
        }

        plugin.api.getUserManager().saveUser(user);

        player.sendRichMessage("<green>Successfully removed <yellow>" + MiniMessage.miniMessage().serialize(onlinePlayer.getPlayer().displayName()) + "</yellow>'s pronouns</green>");

    }

}
