package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;

public class Deleted_book{

    private String admin_id;

    private String book_id;

    private String book_barcode;

    private String date;


    public String getAdmin_id() {
        return admin_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getBook_barcode() {
        return book_barcode;
    }

    public String getDate() {
        return date;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id=admin_id;
    }

    public void setBook_id(String book_id) {
        this.book_id=book_id;
    }

    public void setBook_barcode(String book_barcode) {
        this.book_barcode=book_barcode;
    }

    public void setDate(String date) {
        this.date=date;
    }


}