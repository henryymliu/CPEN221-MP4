package ca.ubc.ece.cpen221.mp4.items.vehicles;

import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public abstract class AbstractVehicle implements Vehicle {

	private Location loc;
	private boolean isDead;
	private ImageIcon image;
	private String name;
	private int energy;
	private int cooldown;
	private int STRENGTH;
	private int MAX_TURN_SPEED;

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

	protected void setSTRENGTH(int strength) {
		this.STRENGTH = strength;
	}

	protected void setMAXTURNSPEED(int turnspeed) {
		this.MAX_TURN_SPEED = turnspeed;
	}

	@Override
	public void moveTo(Location targetLocation) {
		this.loc = targetLocation;

	}

	@Override
	public int getMovingRange() {

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxTurnSpeed() {
		
		return MAX_TURN_SPEED;
	}

	@Override
	public int getViewRange() {
		// TODO Auto-generated method stub
		return 0;
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
