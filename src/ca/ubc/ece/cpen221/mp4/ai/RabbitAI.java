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
    private static final int RABBIT_DENSITY = 1;
    private static final int BREEDING_THRESHOLD = 60;
    private int temp;
    private boolean foxFound;

    public RabbitAI() {
    }

    /*
     * This implementation of RabbitAI uses priorities, the order of the
     * implementations of the various movements and tasks the rabbit is directly
     * related to the priority of tasks. In this case, the RabbitAI prioritizes
     * breeding, then running away from threats, then eating. If the rabbit
     * cannot do either of the three tasks above, then it will move randomly to
     * try and find a task to do.
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

        Map<Item, Integer> itemDistance = new HashMap<Item, Integer>();
        Set<Item> surroundings = world.searchSurroundings(animal);
        List<Item> itemCandidates = new ArrayList<Item>();
        Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
        Location randMoveLoc = Util.getRandomAdjacentMoveLocation(world, animal.getLocation());
        int numRabbits = 0;

        /*
         * Density implementation: Iterates through all the items and animals
         * surrounding the animal and if the item is another animal of the same
         * type, then increase the density.
         */
        for (Item item : surroundings) {
            if (item.getName().equals("Rabbit")) {
                numRabbits++;
            }
        }

        /*
         * Breeding implementation: Animal breeds if predator is far enough away
         * and breeding conditions are met, if there is no predator, then breed
         * if breeding conditions are met.
         */
        for (Item item : surroundings) {
            if (item.getName().equals("Fox")) {
                if (animal.getLocation().getDistance(item.getLocation()) > 3) {
                    if ((animal.getEnergy() > BREEDING_THRESHOLD) && (numRabbits <= RABBIT_DENSITY)
                            && (randLoc != null)) {
                        return new BreedCommand(animal, randLoc);
                    }
                }
            } else {
                if ((animal.getEnergy() > BREEDING_THRESHOLD) && (numRabbits <= RABBIT_DENSITY) && (randLoc != null)) {
                    return new BreedCommand(animal, randLoc);
                }
            }
        }

        /*
         * Flee predator implementation: Run Away from Fox if it is too close.
         * 
         */
        for (Item item : surroundings) {
            if (item.getName().equals("Fox")) {
                if (animal.getLocation().getDistance(item.getLocation()) <= 3) {
                    return moveInOppositeDirection(world, animal,
                            Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
                }
            }
        }

        /*
         * Eat Grass implementation: Eat grass if it is in the adjacent tile and
         * if the animal requires energy.
         */
        for (Item item : surroundings) {
            if ((item.getName().equals("grass") && animal.getLocation().getDistance(item.getLocation()) == 1)
                    && (animal.getEnergy() + item.getPlantCalories() <= animal.getMaxEnergy())) {
                return new EatCommand(animal, item);
            }
        }

        /*
         * Run Towards Grass implementation: Run towards grass if the animal
         * sees grass
         */
        for (Item item : surroundings) {
            if (item.getName().equals("grass")) {
                return moveInDirection(world, animal,
                        Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
            }
        }

        // Moves RandomDirection (away from other rabbits if there is no food or
        // foxes)
        // for (Item item : surroundings) {
        // if (item.getName().equals("Rabbit")) {
        // if (animal.getLocation().getDistance(item.getLocation()) <= 1){
        // return moveInOppositeDirection(world, animal,
        // Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
        // }
        // }
        // }
        if (randMoveLoc != null) {
            return new MoveCommand(animal, randMoveLoc);
        } else {
            return new WaitCommand();
        }
    }

    /*
     * Moves the animal in the direction of an item
     * 
     * @param world (the current ArenaWorld), animal (the animal to move),
     * direction (the direction of the item)
     * @return Command (MoveCommand in the direction of an item or in a random location if
     * the path is blocked, WaitCommand if there is no valid adjacent tile to
     * move to)
     */
    public Command moveInDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        Location randLoc = Util.getRandomAdjacentMoveLocation(world, animal.getLocation());
        if (randLoc != null) {
            if (isLocationEmpty(world, animal, new Location(animal.getLocation(), direction))) {
                return new MoveCommand(animal, new Location(animal.getLocation(), direction));
            } else {
                return new MoveCommand(animal, new Location(randLoc));
            }
        }
        return new WaitCommand();
    }

    /*
     * Moves the animal opposite of the direction of the item
     * 
     * @param world (the current ArenaWorld), animal (the animal to move),
     * direction (the direction of the item)
     * @return Command (MoveCommand in the opposite direction of an item or in a random location if
     * the path is blocked, WaitCommand if there is no valid adjacent tile to
     * move to)
     */
    public Command moveInOppositeDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        Location randLoc = Util.getRandomAdjacentMoveLocation(world, animal.getLocation());
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
