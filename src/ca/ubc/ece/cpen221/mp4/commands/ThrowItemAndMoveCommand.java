package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class ThrowItemAndMoveCommand implements Command {
	private final Item thrownItem;
	private final MoveableItem moveableItem;
	private final Location targetLoc;
	
	/**
	 * Create new command where a moveable item creates a new item and 'throws' it to another location, then moves to a different location.
	 *
	 * @param moveItem item that throws new item and moves to a different location
	 * @param thrownItem item that is thrown; to be created in world
	 * @param targetLoc location for moveItem to go to
	 */
	public ThrowItemAndMoveCommand(MoveableItem moveItem, Item thrownItem, Location targetLoc){
		this.moveableItem = moveItem;
		this.thrownItem = thrownItem;
		this.targetLoc = targetLoc;
	}
	@Override
	public void execute(World world) throws InvalidCommandException {
		if (thrownItem.isDead()) {
			return;
		}
		
		if (!Util.isValidLocation(world, targetLoc) || !Util.isLocationEmpty(world, targetLoc)) {
			throw new InvalidCommandException("Invalid MoveCommand: Invalid/non-empty target location " + moveableItem.getLocation().toString());
		}
		if (moveableItem.getMovingRange() < targetLoc.getDistance(moveableItem.getLocation())) {
			throw new InvalidCommandException("Invalid MoveCommand: Target location farther than moving range " + moveableItem.getLocation().toString());
		}
		moveableItem.moveTo(targetLoc);
		if (!Util.isValidLocation(world, thrownItem.getLocation()) || !Util.isLocationEmpty(world, thrownItem.getLocation())) {
			throw new InvalidCommandException("Invalid ThrowCommand: Invalid/non-empty target location " + thrownItem.getLocation().toString());
		}
		world.addItem(thrownItem);
		

	}

}
