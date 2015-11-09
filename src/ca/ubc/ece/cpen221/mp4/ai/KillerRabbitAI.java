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
	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal){
		String[] predators = {"Holy Hand Grenade"};
		String[] prey = {"Knight", "Fox", "Rabbit"};
		List<String> listOfPredators = new ArrayList<String>(Arrays.asList(predators));
		List<String> listOfPrey = new ArrayList<String>(Arrays.asList(prey));
		return new WaitCommand();
	}

}
