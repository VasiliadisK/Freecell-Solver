package freecell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
	
		Timer timer=new Timer();
		TimerTask task = new TimerTask() 
		{

			@Override
			public void run() {
				System.out.println("Exceeded 3 minutes limit");
				System.exit(1);
				
			}
		};	
		
		
		timer.schedule(task, 180000);
		
	//Depth of breadth
		String method = args[0];
	//passing input.txt (Starting Cards) into the program
	// passing each line of cards into a stack 
		File file = new File(args[1]);
		Stack<String> s1=new Stack();
		Stack<String> s2=new Stack();
		Stack<String> s3=new Stack();
		Stack<String> s4=new Stack();
		Stack<String> s5=new Stack();
		Stack<String> s6=new Stack();
		Stack<String> s7=new Stack();
		Stack<String> s8=new Stack();
		
		
		ArrayList Stacks=new ArrayList();
		
		Stacks.add(s1);
		Stacks.add(s2);
		Stacks.add(s3);
		Stacks.add(s4);
		Stacks.add(s5);
		Stacks.add(s6);
		Stacks.add(s7);
		Stacks.add(s8);
		

		
		try {
			Scanner scan = new Scanner(file);
			
		//first 4 rows contain 7 cards 
			
			for(int i=0;i<4;i++) {
				for(int j=0;j<7;j++) {
					 ((Vector) Stacks.get(i)).add(scan.next());
				}
			}
		
		//last 4 rows contain 6 cards 
			for(int i=4;i<8;i++)	
				for(int j=0;j<6;j++) {
					 ((Vector) Stacks.get(i)).add(scan.next());
				}
				
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//end of stacks creation 
			
		
		
		CardFunctions test = new CardFunctions();
		Node root = new Node(Stacks,null,null,0);
		
		root.InitFrontier(root);
		Node wonode = null;
		
		if(method.equals("depth")) {
			System.out.println("Starting DepthFirstSearch.... time limit set to 3 minutes ");
			wonode=root.DepthFirstSearch();
			
		}
		else if(method.equals("breadth"))
		{
			System.out.println("Starting BreadthFirstSearch.... time limit set to 3 minutes ");
			wonode=root.DepthFirstSearch();
		}
		
		if(wonode == null)
			System.out.println("Solution was not found");
		else
			
		{
			ArrayList<String> solution ; 
			//get the steps of the correct branch up until the root
			solution=wonode.ExtractSolution(wonode);
			//write it in the new solution txt file 
			try {
			      FileWriter myWriter = new FileWriter("solution.txt");
			     for(int i=0;i<solution.size();i++) 
			    	 myWriter.write(solution.get(i));
			     myWriter.close();
			     System.out.println("Successfully wrote to the file.");
			    } 
			catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
		}
			
	}

}
