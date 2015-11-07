package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class ThrowCommand implements Command {
	private final Item thrownItem;
	private final MoveableItem moveableItem;
	private final Location loc;
	
	public ThrowCommand(MoveableItem moveItem, Item thrownItem, Location loc){
		this.moveableItem = moveItem;
		this.thrownItem = thrownItem;
		this.loc = loc;
	}
	@Override
	public void execute(World world) throws InvalidCommandException {
		// TODO Auto-generated method stub

	}

}
