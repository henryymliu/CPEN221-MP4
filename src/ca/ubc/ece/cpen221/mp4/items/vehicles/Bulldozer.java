package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveAndThrowItemCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.otheritems.Wall;

/*
 * Bulldozer will move randomly as a vehicle and destroy everything in its path
 */
public class Bulldozer implements Vehicle {
    private int MOVING_RANGE = 1;
    private static final int MAX_TURN_SPEED = 2;
    private static final int VIEW_RANGE = 1;
    private static final int STRENGTH = 5000;
    private int MIN_COOLDOWN = 2;
    private int cooldown = 6;
    private int MAX_COOLDOWN = 6;
    private int straightLineDistanceTravelled = 2;
    private boolean isDead;
    private Location loc;
    private Location prevLoc;
    private Direction currentDir = Util.getRandomDirection();
    private Direction newDir = Util.getRandomDirection();

    private static final ImageIcon bulldozerImage = Util.loadImage("bulldozer.jpg");

    public Bulldozer(Location initLoc) {

        this.loc = initLoc;
        isDead = false;
    }

    /**
     * Returns commands for bulldozer to move. Priorities: Move around randomly.
     * 
     * @param world:
     *            world that the bulldozer is in
     * @return Command: Move to new location if able or Wait if there is no
     *         available tile to move to
     */
    @Override
    public Command getNextAction(World world) {

        if (straightLineDistanceTravelled == 0) {
            newDir = Util.getRandomDirection();
            straightLineDistanceTravelled = 2;

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
        straightLineDistanceTravelled--;
        Location toLoc = new Location(loc, currentDir);
        while (!Util.isValidLocation(world, toLoc)) {
            currentDir = Util.getRandomDirection();
            straightLineDistanceTravelled = 2;
            cooldown = MAX_COOLDOWN;
            toLoc = new Location(loc, currentDir);
        }
        if (Vehicle.canRunOverTile(world, toLoc, this)) {
            return new MoveCommand(this, toLoc);
        } else {
            this.isDead = true;
            return new WaitCommand();
        }

    }

    @Override
    public void moveTo(Location targetLocation) {
        this.loc = targetLocation;

    }

    @Override
    public int getMovingRange() {
        return MOVING_RANGE;
    }

    @Override
    public ImageIcon getImage() {

        return bulldozerImage;
    }

    @Override
    public String getName() {

        return "Bulldozer";
    }

    @Override
    public Location getLocation() {

        return loc;
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

        return VIEW_RANGE;
    }

}
