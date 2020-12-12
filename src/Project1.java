import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
* COP 3530: Project 1 – Array Searches and Sorts
* 
* Description of the class using as many lines as needed
* with <p> between paragraphs. Including descriptions of the
* input required and output generated.
* 
* This class creates an array of objects that holds an array of objects of type Country.
* The user is prompted for the csv file name and that file is parsed using buffered reader and separated by commas.
* This process is looped until null is reached, also in this is the creation of the country object passing in the values from String[] countries as the parameters to the Country object.
* <p>
* The switch case is inside a loop that is only exited once the exit value is changed by case 6. The Switch case receives input, if the character entered is not 1-6 the default is activated and the statements starts again.
* The first case calls caseOne() that prints all the countries and their data, the 2nd case calls caseTwo() which sorts the countries by name using Insertion sort, the third case calls caseThree which sorts the countries using selection sort,
* the fourth case calls caseFour() which sorts the countries using bubble sort, and case 5 asks for input for the country name you want to find, if the countries are sorted by name, represented by search = 1 then it searches using binary search
* otherwise it searches sequentially. case 6 exits the will loop and then the program ends, and the default case says that the input is invalid and the loop starts over.
* 
* @author Nathanial Smith
* @version 9/10/2020
*/
public class Project1 {

	static Country[] country = new Country[153];
	public static int search = 0;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("COP3530 Project1\nName: Nathanial Smith N01232886\nInstructor: Xudong Liu\nArray Searches and Sorts");
		System.out.printf("Enter the name of the file: ");
		
		
		
		BufferedReader read = null;
		String split = ",";
		String line = "";
		
		
		String name, capitol;
		Double gdp;
		Long population, cases, deaths;
		
		int i = 0;
		while(i == 0) {
			
		String file = input.nextLine();
		try {
			
			read = new BufferedReader(new FileReader(file));
			read.readLine();
			while ((line = read.readLine()) != null) {
				String[] countries = line.split(split);
				
				
				name = countries[0];
				capitol = countries[1];
				population = Long.parseLong(countries[2]);
				gdp = Double.parseDouble(countries[3]);
				cases = Long.parseLong(countries[4]);
				deaths = Long.parseLong(countries[5]);
				
				country[i] = new Country(name, capitol, population, gdp, cases, deaths);
				
				
				i++;
			}
				
				 
				
				read.close();
			}
		catch(FileNotFoundException e) {
			System.out.println("Can't open file");
			System.out.print("Enter the name of the file: ");
		}
		catch(IOException e) {
			System.out.println("Problem with reading file");
	
	
		}
		}
		input.close();
		int exit = 0;
		System.out.println("");
		
        
        do {
        
        System.out.println("1. Print a countries report");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by COVID-19 CFR");
        System.out.println("4. Sort by GDP per capita");
        System.out.println("5. Find and print a given country");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
        
        String in = input.nextLine();
        System.out.println("");
        
        switch (in) {
        
            case "1":
                caseOne();
            break;
            
            case "2":
            	System.out.println("Countries sorted by Name.\n");
            	caseTwo();
            	search = 1;
            break;
            
            case "3":
            	System.out.println("Countries sorted by COVID-19 CFR.\n");
            	caseThree();
            	search = 0;
            break;
            
            case "4":
            	System.out.println("Countries are sorted by GDP per capita\n");
                caseFour();
                search = 0;
            break;
            
            case "5":
            	System.out.print("Enter Name of Country: ");
            	String enteredName = input.nextLine();
            	System.out.println("");
            	int elementFound;
            	
            	if (search == 1) {
            		elementFound = caseFive_Binary(enteredName);
            		if (elementFound == 154) {
            			System.out.println("Country not found in list");
            		} else {
            			country[elementFound].print_Countries();
            		}
            	} else {
            		elementFound = caseFive_Sequential(enteredName);
            		if (elementFound == 153) {
            			System.out.println("Country not found in list");
            		} else {
            			country[elementFound].print_Countries();
            		}
            	}
            break;
            
            case "6":
            	exit = 1;
            break;
            
            default:
            	System.out.println("The option entered is invalid, enter 1-6");
            	System.out.println("");
            break;
        }
        } while(exit==0);
			
