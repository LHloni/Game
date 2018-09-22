/**
 * @author L MPHUTI 216040600
 * @since 2018-05-14 
 */
package file;

import gui.GamePanel;

public class FileProxy implements FileHandler{

	private FileReal fileMan;
	
	public FileProxy() {
		this.fileMan = new FileReal();
	}
	
	@Override
	public void write(String information) {
		fileMan.write(information);
		
	}

	@Override
	public GamePanel read(String name) {

		return fileMan.read(name);
	}
	 

}
