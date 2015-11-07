package ca.ubc.ece.cpen221.mp4.otheritems;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Wall implements Item {
    private final static ImageIcon wallImage = Util.loadImage("wall.gif");
    private final static int strength = 500;

    private Location location;
    private boolean isDead;

    public Wall(Location location) {
        this.location = location;
        this.isDead = false;
    }
    
    @Override
    public ImageIcon getImage() {
        return wallImage;
    }

    @Override
    public String getName() {
        return "wall";
    }

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void loseEnergy(int energy) {
        isDead = true;

    }

    @Override
    public boolean isDead() {
        return isDead;
    }

}