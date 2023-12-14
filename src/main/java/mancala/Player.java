package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L; 
    private UserProfile userProfile;
    private Store store;

    public Player() {
        this.store = new Store(); 
    }
    public Player(UserProfile userProfile) {
        this.userProfile = userProfile;
        this.store = new Store(); 
    }

    public int getKalahGamesPlayed() {
        return userProfile.getKalahGamesPlayed();
    }

    public void setKalahGamesPlayed(int kalahGamesPlayed) {
        userProfile.setKalahGamesPlayed(kalahGamesPlayed);
    }

    public void addKalahGamesPlayed() {
        userProfile.addKalahGamesPlayed();
    }

    public void addAyoGamesPlayed() {
        userProfile.addAyoGamesPlayed();
    }

    public int getAyoGamesPlayed() {
        return userProfile.getAyoGamesPlayed();
    }

    public void setAyoGamesPlayed(int ayoGamesPlayed) {
        userProfile.setAyoGamesPlayed(ayoGamesPlayed);
    }

    public int getKalahWins() {
        return userProfile.getKalahWins();
    }

    public void setKalahWins(int kalahWins) {
        userProfile.setKalahWins(kalahWins);
    }

    public int getAyoWins() {
        return userProfile.getAyoWins();
    }

    public void addKalahGamesWon() {
        userProfile.addKalahGamesWon();
    }

    public void addAyoGamesWon() {
        userProfile.addAyoGamesWon();
    }

    public void setAyoWins(int ayoWins) {
        userProfile.setAyoWins(ayoWins);
    }

    public Player(final String playerName) {
        userProfile.setName(playerName);
        this.store = new Store();  
    }

    public String getName() {
        return userProfile.getName();
    }

    public void setName(final String newName) {
        userProfile.setName(newName);
    }
    
    public Store getStore(){
        return store;
    }
    public void setStore(final Store newStore) {
        this.store = newStore;
    }

    public int getStoreCount() {
        return store.getStoneCount();
    }
    @Override
    public String toString(){
        return getName()+" has "+getStoreCount()
            +" stones in his store";
    }
}
