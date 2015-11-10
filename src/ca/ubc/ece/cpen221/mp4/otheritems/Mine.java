package ca.ubc.ece.cpen221.mp4.otheritems;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Mine implements Item, Actor {
    private final static ImageIcon mineImage = Util.loadImage("mine.gif");
    private final static int strength = 1;
    private int fuseTime = 1; //instant kill
    private static final int RADIUS = 10;

    private Location location;
    private boolean isDead;

    public Mine(Location location) {
        this.location = location;
        this.isDead = false;
    }
    
    @Override
    public ImageIcon getImage() {
        return mineImage;
    }

    @Override
    public String getName() {
        return "Mine";
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

    @Override
    public int getCoolDownPeriod() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Command getNextAction(World world) {
        Set<Item> surroundings = world.searchSurroundings(this.location, RADIUS);
        
        for (Item item : surroundings) {
            if (item.getName().equals("Mine") || item.getName().equals("grass")) {
               
                if (fuseTime == 0) {
                    for (Item toDie : surroundings) {
                        toDie.loseEnergy(Integer.MAX_VALUE);
                    }
                    isDead = true;
                    
                }
                else{
                    fuseTime--;
                }
                return new WaitCommand();
            }
        }
        return null;
    }

}