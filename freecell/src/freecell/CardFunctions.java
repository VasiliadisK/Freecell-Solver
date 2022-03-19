package freecell;

import java.util.ArrayList;
import java.util.Stack;

public class CardFunctions {
	
	
	//Creation of Freecells(Top left)
	
			ArrayList<String> freecell=new ArrayList<>();

			//Creation of Foundation(Top right)

			ArrayList<String> foundation=new ArrayList<>();

			 public ArrayList<String> getFreecell() {
				return freecell;
			}
			public ArrayList<String> getFoundation() {
				return foundation;
			}

			
	
	//method that performs the changes to the board
	//Seperate Method than StackCard because it creates logical errors during running
	//first we find all the moves without the changes and then apply them 
	//StackCard,FreecellToBoard etc have to be called first to check for conditions 
	//and after the check of conditions all perform can be called 
	public void performStack(Stack OriginalStack,Stack DestinationStack)
	{
		if(!OriginalStack.isEmpty() && !DestinationStack.isEmpty()) {
			DestinationStack.add(OriginalStack.peek());
			OriginalStack.pop();	
			}
	}
	public void performBoardFreecell(Stack OriginalStack)
	{
		if(!OriginalStack.isEmpty() ) {
			freecell.add((String) OriginalStack.peek());
			OriginalStack.remove(OriginalStack.peek());
		}
	}
	public void performFreecellBoard(Stack DestinationStack,String card) {
		freecell.remove(card);
		DestinationStack.add(card);
	}
	public void performBoardFoundation(Stack OriginalStack)
	{	String topcard="";
		if(!OriginalStack.isEmpty())	
			topcard=(String) OriginalStack.peek();
		else 
			System.out.println("Empty Stack");
		foundation.add(topcard);
		OriginalStack.remove(topcard);
	}
	public void performFoundationBoard(Stack DestinationStack,String card)
	{
		foundation.remove(card);
		DestinationStack.add(card);
		
	}
	
	//Method that stacks a card into another stack 
		public String StackCard(Stack OriginalStack,Stack DestinationStack) {
			String topcardD="";
			String topcardO="";
			//get the topcard of our stack
			if(!OriginalStack.isEmpty())	
				topcardO=(String) OriginalStack.peek();
			//get the topcard of the Destination stack we want to add our card	
			if(!DestinationStack.isEmpty())	
				 topcardD=(String) DestinationStack.peek();

				
		//check if our card(topcardO) is smaller from the destination card(topcardD)
		//by 1 and switches red to black or black to red
			
		//if this card isnt already stacked to the destination and stacks are not empty then ...
			if(!DestinationStack.contains(topcardO) && !DestinationStack.empty() && !OriginalStack.empty()) {
				if(this.getCardNumber(topcardO)==this.getCardNumber(topcardD)-1)
				{
					if((this.IsRed(topcardO) && this.IsBlack(topcardD)) || (this.IsBlack(topcardO) && this.IsRed(topcardD)))
					{
						return ("Stack "+topcardO+" "+topcardD);
					}
						
				}
			}
			return null;
			
		}
	
	//method that moves a card from a main board into the freecell area 
	public String BoardToFreecell(Stack OriginalStack)
	{
		if(freecell.size() < 3)		
			return "freecell "+OriginalStack.peek();
			
		
		return null;
	}
	
	
	//method that moves a card from  the freecell area into the main board
		
	public String FreecellToBoard(Stack DestinationStack,String card)
		{
		
		//if stack is empty just add the card 
			if(DestinationStack.isEmpty())
			{
					
				return "newstack "+card;
				
			}
			//if its not empty check if freecell card is red and topcard is black(and the other way around)
			//and our card is smaller by 1
			else
			{
				if(!DestinationStack.contains(card) ) {
					

					if(this.getCardNumber(card)==this.getCardNumber((String) DestinationStack.peek())-1)
					{	
						
						if((this.IsRed(card) && this.IsBlack((String)DestinationStack.peek())) || (this.IsBlack(card) && this.IsRed((String) DestinationStack.peek())))
						{
							return "Stack "+card+" "+DestinationStack.peek();
						}
					
					
					}
				}
			}
			
			return null;
		}
	
	//method that moves a card from a main board into the foundation area 
		public String BoardToFoundation(Stack OriginalStack)
		{	
			String topcard="";
			if(!OriginalStack.isEmpty())
				topcard=(String) OriginalStack.peek();
			//Adding the Aces into the foundation
			if(foundation.isEmpty() && this.getCardNumber(topcard)==1)
			{
				return "source "+topcard;
			}
			//Adding all the other cards
			else {
				
				for(int i=0;i<foundation.size(); i++) {
					if(this.getCardType(foundation.get(i)).equals(this.getCardType(topcard)))
					{
						if(this.getCardNumber(topcard)-this.getCardNumber((String) foundation.get(i))==1)
							return "source "+topcard;
					
					}
				}
			}
			
			return null;
		}
		//method that moves a card from foundation to a stack 
		//not tested
		public String FoundationToBoard(Stack destinationStack,String card)
		{	
			String topcard=(String)destinationStack.peek();
			if(foundation.size()>0)
			{
				if(foundation.contains(card))
				{
					if(!destinationStack.isEmpty())	{
						if((this.IsRed(card) && this.IsBlack(topcard)) || 
								this.IsRed(topcard) && this.IsBlack(card))
						{
							if(this.getCardNumber(topcard)-this.getCardNumber(card)==1)
							{	
								return "stack "+topcard+" "+destinationStack.peek();
							}
						}
					}
					else
						return "newstack "+topcard;
				}
				else
				{
					System.out.println("Card is not in the foundation");
					
				}
			}
			else
			{
				System.out.println("Foundation is empty,can't move a card");
				
			}
			return null;
		}
		
