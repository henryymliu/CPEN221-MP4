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
	private int MOVING_RANGE = 1;
	private static final int MAX_TURN_SPEED = 3;
	private static final int VIEW_RANGE = 2;
	private static final int STRENGTH = 3000;
	//private int MIN_COOLDOWN = 2;
	// private int speed = 1;
	private int cooldown = 5;
	private int MAX_COOLDOWN = 5;
	private int straightLineDistanceTravelled = 0;
	private static final int maxStraightLineDistance = 3;
	private boolean isDead;
	private Location loc;
	private Location prevLoc;
	private Direction currentDir = Util.getRandomDirection();
	private Direction newDir = Util.getRandomDirection();

	private static final ImageIcon trumpImage = Util.loadImage("trump.jpg");

	public DonaldTrump(Location initLoc) {

		this.loc = initLoc;
		isDead = false;
	}

    /**
     * Returns commands for Donald Trump to move and build walls.
     * Priorities: Move and build wars to keep intruders out.
     * 
     * @param world: world that Donald Trump is in
     * @return Command: Move and Create wall or Wait if Donald Trump is dead
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
		Location toLoc = new Location(loc, currentDir);
		while (!Util.isValidLocation(world, toLoc)) {
			currentDir = Util.getRandomDirection();
			straightLineDistanceTravelled = 0;
			cooldown = MAX_COOLDOWN;
			toLoc = new Location(loc, currentDir);
		}
		if (Vehicle.canRunOverTile(world, toLoc, this)) {
			return new MoveAndThrowItemCommand(this, new Wall(loc), toLoc);
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

		return trumpImage;
	}

	@Override
	public String getName() {

		return "Trump";
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
