package net.cleardragonf.explosionguard;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;

public class Mend {
    private final Collection<Object> blockSnapshot = new LinkedHashSet<>(15);

    public Mend(Set<Object> set) {
        this.blockSnapshot.addAll(set);
    }

    public void heal(ScheduledTask t) {
        this.blockSnapshot.forEach(bs -> {

        });
    }
}
