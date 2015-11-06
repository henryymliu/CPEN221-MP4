package ca.ubc.ece.cpen221.mp4.ai;

import java.util.*;

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
    private int temp;
    private boolean foxFound;
    private static final int MAX_ENERGY = 60;

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI rules.
        Map<Item, Integer> itemDistances = new HashMap<Item, Integer>();
        Set<Item> itemsInRange = world.searchSurroundings(animal);
        List<Integer> itemCandidate = new ArrayList<Integer>();

        if (animal.getEnergy() >= 2 * animal.getMaxEnergy() / 3) {
            return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
        }

        for (Item items : itemsInRange) {

            if (items.getName().equals("grass")) {
                itemDistances.put(items, animal.getLocation().getDistance(items.getLocation()));
                if (animal.getLocation().getDistance(items.getLocation()) == 1) {
                    return new EatCommand(animal, items);
                }
            } else if (items.getName().equals("Fox")) {
                if (animal.getLocation().getDistance(items.getLocation()) <= 3) {
                    Direction direction = Util.getDirectionTowards(animal.getLocation(), items.getLocation());
                    if (direction.equals(Direction.NORTH)) {
                        if (Util.isLocationEmpty((World) world, new Location(animal.getLocation(), Direction.SOUTH))) {
                            return new MoveCommand(animal, new Location(animal.getLocation(), Direction.SOUTH));
                        } else {
                            return new MoveCommand(animal, new Location(
                                    Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
                        }
                    }
                    if (direction.equals(Direction.SOUTH)) {
                        if (Util.isLocationEmpty((World) world, new Location(animal.getLocation(), Direction.NORTH))) {
                            return new MoveCommand(animal, new Location(animal.getLocation(), Direction.NORTH));
                        } else {
                            return new MoveCommand(animal, new Location(
                                    Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
                        }
                    }
                    if (direction.equals(Direction.WEST)) {
                        if (Util.isLocationEmpty((World) world, new Location(animal.getLocation(), Direction.EAST))) {
                            return new MoveCommand(animal, new Location(animal.getLocation(), Direction.EAST));
                        } else {
                            return new MoveCommand(animal, new Location(
                                    Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
                        }
                    }
                    if (direction.equals(Direction.EAST)) {
                        if (Util.isLocationEmpty((World) world, new Location(animal.getLocation(), Direction.WEST))) {
                            return new MoveCommand(animal, new Location(animal.getLocation(), Direction.WEST));
                        } else {
                            return new MoveCommand(animal, new Location(
                                    Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
                        }
                    }
                }
            }
//            } else {
//                while (true) {
//                    Direction dir = Util.getRandomDirection();
//                    if (this.isLocationEmpty(world, animal, new Location(animal.getLocation(), dir))) {
//                        return new MoveCommand(animal,
//                                new Location(Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
//                    }
//                }
//            }
        }
        itemCandidate.addAll(itemDistances.values());
        Collections.sort(itemCandidate);

        return new WaitCommand();
    }
}
