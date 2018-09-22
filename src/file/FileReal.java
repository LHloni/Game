/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.GamePanel;
import model.BallEntity;
import model.Entities;
import model.EnumComponents;
import model.EnumDirection;

public class FileReal implements FileHandler{
	
	//private Entities anglor;
	private Scanner scan;
	private PrintWriter writter;
	private GamePanel panel;
	private ArrayList<Entities> balls;
	private String size;
	private String score;
	private String height;
	private String width;
	private String name;
	private String state;
	private String direction;
	private String component;
	private String x;
	private String y;
	private FileOutputStream fos;
	private DataOutputStream dos;
	private BufferedOutputStream bof;
	private DataInputStream dis;
	private BufferedInputStream bif;
	private InputStream fis;
	
	public FileReal() {
		this.balls = new ArrayList<Entities>();
		this.size = "";
		this.name = "";
		this.width = "";
		this.state = null;
		this.direction = "";
		this.panel = new GamePanel();
		//populate balls array
		for(int i = 0;i < this.panel.getNumberOfBalls();i++) {
			this.balls.add(new  BallEntity());
		}
	}

	/**
	 * @param - String of information
	 */
	public void write(String information) {
		StringTokenizer tokens = new StringTokenizer(information,".");
		//write score,name,game-state
		String firstLine = tokens.nextToken();
		writeTextFile(firstLine);
		
		String secondLine = tokens.nextToken();
		writeBinaryFile(secondLine);
	}
	/**
	 * @param - String of name
	 */
	public GamePanel read(String name) {
		this.name = name;
		this.panel.setBalls(readBinaryFile(readTextFile(this.name)));
		return this.panel;
		
	}
	/**
	 * @param playerName
	 * @return arraylist of entities
	 */
	private ArrayList<Entities> readBinaryFile(String playerName) {
		
		try {
			// direction:component:x:y
			 fis = new FileInputStream(new File("./\\data\\game_progress\\"+this.name+".bin"));
			 bif = new BufferedInputStream(fis);
			 dis  = new DataInputStream(bif);
			 
			 for(int i = 1;i <= 20;i++) {
				 StringTokenizer tokens = new StringTokenizer(dis.readUTF(),":");
				 String dir = tokens.nextToken().trim();
				
				 if(dir.equals("UP")) {
					 balls.get(i-1).setDirection(EnumDirection.UP);
				 }else if(dir.equals("DOWN")) {
					 balls.get(i-1).setDirection(EnumDirection.DOWN);
				 }else if(dir.equals("LEFT")) {
					 balls.get(i-1).setDirection(EnumDirection.LEFT);
				 }else if(dir.equals("RIGHT")) {
					 balls.get(i-1).setDirection(EnumDirection.RIGHT);
				 }else if(dir.equals("STOPMOVING")){
					 balls.get(i-1).setDirection(EnumDirection.STOPMOVING);
				 }
				 
				 String comp = tokens.nextToken().trim();
				 
				 if(comp.equals("REDBALL")) {
					 balls.get(i-1).setCOMPONETS(EnumComponents.REDBALL);
				 }else if(comp.equals("BLUEBALL")) {
					 balls.get(i-1).setCOMPONETS(EnumComponents.BLUEBALL);
				 }else if(comp.equals("BOMB")) {
					 balls.get(i-1).setCOMPONETS(EnumComponents.BOMB);
				 }
				 
				 balls.get(i-1).setX(Integer.parseInt(tokens.nextToken()));
				 balls.get(i-1).setY(Integer.parseInt(tokens.nextToken()));
				 
				 
			 }
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(dos != null) {
				try {
					dos.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return this.balls;
	}
	/**
	 * 
	 * @param playerName
	 * @return string of playername
	 */
	private String readTextFile(String playerName) {
		
		try {
			scan = new Scanner(new File("./\\data\\game_progress\\PlayerBoard.txt"));
			
			while(scan.hasNext()) {
				
				String line = scan.nextLine();
				StringTokenizer tokens = new StringTokenizer(line," ");
				if(playerName.equals(tokens.nextToken())) {
					 state  = tokens.nextToken();
					 score  = tokens.nextToken();
					}
				}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(scan != null) {
				scan.close();
			}
		}
		
		return playerName;
	}
	/**
	 * 
	 * @param secondLine
	 */
	private void writeBinaryFile(String secondLine) {
		File newFile = new File("./\\data\\game_progress\\"+this.name+".bin");
		try {
			
			// direction:component:x:y
			

			 fos = new FileOutputStream(newFile);
			 bof = new BufferedOutputStream(fos);
			 dos  = new DataOutputStream(bof);
			 
			 StringTokenizer tokens = new StringTokenizer(secondLine,",");
			 
			 for(int i = 1;i <= 20;i++) {
				 dos.writeUTF(tokens.nextToken());
			
			 }
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(dos != null) {
				try {
					dos.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @param firstLine
	 */
	private void writeTextFile(String firstLine) {
		try {
			FileWriter fw = new FileWriter("./\\data\\game_progress\\PlayerBoard.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			writter = new PrintWriter(bw);
			
			Pattern pattForPlayer = Pattern.compile("[A-Za-z0-9]{3}:\\d{1,4}:(true|false)");
			Matcher match = pattForPlayer.matcher(firstLine);
			if(match.matches()) {
				StringTokenizer tokens = new StringTokenizer(firstLine,":");
				name = tokens.nextToken();
				 score = tokens.nextToken();
				 state = tokens.nextToken();
				writter.println(name+" "+score+" "+state);
			}
		
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(writter != null) {
				writter.close();
			}
		}
	}
	
}
