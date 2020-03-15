$("#btnTokenLogin").click(function () {
    let token = $("#token").val();
    console.log("/login/token?token=" + token);
    window.open("/login/token?token=" + token);
});