	//method that returns 0 if game is over(win) or 1 if game is not over
	public boolean isSolution(ArrayList<Stack> Board)
	{	
		//if all the cards are in the foundation area then game is over
		//other methods check for the correct insertion of the cards into the foundation
		for(int i=0;i<Board.size();i++) {
			//if even one of the card stacks is not empty game is not won
			if(!Board.get(i).isEmpty())
				return false; 
		
		}
		return true;
	}
	//method that finds and returns all the possible moves given the current state of the board
	//possible moves:
	//stack card1 card2
	//freecell card
	//newstack card
	//source card
	public ArrayList<String> PossibleMoves(ArrayList<Stack> Board)
	{	ArrayList<String> Moves= new ArrayList<>();
		
		
		
		for(int i=0;i<Board.size();i++)
		//checking possible moves from stack to another stack first	
		{	
			for(int j=0;j<Board.size();j++)
			{//check if the same stack is not used 
				if(!Board.get(i).equals(Board.get(j)))
				{	String move=this.StackCard(Board.get(i), Board.get(j));
					if(move!=null)
						{	
							if(!Moves.contains(move)) 
							{	
								Moves.add(move);
								
							}
						}
				}
				
			}
		}
		//from freecell to stack and opposite 
		for(int i=0;i<freecell.size();i++)
		{
			String topcard=freecell.get(i);
			for(int j=0;j<Board.size();j++)
			{	
				String move=this.FreecellToBoard(Board.get(j), freecell.get(i));
				if(move!=null)
				{
					Moves.add(move);
				}
			
					
			}
		}
		//opposite
		for(int i=0;i<Board.size();i++)
		{
			String move=this.BoardToFreecell(Board.get(i));
			if(move!=null)	
				Moves.add(move);
		}
		//Board to foundation
		for(int i=0;i<Board.size();i++)
		{
			String move=this.BoardToFoundation(Board.get(i));
			if(move!=null) 
				Moves.add(move);
		
		}
		//opposite
		for(int i=0;i<foundation.size();i++)
		{	String card=foundation.get(i);
			for(int j=0;j<Board.size();j++)
			{
				String move=this.FoundationToBoard(Board.get(j), card);
				if(move!=null)
					Moves.add(move);
			}
		}
		
		return	Moves;
	}
	
	
	public Stack performMove(ArrayList<Stack> Board,String Move)
	{	
			String[] move=Move.split(" ");
			
			for(int j=0;j<move.length;j++) {
				if(move[j].equals("Stack"))
				{
					String card1=move[j+1];
					String card2=move[j+2];
					String cardF=null;
					Stack originalStack=new Stack();
					Stack destinationStack=new Stack();
					//find the correct stacks from the cards
					for(int k=0;k<Board.size();k++)
					{
						if(Board.get(k).contains(card1))
							originalStack=Board.get(k);
						
						if(Board.get(k).contains(card2))
							destinationStack=Board.get(k);
						
					}
					for(int k=0;k<freecell.size();k++) 
					{
						 if (freecell.get(k).contains(card1)) {
							cardF=freecell.get(k);
						 	freecell.remove(cardF);
						 
						 }
					}
					if(cardF!=null)
						this.performFreecellBoard(destinationStack, cardF);
					else {
						
						this.performStack(originalStack, destinationStack);
						return originalStack;
					}
				}
			//Stack to freecell
				else if(move[j].equals("freecell"))
				{
					String card=move[j+1];
					Stack originalStack=new Stack();
					
					for(int k=0;k<Board.size();k++)
					{
						if(Board.get(k).contains(card))
							originalStack=Board.get(k);
						
						
					}
					this.performBoardFreecell(originalStack);
					return originalStack;
				}
			
				
				
				
			//Stack to source
				else if(move[j].equals("source"))
				{
					String card=move[j+1];
					Stack originalStack=new Stack();
					for(int k=0;k<Board.size();k++)
					{
						if(Board.get(k).contains(card))
							originalStack=Board.get(k);
						
					}
					this.performBoardFoundation(originalStack);
					return originalStack;
				}
				else if(move[j].equals("newstack"))
				{
					String card=move[j+1];
					Stack destinationStack=new Stack();
					for(int k=0;k<Board.size();k++)
					{
						if(Board.get(k).isEmpty())
							destinationStack=Board.get(k);
					}
					
					this.performFreecellBoard(destinationStack, card);
				
				}
			
		
			}
			
		
		return null;
	}
	
	//method that returns the number of a card 		
	public int getCardNumber(String card)
	{
		
		return Integer.parseInt(card.substring(1));
	}
	//method that returns the type of a card
	public String getCardType(String card)
	{
		
		return card.substring(0,1);
	}
	
	//method that returns true if the given card is red(H or D)
	public boolean IsRed(String card) {
		return (getCardType(card).equals("D") || getCardType(card).equals("H")); 
	}
	
	//method that returns true if the given card is black(C or S)
		public boolean IsBlack(String card) {
			return (getCardType(card).equals("S") || getCardType(card).equals("C")); 
		}
	
	
	
	
	
	
	
	public void PrintStack(Stack OriginalStack,Stack changedStack) {
		
		//print the changes between 2 stacks
		System.out.println(OriginalStack);
		
		System.out.println(changedStack);
	}
	
	
}
