package mancala;

import java.io.Serializable;

public class Store implements Serializable, Countable {
    private static final long serialVersionUID = 1L;

    private Player owner; 
    private int totalStones=0;

    public void setOwner(final Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public int getStoneCount() {
        return totalStones;
    }

    @Override
    public void addStones(int amount) {
        totalStones += amount;
    }

    @Override
    public void addStone() {
        totalStones += 1;
    }

    @Override
    public int removeStones() {
        int stones = totalStones;
        totalStones = 0;
        return stones;
    }

    @Override
    public String toString() {
        return "Total stones in the store: " + totalStones;
    }
}
