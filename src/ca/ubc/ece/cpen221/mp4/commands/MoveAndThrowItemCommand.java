package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.otheritems.ActingItem;

public class MoveAndThrowItemCommand implements Command {
	private final ActingItem thrownItem;
	private final MoveableItem moveableItem;
	private final Location targetLoc;

	/**
	 * Create new command where a moveable item creates a new item and 'throws'
	 * it to another location, then moves to a different location.
	 *
	 * @param moveItem
	 *            item that throws new item and moves to a different location
	 * @param thrownItem
	 *            item that is thrown; to be created in world
	 * @param targetLoc
	 *            location for moveItem to go to
	 */
	public MoveAndThrowItemCommand(MoveableItem moveItem, ActingItem thrownItem, Location targetLoc) {
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
			throw new InvalidCommandException(
					"Invalid MoveCommand: Invalid/non-empty target location " + moveableItem.getLocation().toString());
		}
		if (moveableItem.getMovingRange() < targetLoc.getDistance(moveableItem.getLocation())) {
			throw new InvalidCommandException("Invalid MoveCommand: Target location farther than moving range "
					+ moveableItem.getLocation().toString());
		}
		moveableItem.moveTo(targetLoc);
		
		if (!Util.isLocationEmpty(world, thrownItem.getLocation())) {
			throw new InvalidCommandException(
					"Invalid ThrowCommand: Invalid/non-empty target location " + thrownItem.getLocation().toString());
		}
		
		if (thrownItem.getLocation() != null) {
			world.addItem(thrownItem);
			world.addActor(thrownItem);
		}

	}

}
