function sub_book() {
    var book={
        bookname:"",author:"",publisher:"",date: "",isbn: "",type: "",address: "",price:0.0,brief:""
    };
    var bookname=document.getElementById("bookname").value.trim();
    var publishinghouse=document.getElementById("publishinghouse").value.trim();
    var author=document.getElementById("author").value.trim();
    var date=document.getElementById("date").value.trim();
    var isbn=document.getElementById("isbn").value.trim();
    var type=document.getElementById("type").value.trim();
    var address=document.getElementById("address").value.trim();
    var brief=document.getElementById("brief").value.trim();
    var price=document.getElementById("price").value.trim();
    if(bookname==="" || publishinghouse==="" || date==="" || author==="" || address==="" || isbn==="" || type==="" || brief==="" || price==="" || isNaN(price)){
        alert("Error input");
    }
    else {
        var xml=new XMLHttpRequest();
        /*xml.open("POST", "http://114.55.250.159:8080/book/add_new_book", true);
        xml.setRequestHeader('content-type', 'application/json');
        xml.onreadystatechange = function() {
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="1"){
                    alert("Add a success");
                }
                else {
                    alert("Add failure "+xml.responseText);
                }
            }
        }

        book.bookname=bookname;
        book.publisher=publishinghouse;
        book.author=author;
        book.date=date;
        book.type=type;
        book.isbn=isbn;
        book.address=address;
        book.brief=brief;
        book.price=Number(price);
        xml.send(JSON.stringify(book));*/
        var url='http://114.55.250.159:8080/book/add_new_book?book_name='+bookname+'&book_author='+author+
            '&book_publisher='+publishinghouse+'&book_date='+date+'&book_id='+isbn+'&book_type='+type+'&book_address='+address+'&book_price='+price+'&book_brief='+brief;
        //console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("Add a success");

                    document.getElementById("bookname").value="";
                    document.getElementById("publishinghouse").value="";
                    document.getElementById("author").value="";
                    document.getElementById("date").value="";
                    document.getElementById("isbn").value="";
                    document.getElementById("type").value="";
                    document.getElementById("address").value="";
                    document.getElementById("brief").value="";
                    document.getElementById("price").value="";

                }
                else {
                    alert("Add failure "+xml.responseText);
                }
            }
        }
    }
}

function judge_blank(button) {
    if(button.value.trim()===""){
        document.getElementById(button.id+"_p").innerHTML="Can't be empty";
        document.getElementById(button.id+"_p").style.color="#ff050d";
    }
    else {
            if(button.id==="price" && isNaN(button.value)){
                document.getElementById(button.id+"_p").innerHTML="Can't be NaN";
                document.getElementById(button.id+"_p").style.color="#ff050d";
                return;
            }
            document.getElementById(button.id+"_p").innerHTML="√";
            document.getElementById(button.id+"_p").style.color="#0CFF2E";

    }
}