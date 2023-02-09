package gui;
import gui.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

//import javax.swing.JFrame;
//import javax.swing.JButton;
import javax.swing.*;

public class NoteReader {
	private String[] files;
	private int count = 0;
	private static File record;	
	private String fileFile = "";
	private int lineNum = 0;
	
	
	public void initApp() {
		
	}
	
	//Need to implement case handling for deleted txt files
	
	public void loadFiles(String fileNames, GUI wind) throws IOException {
		
		if(fileNames.contentEquals("?")) {	
			System.out.println("Searching files");
			Scanner recs = new Scanner(wind.getFile());
			System.out.println("Searching...");
			while(recs.hasNextLine()) {
				String line = recs.nextLine();
				if(!fileFile.contains(line)){
				fileFile+=line;
			}
				}
			files = fileFile.split(",");
			System.out.println("Files Retrieved");
			System.out.println(files.toString());
			return;
		}
		else {
			files = fileNames.split(",");
		}
		
		
		
		//System.out.println(files.toString());
	}
	
	public String getFileFile() {
		return this.fileFile;
	}
	
	public File getFile() {
		return this.record;
	}
	public String[] getFiles() {
		return this.files;
	}
	public void setFiles(String[] fil) {
		this.files = fil;
	}
	public NoteReader() {
		
	}
	
	public int getLine() {
		return this.lineNum;
	}
	
	public String selectNote() throws IOException {
		int index = (int)Math.round((Math.random()*(files.length-1)));
		File fname = new File("Notes/"+files[index]);
		Scanner counter = new Scanner(fname);
		while(counter.hasNextLine()) {
			counter.nextLine();
			count++;
		}
		int lineIndex = (int)Math.round(Math.random()*(count));
		Scanner read = new Scanner(fname);
		for(int i = 0; i < lineIndex-1; i++) {
			read.nextLine();
		}
		String l = read.nextLine();
		if(l==null&&read.hasNextLine()) l=read.nextLine();
		lineNum = lineIndex;
		
		
		counter.close();
		read.close();
		return l;
	}
	

	//Flashcards are made by testing for presence of ":"
	public void makeFlashSet(String name, GUI window) throws IOException {
		File cards = new File("Flashcards_"+ window.username.getText());
		cards.createNewFile();
		cards.createNewFile();
		
		String line = "";
		
		FileWriter fw = new FileWriter(cards, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("Set:"+name);
		
		for(int i = 0; i < files.length; i++) {
			File f = new File("Notes/"+files[i]);
			Scanner cardMaker = new Scanner(f);
		
			while(cardMaker.hasNextLine()) {
				line = cardMaker.nextLine();
				if(line.contains(":")) {
					pw.println(line);
			}
				
		}
			
			cardMaker.close();
		}
		pw.println();
		pw.flush();
		pw.close();
	}
	
	public HashMap<String,String> selectFlashSet(String name, GUI window) throws FileNotFoundException {
		File flashFile = new File("Flashcards_"+window.username.getText());
		Scanner readFlash = new Scanner(flashFile.getAbsoluteFile());
		String line;
		String nestedLine;
		HashMap<String,String> terms = new HashMap<String,String>();
		while(readFlash.hasNextLine()){
			line = readFlash.nextLine();
			System.out.println(line);
			if(line.contentEquals("Set:"+name)) {
				System.out.println("equals");
				while(readFlash.hasNextLine()) {
					nestedLine = readFlash.nextLine();
					if(nestedLine.contains(":")) {
						String[] splt = nestedLine.split(":");
						terms.put(splt[0], splt[1]);
					}
					else {
						break;
					}
				}
					break;
				
			}
		}
		readFlash.close();
		return terms;
	}
	
	public Map.Entry<String, String> selectFlashCard(HashMap<String,String> cards){
			int index = (int)Math.round(Math.random()*(cards.size()-1));
			int i = 0;
			Map.Entry<String, String> result = null;
//			List keys = new ArrayList(cards.keySet());
//			Object o = keys.get(index);
			for(Map.Entry<String, String> ent:cards.entrySet()) {
				if(i<index) {
					i++;
				}else {
					result = ent;
				}
			}
//			return cards.get(o);
			return result;
			
		
	}
		
		
	public String toString() {
		String result = "";
		for(String s:files) {
			result += s+",";
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GUI window1 = new GUI();

		NoteReader nr = new NoteReader();


}}
