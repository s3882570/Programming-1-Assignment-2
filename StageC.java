
/*
 * Sample solution for assignment 2, SP1 2021
 * Not the most efficient implementation but about the
 * standard expected by week 8.
 */
import java.util.Scanner;


public class StageC {
	Scanner sc = new Scanner(System.in);
	TffEvent [] tffEvents; // The array of event references
	int numEvents = 0;
	 
	public void runMenu() {
		String selection;
		System.out.print("Please enter the maximum number of events : ");
		tffEvents = new TffEvent[Integer.parseInt(sc.nextLine())];
		
		 do {
			   // display menu options
			   System.out.println("* Taradale Folk Festival Event Booking Menu *");
			   System.out.println("   A. Add new event");
			   System.out.println("   B. View event");
			   System.out.println("   C. List All events");
			   System.out.println("   D. Make booking");	
			   System.out.println("   E. Refund booking");	
			   System.out.println("   X. Exit");
			   System.out.println();

			   System.out.print("Please enter your choice: ");
			   selection = sc.nextLine();

			   System.out.println();

			   // process user's selection
			   switch (selection.toUpperCase()) {
			   	case "A":
			   		addNewEvent();
			   		break;
			   	case "B":
			   		displayEvent();
			   		break;
			   	case "C":
			        displayAllEvents();
			        break;       
			    case "D":
			        bookEvent();
			        break;
			    case "E":
			        refundBooking();
			        break;
			    case "X":
			        System.out.println("Exiting the program...");
			        break;
			    default:
			        System.out.println("Error - invalid selection!");
			   }
			   System.out.println();

		   } while (!selection.equalsIgnoreCase("X"));
		   sc.close();
	}
	
	// If the tffEvents array is not full, collect details
	// from user, create new event object and store in array.
	// Display error message if array is full.
	private void addNewEvent() {
		TffEvent event;
		String title;
		String description;
		String choice;
		double priceAdult, priceChild, priceConcession;
		
		// Can't add an event if the tffEvents array is full
		if (numEvents >= tffEvents.length) {
			System.out.println("Error - unable to store more events");
			return;
		}
		
		System.out.print("Please enter the event title: ");
		title = sc.nextLine();
		System.out.print("Please enter the event description: ");
		description = sc.nextLine();
		
		System.out.print("Please enter the price of an adult ticket: ");
		priceAdult = Double.parseDouble(sc.nextLine());	
		
		System.out.print("Please enter the price of an child ticket: ");
		priceChild = Double.parseDouble(sc.nextLine());	
		
		System.out.print("Please enter the price of an concession ticket: ");
		priceConcession = Double.parseDouble(sc.nextLine());	
		
		do {
			System.out.print("Is this an indoor 'experience'? Y/N: ");
			choice = sc.nextLine().toUpperCase();
		} while (!(choice.contentEquals("Y") || choice.contentEquals("N")));
		
		if (choice.contentEquals("N"))
				event = new TffEvent(title,description, priceAdult, priceChild, priceConcession);
		else {
			int maxTickets;
			System.out.print("Please enter the maximum number of tickets the event can have: ");
			maxTickets = Integer.parseInt(sc.nextLine());
			event = null;
			event = new TffExperienceEvent(title,description, priceAdult, priceChild,
										   priceConcession, maxTickets);
		}
		
		tffEvents[numEvents] = event;
		numEvents++;
		event.displayEvent();
	}

	// A helper method for determining
	// if an event with title of targetTitle is
	// stored in the system. Returns either the index
	// if found, or -1 to signal it wasn't found
	private int findEventPos(String targetTitle){
		for (int i = 0; i < numEvents; i++){
			if (tffEvents[i].getTitle().equals(targetTitle))
				return i;
		}
		return -1;
	}

	// Displays all tffEvents
	private void displayAllEvents() {
		if (numEvents > 0) {			// Check there are tffEvents to display
			for (int i = 0; i < numEvents; i++) {
				System.out.println(tffEvents[i].getTitle());
			}
		} else {
			System.out.printf("There are no events in the system.\n");
		}
	}

	// Ask the user for an event title and attempt to locate that event.
	private void displayEvent(){
		String title;
		int pos;
		
		if (numEvents > 0) {	// First check there are tffEvents to display
			System.out.print("Please enter event title: ");
			title = sc.nextLine();
			pos = findEventPos(title);
			if (pos >= 0) {		// If nothing found return error
				tffEvents[pos].displayEvent();
			} 
			else
				System.out.printf("\nError - event with title %s could not be found.\n" ,title);
		} 
		else {
			System.out.printf("\nError - There are no tffEvents to view in the system.\n");
		}
	}
	
	// Ask the user for an event title and attempt to locate that event.
	private void bookEvent(){
		String title, name;
		int pos, numTickets, choice;
		String ticketType = null;
		
		if (numEvents > 0) {	// First check there are events to display
			System.out.print("Please enter event title: ");
			title = sc.nextLine();
			pos = findEventPos(title);
			if (pos >= 0) {		// If nothing found return error
				
				System.out.print("Please enter the number of bookings required: ");
				numTickets = Integer.parseInt(sc.nextLine());
				
				for(int i = 0; i < numTickets; i++) {
					do {
						System.out.println("Please choose from the following ticket types: ");
						System.out.println("   1: Adult");
						System.out.println("   2: Child");
						System.out.println("   3: Concession");
						System.out.print("Enter choice : ");
						choice = Integer.parseInt(sc.nextLine());
						if (choice < 1 || choice > 3)
							System.out.println("Must be 1, 2 or 3");
					} while (choice < 1 || choice > 3);
					switch (choice) {
					case 1:
						ticketType = "Adult";
						break;
					case 2:
						ticketType = "Child";
						break;
					case 3:
						ticketType = "Concession";
						break;
					}
					
					System.out.print("Please enter the name: ");
					name = sc.nextLine();
					if (tffEvents[pos].bookEvent(ticketType,name) == false)
						System.out.println("Error - already fully booked.");
				}
			}
			else
				System.out.printf("\nError - event with title %s could not be found.\n" ,title);
		} 
		else {
			System.out.printf("\nError - There are no events in the system.\n");
		}
	}
	
	//Attempt to refund an event booking 
	private void refundBooking(){
		String title, name;
		int pos;
		boolean result;
		
		// Can't refund if there are no events!
		if (numEvents == 0) {
			System.out.println("Error - there are no events!");
			return;
		}
		
		System.out.print("Enter event title: ");
		title = sc.nextLine();
		pos = findEventPos(title);
		
		if (pos < 0) {		// If nothing found return error
			System.out.printf("\nError - event with title %s could not be found.\n" ,title);
			return;
		}
		
		// Can't refund a TffEvent event, only a TffExperienceEvent
		if (!(tffEvents[pos] instanceof TffExperienceEvent)) {
			System.out.println("Error - event not an 'experience' event");
			return;
		}
		
		System.out.print("Please enter the name: ");
		name = sc.nextLine();
		result = ((TffExperienceEvent) tffEvents[pos]).refundBooking(name);
		if (result == false)
			System.out.println("Error - No such booking");
	}
	
	public static void main(String[] args) {
		StageC stageC = new StageC();
		stageC.runMenu();
	}

}
