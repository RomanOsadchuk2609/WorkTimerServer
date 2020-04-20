/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

$("#downloadReportButton").on("click", function () {
    let start = $("#startTime").val();
    let end = $("#endTime").val();
    let url = "/timeReport/global/download?startDate=" + start + "&endDate=" + end;
    console.log(url);
    $(location).attr('href', url);
});