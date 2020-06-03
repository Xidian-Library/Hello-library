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
    }
    else {
        if(isbn===""){
            for(i = 0; i<13; i++){
                isbn += Math.floor(Math.random()*10).toString();
            }
            document.getElementById("isbn").value = isbn;
        }
        var xml=new XMLHttpRequest();

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
                    if(xml.responseText==='ClassroomNotExist'){
                        alert('Error! Classroom Not Exist!');
                        return;
                    }
                    if(xml.responseText==='TypeNotExist'){
                        alert('Error! Type Not Exist!');
                        return;
                    }
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
    for(var i=0;i<10;i++){
        red[i].style.color="#ff050d";
        red[i].innerHTML="*";
    }
    document.getElementById("picture").style.display="none";
}
function turn_rad_1() {
    document.getElementById("isbn_dup").value="";
    document.getElementById("number").value="";
    var red=document.getElementsByClassName("red");
    for(var i=10;i<red.length;i++){
        red[i].style.color="#ff050d";
        red[i].innerHTML="*";
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
            if(!/^\+?[1-9][0-9]*$/.test(button.value) && button.id==="number"){
                document.getElementById(button.id+"_p").innerHTML="Must be int";
                document.getElementById(button.id+"_p").style.color="#ff050d";
                return;
            }
            document.getElementById(button.id+"_p").innerHTML="√";
            document.getElementById(button.id+"_p").style.color="#0CFF2E";

    }
}
function turn() {
    document.getElementById("sub-but").style.display="none";
    document.getElementById("sub-but-1").style.display="block";
}
function back() {
    document.getElementById("sub-but").style.display="block";
    document.getElementById("sub-but-1").style.display="none";
}
function sub_book_1() {
    var number=document.getElementById("number").value.trim();
    var isbn=document.getElementById("isbn_dup").value.trim();
    if(number === "" || isbn ==="" || !/^\+?[1-9][0-9]*$/.test(number)){
        alert("Error input");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/book/add_new_books?book_id='+isbn+'&amount='+number;
        //console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Add success!"){
                    console.log(xml.responseText);
                    alert("Add success")
                }
                else {
                    alert("Add failure "+xml.responseText);
                }
            }
        }
    }
}