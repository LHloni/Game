/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import model.AnglorEntity;
import model.BallEntity;
import model.EnumComponents;
import model.EnumDirection;
import model.IDrawVisitor;

public class DrawComponentVisitor implements IDrawVisitor{
	
	public Graphics g;
	
	private BufferedImage redBall;
	private BufferedImage blueBall;
	private BufferedImage blackBall;
	private BufferedImage blackBall1;
	
	private BufferedImage upArrow;
	private BufferedImage downArrow;
	private BufferedImage leftArrow;
	private BufferedImage rightArrow;

	private int one;
	
	public DrawComponentVisitor() {
		init();
	}
	
	public void init() {
		this.getImages();
		this.one = 0;
	}
	/**
	 * @param anglorEntity
	 */
	@Override
	public void draw(AnglorEntity anglorEntity) {
		
		if(anglorEntity.getDirection() == EnumDirection.UP) {
			g.drawImage(upArrow,anglorEntity.getX() , anglorEntity.getY(), anglorEntity.getSize(), anglorEntity.getSize(), null);
		}else if(anglorEntity.getDirection() == EnumDirection.DOWN) {
			g.drawImage(downArrow,anglorEntity.getX() , anglorEntity.getY(), anglorEntity.getSize(), anglorEntity.getSize(), null);
		}else if(anglorEntity.getDirection() == EnumDirection.LEFT) {
			g.drawImage(leftArrow,anglorEntity.getX() , anglorEntity.getY(), anglorEntity.getSize(), anglorEntity.getSize(), null);
		}else if(anglorEntity.getDirection() == EnumDirection.RIGHT) {
			g.drawImage(rightArrow,anglorEntity.getX() , anglorEntity.getY(), anglorEntity.getSize(), anglorEntity.getSize(), null);
		}else {
			g.drawImage(upArrow,anglorEntity.getX() , anglorEntity.getY(), anglorEntity.getSize(), anglorEntity.getSize(), null);
		}
		
	}
	
	public void getImages() {
		
		try {
			redBall = ImageIO.read(new File("./\\data\\redBall.png"));
			blueBall = ImageIO.read(new File("./\\data\\blueBall.png"));
			upArrow = ImageIO.read(new File("./\\data\\upArrow.png"));
			downArrow = ImageIO.read(new File("./\\data\\downArrow.png"));
			leftArrow = ImageIO.read(new File("./\\data\\leftArrow.png"));
			rightArrow = ImageIO.read(new File("./\\data\\rightArrow.png"));
			blackBall = ImageIO.read(new File("./\\data\\bomb.png"));
			blackBall1 = ImageIO.read(new File("./\\data\\bomb1.png"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * @param ballEntity
	 */
	@Override
	public void draw(BallEntity ballEntity) {
		
		if(ballEntity.getCOMPONETS() == EnumComponents.REDBALL) {
			if(ballEntity.getDirection() != EnumDirection.STOPMOVING) {
				g.drawImage(redBall,ballEntity.getX() , ballEntity.getY(), ballEntity.getSize(), ballEntity.getSize(), null);
			}
		}
		if(ballEntity.getCOMPONETS() == EnumComponents.BLUEBALL) {
			if(ballEntity.getDirection() != EnumDirection.STOPMOVING) {
				g.drawImage(blueBall,ballEntity.getX() , ballEntity.getY(), ballEntity.getSize(), ballEntity.getSize(), null);
			}
		}
		if(ballEntity.getCOMPONETS() == EnumComponents.BOMB) {
			if(ballEntity.getDirection() != EnumDirection.STOPMOVING) {
				if(one >= 0 && one <=3) {
					g.drawImage(blackBall,ballEntity.getX() , ballEntity.getY(), ballEntity.getSize(), ballEntity.getSize(), null);
				}else if(one > 3 && one <=6){
					g.drawImage(blackBall1,ballEntity.getX() , ballEntity.getY(), ballEntity.getSize(), ballEntity.getSize(), null);
				}
				this.one++;
				if(one > 6) { one = 0;}
			}
		}
		
	}
	/**
	 * 
	 * @param g
	 */
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	

}
