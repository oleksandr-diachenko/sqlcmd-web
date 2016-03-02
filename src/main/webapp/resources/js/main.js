function init(ctx) {

    var isConnected = function(fromPage, onConnected) {
        $.getJSON(ctx + "/connected", function(isConnected) {
            if (isConnected) {
                if (!!onConnected) {
                    onConnected();
                }
            } else {
                gotoConnectPage(fromPage);
            }
        });
    }

    var gotoConnectPage = function(fromPage) {
        window.location = ctx + '/connect' + '?fromPage=' + escape('/main#/' + fromPage);
    }

    var show = function(selector) {
        var component = $(selector);
        component.find('.container').children().not(':first').remove();
        component.show();
    }

    var initTables = function() {
        isConnected("tables", function() {
            show('#tables');

            $.getJSON(ctx + "/tables/content", function(elements) {
                $("#loading").hide(300, function() {
                    $('#tables script').tmpl(elements).appendTo('#tables .container');
                });
            });
        });
    };

    var initTableData = function(tableName) {
        isConnected("tables/" + tableName, function() {
            show('#tables');

            $.getJSON(ctx + '/tables/' + tableName + '/content', function(elements) {
                $('#loading').hide(300, function() {
                    $('#tableData script').tmpl(elements).appendTo('#tableData .container');
                });
            });
        });
    };

    var initMenu = function() {
        show('#menu');

        $.getJSON(ctx + "/menu/content", function(elements) {
            $("#loading").hide(300, function() {
                $('#menu script').tmpl(elements).appendTo('#menu .container');
            });
        });
    };

    var hideAllScreens = function() {
        $('#tables').hide();
        $('#tableData').hide();
        $('#menu').hide();
    }

    var loadPage = function(data) {
        hideAllScreens();
        $("#loading").show();

        var page = data[0];
        if (!data[1] && page == 'tables') {
            initTables();
        } else if (page == 'tables') {
            initTableData(data[1]);
        } else if (page == 'menu') {
            initMenu();
        } else {
            window.location.hash = "/menu";
        }
    }

    var load = function() {
        var hash = window.location.hash.substring(1);
        var parts = hash.split('/');
        if (parts[0] == '') {
            parts.shift();
        }
        loadPage(parts);
    }

    $(window).bind('hashchange', function(event) {
        load();
    });

    load();
}
