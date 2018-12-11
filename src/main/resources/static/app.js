var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#response").html("");
}

var agentId = "11"

function connect() {
    var socket = new SockJS("http://localhost:8080/websocket-chat");
    // var socket = new SockJS("http://localhost:8081/websocket-chat");
    stompClient = Stomp.over(socket);

    stompClient.connect(
        {"agentId": agentId}
        , function (frame) {
            // stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            // stompClient.subscribe('/topic/chat', function (message) {
            stompClient.subscribe('/topic/chat/' + agentId, function (message) {
                showGreeting(JSON.parse(message.body).message.text);
            });
        });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    // stompClient.send("/api/v1/app/message", {}, JSON.stringify({'message': $("#name").val()}));
    stompClient.send("/app/message", {}, JSON.stringify({'message': $("#name").val()}));
}

function showGreeting(message) {
    $("#response").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});

