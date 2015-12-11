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

import javax.servlet.http.HttpServletRequest;
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
        } catch (ServiceException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/table-names", method = RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        try {
            model.addAttribute("tables", getManager(session).getTableNames());
            return "table-names";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = {"/table-data", "/clear-table", "/delete-table"}, method = RequestMethod.GET)
    public String tableName() {
        return "table-name";
    }

    @RequestMapping(value = "/table-data", method = RequestMethod.POST)
    public String find(Model model, HttpSession session, @RequestParam(value = "tableName") String tableName) {
        try {
            model.addAttribute("table", service.getTableData(getManager(session), tableName));
            return "table-data";
        } catch (ServiceException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/clear-table", method = RequestMethod.POST)
    public String clearingTable(Model model, HttpSession session, @RequestParam(value = "tableName") String tableName) {
        try {
            getManager(session).clearTable(tableName);
            return "success";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
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
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/create-record", method = RequestMethod.GET)
    public String createRecord(Model model) {
        model.addAttribute("tableName", "1");
        model.addAttribute("columnCount", "1");
        model.addAttribute("key", "1");
        model.addAttribute("value", "1");
        return "table-name";
    }

    @RequestMapping(value = "/create-record", method = RequestMethod.POST)
    public String getCreateRecordParameters(Model model, HttpSession session, HttpServletRequest request,
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
                                 @RequestParam(value = "columnValue1") String value) { //TODO сделать динамическую генерацию

        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < columnCount; index++) {
            data.put(key, value);
        }
        try {
            getManager(session).createRecord(tableName, data);
            return "success";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/delete-table", method = RequestMethod.POST)
    public String deleteTable(HttpSession session, Model model, @RequestParam(value = "tableName") String tableName) {
        try {
            getManager(session).dropTable(tableName);
            return "success";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = {"/create-database", "/delete-database"}, method = RequestMethod.GET)
    public String databaseName() {
            return "database-name";
    }

    @RequestMapping(value = "/delete-database", method = RequestMethod.GET)
    public String deleteDatabase(HttpSession session, Model model, @RequestParam(value = "database") String database) {
        try {
            getManager(session).dropBase(database);
            return "success";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/create-database", method = RequestMethod.GET)
    public String createDatabase(HttpSession session, Model model, @RequestParam(value = "database") String database) {
        try {
            getManager(session).createBase(database);
            return "success";
        } catch (DatabaseException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    private int getColumnCount(HttpSession session, @RequestParam(value = "tableName") String tableName) {
        try {
            List<List<String>> tableData = service.getTableData(getManager(session), tableName);
            return tableData.get(0).size();
        } catch (ServiceException e) {
            return -1;
        }
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("manager");
    }
}
