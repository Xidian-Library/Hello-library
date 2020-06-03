var jsonObj;
var originTitle;
function searchTitle() {
    var title=document.getElementById("titleName").value.trim();
    originTitle=title;
    console.log("Search!");
    console.log(title);
    if (title===""){
        alert("Please input book name!");
    }
    else{
        var theNews=document.getElementById("news");
        var section1=document.getElementById("section-1");
        theNews.style.display="block";
        section1.style.display="none";
        var ajax=new XMLHttpRequest();
        var url="http://114.55.250.159:8080/api//get_post_by_title?title="+title;
        ajax.open('POST',url);
        ajax.send();
        ajax.onreadystatechange=function () {
            if(ajax.readyState===4 && ajax.status===200){
                jsonObj=JSON.parse(ajax.responseText);
                console.log(jsonObj);
                if(jQuery.isEmptyObject(jsonObj)){
                    var html='<p>No such news!</p>';
                    document.getElementById("news").innerHTML=html;
                    return;
                }
                var html='';
                html+='<div class="hl__staggered-type-list__list-wrapper">';
                html+='<div class="hl__staggered-type-list__list">';
                html+='<a class="hl__type-promo" onclick="choose()" title="Use Library Resources for Remote Teaching" style="background-image: url(\'';
                html+=jsonObj[0].photo+'\')">';
                html+='<div class="hl__type-promo__header">';
                html+='<div class="hl__type-promo__icon">';
                html+='<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="30" height="30" viewBox="0 0 30 30">';
                html+='<g transform="translate(-169 -5817)"><path fill="#f8c21b" d="M199 5832a15 15 0 1 1-30 0 15 15 0 0 1 30 0zm-17.69-2.69l-4.64 10.02 10.02-4.64 4.64-10.02zm2.69 1.3a1.4 1.4 0 0 1 .99 2.38A1.4 1.4 0 0 1 183 5831c.27-.27.63-.4.99-.4z"></path></g>';
                html+='</svg></div></div><div class="hl__type-promo__details"><h3 class="hl__type-promo__title">';
                html+=jsonObj[0].title+'</h3><div class="hl__type-promo__description">'+jsonObj[0].content+'</div></div></a></div></div>';
                document.getElementById("news").innerHTML=html;
            }
            else if(ajax.readyState===4 && ajax.status!==200){
                console.log("Error!");
                alert("Error!");
            }
        }

    }
}
function choose() {
    //console.log("Choose!");
    var theNews=document.getElementById("news");
    var section1=document.getElementById("section-1");
    theNews.style.display="none";
    section1.style.display="block";

    //console.log(jsonObj);
    var title=document.getElementById("title");
    var content=document.getElementById("content");
    var imageFile=document.getElementById("file");
    title.setAttribute("value",jsonObj[0].title);
    content.innerHTML=jsonObj[0].content;
}
function Update() {
    //console.log("Update!");
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
    var form = new FormData();
    console.log('originTitle='+originTitle);
    form.append("title_original",originTitle);
    form.append("title_new",title);
    form.append("file",imageFile);
    form.append("content",content);
    var url='http://114.55.250.159:8080/api/edit_post';
    var ajax=new XMLHttpRequest();
    ajax.open('POST',url);
    ajax.send(form);
    ajax.onreadystatechange=function () {
        if(ajax.readyState===4 && ajax.status===200){
            //console.log("Success!");
            alert("Update successfully!");
            console.log(ajax.responseText);
        }else if(ajax.readyState===4){
            console.log("Failed!");
            console.log(ajax.responseText);
        }
    }
}
function Delete() {
    console.log("Delete!");
    var ajax=new XMLHttpRequest();
    var url='http://114.55.250.159:8080/api/delete_post?title='+originTitle;
    ajax.open('POST',url);
    ajax.send();
    ajax.onreadystatechange=function () {
        if(ajax.readyState===4 && ajax.status===200){
            //console.log("Success!");
            alert("Delete successfully!");
            console.log(ajax.responseText);
        }else if(ajax.readyState===4){
            console.log("Failed!");
            console.log(ajax.responseText);
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