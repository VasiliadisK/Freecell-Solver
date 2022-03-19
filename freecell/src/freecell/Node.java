package freecell;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Node {
	
	LinkedList<Node> frontier = new LinkedList<>();
	ArrayList<String> explored = new ArrayList<String>();
	
	
	CardFunctions actions=new CardFunctions();
	ArrayList<Stack> board;
	String move;
	ArrayList<Node> children;
	Node parent;
	int depth;
	
	
	
	public Node(ArrayList<Stack> board ,String move, Node parent,int depth) {
	
		this.board=board;
		this.move=move;
		children=new ArrayList<> ();
		this.parent=parent;
		this.depth=depth;
		
	}
	
		
	//initialize frontier and find root's children
	public void InitFrontier(Node root)
	{
		frontier.add(root);	
		this.findChildren(root);
	}
	
	public void findChildren(Node node) {
		
		ArrayList<String> moves = new ArrayList<>();

		moves=actions.PossibleMoves(node.board);
		
		for(int i=0;i<moves.size();i++)
		{	
			
			
			if(!this.CheckForRepeats(node))
			{		
			
				node.children.add(new Node(node.board,moves.get(i),node,depth+1));
				
			}
		
			
		}
	
	}
	
	
	public void RestoreBoard(ArrayList<Stack> Board,String move,Stack OriginalStack)
	{	//reverse stack cards
		String moves[] = move.split(" ");
		for(int j=0;j<moves.length;j++) {
			if(moves[j].equals("Stack"))
			{
				String card1=moves[j+1];
				String card2=moves[j+2];
				String cardF=null;
				Stack destinationStack=new Stack();
				//find the correct stacks from the cards
				for(int i=0;i<Board.size();i++) {
					if(Board.get(i).contains(card2))
						destinationStack=Board.get(i);
					
				}
				

			
				OriginalStack.add(destinationStack.peek());
				destinationStack.pop();
								
			}
		//reverse Stack to freecell
			else if(moves[j].equals("freecell"))
			{
				String card=moves[j+1];
				
				OriginalStack.add(card);
				actions.getFreecell().remove(card);
						
			}		

		//reverse Stack to source
			else if(moves[j].equals("source"))
			{
				String card=moves[j+1];
				OriginalStack.add(card);
				actions.getFoundation().remove(card);
				
				
			}
			else if(moves[j].equals("newstack"))
			{
				String card=moves[j+1];
				Stack destinationStack=new Stack();
				for(int k=0;k<Board.size();k++)
				{
					if(Board.get(k).isEmpty())
						destinationStack=Board.get(k);
				}
				actions.getFreecell().add(card);
				destinationStack.remove(card);
				
			
			}	
		}			
	}
	
	public void ExtendFrontier(Node node,String search) 
	{	
		
		for(int i=0;i<node.children.size();i++)
		{	
			if(!explored.contains(node.children.get(i).move)) {
				if(search.equals("depth"))
					frontier.addFirst(node.children.get(i));
				else if(search.equals("breadth"))
					frontier.addLast(node.children.get(i));
			}
		}
	}
	
	public boolean CheckForRepeats(Node node)
	{
		while(node.parent!=null)
		{
			if(node.move.equals(node.parent.move))
				return true;
			node=node.parent;
		}
		return false;
		
	}
	 
	
	public Node DepthFirstSearch()
	{	Node node = null;
		Stack stack=null;
		
		while(!frontier.isEmpty()) {
			
			node=frontier.pop();
			explored.add(node.move);
			//for the root (no move)
			if(node.move!=null)
				stack = actions.performMove(node.board,node.move);
			
			if(this.isSolution(node))
				return node;
			else 
			{	
				
				this.findChildren(node);
				
				if(node.children.size()!=0)
				{
					this.ExtendFrontier(node,"depth");
				}
				//if there are no more children (current instance) then board has to be restored to previous state to check other branches
				else 
					this.RestoreBoard(node.board, node.move,stack);
				
			}
			
			for(int i=0;i<frontier.size();i++)
				System.out.println(frontier.get(i).move);
		}
		
		
		return null;
	}
	
	public Node BreadthFirstSearch() {
		
		Node node = frontier.pop();
		explored.add(node.move);
		Stack stack=null;
		
		while(!frontier.isEmpty()) {
			
			node=frontier.pop();
			explored.add(node.move);
			//for the root (no move)
			if(node.move!=null)
				stack = actions.performMove(node.board,node.move);
			
			if(this.isSolution(node))
				return node;
			else 
			{	
				
				this.findChildren(node);
				
				this.ExtendFrontier(node,"breadth");
			
			}
			
			for(int i=0;i<frontier.size();i++)
				System.out.println(frontier.get(i).move);
		}
		
		return null;
	}
	
	public boolean isSolution(Node node) {
		
		for(int i=0;i<node.board.size();i++)
		{
			if(!node.board.get(i).isEmpty())
				return false;
		}
		return true;
	}
	//method that finds the path of the winning branch from a node 
	public ArrayList<String> ExtractSolution(Node node) {
		
		
		ArrayList<String> solution = new ArrayList<String>();
			
		while(node.parent!=null)
		{
			solution.add(node.move);
			node=node.parent;
		}
		
		return solution;
	
		
		
		
	    
	}
	
}
