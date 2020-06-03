var readerId;
var fineMoney;
function getFine() {
    //console.log("Get Fine!");
    var ajax=new XMLHttpRequest();
    readerId=document.getElementById('readerId');
    console.log(readerId.value);
    var url='http://114.55.250.159:8080/api/check_fine?borrowerid='+readerId.value;
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function(){
        if(ajax.readyState===4 && ajax.status===200){
            if(ajax.responseText===-1){
                alert('Error!');
                return;
            }
            console.log("Please pay the fine:"+ajax.responseText);
            var graph=document.getElementById("book-table");
            graph.style.display="none";
            var fine=document.getElementById("fine");
            fine.style.display="block";
            var fineNum=document.getElementById("fineNum");
            fineMoney=ajax.responseText;
            fineNum.innerHTML=ajax.responseText;
        }
        else if(ajax.readyState===4){
            console.log('Error!');
        }
    }


}
function pay() {
    console.log(fineMoney);
    if(fineMoney==0){
        alert("You don't have fine to pay!");
        return;
    }
    console.log("pay!");
    var url="http://114.55.250.159:8080/api/all_ispay?borrowerid="+readerId.value;
    var ajax=new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4&&ajax.status===200){
            alert("Pay Successfully!");
            window.location.replace("payFine.html");
        }
    }
}
function cancel() {
    console.log("cancel");
    if(fineMoney==0){
        //alert("You don't have fine to pay!");
        window.location.replace("payFine.html");
        return;
    }
    else{
        alert("What a pity! Please pay the fine next time!");
        window.location.replace("payFine.html");
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
