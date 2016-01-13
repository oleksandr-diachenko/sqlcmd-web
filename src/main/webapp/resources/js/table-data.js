$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var tableName = urlParts[5];
    $.get(tableName + "/content", function(data) {
        var container = $("#table_data_container");
        for (var rowIndex in data) {
            var row = data[rowIndex];
            container.append('<tr>');
            for (var elementItem in row) {
                var element = row[elementItem]
                container.append('<td>' + element + '</td>');
            }
            container.append('<br>')
        }
    });
});