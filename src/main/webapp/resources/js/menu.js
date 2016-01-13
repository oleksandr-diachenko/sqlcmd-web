$(window).load(function(){
    $.get("menu/content", function(data) {
        var container = $("#menu_container");
        for (var index in data) {
            var element = data[index];
            container.append('<tr><td><b><a href="' + element + '">' + element + '</a></b></br></td></tr>');
        }
    });
});