package VendingMachine;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Machine vm = new Machine();
		boolean cont = true;
		
		while(cont) 
		{
			System.out.println("Sam's Vending Machine");
			System.out.println("Options: ");
			System.out.println("1. Purchase");
			System.out.println("2. Update Inventory");
			System.out.println("3. Exit");
			System.out.print("Please make a selection: ");
			Scanner in = new Scanner(System.in);
			int select = in.nextInt();
			in.nextLine();
		
			switch (select)
			{
			case 1:
				vm.Vend();
				break;
			case 2:
				vm.Restock();
				break;
			case 3:
				cont=false;
				break;
			default:
				System.out.println("Invalid choice, please choose again.");
				break;
			}
			
		}
		

	}

}
