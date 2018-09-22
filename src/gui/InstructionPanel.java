package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class InstructionPanel extends JPanel{
	private BufferedImage backGround;
	private final int WIDTH = 850;
	private final int HEIGHT = 580;
	
	public InstructionPanel() {
	//	this.setSize(WIDTH,HEIGHT);
	//	this.setBackground(Color.BLACK);
		this.init();
	}
	
	public void init() {
		//get image
		backGround = null;
		try {
			backGround = ImageIO.read(new File("./\\data\\instructions.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	/**
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		//draw background image
		g.drawImage(backGround, 0, 0,WIDTH+50,HEIGHT+50, null);
	}

}
