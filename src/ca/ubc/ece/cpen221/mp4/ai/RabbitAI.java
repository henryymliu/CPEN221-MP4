package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

    private int closest = 10; // max number; greater than rabbit's view range
    private static final int RABBIT_DENSITY = 2;
    private static final int BREEDING_THRESHOLD = 45;
    private int temp;
    private boolean foxFound;

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI rules.

        Map<Item, Integer> itemDistance = new HashMap<Item, Integer>();
        Set<Item> surroundings = world.searchSurroundings(animal);
        List<Item> itemCandidates = new ArrayList<Item>();
        Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
        int numRabbits = 0;

        // Density implementation
        for (Item item : surroundings) {
            if (item.getName().equals("Rabbit")) {
                numRabbits++;
            }
        }

        // Breeding implementation
        for (Item item : surroundings) {
            if (item.getName().equals("Fox")) {
                if (animal.getLocation().getDistance(item.getLocation()) > 3) {
                    if ((animal.getEnergy() > BREEDING_THRESHOLD) && (numRabbits <= RABBIT_DENSITY)
                            && (randLoc != null)) {
                        return new BreedCommand(animal, randLoc);
                    }
                }
            }
        }

        // Run Away from Fox
        for (Item item : surroundings) {
            if (item.getName().equals("Fox")) {
                if (animal.getLocation().getDistance(item.getLocation()) <= 3) {
                    return moveInOppositeDirection(world, animal,
                            Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
                }
            }
        }

        // Eat Grass
        for (Item item : surroundings) {
            if (item.getName().equals("grass") && animal.getLocation().getDistance(item.getLocation()) == 1) {
                return new EatCommand(animal, item);
            }
        }

        // Run Towards Grass
        for (Item item : surroundings) {
            if (item.getName().equals("grass")) {
                return moveInDirection(world, animal,
                        Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
            }
        }

        // Moves RandomDirection
        if (randLoc != null) {
            return new MoveCommand(animal, randLoc);
        } else {
            return new WaitCommand();
        }
    }

    public Command moveInDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
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
        Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
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