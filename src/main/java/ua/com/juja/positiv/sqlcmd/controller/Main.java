package ua.com.juja.positiv.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by POSITIV on 11.12.2015.
 */
@Controller
public class Main {

    @Autowired
    private Service service;

    @RequestMapping(value = {"/table-data", "/clear-table", "/delete-table", "update-record", "/create-record"},
            method = RequestMethod.GET)
    public String tableName() {
        return "table-name";
    }

    @RequestMapping(value = {"/create-database", "/delete-database"}, method = RequestMethod.GET)
    public String databaseName() {
        return "database-name";
    }

    @RequestMapping(value = {"/menu", "/"}, method = RequestMethod.GET)
    public String menu(ModelMap model) {
        model.put("items", service.commandList());
        return "menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session) {
        if (getManager(session) == null) {
            return "connect";
        }
        return "redirect:menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(HttpSession session, Model model,
                             @RequestParam(value = "database") String database,
                             @RequestParam(value = "user") String user,
                             @RequestParam(value = "password") String password) {
        try {
            DatabaseManager manager = service.connect(database, user, password);
            session.setAttribute("manager", manager);
            return "redirect:menu";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/table-names", method = RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        try {
            model.addAttribute("tables", getManager(session).getTableNames());
            return "table-names";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/table-data", method = RequestMethod.POST)
    public String find(Model model, HttpSession session,
                       @RequestParam(value = "tableName") String tableName) {
        try {
            model.addAttribute("table", service.getTableData(getManager(session), tableName));
            return "table-data";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/clear-table", method = RequestMethod.POST)
    public String clearingTable(Model model, HttpSession session,
                                @RequestParam(value = "tableName") String tableName) {
        try {
            getManager(session).clearTable(tableName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.GET)
    public String deleteRecord() {
        return "delete-record";
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deletingRecord(Model model, HttpSession session,
                                 @RequestParam(value = "tableName") String tableName,
                                 @RequestParam(value = "keyName") String keyName,
                                 @RequestParam(value = "keyValue") String keyValue) {
        try {
            getManager(session).deleteRecord(tableName, keyName, keyValue);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-record", method = RequestMethod.POST)
    public String createRecord(Model model, HttpSession session,
                               @RequestParam(value = "tableName") String tableName) {
        model.addAttribute("columnCount", getColumnCount(session, tableName));
        model.addAttribute("tableName", tableName);
        return "create-record";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String creatingRecord(Model model, HttpSession session,
                                 @RequestParam(value = "tableName") String tableName,
                                 @RequestParam(value = "columnCount") int columnCount,
                                 @RequestParam(value = "columnName1") String key,
                                 @RequestParam(value = "columnValue1") String value) {

        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < columnCount; index++) {//TODO подумать над заполнением мапы
            data.put(key, value);
        }
        try {
            getManager(session).createRecord(tableName, data);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/delete-table", method = RequestMethod.POST)
    public String deleteTable(HttpSession session, Model model,
                              @RequestParam(value = "tableName") String tableName) {
        try {
            getManager(session).dropTable(tableName);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/delete-database", method = RequestMethod.POST)
    public String deleteDatabase(HttpSession session, Model model,
                                 @RequestParam(value = "database") String database) {
        try {
            getManager(session).dropBase(database);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-database", method = RequestMethod.POST)
    public String createDatabase(HttpSession session, Model model,
                                 @RequestParam(value = "database") String database) {
        try {
            getManager(session).createBase(database);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/update-record", method = RequestMethod.POST)
    public String updateRecord(HttpSession session, Model model,
                               @RequestParam(value = "tableName") String tableName) {
        model.addAttribute("columnCount", getColumnCount(session, tableName));
        model.addAttribute("tableName", tableName);
        return "update-record";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatingRecord(HttpSession session, Model model,
                                 @RequestParam(value = "tableName") String tableName,
                                 @RequestParam(value = "columnName1") String columnName,
                                 @RequestParam(value = "columnValue1") String columnValue,
                                 @RequestParam(value = "keyName") String keyName,
                                 @RequestParam(value = "keyValue") String keyValue) {

        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < getColumnCount(session, tableName) - 1; index++) {//TODO подумать над заполнением мапы
            int columnIndex = index + 1;
            data.put(columnName, columnValue);
        }
        try {
            getManager(session).updateRecord(tableName, keyName, keyValue, data);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-table", method = RequestMethod.GET)
    public String createTable() {
        return "table-name-column-count";
    }

    @RequestMapping(value = "/column-parameters", method = RequestMethod.POST)
    public String columnParameters(Model model,
                                   @RequestParam(value = "tableName") String tableName,
                                   @RequestParam(value = "columnCount") String columnCount) {
        model.addAttribute("tableName", tableName);
        model.addAttribute("columnCount", columnCount);
        return "create-table";
    }

    @RequestMapping(value = "/create-table", method = RequestMethod.POST)
    public String creatingTable(HttpSession session, Model model,
                                @RequestParam(value = "tableName") String tableName,
                                @RequestParam(value = "columnCount") int columnCount,
                                @RequestParam(value = "columnName1") String columnName,
                                @RequestParam(value = "columnType") String columnType,
                                @RequestParam(value = "keyName") String keyName) {
        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < columnCount - 1; index++) { //TODO подумать над заполнением мапы
            int columnIndex = index + 1;
            data.put(columnName, columnType);
        }
        try {
            getManager(session).createTable(tableName, keyName, data);
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    private int getColumnCount(HttpSession session, @RequestParam(value = "tableName") String tableName) {
        try {
            List<List<String>> tableData = service.getTableData(getManager(session), tableName);
            return tableData.get(0).size();
        } catch (Exception e) {
            return -1;
        }
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("manager");
    }


    private String error(Model model, Exception e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
