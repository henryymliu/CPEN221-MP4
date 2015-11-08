package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class ThrowCommand implements Command {
	private final Item thrownItem;
	private final MoveableItem moveableItem;

	
	public ThrowCommand(MoveableItem moveItem, Item thrownItem){
		this.moveableItem = moveItem;
		this.thrownItem = thrownItem;
		
	}
	@Override
	public void execute(World world) throws InvalidCommandException {
		if (thrownItem.isDead()) {
			return;
		}
		if (!Util.isValidLocation(world, thrownItem.getLocation()) || !Util.isLocationEmpty(world, thrownItem.getLocation())) {
			throw new InvalidCommandException("Invalid ThrowCommand: Invalid/non-empty target location " + thrownItem.getLocation().toString());
		}
		world.addItem(thrownItem);
		

	}

}
