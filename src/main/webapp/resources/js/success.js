$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var action = urlParts[urlParts.length - 1];
    $.get(action + "/content", function(data) {
        var container = $("#success_container");
        for (var index in data) {
            container.append('<b>' + data[index] + '<br></b>');
        }
        container.append('<b><a href="/sqlcmd/menu">menu</a></b>')
    });
});