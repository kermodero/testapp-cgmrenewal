package moh.adp.xml;

public enum ContactRelationship {	
    SPOUSE  ("spouse"),
    PARENT  ("parent"),
    GUARDIAN  ("guardian"),
    TRUSTEE  ("trustee"),
    ATTORNEY  ("attorney");
	
	private String text;

    ContactRelationship(String text) {
    	this.text = text;
    }

    public String getText(){
    	return text;
    }
	
}
