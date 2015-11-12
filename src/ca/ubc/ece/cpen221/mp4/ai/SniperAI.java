package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Sniper AI
 */
public class SniperAI extends AbstractAI {

    public SniperAI() {

    }

    /**
     * Returns command for sniper that make him camp and wait. Priorities: Camp
     * 
     * @param world:
     *            world that the rabbit can see
     * @param animal:
     *            animal that uses this AI
     * @return Command: Wait (camp)
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

        return new WaitCommand();
    }

}
