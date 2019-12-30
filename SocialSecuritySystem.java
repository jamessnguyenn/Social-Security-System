package pa02;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

/***
 * This class will allows Users to create a social security system. The social
 * security system will generate 300 random different social security numbers
 * and store them into a file. The Users will then be given options to pick on
 * how they would like to sort the random different social security numbers.
 * 
 * @author - James Nguyen
 *
 */
public class SocialSecuritySystem extends JFrame implements ActionListener {
	Sorter ssnSorter; // sorter which will be used to call on the different sorting algorithms
						// throughout the program

	/***
	 * Constructor which will generate 300 random different social security numbers,
	 * and store them in a file named Random_SSN.txt. The Constructor will also
	 * create a user interface in which users can choose what to do with the file.
	 * 
	 * @throws IOException
	 *             - input output except thrown if there is an input error or output
	 *             error
	 */
	public SocialSecuritySystem() throws IOException {
		super("Choose What to Do Next"); // set the title of the user interface
		ssnSorter = new Sorter();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		File randomSSNFile = new File(System.getProperty("user.home") + "/Desktop", "Random_SSN.txt"); // sets the
																										// location and
																										// name of the
																										// file to be on
																										// the desktop
		// creating a file writer and print writer in order to edit the text file
		FileWriter fw = new FileWriter(randomSSNFile);
		PrintWriter pw = new PrintWriter(fw);

		for (int i = 1; i <= 300; i++) {
			String ssn = "";
			for (int j = 1; j <= 9; j++) {
				if (j == 4 || j == 6) {
					ssn = ssn + "-"; // adds a dash after the third and fifth numbers to put it in
				}

				ssn = ssn + (int) (Math.random() * 10);
				// generating a digit between 0 to 9 and adding it to the ssn
				// string until there is 9 digits.
			}
			if (ssn.substring(0, 3).equals("000") || ssn.substring(4, 6).equals("00")
					|| ssn.substring(7).equals("000")) { // if the ssn create contains consecutive zeroes in the area
															// number, group number, or serial number, the ssn will not
															// be used
				i--;
			} else {
				pw.println(ssn);
			}
		}
		pw.close();
		fw.close();
		System.out.print("File created and Values have been added");
		this.setSize(525, 250); // setting the size of the user interface
		this.setLayout(new GridLayout(5, 1));

		// creating the different buttons that will be on the User interface
		Button quickSort = new Button("Quick Sort the SSN file & View the Amount of People who applied in the Different Areas");
		Button bucketSort = new Button("Bucket Sort the SSN file & View the Amount of People who applied in the Different Areas");
		Button radixSort = new Button("Radix Sort the SSN file & View the Amount of People who applied in the Different Areas");
		Button exitWindow = new Button("Exit Window");

		quickSort.addActionListener(this);
		bucketSort.addActionListener(this);
		radixSort.addActionListener(this);
		exitWindow.addActionListener(this);

		quickSort.setActionCommand("quickSort");
		bucketSort.setActionCommand("bucketSort");
		radixSort.setActionCommand("radixSort");
		exitWindow.setActionCommand("exitWindow");

		this.add(new JLabel("<html>File of the Social Security Numbers, \"Random_SSN.txt\",  <br/> "
				+ "&nbsp &nbsp &nbsp has been created & can be found on your desktop</html>", JLabel.CENTER));
		// adding the created buttons and labels on to the User interface
		this.add(quickSort);
		this.add(bucketSort);
		this.add(radixSort);
		this.add(exitWindow);
		this.setVisible(true);
	}

	/***
	 * method which will take a social security string and return it in an integer
	 * form and without the dashes.
	 * 
	 * @param social
	 *            - the social security string that will be turned into an integer
	 * @return - the integer form of the social security string
	 */
	public int socialToInt(String social) {
		String socialString = social.substring(0, 3) + social.substring(4, 6) + social.substring(7); // taking out the
																										// dashes
		return Integer.parseInt(socialString);// turning the string into an integers
	}

	/***
	 * getter method which will return an int that is supposed to represent an area
	 * groups of the social security number
	 * 
	 * @param social
	 *            - the social security number
	 * @return - 1 if the ssn represents the northeast coast, 2 if the ssn
	 *         represents the south coast states, 3 if the ssn represents the middle
	 *         states, 4 if the ssn represents northwest coast states, and 5 if the
	 *         number represent the west coast states
	 */
	public int getGroup(int social) {
		String socialString = intToSocial(social); // turns the integer into a string through the int to string method
		socialString = socialString.substring(0, 3); // takes the first 3 digits of the social
		int socialNum = Integer.parseInt(socialString); // turns the 3 digits into an integer
		if (socialNum <= 199) {
			return 1;
		}
		if (socialNum <= 399) {
			return 2;
		}
		if (socialNum <= 599) {
			return 3;
		}
		if (socialNum <= 799) {
			return 4;
		} else {
			return 5;
		}
	}

