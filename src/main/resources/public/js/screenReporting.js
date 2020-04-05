/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

$("#screenshotDateSelect").on("change", function () {
    let screenshotId = $("#screenshotDateSelect").val();
    console.log("/screenshot/byId?id=" + screenshotId);
    $.ajax({
        type: "get",
        url: "/screenshot/byId",
        data: {id: screenshotId},
        success: function (response) {
            $("#screenshotImg").attr("src", "data:image/png;base64, " + response);
        }
    });
});

$("#downloadScreenshotsButton").on("click", function () {
    let startTime = $("#startTime").val();
    let endTime = $("#endTime").val();
    let username = $("#user").val();
    let url = "/screenshot/download?username=" + username + "&startTime=" + startTime + "&endTime=" + endTime;
    console.log(url);
    $(location).attr('href', url);
});