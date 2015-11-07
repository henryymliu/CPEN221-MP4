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
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
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

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI rules.
        Map<Item, Integer> itemDistances = new HashMap<Item, Integer>();
        Set<Item> itemsInRange = world.searchSurroundings(animal);
        List<Integer> itemCandidate = new ArrayList<Integer>();

        for(Item item: itemsInRange){
            itemDistances.put(item, animal.getLocation().getDistance(item.getLocation()));
        }
        itemCandidate.addAll(itemDistances.values());
        Collections.sort(itemCandidate);
        Integer closestDistance = itemCandidate.get(0);
        
        for (Item closestItem: itemDistances.keySet()){
            
        }
        
        
        if (animal.getEnergy() >= 4 * animal.getMaxEnergy() / 5) {
            return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation()));
        } else {
            for (Item items : itemsInRange) {

                if (items.getName().equals("grass")) {
                    itemDistances.put(items, animal.getLocation().getDistance(items.getLocation()));
                    if (animal.getLocation().getDistance(items.getLocation()) == 1) {
                        return new EatCommand(animal, items);

                    }
                } else if (items.getName().equals("Fox")) {
                    if (animal.getLocation().getDistance(items.getLocation()) <= 2) {
                        Direction direction = Util.getDirectionTowards(animal.getLocation(), items.getLocation());
                        if (direction.equals(Direction.NORTH)) {
                            return moveCommandOppositeDirection(world, animal, direction);
                        }
                        if (direction.equals(Direction.SOUTH)) {
                            return moveCommandOppositeDirection(world, animal, direction);
                        }
                        if (direction.equals(Direction.WEST)) {
                            return moveCommandOppositeDirection(world, animal, direction);
                        }
                        if (direction.equals(Direction.EAST)) {
                            return moveCommandOppositeDirection(world, animal, direction);
                        }
                    }

                } else {
                    this.moveCommandRandom(world, animal);

                }
            }
            return new WaitCommand();
        }
    }

    public Command moveCommandOppositeDirection(ArenaWorld world, ArenaAnimal animal, Direction direction) {
        if (this.isLocationEmpty(world, animal, new Location(animal.getLocation(), direction))) {
            return new MoveCommand(animal, new Location(animal.getLocation(), this.oppositeDir(direction)));
        } else {
            return new MoveCommand(animal,
                    new Location(Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
        }
    }

    public Command moveCommandRandom(ArenaWorld world, ArenaAnimal animal) {
        if (!this.isStuck(world, animal)) {
            return new MoveCommand(animal,
                    new Location(Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation())));
        } else {
            return new WaitCommand();
        }
    }

    public boolean isStuck(ArenaWorld world, ArenaAnimal animal) {
        for (Direction dir : Direction.values()) {
            if (this.isLocationEmpty(world, animal, new Location(animal.getLocation(), dir))) {
                return false;
            }

        }
        return true;
    }
}
