package mancala;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private Player currentPlayer;
    private final List<Player> players;
    private GameRules gameRules; 

    /**
     * Constructs a new Mancala game with default game rules.
     */
    public MancalaGame() {
        players = new ArrayList<>();
        gameRules = new KalahRules(); // Or new AyoRules() depending on the game variant
    }

    /**
     * Gets the current game rules.
     * 
     * @return The current {@link GameRules} object.
     */
    public GameRules getBoard() {
        return gameRules;
    }

    /**
     * Gets the current player of the game.
     * 
     * @return The current {@link Player}.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    
    

    public List<Player> getPlayers() {
        return players;  
    }


    /**
     * Method to update the user profiles after a game.
     * 
     * @param gameRules The current game rules.
     * @param winner    The winner.
     */
    public void updatePlayerStats(final Player winner, final GameRules gameRules) {
        for (final Player player : players) {
            updateStatsForPlayer(player, gameRules,winner);
        }
    }

    /**
     * Updates the stats for a single player.
     * 
     * @param player The player whose stats are to be updated.
     * @param gameRules The current game rules.
     * @param winner    The winner.
     */
    private void updateStatsForPlayer(final Player player, final GameRules gameRules, final Player winner) {
        final boolean isKalah = gameRules instanceof KalahRules;
    
        if (isKalah) {
            player.addKalahGamesPlayed();
            if (player.equals(winner))  {
                player.addKalahGamesWon();
            }

        } else {
            player.addAyoGamesPlayed();
            if (player.equals(winner)) {
                player.addAyoGamesWon();
            }
        }
    
    }



    
    /**
     * Gets the number of stones in a specified pit.
     * 
     * @param pitNum The pit number to check.
     * @return The number of stones in the specified pit.
     */
    public int getNumStones(final int pitNum) {
        return gameRules.getNumStones(pitNum);
    }

    /**
     * Gets the stone count in the store of a specified player.
     * 
     * @param player The {@link Player} whose store count is to be retrieved.
     * @return The number of stones in the player's store.
     */
    public int getStoreCount(final Player player) {
        return player.getStore().getStoneCount();
    }

    /**
     * Determines the winner of the game, if the game is over.
     * 
     * @return The winning {@link Player}, or null if there is no winner yet.
     * @throws GameNotOverException if the game is not yet over.
     */
    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()) {
            throw new GameNotOverException("The game is not over yet!");
        }
        final int playerOneStones = gameRules.getDataStructure().getStoreCount(1);
        final int playerTwoStones = gameRules.getDataStructure().getStoreCount(2);
        if (playerOneStones > playerTwoStones) {
            return players.get(0);
        } else if (playerTwoStones > playerOneStones) {
            return players.get(1);
        } 
        return null; 
    }

    /**
     * Checks if the game is over.
     * 
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameRules.isSideEmpty(1) || gameRules.isSideEmpty(gameRules.getDataStructure().PITS_PER_PLAYER);
    }

    /**
     * Checks if a move is valid for a given player starting from a specified pit.
     * 
     * @param startPit The starting pit for the move.
     * @param player The {@link Player} making the move.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(final int startPit, final Player player) {
        final int lowerBound = player.equals(players.get(0)) ? 1 : gameRules.getDataStructure().PITS_PER_PLAYER+1;
        final int upperBound = lowerBound + gameRules.getDataStructure().PITS_PER_PLAYER-1;
        return startPit >= lowerBound && startPit <= upperBound && getNumStones(startPit) > 0;
    }

    /**
     * Performs a move in the game.
     * 
     * @param startPit The starting pit for the move.
     * @return The result of the move.
     * @throws InvalidMoveException if the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException {
        if (!isValidMove(startPit, currentPlayer)) {
            throw new InvalidMoveException("This move is invalid");
        }
        final int stonesInStartPit = getNumStones(startPit);
        final int moveResult = gameRules.moveStones(startPit, getPlayerNum(currentPlayer));
        final int endPit = stonesInStartPit + startPit;
        
        if (!((getPlayerNum(currentPlayer) == 2 && endPit == 13) || (getPlayerNum(currentPlayer) == 1 && endPit == 7))){
            switchPlayer();
        }

        if (gameRules.isSideEmpty(1) || gameRules.isSideEmpty(gameRules.getDataStructure().PITS_PER_PLAYER + 1)) {
            final int playerIndex = gameRules.isSideEmpty(1) ? 1 : 0; // Assuming player 1 is index 0 and player 2 is index 1
            collectRemainingStones(playerIndex);
        }
        return moveResult;
    }

    /**
     * Switches the current player to the other player.
     */
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(players.get(0)) ? players.get(1) : players.get(0);
    }

    /**
     * Collects the remaining stones from a player's pits at the end of the game.
     * 
     * @param playerIndex The index of the player whose stones are to be collected.
     * @return The total number of stones collected.
     */
    public int collectRemainingStones(final int playerIndex) {
        final int start = playerIndex == 0 ? 1 : gameRules.getDataStructure().PITS_PER_PLAYER + 1;
        final int end = start + gameRules.getDataStructure().PITS_PER_PLAYER - 1;
        int totalStones = 0;
        for (int pit = start; pit <= end; pit++) {
            totalStones += gameRules.getDataStructure().removeStones(pit);
        }
        gameRules.getDataStructure().addToStore(playerIndex + 1, totalStones); 
        return totalStones;
    }

    /**
     * Sets the current player.
     * 
     * @param player The {@link Player} to be set as the current player.
     */
    public void setCurrentPlayer(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Gets the player number (1 or 2) for a given player.
     * 
     * @param player The {@link Player} whose number is to be retrieved.
     * @return The player number.
     */
    public int getPlayerNum(final Player player) {
        return players.get(0).equals(player) ? 1 : 2;
    }

    /**
     * Gets the player associated with a given pit number.
     * 
     * @param pitnum The pit number.
     * @return The associated {@link Player}.
     */
    public Player getPlayer(final int pitnum) {
        return pitnum == 0 ? players.get(0) : players.get(1);
    }

    /**
     * Sets the players for the game and registers them with the game rules.
     * 
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public void setPlayers(final Player onePlayer,final Player twoPlayer) {
        players.clear();
        players.add(onePlayer);
        players.add(twoPlayer);
        gameRules.registerPlayers(onePlayer, twoPlayer);
        setCurrentPlayer(onePlayer); 
    }

    /**
     * Starts a new game, resetting the board and setting the current player.
     */
    public void startNewGame() {
        gameRules.removeStones();
        gameRules.resetBoard();
            // Update player references to the new stores
        players.get(0).setStore(gameRules.getDataStructure().getStore(1));
        players.get(1).setStore(gameRules.getDataStructure().getStore(2));
    
        
        currentPlayer = players.get(0);
    }

    /**
     * Sets the game rules.
     * 
     * @param newGameRules The new game rules to be set.
     * @throws IllegalArgumentException if the new game rules are null.
     */
    public void setBoard(final GameRules newGameRules) {
        if (newGameRules == null) {
            throw new IllegalArgumentException("Game rules cannot be null.");
        }
        this.gameRules = newGameRules;
    }

    /**
     * Returns a string representation of the game board.
     * 
     * @return The string representation of the game board.
     */
    @Override
    public String toString() {
        return gameRules.toString();
    }
}
