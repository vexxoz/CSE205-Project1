//******************************************************************************************************** 
// CLASS: Main (Main.java) 
// 
// DESCRIPTION 
// This program takes a sequence of numbers from a text file and counts the run-ups and run-downs, then calculates the total number of runs. After this is done the result is printed to another text file. 
// 
// COURSE AND PROJECT INFO 
// CSE205 Object Oriented Programming and Data Structures, semester and year 
// Project Number: 1 
// 
// AUTHOR 
// Blake Caldwell bmcaldw2@asu.edu 
//********************************************************************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	/**
	 * Creates the main object and calls the run method on it.
	 * @param args
	 */
	public static void main(String[] args) {
		Main mainObject = new Main();
		mainObject.run();

	}

	/**
	 * Creates local array variables and calls the methods findRuns to find count ups and count downs
	 * method then calls the merge method to combine all data for total runs
	 * the output method is then called to write to a file 
	 */
	private void run() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File("p01-in.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, file can not be found!");
			System.exit(-1);
		}
		
		while(fileInput.hasNextInt()) {
			list.add(fileInput.nextInt());
		}
		
		ArrayList<Integer> listRunsUpCount = new ArrayList<Integer>();
		ArrayList<Integer> listRunsDnCount = new ArrayList<Integer>();
		
		listRunsUpCount = FindRuns(list, 0);
		listRunsDnCount = FindRuns(list, 1);
		
		ArrayList<Integer> listRunsCount = new ArrayList<Integer>();
		listRunsCount = Merge(listRunsUpCount, listRunsDnCount);
		Output("p01-runs.txt", listRunsCount);
	}
	
	/**
	 * This method finds all run ups or downs of an array of integers 
	 * @param pList an array of integers to find the runs of
	 * @param pDir what type of run (run-up or run-down)
	 * @return array of the number of run ups of each type indicated by the index of the array
	 */
	private ArrayList<Integer> FindRuns(ArrayList<Integer> pList, int pDir){
		ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);
		int i = 0, k = 0;
		while(i<pList.size()-1) {
			if(pDir == 0 && pList.get(i)<=pList.get(i+1)) {
				k++;
			}else if(pDir == 1 && pList.get(i) >= pList.get(i+1)) {
				k++;
			}else {
				if(k!=0) {
					listRunsCount.set(k, listRunsCount.get(k)+1);
					k = 0;
				}
			}
			i++;
		}
		if(k!=0) {
			listRunsCount.set(k, listRunsCount.get(k)+1);
		}
		return listRunsCount;
	}
	
	/**
	 * Merges both-run ups and run-downs for one array of total runs of each type indicated by the index of the array
	 * @param pListRunsUpCount array of run-ups
	 * @param pListRunsDpCount array of run-downs
	 * @return an array of the total number of runs of each type indicated by the index of the array
	 */
	private ArrayList<Integer> Merge(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDpCount) {
		ArrayList<Integer>listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);
		for(int i = 0; i<pListRunsUpCount.size()-1;i++) {
			listRunsCount.set(i, pListRunsUpCount.get(i)+pListRunsDpCount.get(i));
		}
		
		return listRunsCount;
	}
	
	/**
	 * Creates an array list of a specific length with a starting value
	 * @param pSize length of array
	 * @param pInitValue starting value for each array index
	 * @return array of a set length with starting values
	 */
	private ArrayList<Integer> arrayListCreate(int pSize, int pInitValue){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < pSize; i++) {
			list.add(pInitValue);
		}
		return list;
	}
	
	/**
	 * writes the results to a file
	 * @param pFilename name of file to write to
	 * @param pListRuns array of the total runs
	 */
	private void Output(String pFilename, ArrayList<Integer> pListRuns) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(pFilename));
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, file can not be found!");
			System.exit(-1);
		}
		int totalRuns = 0;
		for(int i = 0; i < pListRuns.size(); i++) {
			totalRuns += pListRuns.get(i);
		}
		out.print("runs_total, " + totalRuns);
		for(int k = 1; k < pListRuns.size()-1;k++) {
			out.print("runs_"+k+", " + pListRuns.get(k));
		}
		out.close();
	}
}
