package io.junq.examples.boot;



public class Appointment {
	String rid,book_name,barcode,appointment_time;
	
	public String getrid() {
        return rid;
    }

    public String getbook_name() {
        return book_name;
    }
	
    public String getbarcode() {
        return barcode;
    }
    
    public String getappointment_time() {
        return appointment_time;
    }
    
    public void setrid(String rid) {
        this.rid = rid;
    }

    public void setbook_name(String book_name) {
        this.book_name = book_name;
    }
    
    public void setbarcode(String barcode) {
    	this.barcode=barcode;
    }
    
    public void setappointment_time(String appointment_time) {
    	this.appointment_time=appointment_time;
    }
    
}