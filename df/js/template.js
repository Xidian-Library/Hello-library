function logout() {
    //alert("logout!");
    console.log('sessionstorage='+sessionStorage.getItem('usrname'));
    sessionStorage.removeItem('usrname');
    window.location.replace('realIndex.html');
}