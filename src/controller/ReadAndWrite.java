package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReadAndWrite {
	
	private String dir = "c://Users//Public//";

	public void saveData(char[][] data) throws IOException {
		String fileName = "puzzle_platformer.dat";
		File file = new File(this.dir + fileName);
		if(!file.createNewFile()) {
			file.delete();
			file.createNewFile();
		}
		try{ 
			FileOutputStream unsaved = new FileOutputStream(file);
			ObjectOutputStream saved = new ObjectOutputStream(unsaved);
			saved.writeObject(data);
			saved.close();
			System.out.println("Game saved at " + this.dir + fileName);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}

	public char[][] readData() throws Exception{
		File fileName = new File("c://Users//Public//puzzle_platformer.dat");
		if(fileName.exists()) {
			FileInputStream unsaved = new FileInputStream(fileName);
			ObjectInputStream saved = new ObjectInputStream(unsaved);
			char[][] temp = (char[][]) saved.readObject();
			saved.close();
			return temp;
		}
		return null;
	}

	public void setDir(String newDir) {
		this.dir = newDir;
	}

	public void deleteSave() {
		File file = new File("c://Users//Public//puzzle_platformer.dat");
		file.delete();
	}


}
