

// Definition for a venue event
public class TffEvent {
	private String title;
	private String description;
	protected int numSold = 0;
	private final double PRICE_ADULT, PRICE_CHILD, PRICE_CONCESSION;
	
	
	public TffEvent(String title, String description, double priceAdult,
					double priceChild, double priceConcession) {
		this.title = title;
		this.description = description;
		this.PRICE_ADULT = priceAdult;
		this.PRICE_CHILD = priceChild;
		this.PRICE_CONCESSION = priceConcession;
	}
	
	protected double getPrice(String ticketType) {
		double price = 0;
		switch (ticketType) {
		case "Adult":
			price = PRICE_ADULT;
			break;
		case "Child":
			price = PRICE_CHILD;
			break;
		case "Concession":
			price = PRICE_CONCESSION;
		}
		return price;
	}
	
	public String getTitle() {
		return title;
	}
	
	// Book an event and give ticket details
	public boolean bookEvent(String ticketType, String name) {
		double price = 0;
		System.out.printf("\n  Ticket        : %s\n", this.title);
		System.out.printf("  Ticket holder : %s\n", name);
		switch (ticketType) {
		case "Adult":
			price = PRICE_ADULT;
			break;
		case "Child":
			price = PRICE_CHILD;
			break;
		case "Concession":
			price = PRICE_CONCESSION;
		}
		System.out.printf("  Price         : $%-6.2f\n",price);
		
		numSold++;
		return true;
	}
	
	// Display details of an TffEvent
	public void displayEvent() {
		System.out.printf("Title                    : %s\n",title);
		System.out.printf("Description              : %s\n",description);
		System.out.printf("Price adult              : $%-6.2f\n",PRICE_ADULT);
		System.out.printf("Price child              : $%-6.2f\n",PRICE_CHILD);
		System.out.printf("Price concession         : $%-6.2f\n",PRICE_CONCESSION);
		System.out.printf("Total ticket sales       : %d\n",numSold);
	}
}