	/***
	 * method which will create a list from the Random SSN file
	 * 
	 * @return - an array list of the different social security numbers on the file
	 * @throws IOException
	 *             - exception thrown from input and output error
	 */
	public ArrayList<Integer> getRanSSNList() throws IOException {
		File randomSSNFile = new File(System.getProperty("user.home") + "/Desktop", "Random_SSN.txt");
		ArrayList<Integer> ranSSNList = new ArrayList<Integer>();
		FileReader fr;
		try {
			fr = new FileReader(randomSSNFile);
			BufferedReader br = new BufferedReader(fr);
			for (int i = 1; i <= 300; i++) {
				ranSSNList.add(socialToInt(br.readLine()));// turns the string into an integer and then adds it into the
															// array list
			}
		} catch (FileNotFoundException e1) {
			System.out.println("file unable to be found");
		}
		return ranSSNList;

	}

	/**
	 * method which will turn an integer social security social into a social
	 * security string
	 * 
	 * @param social
	 *            - the integer that will be turned into a string
	 * @return - the string version of the social security number
	 */
	public String intToSocial(int social) {
		String ssn = "" + social;
		while (ssn.length() != 9) {// adds 0 in front of the string if the social security int doesn't have a
									// length of 9
			ssn = "0" + ssn;
		}
		return ssn.substring(0, 3) + "-" + ssn.substring(3, 5) + "-" + ssn.substring(5); // returns the social integer
																							// as a string with dashes.
	}

