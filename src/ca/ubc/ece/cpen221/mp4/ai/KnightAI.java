package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class KnightAI extends AbstractAI {
    private static final int FOX_DENSITY = 3;
    private static final int BREEDING_THRESHOLD = 35;
    private static final int ENERGY_THRESHOLD = 50;

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO Auto-generated method stub

        Map<Item, Integer> preyDistance = new HashMap<Item, Integer>();
        Set<Item> surroundings = world.searchSurroundings(animal);
        List<Item> preyCandidates = new ArrayList<Item>();
        Location randLoc = getRandomEmptyAdjacentLocation(world, animal, animal.getLocation());
        Location randMoveLoc = getRandomAdjacentMoveLocation(world, animal, animal.getLocation());
        int numKnights = 0;

        // Eat to survive
        if (animal.getEnergy() <= ENERGY_THRESHOLD) {
            for (Item item : surroundings) {
                if (item.getName().equals("Rabbit") || item.getName().equals("Fox")) {
                    if (animal.getLocation().getDistance(item.getLocation()) == 1) {
                        return new EatCommand(animal, item);
                    }
                }
            }
        }
        
        // Move towards prey
        for (Item item : surroundings) {
            if (item.getName().equals("Rabbit") || item.getName().equals("Fox")) {
                return moveInDirection(world, animal,
                        Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
            }
        }

        // Move in random location if cannot see anything
        if (randMoveLoc != null) {
            return new MoveCommand(animal, randMoveLoc);
        } else {
            return new WaitCommand();
        }
        
    }
    
    public Command moveInDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        Location randLoc = getRandomAdjacentMoveLocation(world, animal, animal.getLocation());
        if (randLoc != null) {
            if (isLocationEmpty(world, animal, new Location(animal.getLocation(), direction))) {
                return new MoveCommand(animal, new Location(animal.getLocation(), direction));
            } else {
                return new MoveCommand(animal, new Location(randLoc));
            }
        }
        return new WaitCommand();
    }

    public Command moveInOppositeDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        Location randLoc = getRandomAdjacentMoveLocation(world, animal, animal.getLocation());
        if (randLoc != null) {
            if (isLocationEmpty(world, animal, new Location(animal.getLocation(), oppositeDir(direction)))) {
                return new MoveCommand(animal, new Location(animal.getLocation(), oppositeDir(direction)));
            } else {
                return new MoveCommand(animal, new Location(randLoc));
            }
        }
        return new WaitCommand();
    }

}
