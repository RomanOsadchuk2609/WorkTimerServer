/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

$("#downloadGlobalReportButton").on("click", function () {
    let start = $("#startTime").val();
    let end = $("#endTime").val();
    let url = "/timeReport/global/download?startDate=" + start + "&endDate=" + end;
    console.log(url);
    $(location).attr('href', url);
});

$("#downloadDetailReportButton").on("click", function () {
    let start = $("#startTime").val();
    let end = $("#endTime").val();
    let username = $("#user").val();
    if (!username || username === '*') {
        alert("Please, choose user.");
    } else {
        let url = "/timeReport/detail/download?username=" + username + "&startDate=" + start + "&endDate=" + end;
        console.log(url);
        $(location).attr('href', url);
    }
});