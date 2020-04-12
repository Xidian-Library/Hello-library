function judge_blank(button) {

    if(button.value.trim()===""){
        document.getElementById(button.id+"_p").innerHTML="Can't be empty";
        document.getElementById(button.id+"_p").style.color="#ff050d";
    }
    else {
        // if(isNaN(button.value)){
        //     document.getElementById(button.id+"_p").innerHTML="Can't be NaN";
        //     document.getElementById(button.id+"_p").style.color="#ff050d";
        //     return;
        // }
        document.getElementById(button.id+"_p").innerHTML="√";
        document.getElementById(button.id+"_p").style.color="#0CFF2E";

    }
}
function del_book() {
    console.log(document.getElementById("delete-id").value);
    var id=document.getElementById("id").value.trim();
    var admin_id="123456";
    if(id===""){
        alert("ID can't be empty");
    }
    else {
        var xml=new XMLHttpRequest();
        var url;
        if(document.getElementById("delete-id").value==="Book_id"){
            url='http://114.55.250.159:8080/book/deleteAKindBook?admin_id='+admin_id+'&book_id='+id;
        }
        else {
            url='http://114.55.250.159:8080/book/deleteABook?admin_id='+admin_id+'&book_barcode='+id;
        }
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("Delete success");

                    document.getElementById("id").value="";

                }
                else {
                    alert("Delete failure "+xml.responseText);
                }
            }
        }
    }
}
function turn() {
    document.getElementById("del").style.display="none";
}
function back() {
    document.getElementById("del").style.display="block";
}