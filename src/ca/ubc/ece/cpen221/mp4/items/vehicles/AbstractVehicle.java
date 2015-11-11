package ca.ubc.ece.cpen221.mp4.items.vehicles;

import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public abstract class AbstractVehicle implements Vehicle {
	
	private boolean isDead;
	//setter methods
	

	@Override
	public void moveTo(Location targetLocation) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCoolDownPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Command getNextAction(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxTurnSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { // returns
		// true
		// if
		// location
		// is
		// empty
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

}
