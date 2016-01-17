$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var tableName = urlParts[urlParts.length - 1];
    $.get(tableName + "/content", function(data) {
        var container = $("#table_data_container");
        for (var rowIndex in data) {
            var row = data[rowIndex];
            container.append('<tr>');
            for (var elementIndex in row) {
                var element = row[elementIndex]
                container.append('<td>' + element + '</td>');
            }
            container.append('<br>')
        }
    });
});