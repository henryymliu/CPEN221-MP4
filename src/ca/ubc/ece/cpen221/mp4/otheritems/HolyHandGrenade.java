package ca.ubc.ece.cpen221.mp4.otheritems;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

/*
 * HolyHandGrenade destroys everything in a specified radius
 */
public class HolyHandGrenade implements ActingItem{
	private final static ImageIcon HHGImage = Util.loadImage("HolyHandGrenade.png");
	Location loc;
	private int fuseTime = 3; // 1, 2, 5!!! -you mean 3, sir
	private static final int RADIUS = 2;
	Boolean isDead;
	
	public HolyHandGrenade(Location loc) {
		this.loc = loc;
		this.isDead = false;
	}

	@Override
	public int getPlantCalories() {
		
		return 0;
	}

	@Override
	public int getMeatCalories() {
		// you think you can eat a grenade?
		return 0;
	}

	@Override
	public int getCoolDownPeriod() {
		return 0;
	}

	@Override
	public Command getNextAction(World world) {
		Set<Item> toKill = new HashSet<Item>(world.searchSurroundings(this.loc, RADIUS));
		if (fuseTime == 0) {
			for (Item toDie : toKill) {
				toDie.loseEnergy(Integer.MAX_VALUE);
			}
			isDead = true;
			
		}
		else{
			fuseTime--;
		}
		return new WaitCommand();
	}

	@Override
	public ImageIcon getImage() {

		return HHGImage;
	}

	@Override
	public String getName() {

		return "Holy Hand Grenade";
	}

	@Override
	public Location getLocation() {

		return loc;
	}

	@Override
	public int getStrength() {
		return 500;
	}

	@Override
	public void loseEnergy(int energy) {
		return;

	}

	@Override
	public boolean isDead() {
	
		return isDead;
	}

}
