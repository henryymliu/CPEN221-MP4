package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class KillerRabbit implements ArenaAnimal {

    private final AI ai;
    private Location loc;
    private boolean isDead;
    private static final int VIEW_RANGE = 3;
    private static final int COOLDOWN_PERIOD = 1;
    private static final ImageIcon killerRabbitImage = Util.loadImage("killer_rabbit.gif");
    private static final int STRENGTH = 50;
    private static final int INITIAL_ENERGY = 50;
    private static final int MAX_ENERGY = 120;
    private static final int MIN_BREEDING_ENERGY = 70;
    private int energy = INITIAL_ENERGY;

    
    public KillerRabbit(AI ai, Location initialLocation) {
        this.ai = ai;
        this.loc = initialLocation;
        isDead = false;
        
    }
	
    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public LivingItem breed() {
        KillerRabbit child = new KillerRabbit(ai, loc);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());

    }

    @Override
    public void moveTo(Location targetLocation) {
        loc = targetLocation;

    }

    @Override
    public int getMovingRange() {
        return 1;
    }

    @Override
    public ImageIcon getImage() {
        return killerRabbitImage;
    }

    @Override
    public String getName() {
        return "Killer Rabbit";
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;

    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public int getPlantCalories() {
        // Killer Rabbit is not a plant.
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return energy;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN_PERIOD;
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--; // Loses 1 energy regardless of action.
        return nextAction;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }
}
