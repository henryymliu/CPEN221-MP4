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
	private static final int FOX_DENSITY = 3;
	private static final int BREEDING_THRESHOLD = 35;
	
	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI to make decisions regarding
		// the next action.
		Map<Item, Integer> preyDistance = new HashMap<Item, Integer>();
		Set<Item> surroundings = world.searchSurroundings(animal);
		List<Item> preyCandidates = new ArrayList<Item>();
		Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());

		// search surroundings for rabbits and adds the rabbits with their
		// distance to the current fox
		for (Item item : surroundings) {
			if (item.getName().equals("Rabbit")) {
				preyDistance.put(item, (Integer) animal.getLocation().getDistance(item.getLocation()));
				if ((preyDistance.get(item) == 1)
						&& ((animal.getEnergy() + item.getMeatCalories() <= animal.getMaxEnergy()))) {
					return new EatCommand(animal, item);
				} else if (animal.getEnergy() > BREEDING_THRESHOLD){
					return new BreedCommand(animal,randLoc);
				}
				

			}
		}

		List<Integer> distances = new ArrayList<Integer>(preyDistance.values());
		Collections.sort(distances);
		if (distances.isEmpty()) {
			return new MoveCommand(animal, randLoc);
		}
		int shortestDistance = distances.get(0);

		// for now, only look at rabbits that are closest to you
		for (Item rabbit : preyDistance.keySet()) {
			if (preyDistance.get(rabbit) == shortestDistance) {
				Direction dir = Util.getDirectionTowards(animal.getLocation(), rabbit.getLocation());
				Location loc = new Location(animal.getLocation(), dir);
				if(Util.isLocationEmpty((World) world, loc)){
					return new MoveCommand(animal, loc);
				}
			}

		}

		
		if (randLoc != null) {
			return new MoveCommand(animal, randLoc);
		}
		// if you're completely stuck
		else {
			return new WaitCommand();
		}
	}


}
