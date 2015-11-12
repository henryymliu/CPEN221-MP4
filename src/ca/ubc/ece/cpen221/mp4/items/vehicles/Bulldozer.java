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
public class Bulldozer extends AbstractVehicle {

    private static final int MAX_TURN_SPEED = 2;
 
    private static final int STRENGTH = 5000;
    private int MIN_COOLDOWN = 2;
    private int cooldown = 6;
    private int MAX_COOLDOWN = 6;
    private int maxStraightLineDistance = 2;
    private boolean isDead;
    private Location loc;
    private Location prevLoc;
    private Direction currentDir = Util.getRandomDirection();
    private Direction newDir = Util.getRandomDirection();

    private static final ImageIcon bulldozerImage = Util.loadImage("bulldozer.jpg");
    private static final String name = "Bulldozer";
    public Bulldozer(Location initLoc) {

    	setLOCATION(initLoc);
        setIMAGE(bulldozerImage);
        setNAME(name);
        setISDEAD(false);
        setCOOLDOWN(cooldown);
        setSTRENGTH(STRENGTH);
        setMAXTURNSPEED(MAX_TURN_SPEED);
        setMAXCOOLDOWN(MAX_COOLDOWN);
        setMAXSTRAIGHTLINEDISTANCE(maxStraightLineDistance);
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
    	
    	Location toLoc = getNewVehicleMoveLocation(world);
        if (canRunOverTile(world, toLoc)) {
            return new MoveCommand(this, toLoc);
        } else {
            setISDEAD(true);
            return new WaitCommand();
        }

    }

    
}
