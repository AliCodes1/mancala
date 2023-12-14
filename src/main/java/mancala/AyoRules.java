package mancala;

public class AyoRules extends GameRules {
    private int currentPlayer;
    private MancalaDataStructure data = getDataStructure();

    public AyoRules() {
        super();
        resetBoard();
    }

    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException {
        if (startPit < 1 || startPit > 12 || data.getNumStones(startPit) == 0) {
            throw new InvalidMoveException("Invalid move: Either the pit is out of range or empty");
        }
        currentPlayer=playerNum;
        int currentStoreCount=data.getStoreCount(currentPlayer);
        int stonesDistributed = distributeStones(startPit);
        return data.getStoreCount(currentPlayer)-currentStoreCount;
    }

    @Override
    public int distributeStones(int startPit) {
        int stonesToDistribute = data.removeStones(startPit);
        int currentPit = startPit; // Initialize with the starting pit
        boolean continueDistribution;
        Countable nextPitOrStore = null;  // Declare outside the loop
        int stonesDistributed=0;
        data.setIterator(startPit, currentPlayer, true);
        do {
            continueDistribution = false;

            while (stonesToDistribute > 0) {
                nextPitOrStore = data.next();

                if (nextPitOrStore instanceof Store) {
                    Store store = (Store) nextPitOrStore;
                    store.addStone();
      
                } else {
                    currentPit = (currentPit % 12) + 1; 
                    nextPitOrStore.addStone();
    
                }
                stonesDistributed++;
                stonesToDistribute--;
            }

            // Check if the last position is a pit and has more than one stone
            if (nextPitOrStore != null && nextPitOrStore.getStoneCount() > 1 && !(nextPitOrStore instanceof Store)) {
                stonesToDistribute = nextPitOrStore.removeStones();
                continueDistribution = true;
            }

        } while (continueDistribution);

        if (nextPitOrStore != null && isPitOnPlayerSide(currentPit, currentPlayer) && nextPitOrStore.getStoneCount() == 1) {
            captureStones(currentPit); // Adjusted to return the result of captureStones
        }

        return stonesDistributed;
    }

    private int calculateNextPitIndex(int currentPitIndex) {
        // Increment pit index and wrap around if necessary
        currentPitIndex++;
        if (currentPitIndex > 13) { 
            currentPitIndex = 1;
        }
        return currentPitIndex;
    }

    @Override
    public int captureStones(int lastPit) {
        int oppositePit = 13 - lastPit;
        if (data.getNumStones(oppositePit) > 0 && data.getNumStones(lastPit) == 1) {
            int capturedStones = data.removeStones(oppositePit);
            currentPlayer=getCurrentPlayer(lastPit);

            data.addToStore(currentPlayer, capturedStones);
            return capturedStones;
        }
        return 0; // No stones captured
    }

    private boolean isPitOnPlayerSide(int pit, int playerNum) {
        // Assuming player 1 controls pits 1-6 and player 2 controls pits 7-12
        return (playerNum == 1 && pit >= 1 && pit <= 6) || (playerNum == 2 && pit >= 7 && pit <= 12);
    }
}
