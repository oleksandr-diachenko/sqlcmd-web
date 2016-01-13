$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var tableName = urlParts[5];
    $.get(tableName + "/content", function() {
        var crud = $("#CRUD");
        crud.append('<tr>');
        crud.append('<td><a href="' + tableName + '/create-record">create</a></td>');
        crud.append('<td><a href="' + tableName + '/update-record">update</a></td>');
        crud.append('<td><a href="' + tableName + '/delete-record">delete</a></td>');
        crud.append('<td><a href="' + tableName + '/clear-table">clear</a></td>');
        crud.append('<td><a href="' + tableName + '/delete-table">drop</a></td>');
        crud.append('</tr>');
    });
});