package com.ridoy.examnoticeusingfirebase.ModelClass;

public class LeaderboardModel {

    String name;
    int currentpoint;

    public LeaderboardModel() {
    }

    public LeaderboardModel(String name, int currentpoint) {
        this.name = name;
        this.currentpoint = currentpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentpoint() {
        return currentpoint;
    }

    public void setCurrentpoint(int currentpoint) {
        this.currentpoint = currentpoint;
    }
}
