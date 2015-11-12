package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveAndThrowItemCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.otheritems.Wall;

/*
 * Donald Trump moves randomly and creates walls behind him
 */
public class DonaldTrump extends AbstractVehicle {

    private static final int MAX_TURN_SPEED = 3;

    private static final int STRENGTH = 3000;

    private int cooldown = 5;
    private int MAX_COOLDOWN = 5;
    //private int straightLineDistanceTravelled = 0;
    private static final int maxStraightLineDistance = 5;
    
    //private Direction currentDir = Util.getRandomDirection();
    //private Direction newDir = Util.getRandomDirection();

    private static final ImageIcon trumpImage = Util.loadImage("trump.jpg");
    private static final String name = "Trump";
    public DonaldTrump(Location initLoc) {

        //this.loc = initLoc;
        setLOCATION(initLoc);
        setIMAGE(trumpImage);
        setNAME(name);
        setISDEAD(false);
        setCOOLDOWN(cooldown);
        setMAXCOOLDOWN(MAX_COOLDOWN);
        setSTRENGTH(STRENGTH);
        setMAXTURNSPEED(MAX_TURN_SPEED);
        setMAXSTRAIGHTLINEDISTANCE(maxStraightLineDistance);
    }

    /**
     * Returns commands for Donald Trump to move and build walls. Priorities:
     * Move and build wars to keep intruders out.
     * 
     * @param world:
     *            world that Donald Trump is in
     * @return Command: Move and Create wall or Wait if Donald Trump is dead
     */
    @Override
    public Command getNextAction(World world) {
    	
    	Location toLoc = getNewVehicleMoveLocation(world);
        if (canRunOverTile(world, toLoc)) {
            return new MoveAndThrowItemCommand(this, new Wall(getLocation()), toLoc);
        } else {
            setISDEAD(true);
            return new WaitCommand();
        }

    }
    
}
