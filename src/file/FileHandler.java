/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package file;

import gui.GamePanel;

public interface FileHandler{
	/**
	 * @param - String of information
	 */
	 public void write(String information);
	 /**
	 * @return GamePanel
	*/
	 public GamePanel read(String name);
	 

}
