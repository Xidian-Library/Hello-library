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
function del_reader() {
    var account=document.getElementById("account").value.trim();
    var admin_id="123456";
    if(account===""){
        alert("Account does not exist.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/reader/deletereader?id='+account;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Success"){
                    alert("Delete success");

                    document.getElementById("account").value="";
                    document.getElementById("account_p").innerHTML="*";
                    document.getElementById("account_p").style.color="#ff050d";
                }
                else {
                    alert("Delete failure "+xml.responseText);
                }
            }
        }
    }
}
function get_reader() {
    var account=document.getElementById("account-1").value.trim();
    var admin_id="123456";
    if(account===""){
        document.getElementById("password").value="";
        document.getElementById("email").value="";
        document.getElementById("paw").style.display="none";
        alert("Account does not exist.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/reader/returnreaderinfo?id='+account;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Not exit this person."){
                    document.getElementById("password").value="";
                    document.getElementById("email").value="";
                    // document.getElementById("password_p").innerHTML="*";
                    // document.getElementById("password_p").style.color="#ff050d";
                    // document.getElementById("email_p").innerHTML="*";
                    // document.getElementById("email_p").style.color="#ff050d";
                    document.getElementById("paw").style.display="none";
                    alert("Account does not exist.");
                    //document.getElementById("account-1_p").innerHTML="*";
                    console.log(xml.responseText);
                }
                else {
                    console.log("get success")
                    document.getElementById("paw").style.display="block";
                    var info=xml.responseText.split('|||');
                    document.getElementById("password").value=info[0];
                    document.getElementById("email").value=info[1];
                    document.getElementById("password_p").innerHTML="√";
                    document.getElementById("password_p").style.color="#0CFF2E";
                    document.getElementById("email_p").innerHTML="√";
                    document.getElementById("email_p").style.color="#0CFF2E";
                }
            }
        }
    }
}
function alter_reader() {
    var account=document.getElementById("account-1").value.trim();
    var password=document.getElementById("password").value.trim();
    var email=document.getElementById("email").value.trim();
    var admin_id="123456";
    if(account===""){
        alert("Account does not exist.");
    }
    else if(password===""||email===""){
        alert("Password or Email can't be empty.")
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/reader/modeifyreaderinfo?id='+account+'&password='+password+'&email='+email;
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
function turn() {
    document.getElementById("del").style.display="none";
    document.getElementById("edit").style.display="block";
}
function back() {
    document.getElementById("del").style.display="block";
    document.getElementById("edit").style.display="none";
}

function main(a,b) {
    if(b.toString().includes(a.toString())) document.write("yes");
    else document.write("no");
}