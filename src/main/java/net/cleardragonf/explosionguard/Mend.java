package net.cleardragonf.explosionguard;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.BlockChangeFlags;

public class Mend {
    private final Collection<BlockSnapshot> blockSnapshot = new LinkedHashSet<>(100);

    public Mend(Set<BlockSnapshot> set) {
        this.blockSnapshot.addAll(set);
    }

    public void heal(ScheduledTask t) {
        Explosionguard ed = new Explosionguard();

        this.blockSnapshot.forEach(bs -> {
            ed.getLogger().warning(bs.toString());
            if(Sponge.server().worldManager().world(BlockSnapshot.empty().world()).isPresent()){
                bs.restore(true, BlockChangeFlags.ALL);

            }
        });
    }
}
