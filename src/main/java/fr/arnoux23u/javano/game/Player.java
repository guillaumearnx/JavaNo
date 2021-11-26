package fr.arnoux23u.javano.game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author arnoux23u
 */
public class Player {

    public final String name;
    public final UUID id;

    public Player(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UUID getId() {
        return id;
    }
}
