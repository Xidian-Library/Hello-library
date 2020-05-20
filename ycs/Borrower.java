package io.junq.examples.boot;

import java.io.Serializable;

public class Borrower{
    private String Borrowerid;
    private String bookname;
    private String returntime;

    public String getBorrowerid(){
       return Borrowerid;
   }
    public String getBookname()
   {
       return bookname;
   }
    public String getReturntime(){
       return returntime;
   }
    public void setBorrowerid(String Borrowerid) { this.Borrowerid = Borrowerid; }
    public void setBookname(String bookname) { this.bookname = bookname; }
    public void setReturntime(String returntime) { this.returntime = returntime; }
}