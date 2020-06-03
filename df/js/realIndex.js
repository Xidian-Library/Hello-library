window.onload=function () {
    console.log("load!");
    var content = document.getElementById("content");
    var ajax = new XMLHttpRequest();
    var url = 'http://114.55.250.159:8080/api/get_all_post';
    ajax.open('POST', url);
    ajax.send();
    ajax.onreadystatechange=function () {
        if (ajax.readyState === 4 && ajax.status === 200) {
            jsonObj = JSON.parse(ajax.responseText);
            console.log(jsonObj);
            if (jQuery.isEmptyObject(jsonObj)) {
                var html = '<p>No such book!</p>';
                document.getElementById("news").innerHTML = html;
                return;
            }
            var html='';
            for(var i=0;i<jsonObj.length;i++){
                html+='<a class="hl__type-promo" title="Use Library Resources for Remote Teaching" style="background-image: url(\'';
                html+=jsonObj[i].photo+'\')">';
                html+='<div class="hl__type-promo__header"><div class="hl__type-promo__icon"><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="30" height="30" viewBox="0 0 30 30">';
                html+='<g transform="translate(-169 -5817)"><path fill="#f8c21b" d="M199 5832a15 15 0 1 1-30 0 15 15 0 0 1 30 0zm-17.69-2.69l-4.64 10.02 10.02-4.64 4.64-10.02zm2.69 1.3a1.4 1.4 0 0 1 .99 2.38A1.4 1.4 0 0 1 183 5831c.27-.27.63-.4.99-.4z"></path></g>';
                html+='</svg></div></div>';
                html+='div class="hl__type-promo__details"><h3 class="hl__type-promo__title">';
                html+=jsonObj[i].title+'</h3><div class="hl__type-promo__description">';
                html+=jsonObj[i].content+'</div></div></a>';
            }
            content.innerHTML=html;
        }
        else if(ajax.readyState === 4){
            alert("Error!");
            console.log("Error!");
        }
    }

}