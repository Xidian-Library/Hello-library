

function admin_login() {
    var username = document.getElementById("username").value;
    var pass = document.getElementById("password").value;
    
    //var usertype= "Reader";
    var testString='test';
    console.log('login');
    if (username === "") {
        alert("请输入用户名");
    }
    else if (pass  === "") {
        alert("请输入密码");
    }
    else {
        var ajax = new XMLHttpRequest();
        var adminurl='http://114.55.250.159:8080/api/AdminLogin?id=' + username + '&password='+ pass;
        //var adminUrl='http://114.55.250.159:8080/api/AdminLogin?id=101&password=66';
        //var url='http://114.55.250.159:8080/api/test?id=2&password=2';
        ajax.open('GET', adminurl);
        ajax.send();
        ajax.onreadystatechange = function () {
            if (ajax.readyState===4&&ajax.status===200) {
                // json 字符串 是字符串 所以我们可以 通过  responseText获取
                console.log("connect successfully");
                console.log(typeof ajax.responseText);
                if(ajax.responseText==='1') {
					console.log('1');
                    sessionStorage.setItem('usrname',username);
                    console.log('sessionstorage='+sessionStorage.getItem('usrname'));
                    window.location.replace('indexnew.html');
                    
                }
                else if(ajax.responseText==='0'){
                    console.log('0');
                    alert('Username Not Found!');
                }
                else if(ajax.responseText==='-1'){
                    console.log('-1');
                    alert('Wrong Password!');
                }
                else if(ajax.responseText==='3'){
                    console.log('3');
                    alert('服务器错误!');
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
function RePass() {
    var username = document.getElementById("username").value;
    if (username === "") {
        alert("请输入用户名");
    }
    else {
        var ajax = new XMLHttpRequest();
        var url='http://114.55.250.159:8080/service/getpassword?id='+username;
        ajax.open('GET',url);
        ajax.send();
        ajax.onreadystatechange = function () {
            if (ajax.readyState===4&&ajax.status===200) {
                // json 字符串 是字符串 所以我们可以 通过  responseText获取
                console.log("connect successfully");
                console.log(typeof ajax.responseText);
            }
            else{
                console.log("error message");
            }
        }

    }
}