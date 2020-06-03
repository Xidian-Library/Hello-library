function add() {
    //console.log(document.getElementById("add-id").value);

    var add_id1=document.getElementById("add-id").value;
    var id=document.getElementById("type1").value.trim();
    if(id===""){
        alert("Please add the new type.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/book/addbooktype?bigtype='+add_id1+'&newtype='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {   //响应建立函数
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {    //成功
                if(xml.responseText=='1'){   //后端返回的
                    alert("Add success!");

                }
                else {
                    alert("Add failure!");
                }
            }
        }
    }
}
function del() {
    //console.log(document.getElementById("add-id").value);

    var del_id=document.getElementById("del-id").value;
    var id=document.getElementById("type2").value.trim();
    if(id===""){
        alert("Please delete the subtype.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/book/deletebooktype?bigtype='+del_id+'&smalltype='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {   //响应建立函数
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {    //成功
                if(xml.responseText=='1'){   //后端返回的
                    alert("Delete success!");

                }
                else {
                    alert("This subcategory does not exist!");
                }
            }
        }
    }
}
function alter() {
    //console.log(document.getElementById("add-id").value);

    var edit_id=document.getElementById("edit-id").value;
    var old_id=document.getElementById("type3").value.trim();
    var new_id=document.getElementById("type4").value.trim();

    if(old_id===""){
        alert("Please edit the subtype.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/book/editbooktype?bigtype='+edit_id+'&oldtype='+old_id+'&newname='+new_id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {   //响应建立函数
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {    //成功
                if(xml.responseText=='1'){   //后端返回的
                    alert("Edit success!");

                }
                else {
                    alert("This subcategory does not exist!");
                }
            }
        }
    }
}


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
function judge_blank(button) {   //判断输入框状态 是否输入东西

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
