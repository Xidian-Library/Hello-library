package io.junq.examples.boot;

public class Delay_message {
String book_name,book_author,barcode,fine,delay_time;
	
	public String getbook_name() {
        return book_name;
    }

    public String getbook_author() {
        return book_author;
    }
	
    public String getbarcode() {
        return barcode;
    }
    
    public String getfine() {
        return fine;
    }
    
    public String getdelay_time() {
        return delay_time;
    }

    public void setbook_name(String book_name) {
        this.book_name = book_name;
    }
    
    public void setbook_author(String book_author) {
        this.book_author = book_author;
    }
    
    public void setbarcode(String barcode) {
    	this.barcode=barcode;
    }
    
    public void setfine(String fine) {
    	this.fine=fine;
    }
    
    public void setdelay_time(String delay_time) {
    	this.delay_time=delay_time;
    }
}
