package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;

public class DonaldTrump implements Vehicle {
	private int getMovingRange = 1;
	private static final int MIN_TURN_SPEED = 2;
	private static final int VIEW_RANGE = 2;
	private static final int STRENGTH = 250;
	private int speed = 1;
	private int cooldown = 5;
	@Override
	public void moveTo(Location targetLocation) {
		// TODO Auto-generated method stub

	}
	@Override
	public Command getNextAction(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMovingRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImageIcon getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loseEnergy(int energy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPlantCalories() {
		// TODO Auto-generated method stub
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
		
		return speed;
	}

	@Override
	public int getMinTurnSpeed() {
		
		return MIN_TURN_SPEED;
	}

	@Override
	public int getViewRange() {
		
		return VIEW_RANGE;
	}

}
