$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var tableName = urlParts[5];
    $.get(tableName + "/content", function() {
        var container = $("#crud_container");
        container.append('<tr>');
        container.append('<td><a href="' + tableName + '/create-record">create</a></td>');
        container.append('<td><a href="' + tableName + '/update-record">update</a></td>');
        container.append('<td><a href="' + tableName + '/delete-record">delete</a></td>');
        container.append('<td><a href="' + tableName + '/clear-table">clear</a></td>');
        container.append('<td><a href="' + tableName + '/delete-table">drop</a></td>');
        container.append('</tr>');
    });
});