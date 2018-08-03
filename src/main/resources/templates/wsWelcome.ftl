<script type="text/javascript">
var ws = new WebSocket('/ws');

ws.onopen = function(ev) {
    console.log('Connection open ..');
    ws.send("hello");
}

ws.onmessage = function(ev) {
    console.log("Received message, data:" + JSON.parse(ev.data));
}

ws.onclose = function(ev) {
    console.log("Connection closed .");
}
</script>