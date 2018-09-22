/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package model;
public class AnglorEntity extends Entities{
	
	public AnglorEntity() {
		
	}

	@Override
	public int getX() {return this.pX;}
	@Override
	public int getY() {return this.pY;}
	@Override
	public void setX(int pX) {this.pX = pX;}
	@Override
	public void setY(int pY) {this.pY = pY;}

	@Override
	public void accept(IDrawVisitor visitor) {
		visitor.draw(this);
	}

}
