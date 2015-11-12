package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public abstract class AbstractVehicle implements Vehicle {

    private Location loc;
    private boolean isDead;
    private ImageIcon image;
    private String name;
    private int energy;
    private int MAX_COOLDOWN;
    private int cooldown;
    private int STRENGTH;
    private int MAX_TURN_SPEED;
    private int straightLineDistanceTravelled = 0;
    private int maxStraightLineDistance;
    
    private Direction currentDir = Util.getRandomDirection();
    private Direction newDir;

    // setter methods
    protected void setLOCATION(Location loc) {
        this.loc = loc;
    }

    protected void setIMAGE(ImageIcon img) {
        this.image = img;
    }

    protected void setNAME(String name) {
        this.name = name;
    }

    protected void setENERGY(int energy) {
        this.energy = energy;
    }

    protected void setISDEAD(boolean isDead) {
        this.isDead = isDead;
    }

    protected void setCOOLDOWN(int cooldown) {
        this.cooldown = cooldown;
    }
    protected void setMAXCOOLDOWN(int cooldown) {
        this.MAX_COOLDOWN = cooldown;
    }

    protected void setSTRENGTH(int strength) {
        this.STRENGTH = strength;
    }

    protected void setMAXTURNSPEED(int turnspeed) {
        this.MAX_TURN_SPEED = turnspeed;
    }
    
    protected void setMAXSTRAIGHTLINEDISTANCE(int maxDistance){
    	this.maxStraightLineDistance = maxDistance;
    }
    @Override
    public void moveTo(Location targetLocation) {
        this.loc = targetLocation;

    }

    @Override
    public int getMovingRange() {
    	//vehicles can only move one tile
        return 1;
    }

    @Override
    public ImageIcon getImage() {

        return image;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public Location getLocation() {

        return loc;
    }

    @Override
    public void loseEnergy(int energy) {
    	//vehicles die if any energy is lost
        isDead = true;

    }

    @Override
    public boolean isDead() {

        return isDead;
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
    public int getCoolDownPeriod() {

        return cooldown;
    }

    @Override
    public Command getNextAction(World world) {

        return new WaitCommand();
    }

    @Override
    public int getStrength() {

        return STRENGTH;
    }

    @Override
    public int getSpeed() {
       
        return cooldown;
    }

    @Override
    public int getMaxTurnSpeed() {

        return MAX_TURN_SPEED;
    }

    @Override
    public int getViewRange() {
        
        return 0;
    }

    /**
     * Checks to see if a location is empty.
     * 
     * @param world
     *            world the location is in
     * @param location
     *            location to be checked
     * @return True is location is empty and False otherwise
     */
    public boolean isLocationEmpty(World world, Location location) { // returns
        // true
        // if
        // location
        // is
        // empty
        if (!Util.isValidLocation(world, location)) {
            return false;
        }
        // Set<Item> possibleMoves = world.getItems();
        // Iterator<Item> it = possibleMoves.iterator();
        for (Item item : world.getItems()) {
            // Item item = it.next();
            if (item.getLocation().equals(location)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks whether vehicle can 'run over' object in neighboring tile in
     * direction. Returns true if neighboring tile is empty or has weaker
     * object, false if object in tile is stronger than vehicle.
     * 
     * @param world world to check items
     * 
     * @param location location to move to
     * 
     * @requires location is valid (within boundaries of world)
     */
    protected boolean canRunOverTile(World world, Location location) {
        Location loc = new Location(location);

        for (Item item : world.getItems()) {
            if (item.getLocation().equals(loc)) {
                // cannot run over then returns false
                if (item.getStrength() > getStrength()) {
                    return false;
                }
                // empty tile out
                item.loseEnergy(Integer.MAX_VALUE);
                return true;
            }
        }

        return true;

    }
    
    /**
     * Return new valid move location vehicle can move to (not necessarily empty).
     * Also modifies the cooldown to represent acceleration and 
     * keeps track of turning.
     * (note: higher cooldown is slower speed and vice versa)
     * 
     * @param world world used to check if location is valid
     * @return location for vehicle to move to 
     * 
     */
    protected Location getNewVehicleMoveLocation(World world){
    	 if (straightLineDistanceTravelled == maxStraightLineDistance) {
             newDir = Util.getRandomDirection();
             straightLineDistanceTravelled = 0;

             if (newDir != currentDir) {
                 if (cooldown < MAX_TURN_SPEED) {
                     cooldown++;
                 }

                 else {
                     currentDir = newDir;
                     cooldown--;
                 }
             } else {
                 cooldown--;
             }
         }
         straightLineDistanceTravelled++;
         Location toLoc = new Location(getLocation(), currentDir);
         while (!Util.isValidLocation(world, toLoc)) {
             currentDir = Util.getRandomDirection();
             straightLineDistanceTravelled = 0;
             cooldown = MAX_COOLDOWN;
             toLoc = new Location(getLocation(), currentDir);
         }
         return toLoc;
    }
}
