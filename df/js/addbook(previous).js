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
    var picture=document.getElementById("cover").value.trim();
    if(bookname==="" || publishinghouse==="" || date==="" || author==="" || address==="" || type==="" || brief==="" || price==="" || isNaN(price) || picture===""){
        alert("Error input");
        console.log('Error!');
    }
    else {
        if(isbn===""){
            for(i = 0; i<13; i++){
                isbn += Math.floor(Math.random()*10).toString();
            }
            document.getElementById("isbn").value = isbn;
        }
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
                if(xml.responseText!="failure"){
                    console.log(xml.responseText+'0');
                    sub_picture(isbn,xml.responseText);
                }
                else {
                    alert("Add failure "+xml.responseText);
                }
            }
        }
    }
}
function sub_picture(id,pic) {
    var imageFile=document.getElementById("cover").files[0];
    var form=new FormData();
    form.append("book_id",id);
    form.append("file",imageFile);
    var url='http://114.55.250.159:8080/book/add_new_bookpic';
    console.log(form);
    var xmlh=new XMLHttpRequest();
    xmlh.open('POST',url);
    xmlh.send(form);
    xmlh.onreadystatechange = function() {
        /*console.log(xml.readyState);
        console.log(xml.status);*/
        if (xmlh.readyState === 4 && xmlh.status===200) {
            if(xmlh.responseText!="failure"){
                document.getElementById("barcode").src="http://114.55.250.159:8080/barcodepic/"+ pic + ".png";
                document.getElementById("picture").style.display="block";
                //window.open("http://114.55.250.159:8080/barcodepic/" + pic + ".png");
                alert("Add a success");
                console.log(xmlh.responseText+'1');
                //turn_rad();
            }
            else {
                console.log(xmlh.responseText);
                alert("Add picture failure "+xmlh.responseText);
            }
        }
    }
}
function turn_rad() {
    document.getElementById("cover").value="";
    document.getElementById("file").value="";
    document.getElementById("bookname").value="";
    document.getElementById("publishinghouse").value="";
    document.getElementById("author").value="";
    document.getElementById("date").value="";
    document.getElementById("isbn").value="";
    document.getElementById("type").value="";
    document.getElementById("address").value="";
    document.getElementById("brief").value="";
    document.getElementById("price").value="";
    var red=document.getElementsByClassName("red");
    for(var i=0;i<red.length;i++){
        red[i].style.color="#ff050d";
        red[i].innerHTML="*";
    }
    document.getElementById("picture").style.display="none";
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