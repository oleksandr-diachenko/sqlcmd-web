$(window).load(function(){
    var urlParts = window.location.href.split('/');
    var tableName = urlParts[5];
    $.get(tableName + "/content", function(data) {
        var container = $("#table_data_container");
        for (var rowIndex in data) {
            var row = data[rowIndex];
            for (var itemIndex in row) {
                var item = row[itemIndex]
                container.append(item + ' ');
            }
            container.append('</br>')
        }
    });
});