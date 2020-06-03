var barcode=document.getElementById("barcode");
var readerid=document.getElementById("readerid");

var codeNum=barcode.value;
function returnBook(){
    console.log("return!");
    var url="http://114.55.250.159:8080/api/librarian_return_book?borrowerid="+readerid.value+"&barcode="+barcode.value;
    //var url="http://114.55.250.159:8080/api/librarian_return_book?borrowerid=1&barcode=1";
    var ajax=new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function () {
        if (ajax.readyState===4&&ajax.status===200){
            console.log("responseText="+ajax.responseText);
            if(ajax.responseText==-1.0||ajax.responseText==-3){
                alert("No such borrow information!");
            }else if(ajax.responseText==-2.0){
                alert("Return Failed!");
            }else{
                if(ajax.responseText==0.0){     //还书成功
                    alert("Return successfully!");
                }else{                          //还钱
                    console.log("Please pay the fine:"+ajax.responseText);
                    var graph=document.getElementById("book-table");
                    graph.style.display="none";
                    var fine=document.getElementById("fine");
                    fine.style.display="block";
                    var fineNum=document.getElementById("fineNum");
                    fineNum.innerHTML=ajax.responseText;
                }
            }
        }else if(ajax.readyState===4&&ajax!==200){
            console.log("connect fail");
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

function pay() {
    console.log("pay!");
    var url="http://114.55.250.159:8080/api/return_ispay?barcode="+codeNum+"&ispay=1";
    var ajax=new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4&&ajax.status===200){
            alert("Return Successfully!");
            window.location.replace("ReturnBook.html");
        }
    }
}

function cancel() {
    console.log("cancel");
    var url="http://114.55.250.159:8080/api/return_ispay?barcode="+codeNum+"&ispay=-1";
    var ajax=new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4&&ajax.status===200){
            alert("What a pity! Please pay the fine next time!");
        }
    }
    //alert("What a pity! Please pay the fine next time!");
}