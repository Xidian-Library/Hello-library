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
    var admin_id=sessionStorage.getItem('usrname');
    console.log('admin id='+admin_id);
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
    document.getElementById("section-1").style.display="none";
    document.getElementById("section-2").style.display="block";
    var ajax = new XMLHttpRequest();
    var url='http://114.55.250.159:8080/api/get_deleted_book';
    ajax.open('GET',url);
    ajax.send();
    ajax.onreadystatechange=function () {
        if(ajax.readyState===4&&ajax.status===200){
            console.log('Delete Log!');
            console.log(ajax.responseText);
            jsonObj=JSON.parse(ajax.responseText);
            var html='<table class="deleteLog"><tr><td>Book ID</td><td>Date</td><td>Bar code</td><td>Librarian</td></tr>';
            for(var i=0;i<jsonObj.length;i++){
                html+='<tr>';
                html+='<td>'+jsonObj[i].book_id+'</td>';
                html+='<td>'+jsonObj[i].date+'</td>';
                html+='<td>'+jsonObj[i].book_barcode+'</td>';
                html+='<td>'+jsonObj[i].admin_id+'</td>';
                html+='</tr>';
            }
            html+='</table>';
            document.getElementById("section-2").innerHTML=html;
        }
        else if(ajax.readyState===4){
            console.log('Error Message!');
        }
    }
}
function back() {
    document.getElementById("del").style.display="block";
    document.getElementById("section-1").style.display="block";
    document.getElementById("section-2").style.display="none";
}