	/***
	 * Method which will perform a specific action when one of the buttons are
	 * pressed on the user interface
	 */
	@Override
	public void actionPerformed(ActionEvent pushed) {
		String event = pushed.getActionCommand();
		// if the user clicks on the quickSort button
		if (event.equals("quickSort")) {
			try {
				ArrayList<Integer> ranSSNList = getRanSSNList();
				ssnSorter.quickSort(ranSSNList, 0, 299);// quick sorts the random social security list so ranSSNLIst is
														// now sorted
				// creates a file to store the sorted elements
				File randomSSNFile = new File(System.getProperty("user.home") + "/Desktop", "Quick_SSN.txt");
				FileWriter fw = new FileWriter(randomSSNFile);
				PrintWriter pw = new PrintWriter(fw);
				// creating local variable to represent the different groups
				int northeast = 0;
				int south = 0;
				int middle = 0;
				int northwest = 0;
				int west = 0;
				for (int i = 0; i < ranSSNList.size(); i++) {// goes through the sorted list and increases one of the
																// local variables that represents the group to keep
																// count of the people in the different areas
					int groupNum = getGroup(ranSSNList.get(i));
					if (groupNum == 1) {
						northeast++;
					}
					if (groupNum == 2) {
						south++;
					}
					if (groupNum == 3) {
						middle++;
					}
					if (groupNum == 4) {
						northwest++;
					}
					if (groupNum == 5) {
						west++;
					}
					pw.println(intToSocial(ranSSNList.get(i))); // prints the sorted list into the quicksort file
				}
				pw.close();
				fw.close();
				// creates a success window which will tell the user that the program
				// successfully sorted the list and also give the users insight about the amount
				// of people in the different areas
				JFrame successWindow = new JFrame("Success");
				successWindow.setSize(400, 250);
				successWindow.setLayout(new GridLayout(7, 1));
				successWindow.add(new JLabel(
						"<html>Success. The Social Security Numbers have been sorted and  <br/> "
								+ " stored into Quick_SSN.txt which can be found on your Desktop</html>",
						JLabel.CENTER));
				successWindow.add(new Label("Here are the amount of people who applied in the different areas"));
				successWindow.add(new Label("Northeast Coast States: " + northeast + " people"));
				successWindow.add(new Label("South Coast States: " + south + " people"));
				successWindow.add(new Label("Middle States: " + middle + " people"));
				successWindow.add(new Label("Northwest Coast States " + northwest + " people"));
				successWindow.add(new Label("West Coast States: " + west + " people"));
				successWindow.setVisible(true);
			} catch (IOException e) {

			}
		}
		// if the user clicks on the quick sort button
		if (event.equals("radixSort")) {
			try {
				ArrayList<Integer> ranSSNList = getRanSSNList();
				ssnSorter.radixSort(ranSSNList, 9); // radix sorts the random social security list
				// creates a file to store the sorted elements
				File randomSSNFile = new File(System.getProperty("user.home") + "/Desktop", "Radix_SSN.txt");
				FileWriter fw = new FileWriter(randomSSNFile);
				PrintWriter pw = new PrintWriter(fw);
				// creating local variables to represent the five distinct groups
				int northeast = 0;
				int south = 0;
				int middle = 0;
				int northwest = 0;
				int west = 0;
				for (int i = 0; i < ranSSNList.size(); i++) {
					// goes through the sorted list and increases one of the
					// local variables that represents the group to keep
					// count of the people in the different areas
					int groupNum = getGroup(ranSSNList.get(i));
					if (groupNum == 1) {
						northeast++;
					}
					if (groupNum == 2) {
						south++;
					}
					if (groupNum == 3) {
						middle++;
					}
					if (groupNum == 4) {
						northwest++;
					}
					if (groupNum == 5) {
						west++;
					}
					pw.println(intToSocial(ranSSNList.get(i))); // prints the sorted social security numbers into the
																// radix sort
				}
				pw.close();
				fw.close();
				// creates a success window which will tell the user that the program
				// successfully sorted the list and also give the users insight about the amount
				// of people in the different areas
				JFrame successWindow = new JFrame("Success");
				successWindow.setSize(400, 250);
				successWindow.setLayout(new GridLayout(7, 1));
				successWindow.add(new JLabel(
						"<html>Success. The Social Security Numbers have been radix sorted and  <br/> "
								+ " stored into Radix_SSN.txt which can be found on your Desktop</html>",
						JLabel.CENTER));
				successWindow.add(new Label("Here are the amount of people who applied in the different areas"));
				successWindow.add(new Label("Northeast Coast States: " + northeast + " people"));
				successWindow.add(new Label("South Coast States: " + south + " people"));
				successWindow.add(new Label("Middle States: " + middle + " people"));
				successWindow.add(new Label("Northwest Coast States " + northwest + " people"));
				successWindow.add(new Label("West Coast States: " + west + " people"));
				successWindow.setVisible(true);
			} catch (IOException e) {

			}

		}
		// if the user clicks on the bucket sort option
		if (event.equals("bucketSort")) {
			try {
				ArrayList<Double> SSNList = new ArrayList<Double>(); // creates a list for doubles
				ArrayList<Integer> ranSSNList = getRanSSNList(); // gets the list from the random SSN file
				// goes through the elements in the random list, divides them by 1000000000 to
				// turn the elements into a decimal and then adds it the SSN list
				for (int i = 0; i < 300; i++) {
					double decimalSSN = (double) (ranSSNList.get(i) / 1000000000.0);
					SSNList.add(decimalSSN);
				}
				// SSNList is then bucket sorted
				SSNList = ssnSorter.bucketSort(SSNList);
				ArrayList<Integer> returnedList = new ArrayList<Integer>();
				// a returned list is created
				// the loop will go through the elements in the double list, and multiplies them
				// by 1000000000 to turn the elements back into an integer whole numer
				for (int i = 0; i < 300; i++) {
					int intSSN = (int) (SSNList.get(i) * 1000000000);
					returnedList.add(intSSN);
				}
				// creates a file to add the sorted list
				File randomSSNFile = new File(System.getProperty("user.home") + "/Desktop", "Bucket_SSN.txt");
				FileWriter fw = new FileWriter(randomSSNFile);
				PrintWriter pw = new PrintWriter(fw);
				// creating local variables to represent the five distinct groups
				int northeast = 0;
				int south = 0;
				int middle = 0;
				int northwest = 0;
				int west = 0;
				for (int i = 0; i < returnedList.size(); i++) {
					// goes through the sorted list and increases one of the
					// local variables that represents the group to keep
					// count of the people in the different areas
					int groupNum = getGroup(returnedList.get(i));
					if (groupNum == 1) {
						northeast++;
					}
					if (groupNum == 2) {
						south++;
					}
					if (groupNum == 3) {
						middle++;
					}
					if (groupNum == 4) {
						northwest++;
					}
					if (groupNum == 5) {
						west++;
					}
					pw.println(intToSocial(returnedList.get(i)));
				}
				pw.close();
				fw.close();
				// creates a success window which will tell the user that the program
				// successfully sorted the list and also give the users insight about the amount
				// of people in the different areas
				JFrame successWindow = new JFrame("Success");
				successWindow.setSize(415, 250);
				successWindow.setLayout(new GridLayout(7, 1));
				successWindow.add(new JLabel(
						"<html>Success. The Social Security Numbers have been bucket sorted and <br/> "
								+ "stored into Bucket_SSN.txt which can be found on your Desktop</html>",
						JLabel.CENTER));
				successWindow.add(new Label("Here are the amount of people who applied in the different areas"));
				successWindow.add(new Label("Northeast Coast States: " + northeast + " people"));
				successWindow.add(new Label("South Coast States: " + south + " people"));
				successWindow.add(new Label("Middle States: " + middle + " people"));
				successWindow.add(new Label("Northwest Coast States " + northwest + " people"));
				successWindow.add(new Label("West Coast States: " + west + " people"));
				successWindow.setVisible(true);
			} catch (IOException e) {

			}
		}
		if (event.equals("exitWindow")) {
			System.exit(0);
		}
	}

	/***
	 * main method which will create the social security system
	 *
	 */
	public static void main(String[] args) throws IOException {

		SocialSecuritySystem ssnSystem = new SocialSecuritySystem();
	}
}
