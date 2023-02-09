package gui;
import gui.NoteReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI implements ActionListener  {
	protected JFrame home;
	protected JFrame flash;
	protected JFrame windo;
	protected JFrame title;
	protected JFrame studying;
	protected JFrame fSet;
	
	protected JPanel panButts;
	protected JPanel panHome;
	protected JPanel pan;
	protected JPanel panTitle;
	protected JPanel panStudy;
	protected JPanel panFlash;
	protected JPanel panSet;
	
	protected JScrollPane buttons;
	
	protected JButton changeUser;
	protected JButton addT;
	protected JButton studyT;
	protected JButton studyNT;
	protected JButton studyFT;
	protected JButton start;
	protected JButton next;
	
	protected JButton makeFlashS;
	protected JButton testSet;
	protected JButton nextFlash;
	protected JButton flipFlash;
	protected JButton flipT;
	
	protected JButton back = new JButton("<-");
	protected JButton back1 = new JButton("<-");
	protected JButton back2 = new JButton("<-");
	protected JButton back3 = new JButton("<-");
	
	protected JButton button;
	protected JButton study;
	
	protected JTextField setName;
	protected JTextField pswd;
	protected JTextField username;
	protected JTextField tf;
	
	protected JTextArea names;
	
	protected JLabel af = new JLabel("Available Files:");
	protected JLabel lab;
	protected JLabel output = new JLabel("");
	protected JLabel newUser;
	protected JLabel st = new JLabel("StudyTime:");
	protected JLabel flashDef;
	
	private String textContent = "";
	private String userFileName = "";
	private String currentSetName = "";
	
	protected File fname; 
	private File userFile;
	private File folder;
	
	private float studyCoin;
	private int dayStreak;
	
	protected HashMap<String, JButton> butts = new HashMap<String,JButton>();
	protected HashMap<String, JRadioButton> rads = new HashMap<String,JRadioButton>();
	protected HashMap<String, JButton> sets = new HashMap<String, JButton>();
	protected HashMap<String, String> currentSet = new HashMap<String,String>();
	
	private Map.Entry<String, String> currentFlash;
	
	protected List<String> currentSelects = new ArrayList<String>();

	private boolean term = false;
	protected boolean eventOccurred = false;
	protected boolean newU = true;
	
	public GUI () throws IOException {
		//fname = new File("Added_Files");
		
		
		windo = new JFrame();
	//	windo.setBackground(null);
		title = new JFrame();
		studying = new JFrame();
		home = new JFrame();
		
		pan = new JPanel();
		panHome = new JPanel();
		panTitle = new JPanel();
		panStudy = new JPanel();
		
		
		//home op
		
		addT = new JButton("*Add Files*");
		addT.addActionListener(this);
		addT.setEnabled(true);
		
		studyT = new JButton("Start Studying");
		studyT.addActionListener(this);
		studyT.setEnabled(true);
				
		changeUser = new JButton("Switch User");
		changeUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				home.setVisible(false);
				panHome.setEnabled(false);
				panTitle.setEnabled(true);
				pswd.setText("Password");
				pswd.setEnabled(false);
				pswd.setVisible(false);
				start.setEnabled(false);
				start.setVisible(false);
				
				pan.setEnabled(true);
				panFlash.setEnabled(true);
				for(Map.Entry<String, JButton> entry: butts.entrySet()) {
					pan.remove(entry.getValue());
				}
				for(Map.Entry<String, JButton> entry:sets.entrySet()) {
					panFlash.remove(entry.getValue());
				}
				for(Map.Entry<String, JRadioButton> entry: rads.entrySet()) {
					panFlash.remove(entry.getValue());
				}
				butts.clear();
				sets.clear();
				rads.clear();
				
				pan.setEnabled(false);
				panFlash.setEnabled(false);
				
				title.setVisible(true);
				username.setEnabled(true);
			}
			
			
		});
		
		back.setBounds(5,15,30,20);
		back.addActionListener(this);
		
		back1.setBounds(5,15,30,20);
		back1.addActionListener(this);
		
		Font titleFont = new Font("title", 30,30);
	
		st.setFont(titleFont);
				
		panHome.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));

		panHome.add(st);

		panHome.add(changeUser);
		panHome.add(addT);
		panHome.add(studyT);
		//st.setBounds(125, 10, 50, 50);
		
		home.add(panHome, BorderLayout.CENTER);

		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home.setTitle("StudyTime - Home");
		home.pack();
		
		home.setVisible(false);
		panHome.setEnabled(false);
		
		//Study op
		
		study = new JButton("Study");
		study.addActionListener(this);
		
		flipT = new JButton("Flashcards");
		flipT.addActionListener(this);

		
		studying = new JFrame("StudyTime - Studying");
		panStudy = new JPanel();
		
		panStudy.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
		
		panStudy.add(back1);
		panStudy.add(study);
		panStudy.add(flipT);
		
		studying.add(panStudy);
		studying.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		studying.pack();
		studying.setVisible(false);
		panStudy.setEnabled(false);
		
		//Make/Select Flashcard op
		
		back2.setBounds(5,15,30,20);
		back2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panFlash.setEnabled(false);
				flash.setVisible(false);
				panStudy.setEnabled(true);
				studying.setVisible(true);
			}
			
			
		});
		
		makeFlashS = new JButton("Make Flashcard Set");
		makeFlashS.addActionListener(new ActionListener() {     //Anonymous action listener - extremely helpful

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NoteReader nr = new NoteReader();
				try {
					//System.out.println("happend");
					String input = "";
					for(String s: currentSelects) { input += s+",";}
					//System.out.println(input);
					
					nr.loadFiles(input, GUI.this);
					nr.makeFlashSet(setName.getText(), GUI.this);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateSets();
				panFlash.setEnabled(false);
				panFlash.setEnabled(true);
			}
			
		});
		
	
		

		
		testSet = new JButton("Set1");
		testSet.setForeground(Color.BLUE);
		testSet.addActionListener(this);
		
		setName = new JTextField("Set Name");
		Font nameFont = new Font("name", 2, 12);
		setName.setFont(nameFont);
		
		panFlash = new JPanel();
		panFlash.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
		
		panFlash.add(makeFlashS);
		panFlash.add(setName);
		panFlash.add(testSet);
		panFlash.add(back2);
		
		
		
		flash = new JFrame("Study Time - Flashcards");
		flash.add(panFlash);
		flash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		flash.pack();
		flash.setVisible(false);
		panFlash.setEnabled(false);
		
		// Study Flashcards op
		
		panSet = new JPanel();
		panSet.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
		panSet.setEnabled(false);
		
		back3.setBounds(10,15,30,20);
		back3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panSet.setEnabled(false);
				fSet.setVisible(false);
				panFlash.setEnabled(true);
				flash.setVisible(true);
			}
			
			});
		
		nextFlash = new JButton("Next Card");
		nextFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NoteReader nr = new NoteReader();
				currentFlash = nr.selectFlashCard(currentSet);
				if(currentSet.size()==1) {
					nextFlash.setEnabled(false);
					nextFlash.setVisible(false);
				}
				currentSet.remove(currentFlash.getKey());
				term = true;
				flashDef.setText(currentFlash.getKey());
				studyCoin+=0.33;
			}
			
		});
		
		flipFlash = new JButton("Flip");
		flipFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(term) {
					flashDef.setText(currentFlash.getValue());
					term = false;
				}else {
					flashDef.setText(currentFlash.getKey());
					term = true;
				}
			}
			
		});
		
		flashDef = new JLabel();
		Font defFont = new Font("defFont",2,20);
		flashDef.setFont(defFont);
		
		panSet.add(back3);
		panSet.add(flipFlash);
		panSet.add(nextFlash);
		panSet.add(flashDef);
		
		fSet = new JFrame();
		fSet.add(panSet);
		fSet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fSet.pack();
		fSet.setVisible(false);
		
		//AddFile op
		
		button = new JButton("Add File");
		button.addActionListener(this);
		
		names = new JTextArea("");
		names.setBounds(130,130,70,200);
		names.setVisible(false);
		
		lab = new JLabel("Please enter a valid file type.");
		lab.setVisible(false);
		
		output.setBounds(lab.getX()-20, lab.getY()+20, 165, 10);
		
		buttons = new JScrollPane(pan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		tf = new JTextField("Insert File Name Here:");
		Font font = new Font("new font", 3, 12);
		tf.setFont(font);

		pan.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
	//	pan.setLayout(new GridLayout(0,2));
		
		pan.add(back);
		pan.add(button);
		//pan.add(buttons);
		pan.add(tf);
		pan.add(names);
		pan.add(af);
		
		pan.add(lab);
		panStudy.add(output);
		pan.setEnabled(false);
		
		windo.add(pan, BorderLayout.CENTER);
		//windo.add(panButts);
		windo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windo.setTitle("StudyTime - Add File");
		windo.pack();
		windo.setVisible(false);
		
		// Title op
		
		username = new JTextField("Username");
		pswd = new JPasswordField("Password");
		pswd.setVisible(false);
		pswd.setEnabled(false);
		newUser = new JLabel("New User Detected: Please set your password.");
		newUser.setVisible(false);
		
		start = new JButton("Let's Get Started");
		start.addActionListener(this);
		start.setFont(font);
		start.setVisible(false);
		
		next = new JButton("Next");
		next.addActionListener(this);
		next.setFont(font);
		
		panTitle.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
		//panButts.setBorder(BorderFactory.createBevelBorder(1, Color.LIGHT_GRAY, Color.DARK_GRAY));

		panTitle.add(username);
		panTitle.add(next);
		panTitle.add(newUser);
		panTitle.add(pswd);
		panTitle.add(start);
		
		
		title.add(panTitle);
		title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title.setTitle("StudyTime");
		title.pack();
		title.setVisible(true);
	}
	
	public boolean getEvent() {
		return this.eventOccurred;
	}
	public void setEvent(boolean eo) {
		this.eventOccurred = eo;
	}
	
	public File getFile() {
		return this.fname;
	}
	
	public void startApp() throws FileNotFoundException, IOException {
	//	System.out.println(userFile.toString());
		userFile.createNewFile();
		
		Scanner readUserFile = new Scanner(userFile.getAbsoluteFile());
		FileWriter fw = new FileWriter(userFile.getAbsoluteFile(),true);
		PrintWriter pw = new PrintWriter(fw);
		
		if(newU) {
			System.out.println(newU);
		//	String encrypt = pswd.getText();
				//encrypt.replaceAll("e","6");
			pw.println(pswd.getText());
			pw.flush();
			pw.close();
			panTitle.setEnabled(false);
			title.setVisible(false);
			home.setVisible(true);
			panHome.setEnabled(true);
		}else {
			if(pswd.getText().contentEquals(readUserFile.nextLine())){
					panTitle.setEnabled(false);
					title.setVisible(false);
					home.setVisible(true);
					panHome.setEnabled(true);
			}else {
				newUser = new JLabel("Incorrect Username or Password");
			}
		}
		

	}
	
	public void updateFileBox() {
		folder = new File("Notes/");
		File[] fileNames = folder.getAbsoluteFile().listFiles();
		
		String lines = "";
		String netRes = "";
		String checkLines = "";
		Scanner check;
		try {
//			HashMap<String, JButton> butts = new HashMap<String,JButton>();
			check = new Scanner(fname.getAbsoluteFile());
			while(check.hasNextLine()) {
			checkLines+=check.nextLine().replaceAll(",", " ");
			
			
		}
		for(File f:fileNames) {
			if(f.toString().endsWith(".txt")&&!(checkLines.contains(f.toString().substring(f.toString().lastIndexOf("/")+1)))) {
			lines = (f.toString()).substring(f.toString().lastIndexOf("/")+1);
			netRes += lines+",";
			if(!butts.containsKey(lines)) {
					butts.put(lines, new JButton());
				}
			}
			}
		
			for(Map.Entry<String,JButton> entry: butts.entrySet()) {
				entry.getValue().addActionListener(this);
				entry.getValue().setText(entry.getKey());
				pan.add(entry.getValue());
			}
	
		names.setText(netRes);

	
		} catch(FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void updateRads() {
		try {
			Scanner opts = new Scanner(fname.getAbsoluteFile());
			String line = "";
			String check;
			while (opts.hasNextLine()){
				check = opts.nextLine();
				if(!(check.equals(""))) {
					line += check;
					System.out.println(line);
				}
				
				
			}
			String[] access = line.split(",");
			for(String s: access) {
				if(!rads.keySet().contains(s)) {
				rads.put(s, new JRadioButton(s));
				}
			}
			for(Map.Entry<String, JRadioButton> entr: rads.entrySet()) {
				entr.getValue().addActionListener(this);
				panFlash.add(entr.getValue());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateSets() {
		try {
			File fuser = new File("Flashcards_"+username.getText());
			Scanner setNames = new Scanner(fuser.getAbsoluteFile());
			String check;
			String line = "";
			while (setNames.hasNextLine()){
				check = setNames.nextLine();
				if (check.contains("Set:")){
					line += check.substring(check.indexOf(":")+1) + ",";
				}
			}
			String[] setNams = line.split(",");
			for(String s:setNams) {
				if(!(sets.keySet().contains(s))) {
				sets.put(s, new JButton(s));
				}
			}
			for(Map.Entry<String, JButton> en: sets.entrySet()) {
			//	panFlash.add(en.getValue());

				en.getValue().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						NoteReader nr = new NoteReader();
						JButton jb = (JButton)e.getSource();
						try {
							System.out.println(jb.getText());
							currentSetName = jb.getText();
							flashDef.setText("Currently Studying: "+currentSetName);
							currentSet = nr.selectFlashSet(jb.getText(), GUI.this);
							System.out.println(currentSet.keySet().toString());
							
							panFlash.setEnabled(false);
							flash.setVisible(false);
							panSet.setEnabled(true);
							fSet.setVisible(true);
							nextFlash.setEnabled(true);
							nextFlash.setVisible(true);
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
					
				});
				panFlash.add(en.getValue());
				
				}
			
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void processText() {
		try {
			
			String line = "";
			Scanner in = new Scanner(fname.getAbsoluteFile());
			while(in.hasNextLine()) {
				line += in.nextLine()+ " ";
			}
			if(!line.contains(textContent)) {
				if(textContent.contains(".")) {
				dataStorage();
				}else {
					lab.setVisible(true);
				}
			}
		
			}catch(FileNotFoundException ex) {
				System.out.println("FNF");
			}catch(IOException ex) {
				System.out.println("IOEx");
			}
		}
	
	public void dataStorage() {
		try {
		
		FileWriter out = new FileWriter(fname.getAbsoluteFile(),true);
		PrintWriter pw = new PrintWriter(out);
		
		pw.println(textContent+",");
		pw.flush();
		pw.close();
		
		
		
	}catch(IOException ex) {
		tf.setText("File Error");
	}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(button)) {
			textContent = tf.getText();
			tf.setText(null);
			tf.setText("        ");

			this.processText();
			updateFileBox();
			
		}else if(e.getSource().equals(next)) {
			fname = new File("Added_Files_"+username.getText());
			try {
				userFileName = username.getText();
				userFile = new File(userFileName);
				username.setEnabled(false);
				
				if(fname.createNewFile()) {
					
					newUser.setVisible(true);
					newU = true;
					
				}else {
					newU = false;
				}
				pswd.setVisible(true);
				pswd.setEnabled(true);
				start.setVisible(true);
				start.setEnabled(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		else if(e.getSource().equals(start)){
			try {
				newUser.setVisible(false);
				this.startApp();
				updateFileBox();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource().equals(addT)) {
			panHome.setEnabled(false);
			home.setVisible(false);
			pan.setEnabled(true);
			windo.setVisible(true);
			//updateFileBox();
			
		}
		else if(e.getSource().equals(studyT)) {
			panHome.setEnabled(false);
			home.setVisible(false);
			panStudy.setEnabled(true);
			studying.setVisible(true);

		}
		else if(e.getSource().equals(back)||e.getSource().equals(back1)) {
			windo.setVisible(false);
			pan.setEnabled(false);
			studying.setVisible(false);
			panStudy.setEnabled(false);
			panHome.setEnabled(true);
			home.setVisible(true);
			output.setText("");
		}

		else if(e.getSource().equals(study)){
			eventOccurred = true;
			//System.out.println(eventOccurred);
			eventOccurred = false;
			NoteReader n = new NoteReader();
			try {
				n.loadFiles("?",this);
				output.setText(n.selectNote());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		} else if(e.getSource().equals(flipT)) {
			panStudy.setEnabled(false);
			studying.setVisible(false);
			
			updateRads();
			updateSets();
			
			panFlash.setEnabled(true);
			flash.setVisible(true);
			
		} 
		else if(e.getSource().getClass().toString().substring(e.getSource().getClass().toString().lastIndexOf(".")+1).equals("JButton")) {
		//	String jb = e.getSource().getClass().toString().substring(e.getSource().getClass().toString().lastIndexOf("."));
		//	System.out.println(jb);
			JButton addF = (JButton)e.getSource();
			String name = addF.getText();
			if (names.getText().contains(name)) {
				//names.setText(names.getText().replace(name, ""));
				textContent = name;
				addF.setEnabled(false);
				addF.setVisible(false);
				pan.remove(addF);
				butts.remove(addF.getText());
				names.setText("");
				this.processText();
				this.updateFileBox();
			}
			
		}
		else if(e.getSource().getClass().toString().substring(e.getSource().getClass().toString().lastIndexOf(".")+1).equals("JRadioButton")){
			JRadioButton jrb = (JRadioButton)e.getSource();
			String name = jrb.getText();
			//System.out.println(name);
			if(rads.keySet().contains(name)) {
				//System.out.print("true");
				if(currentSelects.contains(name)) {
					currentSelects.remove(name);
				}else {
					//System.out.println("Else");
					currentSelects.add(name);
					//System.out.println(currentSelects.toString());
				}
			}

	}}
}
