package com.jphipps.rewatch;

import java.io.Serializable;

public class Entry implements Serializable, Comparable<Entry> {
    private String type;
    private String name;
    private String year;
    private String imageURL;
    private byte[] image;
    private int collection = 0;
    private int watchlist = 0;
    private int bluray = 0;
    private int netflix = 0;
    private int disney = 0;
    private int primevideo = 0;
    private int crunchyroll = 0;
    private int bbc = 0;
    private int itv = 0;
    private int all4 = 0;
    private int my5 = 0;
    private int uktv = 0;
    private int id = -1;

    int getID() {
        return id;
    }

    void setID(int id) {
        this.id = id;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getYear() {
        return year;
    }

    void setYear(String year) {
        this.year = year;
    }

    String getImageURL() {
        return imageURL;
    }

    void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    byte[] getImage() {
        return image;
    }

    void setImage(byte[] image) {
        this.image = image;
    }

    int getCollection() {
        return collection;
    }

    void setCollection(int collection) {
        this.collection = collection;
    }

    int getWatchlist() {
        return watchlist;
    }

    void setWatchlist(int watchlist) {
        this.watchlist = watchlist;
    }

    int getBluray() {
        return bluray;
    }

    void setBluray(int bluray) {
        this.bluray = bluray;
    }

    int getNetflix() {
        return netflix;
    }

    void setNetflix(int netflix) {
        this.netflix = netflix;
    }

    int getDisney() {
        return disney;
    }

    void setDisney(int disney) {
        this.disney = disney;
    }

    int getPrimeVideo() {
        return primevideo;
    }

    void setPrimeVideo(int primevideo) {
        this.primevideo = primevideo;
    }

    int getCrunchyroll() {
        return crunchyroll;
    }

    void setCrunchyroll(int crunchyroll) {
        this.crunchyroll = crunchyroll;
    }

    int getBBC() {
        return bbc;
    }

    void setBBC(int bbc) {
        this.bbc = bbc;
    }

    int getITV() {
        return itv;
    }

    void setITV(int itv) {
        this.itv = itv;
    }

    int getAll4() {
        return all4;
    }

    void setAll4(int all4) {
        this.all4 = all4;
    }

    int getMy5() {
        return my5;
    }

    void setMy5(int my5) {
        this.my5 = my5;
    }

    int getUKTV() {
        return uktv;
    }

    void setUKTV(int uktv) {
        this.uktv = uktv;
    }

    @Override
    public int compareTo(Entry o) {
        return name.compareTo(o.getName());
    }
}
