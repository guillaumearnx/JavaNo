package fr.arnoux23u.javano.mvc;

import game.Player;

import java.util.*;
import java.util.Observer;

/**
 * @author arnoux23u
 */
public class Game implements Model {

    private final List<java.util.Observer> observers;

    private final List<Player> players;

    public Game() {
        this.players = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public synchronized void addPlayer(UUID id, String name) {
        players.add(new Player(id, name));
        notifyObservers();
    }

    public void removePlayer(UUID uuid) {
        players.removeIf(p -> p.getId().equals(uuid));
        notifyObservers();
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void addObserver(java.util.Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update(this));
    }

}

