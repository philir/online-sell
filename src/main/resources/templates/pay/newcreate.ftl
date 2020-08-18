<script>
    function onBridgeReady(){
           location.href = "${returnUrl}";
        }
    if (typeof WeixinJSBridge == "undefined"){
        onBridgeReady();
    }
</script>