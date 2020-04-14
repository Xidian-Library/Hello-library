var barcode=document.getElementById("barcode");
var readerid=document.getElementById("readerid");

function lend() {
    console.log("Lend book!");
    var url="http://114.55.250.159:8080/api/librarian_borrow_book?borrowerid="+readerid.value+"&barcode="+barcode.value;

    console.log(url);
    var ajax = new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4 && ajax.status===200){
            if (ajax.responseText==="OVERFLOW"){
                alert("Sorry, you cannot borrow it because you've borrowed 3 books!");
            }else if(ajax.responseText==="IDNotMatch"){
                alert("Wrong Reader ID!");
            }else if(ajax.responseText==="BarcodeNotMatch"){
                alert("Wrong Barcode!");
            }else if(ajax.responseText==="Fail"){
                alert("Fail!");
            }else if(ajax.responseText==="Borrowed"){
                alert("The book has been borrowed!");
            }
            else{
                alert("Borrow successfully! Please return the book until "+ajax.responseText);
                console.log(ajax.responseText);
            }
        }else if(ajax.readyState===4 && ajax.status!==200){
            console.log("Lend Failed!");
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
