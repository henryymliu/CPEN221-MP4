package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveAndThrowItemCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.otheritems.HolyHandGrenade;

/*
 * HolyGrenadeTank moves around and shoots destructive HolyHandGrenades and destroys EVERYTHING
 */
public class HolyGrenadeTank extends AbstractVehicle {
    private int MOVING_RANGE = 1;
    private static final int MAX_TURN_SPEED = 2;
    private static final int VIEW_RANGE = 7;
    private static final int STRENGTH = 5000;
    private static final int LAUNCH_RADIUS = 6;
    // private int speed = 1;
    private int cooldown = 5;
    private int MAX_COOLDOWN = 7;
    private int straightLineDistanceTravelled = 0;
    private static final int maxStraightLineDistance = 5;
    private boolean isDead;
    private Location loc;
    private Location prevLoc;
    private Direction currentDir = Util.getRandomDirection();
    private Direction newDir = Util.getRandomDirection();
    private static final ImageIcon tankImage = Util.loadImage("tank.png");

    public HolyGrenadeTank(Location loc) {
        this.loc = loc;
        isDead = false;
    }

    @Override
    public void moveTo(Location targetLocation) {
        loc = targetLocation;

    }

    @Override
    public int getMovingRange() {
        // TODO Auto-generated method stub
        return MOVING_RANGE;
    }

    @Override
    public ImageIcon getImage() {

        return tankImage;
    }

    @Override
    public String getName() {

        return "Tank";
    }

    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        return loc;
    }

    @Override
    public void loseEnergy(int energy) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        return isDead;
    }

    @Override
    public int getPlantCalories() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMeatCalories() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCoolDownPeriod() {

        return cooldown;
    }

    /**
     * Returns commands for HolyGrenadeTank to move and shoot HolyGrenades.
     * Priorities: Move and destroy everything by shooting randomly into
     * oblivion.
     * 
     * @param world:
     *            world that the HolyGrenadeTank is in
     * @return Command: one of Move or shoot HolyGrenades; Wait if "dead"
     */
    @Override
    public Command getNextAction(World world) {
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
        Location launchLoc = getRandomEmptyRadialLocation(world, loc, LAUNCH_RADIUS);
        Location toLoc = new Location(loc, currentDir);
        while (!isLocationEmpty(world, toLoc)) {
            currentDir = Util.getRandomDirection();
            straightLineDistanceTravelled = 0;
            cooldown = MAX_COOLDOWN;
            toLoc = new Location(loc, currentDir);
        }
        // determines a random location that is within the throwing (shooting)
        // radius
        if (Vehicle.canRunOverTile(world, toLoc, this)) {
            return new MoveAndThrowItemCommand(this, new HolyHandGrenade(launchLoc), toLoc);
        } else {
            this.isDead = true;
            return new WaitCommand();
        }

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
        // TODO Auto-generated method stub
        return MAX_TURN_SPEED;
    }

    @Override
    public int getViewRange() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Returns a random empty Location in the specified World that is within a
     * specified radius to <code>loc</code>, or null if there is no empty
     * adjacent location.
     *
     * @param world
     *            the current World
     * @param loc
     *            the center location, all empty locations returned (if any) are
     *            adjacent to loc
     * @param radius
     *            radius from given loc
     * @return empty location within radius of loc, or null if none exists
     */
    private Location getRandomEmptyRadialLocation(World world, Location loc, int radius) {
        Location[] neighbors = new Location[(2 * radius + 1) * (2 * radius + 1)];
        int numLocs = 0;
        for (int x = loc.getX() - radius; x <= loc.getX() + radius; x++) {
            for (int y = loc.getY() - radius; y <= loc.getY() + radius; y++) {
                Location l = new Location(x, y);
                if (Util.isValidLocation(world, l) && isLocationEmpty(world, l)) {
                    neighbors[numLocs] = l;
                    numLocs++;
                }
            }
        }
        if (numLocs == 0)
            return null;
        return neighbors[Util.RAND.nextInt(numLocs)];
    }

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

}