        System.out.println("Exited");
			
		}
	
	    /**
	    *This method prints the countries for case 1 and each country's print function
	    */
	    static void caseOne() {
			int i = 0;
			System.out.println("Name                               Capitol              Population      GDP             Cases     Deaths");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            while(i < 153) {
            	country[i].print_Countries();
            	i++;
            }
            System.out.println("");
		}
	    
	    /**
	    *This method is meant to access the countries array and sort the countries alphabetically ascending using insertion sort
	    */
		static void caseTwo() {
			
			Country temp = new Country(null, null, null, null, null, null);
			
			int inner;
			int outer = 1;
			while(outer < 153) {
				temp = country[outer];
				inner = outer - 1;
				
				int value = country[inner].Compare_Names(temp.Get_Name());
				while(inner >= 0 && value > 0) {
					country[inner + 1] = country[inner];
					inner--;
					if(inner >= 0) {
					value = country[inner].Get_Name().compareToIgnoreCase(temp.Get_Name());
					}
				}
				country[inner + 1] = temp;
				outer++;
			}
			 
		}
		
		/**
		* This method sorts the countries by COVID-19 CFR ascending using selection sort
		*/
        static void caseThree() {
        	
        	Country temp = new Country(null, null, null, null, null, null);
        	
        	int numb = 153;
        	
        	for (int i = 0; i < numb - 1; i++) {
        		int min = i;
        		
        		for (int j = i + 1; j < numb; j++) {
        			if (country[j].Get_CFR() < country[min].Get_CFR()) {
        				min = j;
        			}
        		}
        		
        		temp = country[min];
        		country[min] = country[i];
        		country[i] = temp;
        	}
        	
		}
        
        /**
        * This method sorts the countries by GDP per capita descending using bubble sort
        */
        static void caseFour() {
        	
        	Country temp = new Country(null, null, null, null, null, null);
        	
        	int numb = 153;
        	
        	for ( int i = 0; i  < numb - 1; i++) {
        		for (int j = 0; j < numb - i - 1; j++) {
        			
        			if (country[j].Get_GDP_Per_Capita() < country[j + 1].Get_GDP_Per_Capita()) {
        				temp = country[j];
        				country[j] = country[j + 1];
        				country[j + 1] = temp;
        			}
        		}
        	}
		}
        
        /**
        *This method searches the list of countries for a country using binary search
        *
        * @param String enteredName  this passes the name the user is looking to find
        * @return mid  this returns mid because mid is the object being searched for, @return 154 this returns 154
        * because it out out of bounds of the array and the case will use the value to say the item is not found
        */
        static int caseFive_Binary(String enteredName) {
        	int lower = 0;
        	int upper = 154 - 1;
        	int mid;
		
        	while(lower <= upper) {
        		mid = (lower + upper) / 2;
        		int value = country[mid].Compare_Names(enteredName);
        		if(value == 0) {
        			return mid;
        		} else if (value > 0) {
        			upper = mid - 1;
        		} else {
        			lower = mid + 1;
        		}
        	}
        	return 153;
		}
        /**
        * This method searches the list of countries for a country using sequential search
        *
        * @param String enteredName  this passes the name the user is looking to find
        * @return 153 this means the loop went out of bounds and the item was not found, @return i this is the location of the object that was being searched for
        */
        static int caseFive_Sequential(String enteredName) {
        	int i = 0;
        	
        	while(i < 153) {
        		int value = country[i].Compare_Names(enteredName);
        		if (value == 0) {
        			break;
        		}
        		i++;
        	}
        	if(i == 153) {
        		return 153;
        	} else {
        		return i;
        	}
        }

}

