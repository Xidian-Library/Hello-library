package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;

public class Borrow{
    private String bookname;
    private String borrowerid;
    private String barcode;
    private Date borrow_time;
    private Date return_time;
    private Float fine;
    private int delay_time;
    private boolean ispay;
    private int sendmail;
    private String email;


    public String getBookname() {
        return bookname;
    }

    public String getBorrowerid() {
        return borrowerid;
    }

    public String  getBarcode() { return barcode; }

    public Date getBorrowtime() { return borrow_time; }

    public Date getRetuentime() { return return_time; }

    public Float getFine(){return fine;}

    public int getDelaytime(){return delay_time;}

    public boolean getIspay(){return ispay;}

    public int getSendmail(){return sendmail;}

    public String getEmail(){return email;}

    public void setBookname(String bookname) { this.bookname = bookname; }

    public void setBorrowerid(String borrowerid) { this.borrowerid = borrowerid; }

    public void setBarcode(String barcode) { this.barcode = barcode; }

    public void setBorrowtime(Date borrowtime) { this.borrow_time=borrowtime; }

    public void setReturntime(Date returntime) { this.return_time=returntime; }

    public void setFine(Float fine){ this.fine=fine;}

    public void setDelaytime(int delaytime){ this.delay_time=delaytime;}

    public void setIspay(boolean ispay){ this.ispay=ispay;}

    public void setSendmail(int sendmail){this.sendmail=sendmail;}

    public void setEmail(String email){this.email=email;}
}