

function search() {
    var demo=document.getElementById("demo");
    demo.style.display="block";
    var name=document.getElementById("bookname").value;
    console.log("Search book!");
    console.log("Book name is "+name);
    // author.setAttribute("value","Author");
    if (name===""){
        alert("Please input book name!");
    }
    else {
        var obj={
            bookname:name
        };
        jsonObj=JSON.stringify(obj);
        var ajax = new XMLHttpRequest();
        var url="http://114.55.250.159:8080/api/get_edit_book?book_name="+name;
        ajax.open('GET',url);
        ajax.send();
        ajax.onreadystatechange=function () {
            //console.log("ready state="+ajax.readyState+" status="+ajax.status);
            if (ajax.readyState===4 && ajax.status===200){
                //console.log("Get bookinfo successfully!");
                jsonObj=JSON.parse(ajax.responseText);
                console.log(jsonObj);
                //console.log(jQuery.isEmptyObject(jsonObj));
                if(jQuery.isEmptyObject(jsonObj)){
                    var html='<p>No such book!</p>';
                    document.getElementById("demo").innerHTML=html;
                    return;
                }


                //console.log(jsonObj[0].address);
                //显示搜索到的书籍信息

                var html='';
                for (var i=0;i<jsonObj.length;i++){
                    var imageAjax= new XMLHttpRequest();
                    var imageUrl='http://114.55.250.159:8080/bookpic/bookpic'+jsonObj[i].bookid+'.png';
                    console.log(imageUrl);

                    html += '<li id="'+i+'">';
                    html += '<div class="left">';
                    html += '<img src='+imageUrl+' alt="" width="100px" height="150px">';
                    html += '</div>';
                    html += '<div class="right">';
                    html += '<div class="toptitle">' + jsonObj[i].name + '</div>';
                    html += '<p><span>Literature Type：</span>' + jsonObj[i].type + '</p>';
                    html += '<p><span>Author：</span>' + jsonObj[i].author + '</p>';
                    html += '<p><span>ISBN: </span>'+jsonObj[i].bookid+'</p>';
                    html += '<p><span>Year of Publication：</span>' + jsonObj[i].date +
                        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>Publishing House：</span>' + jsonObj[i].publisher +
                        '</p>';
                    html += '</div></li>';
                }
                document.getElementById("demo").innerHTML=html;
            }else if(ajax.readyState===4 && ajax.status===500) {        //没找到书名
                alert("No such book!");
                console.log("Error ");
                console.log("readyState="+ajax.readyState);
                console.log("status="+ajax.status);
                jsonObj=JSON.parse(ajax.responseText);
                console.log(jsonObj);
                var html='<p>No such book!</p>';
                document.getElementById("demo").innerHTML=html;
            }
        }

    }

}