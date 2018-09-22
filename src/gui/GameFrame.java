/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import file.FileHandler;
import file.FileProxy;
import file.FileReal;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable{

	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	private InstructionPanel instructionPanel;
	private final int WIDTH = 750;
	private final int HEIGHT = 580;
	private final int SIZE = 100;
	private boolean playGame;
	private Thread thread;
	private FileHandler fileMan;
	private boolean doOnce;
	private boolean doOnceTwo;
	
	public GameFrame() {
		//initialize frame
		this.setTitle("Director");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH+SIZE,HEIGHT+SIZE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.init();
		
		this.setVisible(true);
	}
	
	public void init() {
		
		this.fileMan = new FileProxy();
		thread = new Thread(this);
		gamePanel = new GamePanel();
		doOnce= true;
		doOnceTwo = true;
		setMenuPanel();
		
	}
	
	public void setMenuPanel() {
		menuPanel = new MenuPanel();
		menuPanel.setLayout(new BorderLayout());
		//add option menu
		this.addOption(); 
		//add menu panel to frame
		add(menuPanel);	
	}
	
	public void setGamePanel() {
		this.gamePanel = new GamePanel();
		this.gamePanel.setName(JOptionPane.showInputDialog("Enter your user name it must be three letters"));
		this.getContentPane().removeAll();
		this.getContentPane().add(this.gamePanel);
		this.revalidate(); 
		this.gamePanel.requestFocus();
		playGame = true;
		gamePanel.setState(true);
		if(doOnceTwo) {
			thread.start();
			doOnceTwo = false;
		}

	}
	
	public void setGamePanelLoadGame() {
		
		JFileChooser jfc = new JFileChooser("./\\data\\game_progress\\PlayerBoard.txt");
		jfc.showOpenDialog(this);
		String name = jfc.getSelectedFile().getName();
		StringTokenizer tokens = new StringTokenizer(name,".");
		
		this.getContentPane().remove(this.menuPanel);
		this.getContentPane().add(this.fileMan.read(tokens.nextToken()));
		this.revalidate(); 
		this.gamePanel.requestFocus();
		playGame = true;
		gamePanel.setState(true);
		thread.start();
		

	}
	
	public void setInstructionPanel() {
		instructionPanel = new InstructionPanel();
		getContentPane().removeAll();
		instructionPanel.setLayout(new BorderLayout());
		JButton back = new JButton("BACK");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				setMenuPanel();
				revalidate(); 
				
			}
		});
		
		
		
		instructionPanel.add(back,BorderLayout.SOUTH);
		this.getContentPane().add(this.instructionPanel);
		
		this.revalidate(); 
		
	}
	
	public void setGamePanelScore() {
		File file = new File("./\\data\\game_progress\\PlayerBoard.txt");
		Scanner scan = null;
		try {
			scan =  new Scanner(file);
			String name = "";
			while(scan.hasNext()) {
				String firstLine  = scan.nextLine();
				StringTokenizer tokens = new StringTokenizer(firstLine," ");
				 
				name = name+tokens.nextToken()+" "+ tokens.nextToken()+"\n";
				System.out.println(name);
			}
			
			JOptionPane.showMessageDialog(this, name);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void addOption() {
		
		JPanel miniPanel = new JPanel();

		miniPanel.setLayout(new GridLayout(5,1));
		
		JButton newGame = new JButton("New Game");
		JButton loadGame = new JButton("Load Game");
		JButton instructions = new JButton("Instructions");
		JButton Scores = new JButton("Score");
		JButton exitGame = new JButton("Exit");
		
			//new game
			newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doOnce = true;
				setGamePanel();
				}
			});
			
			//load game
			loadGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setGamePanelLoadGame();
					
				}
			});
			
			//instructions
			instructions.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setInstructionPanel();
				}
			});
			
			//score 
			Scores.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setGamePanelScore();
					
				}
			});
			
			//exit game
			exitGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					playGame = false;
					gamePanel.setState(false);
					setVisible(false);
				//thread.stop();
				}
			});
		
		
		miniPanel.add(newGame);
		miniPanel.add(loadGame);
		miniPanel.add(instructions);
		miniPanel.add(Scores);
		miniPanel.add(exitGame);
		
		menuPanel.add(miniPanel,BorderLayout.SOUTH);
		
	}

	@Override
	public void run() {
		while(playGame) {
			
			try {
				
				if(gamePanel.getState()) {
					//update game and run it
					gamePanel.upDate();
				}else {
					if(doOnce) {
						doOnce = false;
						if(this.gamePanel.toString() != "") {
							this.fileMan.write(this.gamePanel.toString());
							JOptionPane.showMessageDialog(null, "You loose :"+this.gamePanel.getName());
						}
						this.getContentPane().removeAll();
						setMenuPanel();
						revalidate();
					}
				}
				Thread.sleep(gamePanel.getTime());
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
			
		}
		
	}
	
	
}
