/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package model;
public abstract class Entities implements IDrawable{

	//private final int SIZE = 100;
	private final int SIZE = 50;
	private final int RATE = 25;
	private final int HEIGHT = 600;
	private final int WIDTH = 800;
	protected int pX;
	protected int pY;
	protected EnumComponents COMPONETS;
	protected EnumDirection DIRECTION;
	
	public abstract int getX();
	public abstract int getY();
	public abstract void setX(int pX);
	public abstract void setY(int py);
	
	public int getSize() {
		return SIZE;
	}
	public int getRate() {
		return RATE;
	}
	public int getHeight() {
		return HEIGHT;
	}
	public int getWidth() {
		return WIDTH;
	}
	public EnumComponents getCOMPONETS() {
		return COMPONETS;
	}
	public void setCOMPONETS(EnumComponents cOMPONETS) {
		COMPONETS = cOMPONETS;
	}
	public EnumDirection getDirection() {
		return DIRECTION;
	}
	public void setDirection(EnumDirection direction) {
		DIRECTION = direction;
	}
}
