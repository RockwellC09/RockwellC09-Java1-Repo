package myEnum;

public class myEnumClass {
	
	// create ENUM populated with movie information
	public enum Movies { 
		Movie1("White House Down", "2013", "50", "63"),
		Movie2("Fast & Furious 6", "2013", "80", "84"),
		Movie3("The Family", "2013", "29", "43");
		
		private String title;
	    private String year;
	    private String criticScore;
	    private String audienceScore;
	    
		private Movies(String movTitle, String movYear, String critic, String audience) {
	        this.title = movTitle;
	        this.year = movYear;
	        this.criticScore = critic;
	        this.audienceScore = audience;
	    }
		
		public String setTitle() {
			return title;
		}
		
		public String setYear() {
			return year;
		}
		
		public String setCriticScore() {
			return criticScore;
		}
		
		public String setAudienceScore() {
			return audienceScore;
		}
		
	}
}
