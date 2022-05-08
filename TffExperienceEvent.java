

// Implements an experience event, which has a limited
// number of tickets.
public class TffExperienceEvent extends TffEvent{
	private int maxTickets; // An experience event has a limit on the number of tickets
	private String [] bookings;  // Organised as [ticket], where ticket is 0..(maxTickets - 1). 
								 // The booking is a string
								 // containing the ticket holder's name, 
								 // and the amount the ticket cost
	public TffExperienceEvent(String title, String description, double priceAdult,
							  double priceChild, double priceConcession, int maxTickets) {
		super(title,description,priceAdult, priceChild, priceConcession);
		this.maxTickets = maxTickets;
		this.bookings = new String[maxTickets];
	}
	

	// Attempt to book one ticket for a capped activity - fails if there are no
	// tickets available
	@Override
	public boolean bookEvent(String ticketType, String name) {
		double cost;
		String booking;
		
		// Fail if there are no tickets available
		if (numSold >= maxTickets)
			return false;
		
		// Book the activity - increases a count of the number of tickets sold
		super.bookEvent(ticketType,name);
		
		cost = getPrice(ticketType);
		// Create the booking string: ticket holder name + cost of the ticket
		booking = String.format("%-21s : $%-5.2f",name,cost);
		
		// Find an empty place in the bookings array to store the booking string
		for (int i = 0; i < bookings.length; i++) {
			if (bookings[i] == null) {
				bookings[i] = booking;
				break;
			}
		}
		return true;
	}
	
	// Attempt to cancel a capped activity booking
	// Will fail if no booking in that session contains the ticket holder's name
	public boolean refundBooking(String name) {
		// For the given session, search all array elements for a booking string
		// containing the ticket holder's name - and delete the string
		for(int i = 0; i < bookings.length; i++)
			if (bookings[i] != null) {
				if (bookings[i].contains(name)) {
					System.out.printf("Cancelled ticket : %s\n",bookings[i]);
					bookings[i] = null;
					// reduce the count of the number of tickets sold
					numSold--;
					return true;	
				}
			}
		return false;
	}
	
	@Override
	public void displayEvent() {
		super.displayEvent();
		System.out.printf("Max tickets              : %d\n",maxTickets);
		System.out.println("Bookings                 :");
		for(int i = 0; i < bookings.length; i++)
				if (bookings[i] != null)
					System.out.println("   " + bookings[i]);
	}
}
