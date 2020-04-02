/**
 * Created by Kay on 2016/3/8.
 */



function login() {
    var username = document.getElementById("username").value;
    var pass = document.getElementById("password").value;
    var testString='test';
    console.log('login');
    if (username === "123") {
        alert("请输入用户名");
    } else if (pass  === "") {
        alert("请输入密码");
    }
    else {
        var obj={
            usrname:username,
            password:pass
        };
        var jsonObj=JSON.stringify(obj);
        var ajax = new XMLHttpRequest();
        var url='http://114.55.250.159:8080/api/login?id='+username+'&password='+pass;
        var adminUrl='http://114.55.250.159:8080/api/AdminLogin?id=101&password=66';
        //var url='http://114.55.250.159:8080/api/test?id=2&password=2';
        ajax.open('GET',adminUrl);
        //ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        ajax.send();
        ajax.onreadystatechange = function () {
            if (ajax.readyState===4&&ajax.status===200) {
                // json 字符串 是字符串 所以我们可以 通过  responseText获取
                console.log("connect successfully");
                console.log(typeof ajax.responseText);
                if(ajax.responseText==='1') {
                    console.log('1');
                    window.location.replace('PersonalPage.html');
                }
                else if(ajax.responseText==='0'){
                    console.log('0');
                    alert('Username Not Found!');
                }
                else if(ajax.responseText==='-1'){
                    console.log('-1');
                    alert('Wrong Password!');
                }
                else{
                    console.log('Wrong Number!');
                }
            }
            else{
                console.log("error message");
            }
        }

    }
}