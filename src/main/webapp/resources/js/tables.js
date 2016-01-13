$(window).load(function(){
    $.get("tables/content", function(data) {
        var container = $("#tables_container");
        for (var index in data) {
            var element = data[index];
            container.append('<tr><td><a href="tables/' + element + '">' + element + '</a></br></td></tr>');
        }
    });
});