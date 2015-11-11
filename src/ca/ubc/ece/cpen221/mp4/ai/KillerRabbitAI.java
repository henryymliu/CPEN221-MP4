package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class KillerRabbitAI extends AbstractAI {

	public KillerRabbitAI() {
		
	}
	
    /**
     * Returns appropriate command for fox that ensures its survival.
     * Priorities: Eat, breed, move towards rabbit
     * 
     * @param world: world that the Killer Rabbit can see
     * @param animal: animal that uses this AI
     * @return Command: one of Eat, Breed, or Move
     */
	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal){
		String[] predators = {"Holy Hand Grenade"};
		String[] prey = {"Knight", "Fox", "Rabbit"};
		List<String> listOfPredators = new ArrayList<String>(Arrays.asList(predators));
		List<String> listOfPrey = new ArrayList<String>(Arrays.asList(prey));
		return new WaitCommand();
	}

}
