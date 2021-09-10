package net.cleardragonf.explosionguard;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager instance = new ConfigurationManager();

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

    private CommentedConfigurationNode config;

    public static ConfigurationManager getInstance() {
        return instance;
    }

    public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        this.configLoader = configLoader;
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                loadConfig();
                this.config.node(new Object[] { "Explosions" }).comment("Disable or Enable These Explosion Types? True = No Explosions || False = Explosions");
                this.config.node(new Object[] { "Explosions", "TNT" }).getString("true");
                this.config.node(new Object[] { "Explosions", "Creeper" }).getString("true");
                this.config.node(new Object[] { "Explosions", "Ghast" }).getString("true");
                this.config.node(new Object[] { "Undo?" }).comment("Would you like any damage to be undone? YES = Undoes damage || NO = Damage is permenate");
                this.config.node(new Object[] { "Undo?", "Undo_Creeper_Explosiong" }).getString("yes");
                this.config.node(new Object[] { "Undo?", "Creeper's_Rollback_Time" }).comment("in Seconds").getString("5");
                this.config.node(new Object[] { "Undo?", "UndoTNT)_Explosiong" }).getString("yes");
                this.config.node(new Object[] { "Undo?", "TNT's_Rollback_Time" }).comment("in Seconds").getString("10");
                this.config.node(new Object[] { "Undo?", "Undo_Ghast's_Explosion" }).getString("yes");
                this.config.node(new Object[] { "Undo?", "Ghast_Rollback_Time" }).getString("10");
                this.config.node(new Object[] { "DropItems_Creeper" }).comment("Do you want Creeper Explosions to Drop Items?  YES = true || NO = false").getString("false");
                this.config.node(new Object[] { "DropItems_TNT" }).comment("Do you want TNT Explosions to Drop Items?  YES = true || NO = false").getString("false");
                this.config.node(new Object[] { "DropItems_Ghast" }).comment("Do you want Ghast Explosions to Drop Items?  YES = true || NO = false").getString("false");
                saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loadConfig();
        }
    }

    public CommentedConfigurationNode getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.configLoader.save((ConfigurationNode)this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            this.config = (CommentedConfigurationNode)this.configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
