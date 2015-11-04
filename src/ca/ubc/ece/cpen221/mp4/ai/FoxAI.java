package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.*;
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

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
	private int closest = 2; // max number; greater than fox's view range

	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI to make decisions regarding
		// the next action.
		Map<Item, Integer> preyDistance = new HashMap<Item, Integer>();
		Set<Item> surroundings = world.searchSurroundings(animal);
		List<Item> preyCandidates = new ArrayList<Item>();

		// search surroundings for rabbits and adds the rabbits with their
		// distance to the current fox
		for (Item item : surroundings) {
			if (item.getName().equals("Rabbit")) {
				preyDistance.put(item, (Integer) animal.getLocation().getDistance(item.getLocation()));
				if (preyDistance.get(item) == 1) {
					return new EatCommand(animal, item);
				}

			}
		}

		List<Integer> distances = new ArrayList<Integer>(preyDistance.values());
		Collections.sort(distances);
		if(distances.isEmpty()){
			return new WaitCommand();
		}
		int shortestDistance = distances.get(0);

		Direction xDir = null;
		Direction yDir = null;
		Location xLoc;
		Location yLoc;
		// for now, only look at rabbits that are closest to you
		for (Item rabbit : preyDistance.keySet()) {
			if (preyDistance.containsKey(shortestDistance)) {
				if (rabbit.getLocation().getX() > animal.getLocation().getX()) {
					xDir = Direction.EAST;
					
				} else if (rabbit.getLocation().getX() == animal.getLocation().getX()) {
					if (rabbit.getLocation().getY() > animal.getLocation().getY()) {
						yDir = Direction.NORTH;
					}
					else{
						yDir = Direction.SOUTH;
					}
					yLoc = new Location(animal.getLocation(), yDir);
					if(isLocationEmpty(world, animal, yLoc)){
						return new MoveCommand(animal, yLoc);
					}
					else if (isLocationEmpty(world, animal, new Location(animal.getLocation(), oppositeDir(yDir)))){
						return new MoveCommand(animal, new Location(animal.getLocation(), oppositeDir(yDir)));
					}
					
				}
				else{
					xDir = Direction.WEST;
				}
				xLoc = new Location(animal.getLocation(), xDir);
				
				if (rabbit.getLocation().getY() > animal.getLocation().getY()) {
					yDir = Direction.NORTH;
				} else if (rabbit.getLocation().getY() == animal.getLocation().getY()){
					if (rabbit.getLocation().getX() > animal.getLocation().getX()) {
						xDir = Direction.EAST;
					}
					else{
						xDir = Direction.WEST;
					}
					xLoc = new Location(animal.getLocation(), xDir);
					if(isLocationEmpty(world, animal, xLoc)){
						return new MoveCommand(animal, xLoc);
					}
					else if (isLocationEmpty(world, animal, new Location(animal.getLocation(), oppositeDir(xDir)))){
						return new MoveCommand(animal, new Location(animal.getLocation(), oppositeDir(xDir)));
					}
				}
				else{
					yDir = Direction.SOUTH;
				}
				yLoc = new Location(animal.getLocation(), yDir);

				if (isLocationEmpty(world, animal, xLoc)) {
					return new MoveCommand(animal, xLoc);
				}
				
				else if (isLocationEmpty(world, animal, yLoc)) {
					return new MoveCommand(animal, yLoc);
				}
			}
		}
		
		for(Direction dir : Direction.values()){
			Location loc = new Location(animal.getLocation(), dir);
			if (isLocationEmpty(world, animal, loc)) {
				return new MoveCommand(animal, loc);
			}
		}
		
		//if you're completely stuck
		return new WaitCommand();
	}

}
