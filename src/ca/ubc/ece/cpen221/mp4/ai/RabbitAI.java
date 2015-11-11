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

    private static final int RABBIT_DENSITY = 1;
    private static final int BREEDING_THRESHOLD = 30;
    private static final int CLOSESTSAFEDISTANCETOFOX = 3;

    public RabbitAI() {
    }

    /**
     * Returns appropriate command for rabbit that ensures its survival.
     * Priorities: Eat, breed, move towards grass and away from foxes
     * 
     * @param world: world that the rabbit can see
     * @param animal: animal that uses this AI
     * @return Command: one of Eat, Breed, or Move
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        
        /*
         * This implementation of RabbitAI uses priorities, the order of the
         * implementations of the various movements and tasks the rabbit is directly
         * related to the priority of tasks. In this case, the RabbitAI prioritizes
         * breeding, then running away from threats, then eating. If the rabbit
         * cannot do either of the three tasks above, then it will move randomly to
         * try and find a task to do.
         */

        Set<Item> surroundings = world.searchSurroundings(animal);
        Location randLoc = getRandomEmptyAdjacentLocation(world,animal, animal.getLocation());
        Location randMoveLoc = getRandomAdjacentMoveLocation(world,animal, animal.getLocation());
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
                if (animal.getLocation().getDistance(item.getLocation()) > CLOSESTSAFEDISTANCETOFOX) {
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
                if (animal.getLocation().getDistance(item.getLocation()) <= CLOSESTSAFEDISTANCETOFOX) {
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

}
