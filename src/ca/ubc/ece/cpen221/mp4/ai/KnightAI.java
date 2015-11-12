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

/*
 * Knight AI
 */
public class KnightAI extends AbstractAI {
    private static final int FOX_DENSITY = 3;
    private static final int BREEDING_THRESHOLD = 35;
    private static final int ENERGY_THRESHOLD = 50;

    /**
     * Returns appropriate command for knight that ensures its survival.
     * Priorities: Eat and move towards rabbits and foxes.
     * 
     * @param world:
     *            world that the Knight can see
     * @param animal:
     *            animal that uses this AI
     * @return Command: one of Eat prey or Move towards prey; Wait if cannot do
     *         anything else
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

        Set<Item> surroundings = world.searchSurroundings(animal);
        Location randMoveLoc = getRandomAdjacentMoveLocation(world, animal, animal.getLocation());

        /*
         * Eat to survive: eat only if energy is below desired level and attempt
         * to eat rabbits or foxes within 1 distance.
         */
        if (animal.getEnergy() <= ENERGY_THRESHOLD) {
            for (Item item : surroundings) {
                if (item.getName().equals("Rabbit") || item.getName().equals("Fox")) {
                    if (animal.getLocation().getDistance(item.getLocation()) == 1) {
                        return new EatCommand(animal, item);
                    }
                }
            }
        }

        /*
         * Move towards prey: move towards rabbits and foxes.
         */
        for (Item item : surroundings) {
            if (item.getName().equals("Rabbit") || item.getName().equals("Fox")) {
                return moveInDirection(world, animal,
                        Util.getDirectionTowards(animal.getLocation(), item.getLocation()));
            }
        }

        /*
         * Move in random location if cannot see anything
         */
        if (randMoveLoc != null) {
            return new MoveCommand(animal, randMoveLoc);
        } else {
            return new WaitCommand();
        }

    }

}
