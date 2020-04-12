var bookName=document.getElementById("book-name");
var author=document.getElementById("author");
var publishinghouse=document.getElementById("publishinghouse");
var date=document.getElementById("date");
var isbn=document.getElementById("isbn");
var type=document.getElementById("type");
var address=document.getElementById("address");
var price=document.getElementById("price");
var brief=document.getElementById("brief");

var jsonObj;

function search() {
    var demo=document.getElementById("demo");
    demo.style.display="block";
    var graph=document.getElementById("book-table");
    graph.style.display="none";
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
            console.log("ready state="+ajax.readyState+" status="+ajax.status);
            if (ajax.readyState===4 && ajax.status===200){
                //console.log("Get bookinfo successfully!");
                jsonObj=JSON.parse(ajax.responseText);
                console.log(jsonObj);
                console.log(jQuery.isEmptyObject(jsonObj));
                if(jQuery.isEmptyObject(jsonObj)){
                    var html='<p>No such book!</p>';
                    document.getElementById("demo").innerHTML=html;
                    return;
                }
                //console.log(jsonObj[0].address);

                //显示搜索到的书籍信息

                var html='';
                for (var i=0;i<jsonObj.length;i++){
                    html += '<li id="'+i+'">';
                    html += '<div class="left">';
                    html += '<img src="images/11.jpg" alt="" width="100px" height="150px">';
                    html += '</div>';
                    html += '<div class="right">';
                    html += '<div class="toptitle">' + jsonObj[i].name + '<a class="editButton" onclick="choose(this)">Edit</a></div>';
                    html += '<p><span>Literature Type：</span>' + jsonObj[i].type + '</p>';
                    html += '<p><span>Author：</span>' + jsonObj[i].author + '</p>';
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

// var ul=document.getElementById("demo");
// ul.onclick= function(e) {
//     console.log(e.target.id);
// }

function choose(obj) {
    //console.log(obj.parentNode.parentNode.parentNode.id);
    console.log("choose");
    var i=obj.parentNode.parentNode.parentNode.id;
    //console.log(jsonObj);
    var demo=document.getElementById("demo");
    demo.style.display="none";
    var graph=document.getElementById("book-table");

    bookName.setAttribute("value",jsonObj[i].name);
    author.setAttribute("value",jsonObj[i].author);
    publishinghouse.setAttribute("value",jsonObj[i].publisher);
    date.setAttribute("value",jsonObj[i].date);
    isbn.setAttribute("value",jsonObj[i].bookid);
    type.setAttribute("value",jsonObj[i].type);
    address.setAttribute("value",jsonObj[i].address);
    price.setAttribute("value",jsonObj[i].price);
    brief.setAttribute("value",jsonObj[i].brief);

    graph.style.display="block";
}

function edit() {
    console.log("edit book!");
    var url="http://114.55.250.159:8080/api/edit_book?book_name="+bookName.value+"&book_author="+author.value+"&book_publisher=";
    url+=publishinghouse.value+"&book_date="+date.value+"&book_id="+isbn.value+"&book_type="+type.value+"&book_address=";
    url+=address.value+"&book_price="+price.value+"&book_brief="+brief.value;
    console.log(url);
    var ajax = new XMLHttpRequest();
    ajax.open("GET",url);
    ajax.send();
    ajax.onreadystatechange=function (){
        if (ajax.readyState===4 && ajax.status===200){
            console.log("Edit Successfully!");
        }else if(ajax.readyState===4 && ajax.status!==200){
            console.log("Edit Failed!");
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
