package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;

public class Book{

    private String book_name;

    private String book_author;

    private String book_publisher;

    private String book_date;

    private String book_id;

    private String copy_id;

    private String book_type;

    private String book_address;

    private String book_price;

    private String book_isborrow;

    private String book_isappointment;

    private String book_brief;

    private String barcode;

    private String borrowerid;

    private Date borrow_time;

    private Date return_time;

    private Float fine;

    private int delay_time;

    private boolean ispay;

    private String total;


    public String getName() {
        return book_name;
    }

    public String getAuthor() {
        return book_author;
    }

    public String getPublisher() {
        return book_publisher;
    }

    public String getDate() {
        return book_date;
    }

    public String getBookid() {
        return book_id;
    }

    public String getCopyid() {
        return String.valueOf(copy_id);
    }

    public String getType() {
        return book_type;
    }

    public String getAddress() {
        return book_address;
    }

    public String getPrice(){return book_price;}

    public String getIsborrow(){return book_isborrow;}

    public String getIsappointment(){return book_isappointment;}

    public String getBrief(){return book_brief;}

    public String getBarcode(){return barcode;}

    public String getBorrowerid() {
        return borrowerid;
    }

    public Date getBorrowtime() {
        return borrow_time;
    }

    public Date getRetuentime() { return return_time; }

    public Float getFine(){return fine;}

    public int getDelaytime(){return delay_time;}

    public boolean getIspay(){return ispay;}

    public void setName(String name) {
        this.book_name=name;
    }

    public void setAuthor(String author) {
        this.book_author=author;
    }

    public void setPublisher(String publisher) {
        this.book_publisher=publisher;
    }

    public void setDate(String date) {
        this.book_date=date;
    }

    public void setBookid(String bookid) { this.book_id=bookid; }

    public void setCopyid(String copyid) { this.copy_id=copyid; }

    public void setType(String type) { this.book_type=type; }

    public void setAddress(String address) { this.book_address=address; }

    public void setPrice(String price){this.book_price=price;}

    public void setIsborrow(String isborrow){this.book_isborrow=isborrow;}

    public void setIsappointment(String isappointment){this.book_isappointment=isappointment;}

    public void setBrief(String brief){this.book_brief=brief;}

    public void setBarcode(String code){this.barcode=code;}

    public void setBorrowerid(String borrowerid) {
        this.borrowerid = borrowerid;
    }

    public void setBorrowtime(Date borrowtime) {
        this.borrow_time = borrowtime;
    }

    public void setReturntime(Date returntime) { this.return_time = returntime; }

    public void setFine(Float fine){ this.fine=fine;}

    public void setDelaytime(int delaytime){ this.delay_time=delaytime;}

    public void setIspay(boolean ispay){ this.ispay=ispay;}

    public String toString(){
        total=book_name+"  "+book_author+"  "+book_publisher+"  "+book_date+"  "+
                book_id+"  "+copy_id+"  "+book_type+"  "+book_address+"  "+
                book_price+"  "+book_isborrow+"  "+book_isappointment+"  "+book_brief
                +"  "+barcode;
        System.out.println(total);
        return total;
    }

}