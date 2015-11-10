package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

public class AbstractAI implements AI {

	public Direction oppositeDir(Direction dir) { // returns opposite direction
													// of direction dir
		if (dir == Direction.EAST) {
			return Direction.WEST;
		} else if (dir == Direction.WEST) {
			return Direction.EAST;
		} else if (dir == Direction.SOUTH) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH;
		}
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

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		return new WaitCommand();
	}
	
	/**
	 * Returns location that is adjacent only in the four cardinal directions to the given animal
	 * and location. 
	 * @param world world to search in
	 * @param animal animal to search surroundings with
	 * @param loc location to check adjacency to.
	 * @return Random empty location that animal can move to; null if no such location exists.
	 */
	public Location getRandomAdjacentMoveLocation(ArenaWorld world, ArenaAnimal animal, Location loc){
		Boolean isEmpty = false;
		//check if there are actually any valid locations
		for(Direction d:Direction.values()){
			if(isLocationEmpty(world, animal, new Location(loc, d))){
				isEmpty = true;
				break;
			}
		}
		if(!isEmpty){
			return null;
		}
		Direction dir = Util.getRandomDirection();
		Location newLoc = new Location(loc, dir);
		while(!isLocationEmpty(world, animal, newLoc)){
			dir = Util.getRandomDirection();
			newLoc = new Location(loc, dir);
		}
		return newLoc;
	}
	/**
	 * Returns location in random direction that is adjacent to the given location of the animal.
	 * 
	 * @param world world to search in
	 * @param animal animal to search with
	 * @param loc location to search adjacent locations to
	 * @return Random empty location that is adjacent (one of 8 tiles touching animal) to animal; null if 
	 * no such location exists
	 */
	public Location getRandomEmptyAdjacentLocation(ArenaWorld world, ArenaAnimal animal, Location loc) {
		Location[] neighbors = new Location[3 * 3]; // 3 x 3 bounding box
		int numLocs = 0;
		for (int x = loc.getX() - 1; x <= loc.getX() + 1; x++) {
			for (int y = loc.getY() - 1; y <= loc.getY() + 1; y++) {
				Location l = new Location(x, y);
				if (Util.isValidLocation(world, l) && isLocationEmpty(world,animal, l)) {
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
