package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class SniperAI extends AbstractAI {

    public SniperAI() {
        
    }
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal){

        return new WaitCommand();
    }

}
