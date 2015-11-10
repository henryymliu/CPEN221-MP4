/**
 * 
 */
package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * @author Henry
 *
 */
public interface Vehicle extends MoveableItem, Actor{
	
	/**
	 * Returns strength of given vehicle.
	 */
	int getStrength();
	
	/**
	 * Returns speed of vehicle, represented by cooldown period.
	 * @return speed; represented by cooldown
	 */
	int getSpeed();
	
	/**
	 * Returns minimum speed before vehicle can change direction
	 * @return minimum speed for object to change dir
	 */
	int getMinTurnSpeed();
	
	/**
	 * Returns how far vehicle can see.
	 * @return view radius of vehicle
	 */
	int getViewRange();
	
	/*
	 * Checks whether vehicle can 'run over' object in neighboring tile in direction.
	 * Returns true if neighboring tile is empty or has weaker object, false if object in tile
	 * is stronger than vehicle.
	 * 
	 * @param world world to check items
	 * @param Location location to move to
	 * @param v current vehicle
	 * @requires location  is valid (within boundaries of world)
	 */
	static boolean canRunOverTile(World world, Location location, Vehicle v){
		Location loc = new Location(location);
		
		for (Item item : world.getItems()) {
			if (item.getLocation().equals(loc)) {
				if(item.getStrength() > v.getStrength()){
					return false;
				}
				//empty tile out
				item.loseEnergy(Integer.MAX_VALUE);
				return true;
			}
		}
		
		return true;
		
	}
	
	
}
