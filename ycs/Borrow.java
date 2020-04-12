package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;

public class Borrow{
    private String borrowerid;
    private String barcode;
    private Date borrow_time;
    private Date return_time;
    private Float fine;
    private int delay_time;
    private int ispay;



    public String getBorrowerid() {
        return borrowerid;
    }

    public String  getBarcode() { return barcode; }

    public Date getBorrowtime() { return borrow_time; }

    public Date getRetuentime() { return return_time; }

    public Float getFine(){return fine;}

    public int getDelaytime(){return delay_time;}

    public int getIspay(){return ispay;}

    public void setBorrowerid(String borrowerid) { this.borrowerid = borrowerid; }

    public void setBarcode(String barcode) { this.barcode = barcode; }

    public void setBorrowtime(Date borrowtime) { this.borrow_time=borrowtime; }

    public void setReturntime(Date returntime) { this.return_time=returntime; }

    public void setFine(Float fine){ this.fine=fine;}

    public void setDelaytime(int delaytime){ this.delay_time=delaytime;}

    public void setIspay(int ispay){ this.ispay=ispay;}

}