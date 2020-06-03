function turn_edit() {
    document.getElementById("del").style.display="none";
    document.getElementById("edit").style.display="block";
    document.getElementById("add").style.display="none";
}
function turn_del() {
    document.getElementById("del").style.display="block";
    document.getElementById("edit").style.display="none";
    document.getElementById("add").style.display="none";
}
function back() {
    document.getElementById("add").style.display="block";
    document.getElementById("edit").style.display="none";
    document.getElementById("del").style.display="none";
}
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
function get_room() {
    var id=document.getElementById("edit_room").value.trim();
    var admin_id="123456";
    if(id===""){
        document.getElementById("booktype").value="";
        document.getElementById("paw").style.display="none";
        document.getElementById("booktype_p").innerHTML="*";
        document.getElementById("booktype_p").style.color="#ff050d";
        alert("Room does not exist.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/classroom/classroominfo?roomid='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="This classroom dosen't exited .Please check info carefully"){
                    document.getElementById("booktype").value="";
                    document.getElementById("booktype_p").innerHTML="*";
                    document.getElementById("booktype_p").style.color="#ff050d";
                    document.getElementById("paw").style.display="none";
                    alert("Room does not exist.");
                    console.log(xml.responseText);
                }
                else {
                    console.log("get success")
                    document.getElementById("paw").style.display="block";
                    document.getElementById("booktype").value=xml.responseText;
                    document.getElementById("booktype_p").innerHTML="√";
                    document.getElementById("booktype_p").style.color="#0CFF2E";

                }
            }
        }
    }
}
function alter_loc(){
    var id=document.getElementById("edit_room").value.trim();
    var type=document.getElementById("booktype").value.trim();
    var admin_id="123456";
    if(id===""){
        alert("room does not exist.");
    }
    else if(type===""){
        alert("booktype can't be empty.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/classroom/roomedit?roomid='+id+'&booktype='+type;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("alter success");
                }
                else {
                    alert("alter failure. "+xml.responseText);
                }
            }
        }
    }
}
function add_loc(){
    var id=document.getElementById("room_id").value.trim();
    var type=document.getElementById("type").value.trim();
    var admin_id="123456";
    if(id===""){
        alert("room does not exist.");
    }
    else if(type===""){
        alert("type can't be empty.")
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/classroom/addclassroom?roomid='+id+'&booktype='+type;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("add success");
                }
                else {
                    alert("add failure. "+xml.responseText);
                }
            }
        }
    }
}
function del_loc(){
    var id=document.getElementById("del_room_id").value.trim();
    var admin_id="123456";
    if(id===""){
        alert("room does not exist.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/classroom/deleteclassroom?roomid='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("Delete success");
                    document.getElementById("del_room_id").value="";
                    document.getElementById("del_room_id_p").innerHTML="*";
                    document.getElementById("del_room_id_p").style.color="#ff050d";
                }
                else {
                    alert("Delete failure "+xml.responseText);
                }
            }
        }
    }
}