/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

$("#searchButton").click(function () {
    findUsers();
});

function findUsers() {
    let filter = $("#filterInput").val();
    console.log("/users/find?filter=" + filter);
    $.ajax({
        type: "get",
        url: "/users/find",
        data: {filter: filter},
        success: function (response) {
            $("#foundUsersDiv").replaceWith(response);

            $('[data-toggle="tooltip"]').tooltip();

            $(".bi-envelope").click(function () {
                let username = $(this).attr("username");
                sendSMS(username);
            });

            $(".bi-star").click(function () {
                let username = $(this).attr("username");
                grantAdmin(username);
            });

            $(".bi-star-fill").click(function () {
                let username = $(this).attr("username");
                removeAdmin(username);
            });

            $(".bi-x-octagon").click(function () {
                let username = $(this).attr("username");
                if (window.confirm("Are you sure you want to disable user " + username + "?")) {
                    disableUser(username);
                }
            });

            $(".bi-x-octagon-fill").click(function () {
                let username = $(this).attr("username");
                if (window.confirm("Are you sure you want to enble user " + username + "?")) {
                    enableUser(username);
                }
            });

            $(".bi-trash").click(function () {
                let username = $(this).attr("username");
                if (window.confirm("Are you sure you want to delete user " + username + "?")) {
                    removeUser(username);
                }
            });
        }
    });
}

function sendSMS(username) {
    console.log(".bi-star click");
    console.log("username: " + username);
    $.ajax({
        type: "post",
        url: "/api/users/sendSMS",
        data: {username: username},
        success: function (response) {
            window.alert(response);
        }
    });
}

function grantAdmin(username) {
    console.log(".bi-star-fill click");
    console.log("username: " + username);
    $.ajax({
        type: "put",
        url: "/api/users/grantAdmin",
        data: {username: username},
        success: function (response) {
            window.alert(response);
            findUsers();
        }
    });
}

function removeAdmin(username) {
    console.log(".bi-x-octagon click");
    console.log("username: " + username);
    $.ajax({
        type: "put",
        url: "/api/users/removeAdmin",
        data: {username: username},
        success: function (response) {
            window.alert(response);
            findUsers();
        }
    });
}

function enableUser(username) {
    console.log(".bi-x-octagon-fill click");
    console.log("username: " + username);
    $.ajax({
        type: "put",
        url: "/api/users/enable",
        data: {username: username},
        success: function (response) {
            window.alert(response);
            findUsers();
        }
    });
}

function disableUser(username) {
    console.log(".bi-envelope click");
    console.log("username: " + username);
    $.ajax({
        type: "put",
        url: "/api/users/disable",
        data: {username: username},
        success: function (response) {
            window.alert(response);
            findUsers();
        }
    });
}

function removeUser(username) {
    console.log(".bi-trash click");
    console.log("username: " + username);
    $.ajax({
        type: "delete",
        url: "/api/users/remove",
        data: {username: username},
        success: function (response) {
            window.alert(response);
            findUsers();
        }
    });
}
