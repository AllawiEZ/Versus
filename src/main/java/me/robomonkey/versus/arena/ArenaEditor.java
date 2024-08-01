package me.robomonkey.versus.arena;

import me.robomonkey.versus.kit.Kit;
import me.robomonkey.versus.kit.KitSelectionGUI;
import me.robomonkey.versus.util.MessageUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ArenaEditor {

    static void displayInstructionalMessage(Arena targetArena, ArenaProperty property, Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        String buttonBase = MessageUtil.color("%button% &pto select the");
        String explanationBase = MessageUtil.color("&h "+property.toFriendlyString());
        String explanationOnHover = MessageUtil.color("&s"+property.getExplanation());
        String commandOnClick = "/arena set " + targetArena.getName() + " " +property.toString();
        String commandOnHover = MessageUtil.color("&s"+commandOnClick);
        TextComponent setPropertyMessage = MessageUtil.getClickableMessage(buttonBase, commandOnClick, commandOnHover, "&boldClick here");
        TextComponent explanationMessage = MessageUtil.getHoverText(explanationBase, explanationOnHover);
        setPropertyMessage.addExtra(explanationMessage);
        player.spigot().sendMessage(setPropertyMessage);
    }

    public static void openEditingMenu(Player player, Arena targetArena) {
        player.sendMessage(MessageUtil.LINE);
        Arrays.stream(ArenaProperty.values()).forEach((property) -> displayInstructionalMessage(targetArena, property, player));
        player.sendMessage(MessageUtil.LINE);
    }

    public static void changeArenaProperty(Arena targetArena, ArenaProperty property, Player player, Runnable after) {
        if(property==ArenaProperty.KIT) {
            KitSelectionGUI kitGUI = new KitSelectionGUI(player, (kit, whoClicked) -> {
                changeKit(targetArena, player, kit);
                after.run();
            });
            kitGUI.open();
            return;
        }
        targetArena.setLocationProperty(property, player.getLocation());
        player.sendMessage(MessageUtil.get("&sSet the "+property.toFriendlyString()+" for "+targetArena.getName()+"."));
        after.run();
    }

    public static void changeArenaProperty(Arena targetArena, ArenaProperty property, Player player) {
        if(property==ArenaProperty.KIT) {
            KitSelectionGUI kitGUI = new KitSelectionGUI(player, (kit, whoClicked) -> {
                changeKit(targetArena, player, kit);
            });
            kitGUI.open();
            return;
        }
        targetArena.setLocationProperty(property, player.getLocation());
        player.sendMessage(MessageUtil.get("&sYou have successfully set the "+property.toFriendlyString()+"."));
    }

    public static void changeKit(Arena arena, Player player, Kit kit) {
        arena.setKit(kit);
        player.sendMessage(MessageUtil.get("&sSet the kit to "+kit.getName()+" for "+arena.getName()+"."));
    }
}
