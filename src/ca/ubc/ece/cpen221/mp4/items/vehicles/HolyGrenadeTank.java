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

    private static final int MAX_TURN_SPEED = 2;
    
    private static final int STRENGTH = 5000;
    private static final int LAUNCH_RADIUS = 6;
    // private int speed = 1;
    private int cooldown = 5;
    private int MAX_COOLDOWN = 7;
    
    private static final int maxStraightLineDistance = 5;
  
    
    private static final ImageIcon tankImage = Util.loadImage("tank.png");
    private static final String name = "Tank";
    public HolyGrenadeTank(Location loc) {
    	setNAME(name);
    	setLOCATION(loc);
        setISDEAD(false);
        setCOOLDOWN(cooldown);
        setMAXCOOLDOWN(MAX_COOLDOWN);
        setSTRENGTH(STRENGTH);
        setMAXTURNSPEED(MAX_TURN_SPEED);
        setMAXSTRAIGHTLINEDISTANCE(maxStraightLineDistance);
        setIMAGE(tankImage);
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
    	
        Location toLoc = getNewVehicleMoveLocation(world);
        
        Location launchLoc = getRandomEmptyRadialLocation(world, getLocation(), LAUNCH_RADIUS);
        // determines a random location that is within the throwing (shooting)
        // radius
        if (canRunOverTile(world, toLoc)) {
            return new MoveAndThrowItemCommand(this, new HolyHandGrenade(launchLoc), toLoc);
        } else {
            setISDEAD(true);
            return new WaitCommand();
        }

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

}
