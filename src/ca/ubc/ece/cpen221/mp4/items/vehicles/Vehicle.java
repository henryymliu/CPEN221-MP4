/**
 * 
 */
package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public interface Vehicle extends MoveableItem, Actor {

    /**
     * Returns strength of given vehicle.
     */
    int getStrength();

    /**
     * Returns speed of vehicle, represented by cooldown period.
     * 
     * @return speed; represented by cooldown
     */
    int getSpeed();

    /**
     * Returns minimum speed before vehicle can change direction
     * 
     * @return minimum speed for object to change dir
     */
    int getMaxTurnSpeed();

    /**
     * Returns how far vehicle can see.
     * 
     * @return view radius of vehicle
     */
    int getViewRange();

    
}
