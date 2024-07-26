package me.robomonkey.versus.util;

import me.robomonkey.versus.Versus;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectUtil {

    public static String fireworkNoDamageFlag = "nodamage";

    public static void playSound(Player player, Sound sound){
        player.playSound(player.getLocation(), sound, Float.POSITIVE_INFINITY, 1F);
    }

    public static void spawnFireWorks(Location loc, int amount, Integer power) {
        Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).trail(false).build());
        fireworkMeta.setPower(power);
        for(int i=0; i<amount; i++) {
            Firework firework1 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
            firework1.setFireworkMeta(fireworkMeta);
            firework1.setMetadata(fireworkNoDamageFlag, new FixedMetadataValue(Versus.getInstance(), true));
            firework1.detonate();
        }
    }

    public static void spawnFireWorks(Location loc, Color color) {
        Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(color).flicker(true).trail(false).build());
        fireworkMeta.setPower(50);
        firework.setFireworkMeta(fireworkMeta);
        firework.setMetadata(fireworkNoDamageFlag, new FixedMetadataValue(Versus.getInstance(), true));
        firework.detonate();
    }


    public static void spawnFireWorksDelayed(Location loc, int amount, Integer power, Long delayTicks) {
        Bukkit.getScheduler().runTaskLater(Versus.getInstance(), () -> {
            spawnFireWorks(loc, amount, power);
        }, delayTicks);
    }

    public static void freezePlayer(Player player) {
        player.setWalkSpeed(0);
        player.setSprinting(false);
        player.setFlySpeed(0);
    }

    public static void unfreezePlayer(Player player) {
        player.setInvulnerable(false);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.1F);
    }

    public static void sendTitle(Player player, String title, int ticks, boolean fade) {
        int fadeTime = (fade)? 20: 0;
        player.sendTitle(MessageUtil.color(title),"", fadeTime, ticks, fadeTime);
    }

    public static void sendTitle(Player player, String title, String subtitle, int ticks, boolean fade) {
        int fadeTime = (fade)? 20: 0;
        player.sendTitle(MessageUtil.color(title), MessageUtil.color(subtitle), fadeTime, ticks, fadeTime);
    }

}
