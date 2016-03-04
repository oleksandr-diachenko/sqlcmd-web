package ua.com.juja.positiv.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by POSITIV on 11.12.2015.
 */
@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = {"/main", "/", "menu"}, method = RequestMethod.GET)
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model,
                          @RequestParam(required = false, value = "fromPage") String fromPage) {
        String page = (String) session.getAttribute("from-page");
        session.removeAttribute("from-page");
        Connection connection = new Connection(page);
        if (fromPage != null) {
            connection.setFromPage(fromPage);
        }
        model.addAttribute("connection", connection);

        if (getManager(session) == null || getManager(session) == DatabaseManager.NULL) {
            return "connect";
        } else {
            return "menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection,
                             HttpSession session, Model model)
    {
        try {
            DatabaseManager manager = service.connect(connection.getDbName(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("manager", manager);
            return "redirect:" + connection.getFromPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }


    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tableNames() {
        return "tables";
    }

    @RequestMapping(value = "/tables/{tableName}", method = RequestMethod.GET)
    public String tableData() {
        return "table-data";
    }

    @RequestMapping(value = "tables/{tableName}/clear-table", method = RequestMethod.GET)
    public String clearTable(Model model, HttpSession session,
                             @PathVariable(value = "tableName") String tableName)
    {
        try {
            DatabaseManager manager = getManager(session);
            service.clearTable(manager, tableName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/delete-record",
            method = RequestMethod.GET)
    public String deleteRecord() {
        return "delete-record";
    }

    @RequestMapping(value = "tables/{tableName}/delete-record", method = RequestMethod.POST)
    public String deletingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam(value = "keyValue") String keyValue)
    {
        try {
            DatabaseManager manager = getManager(session);
            String keyName = manager.getPrimaryKey(tableName);
            service.deleteRecord(manager, tableName, keyName, keyValue);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/create-record", method = RequestMethod.GET)
    public String createRecord(Model model, HttpSession session,
                               @PathVariable(value = "tableName") String tableName)
    {
        try {
            model.addAttribute("columnNames",
                    getManager(session).getColumnNames(tableName));
            return "create-record";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/create-record", method = RequestMethod.POST)
    public String creatingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam Map<String, Object> allRequestParams)
    {
        try {
            DatabaseManager manager = getManager(session);
            service.createRecord(manager, tableName, allRequestParams);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/delete-table", method = RequestMethod.GET)
    public String deleteTable(Model model, HttpSession session,
                              @PathVariable(value = "tableName") String tableName)
    {
        try {
            DatabaseManager manager = getManager(session);
            service.dropTable(manager, tableName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = {"/create-database", "/delete-database"}, method = RequestMethod.GET)
    public String databaseName() {
        return "database-name";
    }

    @RequestMapping(value = "/delete-database", method = RequestMethod.POST)
    public String deleteDatabase(Model model, HttpSession session,
                                 @RequestParam(value = "databaseName") String databaseName)
    {
        try {
            DatabaseManager manager = getManager(session);
            service.dropBase(manager, databaseName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-database", method = RequestMethod.POST)
    public String createDatabase(Model model, HttpSession session,
                                 @RequestParam(value = "databaseName") String databaseName)
    {
        try {
            DatabaseManager manager = getManager(session);
            service.createBase(manager, databaseName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/update-record", method = RequestMethod.GET)
    public String updateRecord(Model model, HttpSession session,
                               @PathVariable(value = "tableName") String tableName)
    {
        try {
            model.addAttribute("columnNames",
                    getManager(session).getColumnNames(tableName));
            return "update-record";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/update-record", method = RequestMethod.POST)
    public String updatingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam Map<String, Object> allRequestParams)
    {
        try {
            DatabaseManager manager = getManager(session);
            String keyName = manager.getPrimaryKey(tableName);
            String keyValue = (String) allRequestParams.remove(keyName);
            service.updateRecord(manager, tableName, keyName, keyValue,
                    allRequestParams);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-table", method = RequestMethod.GET)
    public String tableSize() {
        return "table-name-column-count";
    }

    @RequestMapping(value = "/column-parameters", method = RequestMethod.POST)
    public String createTable(Model model,
                              @RequestParam(value = "tableName") String tableName,
                              @RequestParam(value = "columnCount") String columnCount)
    {
        model.addAttribute("tableName", tableName);
        model.addAttribute("columnCount", columnCount);
        return "create-table";
    }

    @RequestMapping(value = "/create-table", method = RequestMethod.POST)
    public String creatingTable(Model model, HttpSession session,
                                @RequestParam Map<String, String> allRequestParams)
    {
        String tableName = allRequestParams.remove("tableName");
        String keyName = allRequestParams.remove("keyName");
        Map<String, Object> columnParameters = getColumnParameters(allRequestParams);
        try {
            DatabaseManager manager = getManager(session);
            service.createTable(manager, tableName, keyName, columnParameters);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/actions/{userName}", method = RequestMethod.GET)
    public String actions(Model model, @PathVariable("userName") String userName) {
        model.addAttribute("actions", service.getAllFor(userName));
        return "actions";
    }

    private Map<String, Object> getColumnParameters(@RequestParam Map<String, String> allRequestParams) {
        Map<String, Object> data = new LinkedHashMap<>();
        Iterator<Map.Entry<String, String>> iterator;
        iterator = allRequestParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> pair = iterator.next();
            String key = pair.getValue();
            pair = iterator.next();
            String value = pair.getValue();
            data.put(key, value);
        }
        return data;
    }

    private DatabaseManager getManager(HttpSession session) {
        DatabaseManager manager = (DatabaseManager) session.getAttribute("manager");
        if (manager == null) {
            return DatabaseManager.NULL;
        }
        return manager;
    }

    private String error(Model model, Exception e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
