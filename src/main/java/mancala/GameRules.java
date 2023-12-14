package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = 1L;

    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    public int getCurrentPlayer(int pit){
        if (pit >= 1 && pit <= 6) {
            return 1;
        }else{
            return 2;
        }
    }
    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(int pitNum) {
        int startPitIndex;
        int endPitIndex;
    
        if (pitNum >= 1 && pitNum <= 6) {
            startPitIndex = 1;
            endPitIndex = 6;
        } else {
            startPitIndex = 6 + 1;
            endPitIndex = 6 * 2;
        }
    
        for (int i = startPitIndex; i <= endPitIndex; i++) {
            if (getNumStones(i) > 0) {
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(Player one, Player two) {
        // Use the existing Store objects from the Player instances
        Store storeOne = new Store();
        Store storeTwo = new Store();
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
        one.setStore(storeOne);
        two.setStore(storeTwo);
        // Set these store instances in the MancalaDataStructure
        gameBoard.setStore(storeOne, 1);
        gameBoard.setStore(storeTwo, 2);
    }
    
    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.resetPitsAndStores();
    }

    
    public void setUpPits() {
        gameBoard.setUpPits();
    }
    public void removeStones() {
        for (int i=1;i<13;i++) {
            int stonesRemoved=gameBoard.removeStones(i);
        }   

    }
    @Override
    public String toString() {
        //return gameBoard.toString();
        StringBuilder builder = new StringBuilder();
        // Add the store for player 2
        builder.append("P2 Store: ").append(gameBoard.getStoreCount(2)).append("\n");
        // Add the pits for player 2
        for (int i = 12; i > 6; i--) {
            builder.append("Pit ").append(i).append(": ").append(gameBoard.getNumStones(i)).append(" | ");
        }
        builder.append("\n");
        // Add the pits for player 1
        for (int i = 1; i < 7; i++) {
            builder.append("Pit ").append(i).append(": ").append(gameBoard.getNumStones(i)).append(" | ");
        }
        builder.append("\n");
        // Add the store for player 1
        builder.append("P1 Store: ").append(gameBoard.getStoreCount(1)).append("\n");
        return builder.toString();
    }

    
}
