package net.cleardragonf.explosionguard;

import com.google.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.explosive.fused.PrimedTNT;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Ghast;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("explosionguard")
public class Explosionguard {
    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFile;


    @Inject
    @DefaultConfig(sharedRoot = false)
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Listener
    public void enable(StartingEngineEvent<Server> event) {
        net.cleardragonf.explosionguard.ConfigurationManager.getInstance().setup(this.configFile, this.configManager);
    }

    @Listener
    public void onExplosionEvent(ExplosionEvent.Pre event) {
        if (event.cause().first(Creeper.class).isPresent()) {
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Creeper" }).getString().equalsIgnoreCase("true"))
                event.setCancelled(true);
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Creeper" }).getString().equalsIgnoreCase("false"))
                event.setCancelled(false);
        }
        if (event.cause().first(PrimedTNT.class).isPresent()) {
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "TNT" }).getString().equalsIgnoreCase("true"))
                event.setCancelled(true);
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "TNT" }).getString().equalsIgnoreCase("false"))
                event.setCancelled(false);
        }
        if (event.cause().first(Ghast.class).isPresent()) {
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Ghast" }).getString().equalsIgnoreCase("true"))
                event.setCancelled(true);
            if (net.cleardragonf.explosionguard.ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Ghast" }).getString().equalsIgnoreCase("false"))
                event.setCancelled(false);
        }
    }

    @Listener(order = Order.LAST)
    public void onExplode(ExplosionEvent.Detonate bang) {
        if (bang.cause().first(Creeper.class).isPresent() &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Creeper" }).getString().equalsIgnoreCase("false") &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "Undo_Creeper_Explosiong" }).getString().equalsIgnoreCase("yes")) {
            bang.affectedLocations();
            Mend mend = new Mend(new HashSet<>(bang.affectedLocations()));
            Sponge.server().scheduler().submit(Task.builder()
                    .delay(ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "Creeper's_Rollback_Time" }).getLong(), TimeUnit.SECONDS)
                    .name("Explosion Repair Task")
                    .execute(mend::heal).build());
        }
        if (bang.cause().first(PrimedTNT.class).isPresent() &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "TNT" }).getString().equalsIgnoreCase("false") &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "Undo_TNT_Explosion" }).getString().equalsIgnoreCase("yes")) {
            bang.affectedLocations();
            Mend mend = new Mend(new HashSet<>(bang.affectedLocations()));
            Sponge.server().scheduler().submit(Task.builder()
                    .delay(ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "TNT't_Rollback_Time" }).getLong(), TimeUnit.SECONDS)
                    .name("Explosion Repair Task")
                    .execute(mend::heal).build());
        }
        if (bang.cause().first(Ghast.class).isPresent() &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Explosions", "Ghast" }).getString().equalsIgnoreCase("false") &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "Undo_Ghast's_Explosion" }).getString().equalsIgnoreCase("yes")) {
            bang.affectedLocations();
            Mend mend = new Mend(new HashSet<>(bang.affectedLocations()));
            Sponge.server().scheduler().submit(Task.builder()
                    .delay(ConfigurationManager.getInstance().getConfig().node(new Object[] { "Undo?", "Ghast_Rollback_Time" }).getLong(), TimeUnit.SECONDS)
                    .name("Explosion Repair Task")
                    .execute(mend::heal).build());
        }
    }

    @Listener
    public void itemDrop(DropItemEvent.Destruct itemDrop) {
        if (itemDrop.cause().first(Creeper.class).isPresent() &&
               ConfigurationManager.getInstance().getConfig().node(new Object[] { "DropItems_Creeper" }).getString().equalsIgnoreCase("false"))
            itemDrop.setCancelled(true);
        if (itemDrop.cause().first(PrimedTNT.class).isPresent() &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "DropItems_TNT" }).getString().equalsIgnoreCase("false"))
            itemDrop.setCancelled(true);
        if (itemDrop.cause().first(Ghast.class).isPresent() &&
                ConfigurationManager.getInstance().getConfig().node(new Object[] { "DropItems_Ghast" }).getString().equalsIgnoreCase("false"))
            itemDrop.setCancelled(true);
    }
}
