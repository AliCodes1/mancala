package mancala;

import java.io.Serializable;

public class Pit implements Serializable, Countable {
    private static final long serialVersionUID = 1L;
    private int stoneCount;

    public Pit() {
        // Initialize the pit with a default number of stones, if required
        this.stoneCount = 0;
    }
    public Pit(int stones) {
        // Initialize the pit with a default number of stones, if required
        this.stoneCount = stones;
    }

    // Implement addStones method declared in Countable interface
    @Override
    public void addStones(int stones) {
        this.stoneCount += stones;
    }

    @Override
    public void addStone() {
        this.stoneCount += 1;
    }

    // Implement getStoneCount method declared in Countable interface
    @Override
    public int getStoneCount() {
        return this.stoneCount;
    }

    // Implement removeStones method declared in Countable interface
    @Override
    public int removeStones() {
        int stones = this.stoneCount;
        this.stoneCount = 0; // Remove all stones from the pit
        return stones; // Return the number of stones that were removed
    }
}
