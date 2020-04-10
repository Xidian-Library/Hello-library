package io.junq.examples.boot;

import java.io.Serializable;

public class Borrow{
    private int record_number;
    private String borrowerid;
    private int copy_id;
    private String borrow_time;
    private String return_time;


    public int getRecordnumber(){return record_number;}

    public String getBorrowerid() {
        return borrowerid;
    }

    public int getCopyid() { return copy_id; }

    public String getBorrowtime() {
        return borrow_time;
    }

    public String getRetuentime() { return return_time; }

    public void setRecordnumber(int number){this.record_number=number;}

    public void setBorrowerid(String borrowerid) {
        this.borrowerid = borrowerid;
    }

    public void setCopyid(int copyid) { this.copy_id = copyid; }

    public void setBorrowtime(String borrowtime) {
        this.borrow_time = borrowtime;
    }

    public void setReturntime(String returntime) { this.return_time = returntime; }
}