
function Post() {
    var title=document.getElementById("title").value.trim();
    var content=document.getElementById("content").value.trim();
    var imageFile=document.getElementById("file").files[0];
    if(!imageFile){
        console.log("image file is null!");
        alert("Please choose an image!");
        return;
    }
    if(title===""||content===""){
            alert("Error Input!");
            return;
    }
    console.log(title);
    console.log(content);
    var form = new FormData();
    form.append("title",title);
    form.append("file",imageFile);
    form.append("content",content);
    console.log(form);
    var url="http://114.55.250.159:8080/api/add_post";
    var ajax=new XMLHttpRequest();
    ajax.open("POST",url);
    ajax.send(form);
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4 && ajax.status===200){
            if(ajax.responseText==='TitleExist'){
                alert("Already exist same title! Change the title!");
                return;
            }
            console.log("Post Successfully!");
            alert("Post Successfully!");
            console.log(ajax.responseText);

        }else if(ajax.readyState===4 && ajax.status!==200){
            console.log("Post Failed!");
            alert("Post Failed!");
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