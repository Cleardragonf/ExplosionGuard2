package net.cleardragonf.explosionguard;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.BlockChangeFlags;
import org.spongepowered.plugin.PluginContainer;

public class Mend {
    private final Collection<BlockSnapshot> blockSnapshot = new LinkedHashSet<>(100);

    public Mend(Set<BlockSnapshot> set) {
        this.blockSnapshot.addAll(set);
    }

    public void heal(ScheduledTask t) {
        this.blockSnapshot.forEach(bs -> {
            if(Sponge.server().worldManager().world(bs.world()).isPresent()){
                bs.restore(true, BlockChangeFlags.ALL);
            }
        });
    }
}
