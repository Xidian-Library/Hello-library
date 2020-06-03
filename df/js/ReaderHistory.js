function get_borrow() {
    var id=document.getElementById("borrower_id").value.trim();
    remove_table("borrow_return");
    if(id===""){
        createblank1();
        alert("ID does not be empty.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/borrowrecord/normalrecord?borrowerid='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Please input the correct borrowerid."){
                    createblank1();
                    alert("Reader does not have borrow record or does not exit.");
                    console.log(xml.responseText);
                }
                else {
                    console.log("get success")
                    console.log(xml.responseText);
                    create("borrow_return",xml.responseText);
                    //var a = "11111111111|.|2|.|3|||4|.|5|.|6|||7|.|8|.|9";
                    //create("borrow_return",a);

                }
            }
        }
    }
}
function get_fine() {
    var id=document.getElementById("reader_id").value.trim();
    remove_table("fine");
    if(id===""){
        createblank2();
        alert("ID does not be empty.");
    }
    else {
        var xml=new XMLHttpRequest();
        var url='http://114.55.250.159:8080/borrowrecord/finerecord?borrowerid='+id;
        console.log(url);
        xml.open('GET',url);
        xml.send();
        xml.onreadystatechange = function() {
            /*console.log(xml.readyState);
            console.log(xml.status);*/
            if (xml.readyState === 4 && xml.status===200) {
                if(xml.responseText=="Please input the correct borrowerid."){
                    createblank2();
                    alert("Reader does not have fine record or does not exit.");
                    console.log(xml.responseText);
                }
                else if (xml.responseText=="This reader doesn't have fine record"){
                    createblank2();
                    alert("Reader does not have fine record");
                    console.log(xml.responseText);
                }
                else {
                    console.log("get success")
                    create("fine",xml.responseText);
                }
            }
        }
    }
}
function judge_blank(button) {

    if(button.value.trim()===""){
        document.getElementById(button.id+"_p").innerHTML="Can't be empty";
        document.getElementById(button.id+"_p").style.color="#ff050d";
    }
    else {
        document.getElementById(button.id+"_p").innerHTML="√";
        document.getElementById(button.id+"_p").style.color="#0CFF2E";
    }
}
function createblank1() {
    var table = document.createElement("table");
    table.setAttribute("id","borrow_return");
    document.getElementById("table1").appendChild(table);
}
function createblank2() {
    var table = document.createElement("table");
    table.setAttribute("id","fine");
    document.getElementById("table2").appendChild(table);
}
function createtable1() {
    var table = document.createElement("table");
    table.setAttribute("id","borrow_return");
    document.getElementById("table1").appendChild(table);
    var th = Createrow1("barcode", "borrow_time", "return_time");
    table.appendChild(th);
}
function createtable2() {
    var table = document.createElement("table");
    table.setAttribute("id","fine");
    document.getElementById("table2").appendChild(table);
    var th = Createrow2("barcode", "fine", "payment status");
    table.appendChild(th);
}
function Createrow1(barcode,borrow_time,return_time) {
    var array = [barcode,borrow_time,return_time];
    var tr = document.createElement("tr");
    for (let index = 0; index < array.length; index++) {
        var data = document.createTextNode(array[index]);
        var td = document.createElement("td");
        td.appendChild(data);
        tr.appendChild(td);
    }
    return tr;
}
function Createrow2(barcode,fine,ispay) {
    var status = ispay;
    if(ispay == "1"){
        status = "yes";
    }
    if(ispay == "-1"||ispay == "0"){
        status = "no";
    }
    var array = [barcode,fine,status];
    var tr = document.createElement("tr");
    for (let index = 0; index < array.length; index++) {
        var data = document.createTextNode(array[index]);
        var td = document.createElement("td");
        td.appendChild(data);
        tr.appendChild(td);
    }
    return tr;
}
function create(name,text) {
    var tabledata = text.split('|||');
    if(name === "fine"){
        createtable2();
    }
    else {
        createtable1();
    }
    var table1 = document.getElementById("borrow_return");
    var table2 = document.getElementById("fine");
    for(let i=0;i<tabledata.length;i++){
        console.log(i);
        var tablerow = tabledata[i].split('|.|');
        if(name === "fine"){
            var tr = Createrow2(tablerow[0],tablerow[1],tablerow[2]);
            table2.appendChild(tr);
        }
        else {
            var tr = Createrow1(tablerow[0],tablerow[1],tablerow[2]);
            table1.appendChild(tr);
        }
    }
}
function remove_table(id) {
    if(id === "fine"){
        document.getElementById("table2").removeChild(document.getElementById("fine"));
    }
    else {
        document.getElementById("table1").removeChild(document.getElementById("borrow_return"));
    }
}