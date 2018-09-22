/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.AnglorEntity;
import model.BallEntity;
import model.Entities;
import model.EnumComponents;
import model.EnumDirection;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener{
	
	private boolean state;
	private Entities anglor;
	private ArrayList<Entities> balls;
	private DrawComponentVisitor drawVisitor;
	private final int WIDTH = 900;
	private final int HEIGHT = 700;
	//private final int SIZE = 100;
	private final int SIZE = 50;
	private final int numberOfBalls = 20;
	private Random rand ;
	private int score;
	private  int time;
	private String name;
	
	private int atATime;
	private int ballIndex;

	private BufferedImage ground;
	private BufferedImage groundUp;
	private BufferedImage groundDown;
	private BufferedImage groundUp2;
	private BufferedImage groundDown2;
	private BufferedImage tunnel;
	private int chance;
	private int showNextBallAfterXMovesOffirstBall;
	
	public GamePanel() {
		//i don't understand this focus stuff
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		
		this.init();
		//get images
		this.getImages();
	}
	
	public void init() {
		this.time = 90;
		this.state = false;
		this.anglor = new AnglorEntity();
		this.anglor.setDirection(EnumDirection.UP);
		this.name = "";
		this.anglor.setX(400);
		this.anglor.setY(300);
		this.balls = new  ArrayList<Entities>();
		this.drawVisitor = new DrawComponentVisitor();
		rand = new Random();
		this.initializeBalls();
		 this.atATime = 1;
		 this.ballIndex = 0;
		 this.showNextBallAfterXMovesOffirstBall = 10;
	}
	
	public void upDate() {
		
		this.makeBallsMove();
		this.move();
		this.changeBallDirection();
		this.looseConditions();
		this.repaint();
		
	}
	
	public int getNumberOfBalls() {
		return this.numberOfBalls;
	}
	
	private void move() {
		for(Entities ball : balls) {
			
			if(ball.getDirection() == EnumDirection.UP) {
				ball.setY(ball.getY()-ball.getRate());
			}else if(ball.getDirection() == EnumDirection.DOWN){
				ball.setY(ball.getY()+ball.getRate());
			}else if(ball.getDirection() == EnumDirection.LEFT) {
				ball.setX(ball.getX()-ball.getRate());
			}else if(ball.getDirection() == EnumDirection.RIGHT){
				ball.setX(ball.getX()+ball.getRate());
			}
		}
	}
	
	public void initializeBalls() {
		chance = 0;
		for(int i = 0;i <= this.numberOfBalls;i++) {
			 chance = (rand.nextInt(100)+1);
			
			 if(chance > 0 && chance <= 40) {
				 balls.add(new BallEntity(EnumDirection.STOPMOVING,EnumComponents.REDBALL,WIDTH,0));
			 }else if(chance > 40 && chance <= 80) {
				 balls.add(new BallEntity(EnumDirection.STOPMOVING,EnumComponents.BLUEBALL,WIDTH,0));
			 }else if(chance > 80 && chance <= 100) {
				 balls.add(new BallEntity(EnumDirection.STOPMOVING,EnumComponents.BOMB,WIDTH,0));
			 }
		}
	}
	
	public void changeBallDirection() {
		for(Entities ball : balls) {
			if((ball.getX() == anglor.getX()) && (ball.getY() == anglor.getY())) {
				if(anglor.getDirection() == EnumDirection.UP) {
					ball.setDirection(EnumDirection.UP);
				}else if(anglor.getDirection() == EnumDirection.DOWN) {
					ball.setDirection(EnumDirection.DOWN);
				}else if(anglor.getDirection() == EnumDirection.LEFT) {
					ball.setDirection(EnumDirection.LEFT);
				}else if(anglor.getDirection() == EnumDirection.RIGHT) {
					ball.setDirection(EnumDirection.RIGHT);
				}else {
					ball.setDirection(EnumDirection.UP);
				}
			}
		}
	}

	public void looseConditions() {
		for(Entities ball : balls) {
			
			if(ball.getX() > WIDTH-SIZE || ball.getX() < -SIZE) {
				ball.setDirection(EnumDirection.STOPMOVING);
				this.randomLeftOrRight(ball);
			}else if(ball.getY() < 0) {
				if(ball.getCOMPONETS() == EnumComponents.BLUEBALL) {
					this.setState(false);
				}else if(ball.getCOMPONETS() == EnumComponents.REDBALL) {
					this.score+=1;
					ball.setDirection(EnumDirection.STOPMOVING);
					this.randomLeftOrRight(ball);
				}else if(ball.getCOMPONETS() == EnumComponents.BOMB) {
					this.setState(false);
				}
			}else if(ball.getY() >= HEIGHT-2*SIZE) {
				if(ball.getCOMPONETS() == EnumComponents.REDBALL) {
					this.setState(false);
				}else if(ball.getCOMPONETS() == EnumComponents.BLUEBALL) {
					this.score+=1;
					ball.setDirection(EnumDirection.STOPMOVING);
					this.randomLeftOrRight(ball);
				}else if(ball.getCOMPONETS() == EnumComponents.BOMB) {
					this.setState(false);
				}
			}
		}
	}
	
	public void speedUp() {
		this.time-=10;
	}
	
	public void makeBallsMove() {
		
		if(atATime == showNextBallAfterXMovesOffirstBall) {
			
			if(ballIndex <= (balls.size()-1)) {
				this.randomLeftOrRight(balls.get(ballIndex));
				
				//make balls moves
				if(WIDTH-2*anglor.getSize() == balls.get(ballIndex).getX()) {
					balls.get(ballIndex).setDirection(EnumDirection.LEFT);
					
				}else if(0 == balls.get(ballIndex).getX()){
					balls.get(ballIndex).setDirection(EnumDirection.RIGHT);
				}
				ballIndex++;
				atATime=1;
			}else {
				ballIndex=0;
				this.speedUp();
			}
		
		}else {
			atATime++;
		}
	}
	/**
	 * 
	 * @param ball
	 */
	public void randomLeftOrRight(Entities ball) {
		chance = (rand.nextInt(100)+1);
		if(chance <= 50) {
			ball.setX(WIDTH-2*anglor.getSize());
			ball.setY(anglor.getY());
			
		}else if(chance >50 && chance <= 100){
			ball.setX(0);
			ball.setY(anglor.getY());
		}
	}
	
 	public void getImages() {
		try {
		ground = ImageIO.read(new File("./\\data\\ground.png"));
		groundUp = ImageIO.read(new File("./\\data\\upGround.png"));
		groundDown = ImageIO.read(new File("./\\data\\downGround.png"));
		groundUp2 = ImageIO.read(new File("./\\data\\upGround2.png"));
		groundDown2 = ImageIO.read(new File("./\\data\\downGround2.png"));
		tunnel = ImageIO.read(new File("./\\data\\tunnel.png"));
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//clear screen
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draw grid
		//draw horizintal line
		g.setColor(Color.BLACK);
		for(int i = 0;i <= this.WIDTH;i+=SIZE) {
			g.drawLine(i, 0, i, this.HEIGHT);
		}
		//draw vertical line
		for(int j = 0;j <= this.HEIGHT;j+=SIZE) {
			g.drawLine(0, j, this.WIDTH,j );
		}
		
		this.drawComponents(g);	
	}

	private void drawComponents(Graphics g) {
	//draw up and down ground
		for(int i = 0;i < WIDTH-SIZE;i+=SIZE) {
			g.drawImage(groundUp,i , 0, SIZE, SIZE, null);
			g.drawImage(groundDown,i , HEIGHT-2*SIZE, SIZE, SIZE, null);
			
		}
		
	//draw specific ground 
		g.drawImage(groundUp2,anglor.getX() , 0, SIZE, SIZE, null);
		g.drawImage(groundDown2,anglor.getX() , HEIGHT-2*SIZE, SIZE, SIZE, null);
		
		
	//draw ground 
		for(int j = SIZE;j<=HEIGHT-3*SIZE;j+=SIZE) {
		
			for(int i = 0;i<WIDTH-SIZE;i+=SIZE) {
				g.drawImage(ground,i , j, SIZE, SIZE, null);
			}
		}
	
	//draw tunnel horizontal and vertical
		
		for(int i = 0;i <= WIDTH-SIZE;i+=SIZE) {
			g.drawImage(tunnel, i,anglor.getY(), SIZE, SIZE, null);
		}
		for(int i = SIZE;i < HEIGHT-2*SIZE;i+=SIZE) {
			g.drawImage(tunnel,anglor.getX() , i, SIZE, SIZE, null);
		}
		
		//set visitor graphics
		drawVisitor.setGraphics(g);
		
	
	//draw balls
		for(Entities ball : balls) {
			//this.addBall(ball,g);
			ball.accept(drawVisitor);
		}
		
	//draw anglor
		this.anglor.accept(drawVisitor);
		
	}
	/**
	 * 
	 * @param state
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) {
			anglor.setDirection(EnumDirection.UP);
		}else if(k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) {
			anglor.setDirection(EnumDirection.DOWN);
		}else if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
			anglor.setDirection(EnumDirection.LEFT);
		}else if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
			anglor.setDirection(EnumDirection.RIGHT);
		}else if(k == KeyEvent.VK_ESCAPE) {
			setState(false);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public DrawComponentVisitor getDrawVisitor() {
		return drawVisitor;
	}

	public void setDrawVisitor(DrawComponentVisitor drawVisitor) {
		this.drawVisitor = drawVisitor;
	}
	
	public void setBalls(ArrayList<Entities> balls) {
		this.balls = balls;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	/**
	 * @return name,score,state,info
	 */
	public String toString() {
		
	     String info = "";
		//return the info array with balls
		for(Entities ball : balls) {
			info = info + ball.getDirection()+":"+ball.getCOMPONETS()+":"+ball.getX()+":"+ball.getY()+",\n";
		}
		//return score and return name
		 String name = this.getName();
		int score = this.getScore();
		//return anglor direction
		EnumDirection anglorDirection = this.anglor.getDirection();
		//return state
		boolean state = this.getState();
		
		return name+":"+score+":"+state+".\n"+info;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
