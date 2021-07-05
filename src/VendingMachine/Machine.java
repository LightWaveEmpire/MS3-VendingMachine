package VendingMachine;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Machine {

	static Vector<Inventory> stock = new Vector<Inventory>();
	Currency reserve = new Currency();
	long rows ;
    long columns ;
    
	public Machine()
	{
		rows=0;
		columns=0;
		
		
	}
@SuppressWarnings("unchecked")
public void Restock()
{
	System.out.print("Please provide the inventory list: ");
	Scanner in = new Scanner(System.in);
	String file = in.nextLine();
	JSONParser path = new JSONParser();
	
	try (FileReader reader = new FileReader(file))
    {
        //Read JSON file
        JSONObject obj = (JSONObject) path.parse(reader);

        JSONArray matrix = new JSONArray();
        matrix.add(obj.get("config"));
        
        
        //Iterate over itemlist array
        matrix.forEach(entry -> {
       JSONObject object = (JSONObject) entry;
       rows= (Long) object.get("rows");
       String col = (String) object.get("columns");
       columns = Long.parseLong(col);
        
        		
        });
        stock.clear();
        JSONArray itemlist = (JSONArray) obj.get("items");
        itemlist.forEach( it -> parselist( (JSONObject) it ));
        
        System.out.println(stock.size() + " items added to the vending machine.");
        
        int cnt = 0;
        
        for(long i=0; i < rows; i++)
        {
        	for (long j=0; j< columns; j++ )
        	{
        		long k = j;
        		if(cnt < stock.size())
        		{
        			// assign columns /rows
            		Inventory inv = stock.get((int) cnt);
            		inv.loc = String.valueOf( (char)(i + 65) ) + Long.toString(k+1);
            		//System.out.println("Item: " + inv.name + " has location assigned: " + inv.loc);
        		}
        
        		cnt++;
        	}
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }
	
	
}
private static void parselist(JSONObject item) 
{
	Inventory i = new Inventory();
   
    //Get item name 
    String name = (String) item.get("name");    
    i.name = name;
    
    //Get item amount
    long amount = (long) item.get("amount");  
    i.quantity = amount; 
    
    //Get item price
    String price = (String) item.get("price");
    i.price = price;
    
    stock.add(i);
}
@SuppressWarnings("unused")
public void Vend(){
	
	if(rows==0)
	{
		System.out.println("The vending maching is empty \n");
	}
	else
	{

		System.out.println("Vending options");
		int cnt = 0;
		
		for(int i=0; i< rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				int k = j;
				String e = String.valueOf( (char)(i + 65) ) + Integer.toString(k+1);
				if(cnt<stock.size())
				{
					Inventory inv = stock.get((int) cnt);
					//String pos = String.valueOf( (char)(i + 62) ) + Long.toString(j+1);
					System.out.println(inv.loc + ": " + inv.name + " " + inv.price);
				}
				else
				{
					System.out.println(e + ": Out of Stock");
				}
				
				cnt++;
			}
		}
			System.out.print("Please make a selection: ");
			Scanner in = new Scanner(System.in);
			String sel = in.nextLine();
			//System.out.println("User input: " + sel);
			
			int first_index = -1;
			for(int u = 0; u < stock.size(); u++)
			{
				Inventory it = stock.get(u);
				if(it.loc.compareToIgnoreCase(sel) == 0)
				{
					first_index = u;
					break;
				}
			}
			
			if(first_index >= 0)
			{
				Inventory it = stock.get(first_index);
				System.out.print("You Selected: " + it.name + " " + it.price + ", is this correct (Y/N)?  ");
				in = new Scanner(System.in);
				char yn = in.next().charAt(0);
				char select = Character.toUpperCase(yn);
				in.nextLine();
				
				switch(select) {
				case 'Y':
					if(it.quantity > 0)
						Purchase(it);
					else
					{
						System.out.println("Item is out of stock. Please choose again.");
						Vend();
					}
					break;
				default:
					System.out.println("Please choose again...");
					Vend();
					break;
				}
				
			}
			else
			{
				System.out.println("That is out of stock or invalid entry, please make another selection: ");
				Vend();
			}
	}
}

 //The following function is courtesy of: https://stackoverflow.com/a/23991368
 public static BigDecimal parse(final String amount, final Locale locale) throws ParseException 
 {
	 final NumberFormat format = NumberFormat.getNumberInstance(locale);
	 if (format instanceof DecimalFormat) 
	 {
		 ((DecimalFormat) format).setParseBigDecimal(true);
	 }
	 try 
	 {
		 return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
	 } 
	 catch (java.text.ParseException e) 
	 {
		 e.printStackTrace();
	 }
	 return null;
}

@SuppressWarnings("resource")
public void Purchase(Inventory item)
{
	boolean cont = true;
	
	BigDecimal total = new BigDecimal(0);
	BigDecimal price = new BigDecimal(0);
	try {
		total = parse(item.price, Locale.US);
		price = total;
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	while(cont)
	{
		if(total.compareTo(BigDecimal.ZERO) < 0)
		{
			cont = false;
			item.quantity--;
			System.out.println("Amount returned: $"+ total.abs());
		}
		else if(total.compareTo(BigDecimal.ZERO) == 0)
		{
			item.quantity--;
			cont = false;
			System.out.println("Thank you. Have a nice day.");
		}
		else
		{
			System.out.println("Please select an option: ");
			System.out.println("D: Insert Dollars");
			System.out.println("Q: Insert Quarters");
			System.out.println("M: Insert Dimes");
			System.out.println("N: Insert Nickles");
			System.out.println("R: Refund Current Purchase");
		
			Scanner in = new Scanner(System.in);
			char yn = in.next().charAt(0);
			char select = Character.toUpperCase(yn);
			in.nextLine();
		
			switch(select) {
			case 'D':
				System.out.print("Please enter dollar value ($ 1,5,10,20): ");
				in = new Scanner(System.in);
				int val = in.nextInt();
				in.nextLine();
				
				if(val != 1 && val != 5 && val != 10 && val != 20)
				{
					System.out.println("Invalid dollar amount. Please choose again.");
					break;
				}
				
				total = total.subtract(BigDecimal.valueOf(val));
				if(total.compareTo(BigDecimal.ZERO) > 0)
					System.out.println("Remaining balance: $" + total);
				break;
			case 'Q':
				total = total.subtract(BigDecimal.valueOf(reserve.qt));
				if(total.compareTo(BigDecimal.ZERO) > 0)
					System.out.println("Remaining balance: $" + total);
				break;
			case 'M': 
				total = total.subtract(BigDecimal.valueOf(reserve.dm));
				if(total.compareTo(BigDecimal.ZERO) > 0)
					System.out.println("Remaining balance: $" + total);
				break;
			case 'N': 
				total = total.subtract(BigDecimal.valueOf(reserve.nk));
				if(total.compareTo(BigDecimal.ZERO) > 0)
					System.out.println("Remaining balance: $" + total);
				break;
			case 'R': 
				System.out.println("You are refunded $" + (price.subtract(total)));
				cont = false;
				break;
			default:
				System.out.println("Invalid selection.");
				break;
			}
		}
		
	}
}

}

