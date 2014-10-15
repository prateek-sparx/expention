var path = "/android_asset/www/data/Applause";
var music = null;
var delay;

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady()
{
    document.addEventListener("backbutton", onBackKeyDown, false);
    var aa=navigator.userAgent;
    //alert(aa)
    if(aa.indexOf('Idea')>0){
        
        $('head').append('<link  rel="stylesheet" href="assets/css/lenovo.css">');
        
    }
    
    function callNativePlugin(returnSuccess)
    {
        HelloPlugin.callNativeFunction(nativePluginResultHandler, nativePluginErrorHandler, returnSuccess);
    }
    function nativePluginResultHandler(result)
    {
        alert("SUCCESS: \r\n" + result);
    }
    function nativePluginErrorHandler(error)
    {
        alert("ERROR: \r\n" + error);
    }
}

function onBackKeyDown() 
{
    clearInterval(delay)
    stopNativeAudio();
    if ($('#HomeScreen').css('display') == 'block' || $('#StartGameScreen').css('display') == 'block')
    {
        navigator.app.exitApp();
    }
    else
    {
        if (previous_panel == 'HomeScreen')
        {
            showPanel('HomeScreen');
        }
        else
        {
            showPanel('StartGameScreen');
        }
    }
}

$('[data-audio]').live('click', function(e)
{
    // music.stop();  
    // music.release();
    path = $(this).data('audio');
    if ($(this).data('correct'))
    {
        path = 'data/Applause';
    }
    playAudio(path);
});

function playAudio(src1)
{
    clearInterval(delay)
    stopNativeAudio();
    var src = src1 + ".mp3";
    delay = setTimeout(function()
    {
        cordova.exec(onSuccess, onError, "com.criticalthinking.riddlerabbitprek.AudioPlayPlugIn", "playAction", [src]);
    }, 500)

    if (!settings.sound)
    {
        clearInterval(delay)
        stopNativeAudio();
    }

}

$('.home-btn').live('click', function(e)
{

    clearInterval(delay)
    stopNativeAudio();
});


function onSuccess() 
{
    bool = true;
}

function onError() 
{//alert(222)

}


function stopNativeAudio()
{
    cordova.exec(onSuccess, onError, "com.criticalthinking.riddlerabbitprek.AudioPlayPlugIn", "stopAction", []);
}