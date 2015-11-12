package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class KillerRabbit extends AbstractArenaAnimal {

    private final AI ai;
    private Location loc;
    private static final int VIEW_RANGE = 3;
    private static final int COOLDOWN_PERIOD = 1;
    private static final ImageIcon killerRabbitImage = Util.loadImage("hyena.gif");
    private static final int STRENGTH = 50;
    private static final int INITIAL_ENERGY = 50;
    private static final int MAX_ENERGY = 120;
    private static final int MIN_BREEDING_ENERGY = 70;
    private int energy = INITIAL_ENERGY;

    public KillerRabbit(AI ai, Location initialLocation) {
        this.ai = ai;
        setLocation(initialLocation);
        setCOOLDOWN(COOLDOWN_PERIOD);
        setMAX_ENERGY(MAX_ENERGY);
        setSTRENGTH(STRENGTH);
        setVIEW_RANGE(VIEW_RANGE);
        setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
        //setIMAGE(kilerRabbitImage);

    }

    @Override
    public LivingItem breed() {
        KillerRabbit child = new KillerRabbit(ai, loc);
        child.setEnergy( this.getEnergy() / 2);
        this.setEnergy(this.getEnergy()/2);
        return child;
    }

    @Override
    public String getName() {

        return "KillerRabbit";
    }

    @Override
    public ImageIcon getImage() {
        return killerRabbitImage;
    }
    
    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.setEnergy(getEnergy()-1); // Loses 1 energy regardless of action.
        return nextAction;
    }
    

}
