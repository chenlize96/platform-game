package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import message.DataMessage;

/**
 * This function is to save and load
 * 
 * @author Lize Chen
 */
public class ReadAndWrite {

	private String dir = "c://Users//Public//"; // default

	/**
	 * save the data
	 * 
	 * @author Lize Chen
	 * @param data - contains info of player and map
	 */
	public void saveData(DataMessage data) throws IOException {
		String fileName = "puzzle_platformer.dat";
		File file = new File(this.dir + fileName);
		if (!file.createNewFile()) {
			file.delete();
			file.createNewFile();
		}
		try {
			FileOutputStream unsaved = new FileOutputStream(file);
			ObjectOutputStream saved = new ObjectOutputStream(unsaved);
			saved.writeObject(data);
			saved.close();
			System.out.println("Game saved at " + dir + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read the data
	 * 
	 * @author Lize Chen
	 * @return DataMessage
	 * @throws Exception
	 */
	public DataMessage readData() throws Exception {
		File fileName = new File("c://Users//Public//puzzle_platformer.dat");
		if (fileName.exists()) {
			FileInputStream unsaved = new FileInputStream(fileName);
			ObjectInputStream saved = new ObjectInputStream(unsaved);
			DataMessage temp = (DataMessage) saved.readObject();
			saved.close();
			return temp;
		}
		return null;
	}

}
