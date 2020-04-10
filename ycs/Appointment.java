package io.junq.examples.boot;



public class Appointment {
	String id,title,call_number,collection_location,appointment_time,take;

	public String getid() {
        return id;
    }

    public String gettitle() {
       return title;
    }

    public String getcall_number() {
        return call_number;
    }

    public String getcollection_location() {
        return collection_location;
    }

    public String getappointment_time() {
        return appointment_time;
    }

    public String gettake() {
        return take;
    }

    public void setid(String id) {
        this.id = id;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public void setcall_number(String call_number) {
    	this.call_number=call_number;
   }

    public void setcollection_location(String collection_location) {
    	this.collection_location=collection_location;
    }

    public void setappointment_time(String appointment_time) {
    	this.appointment_time=appointment_time;
    }

    public void settake(String take) {
    	this.take=take;
    }

}