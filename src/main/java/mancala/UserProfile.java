package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahWins;
    private int ayoWins;

    public UserProfile(String name) {
        this.name = name;
        this.kalahGamesPlayed = 0;
        this.ayoGamesPlayed = 0;
        this.kalahWins = 0;
        this.ayoWins = 0;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKalahGamesPlayed() {
        return kalahGamesPlayed;
    }

    public void setKalahGamesPlayed(int kalahGamesPlayed) {
        this.kalahGamesPlayed = kalahGamesPlayed;
    }

    public void addKalahGamesPlayed() {
        this.kalahGamesPlayed+=1;
    }

    public void addAyoGamesPlayed() {
        this.ayoGamesPlayed+=1;
    }

    public int getAyoGamesPlayed() {
        return ayoGamesPlayed;
    }

    public void setAyoGamesPlayed(int ayoGamesPlayed) {
        this.ayoGamesPlayed = ayoGamesPlayed;
    }

    public int getKalahWins() {
        return kalahWins;
    }

    public void setKalahWins(int kalahWins) {
        this.kalahWins = kalahWins;
    }

    public int getAyoWins() {
        return ayoWins;
    }

    public void addKalahGamesWon() {
        this.kalahWins+=1;
    }

    public void addAyoGamesWon() {
        this.ayoWins+=1;
    }

    public void setAyoWins(int ayoWins) {
        this.ayoWins = ayoWins;
    }

}
