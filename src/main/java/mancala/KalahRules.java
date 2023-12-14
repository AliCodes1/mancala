package mancala;

public class KalahRules extends GameRules {
    private int currentPlayer;
    MancalaDataStructure data=getDataStructure();
    public KalahRules() {
        super();
        resetBoard();
    }
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (startPit < 1 || startPit > 12 || data.getNumStones(startPit) == 0) {
            throw new InvalidMoveException("Invalid move: Either the pit is out of range or empty");
        }
        currentPlayer=playerNum;
        final int currentStoreCount=data.getStoreCount(currentPlayer);
        final int stonesDistributed = distributeStones(startPit);
        return data.getStoreCount(currentPlayer)-currentStoreCount;
    }

    @Override
    public int distributeStones(final int startPit) {
        int stonesToDistribute = data.removeStones(startPit);
        int currentPit = startPit;
        int stonesDistributed=0;
        data.setIterator(startPit, currentPlayer, false);

        while (stonesToDistribute > 0) {
            final Countable nextPitOrStore = data.next();
        
            if (nextPitOrStore instanceof Store) {
                final Store store = (Store) nextPitOrStore;
                store.addStone();
  
            } else {
                currentPit = (currentPit % 12) + 1; 
                nextPitOrStore.addStone();

            }
            stonesDistributed++;
            stonesToDistribute--;
        }
        if (isPitOnPlayerSide(currentPit, currentPlayer) && data.getNumStones(currentPit) == 1) {
            captureStones(currentPit);
        }
        return stonesDistributed; 
    }

    @Override
    public int captureStones(final int lastPit) {
        final int oppositePit = 13 - lastPit;
        final int capturedStones = data.removeStones(oppositePit);
        final int removed=data.removeStones(lastPit); 
        currentPlayer=getCurrentPlayer(lastPit);
        data.addToStore(currentPlayer, (capturedStones + removed)); 
        return capturedStones + removed;
    }

    private boolean isPitOnPlayerSide(final int pit, final int playerNum) {
        if (playerNum == 1) {
            return pit >= 1 && pit <= 6;
        } else {
            return pit >= 7 && pit <= 12;
        }
    }
}
