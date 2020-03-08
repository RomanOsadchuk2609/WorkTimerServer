$("#searchButton").click(function () {
    let filter = $("#filterInput").val();
    console.log("/users/find?filter=" + filter);
    $.ajax({
        type: "get",
        url: "/users/find",
        data: {filter: filter},
        success: function (response) {
            $("#foundUsersDiv").replaceWith(response);
        }
    });
});