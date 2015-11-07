/**
 * 
 */
package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * @author Henry
 *
 */
public interface Vehicle extends MoveableItem, Actor{
	
	int getStrength();
	
	int getSpeed();
	
	int getMinTurnSpeed();
	
	int getViewRange();
	
	
}
