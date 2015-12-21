package ua.com.juja.positiv.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.databasemanager.UserAction;
import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by POSITIV on 11.12.2015.
 */
@Controller
@Scope("session")
public class MainServlet {

    @Autowired
    private Service service;

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
        if (getManager(session) == null || getManager(session) == DatabaseManager.NULL) {
            return "connect";
        }
        return "redirect:menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(Model model, HttpSession session,
                             @RequestParam(value = "database") String database,
                             @RequestParam(value = "user") String user,
                             @RequestParam(value = "password") String password) {
        try {
            DatabaseManager manager = service.connect(database, user, password);
            session.setAttribute("manager", manager);
            service.log(new UserAction(manager.getUser(),
                    manager.getDatabase(),
                    "CONNECT"));
            return "redirect:menu";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tableNames(Model model, HttpSession session) {
        try {
            DatabaseManager manager = getManager(session);
            model.addAttribute("list", manager.getTableNames());
            service.log(new UserAction(manager.getUser(),
                    manager.getDatabase(), "GET TABLE LIST"));
            return "tables";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/tables/{tableName}", method = RequestMethod.GET)
    public String tableData(Model model, HttpSession session,
                            @PathVariable(value = "tableName") String tableName) {
        try {
            model.addAttribute("tableName", tableName);
            DatabaseManager manager = getManager(session);
            List<List<String>> tableData = manager.getTableData(tableName);
            tableData.add(0, manager.getColumnNames(tableName));
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "GET TABLE DATA(" + tableName + ")"));
            model.addAttribute("table", tableData);
            return "table-data";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/clear-table", method = RequestMethod.GET)
    public String clearTable(Model model, HttpSession session,
                                @PathVariable(value = "tableName") String tableName) {
        try {
            DatabaseManager manager = getManager(session);
            manager.clearTable(tableName);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "CLEAR TABLE(" + tableName + ")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/delete-record", method = RequestMethod.GET)
    public String deleteRecord() {
        return "delete-record";
    }

    @RequestMapping(value = "tables/{tableName}/delete-record", method = RequestMethod.POST)
    public String deletingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam(value = "keyValue") String keyValue) {
        try {
            DatabaseManager manager = getManager(session);
            String keyName = manager.getPrimaryKey(tableName);
            manager.deleteRecord(tableName, keyName, keyValue);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "DELETE RECORD(" + keyName + "=" + keyValue + ")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/create-record", method = RequestMethod.GET)
    public String createRecord(Model model, HttpSession session,
                               @PathVariable(value = "tableName") String tableName) {
        try {
            model.addAttribute("columnNames", getManager(session).getColumnNames(tableName));
            return "create-record";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/create-record", method = RequestMethod.POST)
    public String creatingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam Map<String, Object> allRequestParams) {
        try {
            DatabaseManager manager = getManager(session);
            manager.createRecord(tableName, allRequestParams);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "CREATE RECORD IN TABLE (" + tableName +")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/delete-table", method = RequestMethod.GET)
    public String deleteTable(Model model, HttpSession session,
                              @PathVariable(value = "tableName") String tableName) {
        try {
            DatabaseManager manager = getManager(session);
            manager.dropTable(tableName);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "DELETE TABLE (" + tableName +")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/delete-database", method = RequestMethod.POST)
    public String deleteDatabase(Model model, HttpSession session,
                                 @RequestParam(value = "databaseName") String databaseName) {
        try {
            DatabaseManager manager = getManager(session);
            manager.dropBase(databaseName);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "DELETE DATABASE (" + databaseName +")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "/create-database", method = RequestMethod.POST)
    public String createDatabase(Model model, HttpSession session,
                                 @RequestParam(value = "databaseName") String databaseName) {
        try {
            DatabaseManager manager = getManager(session);
            manager.createBase(databaseName);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "CREATE DATABASE (" + databaseName +")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/update-record", method = RequestMethod.GET)
    public String updateRecord(Model model, HttpSession session,
                               @PathVariable(value = "tableName") String tableName) {
        try {
            model.addAttribute("columnNames", getManager(session).getColumnNames(tableName));
            return "update-record";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    @RequestMapping(value = "tables/{tableName}/update-record", method = RequestMethod.POST)
    public String updatingRecord(Model model, HttpSession session,
                                 @PathVariable(value = "tableName") String tableName,
                                 @RequestParam Map<String, Object> allRequestParams) {
        try {
            DatabaseManager manager = getManager(session);
            String keyName = manager.getPrimaryKey(tableName);
            String keyValue = (String) allRequestParams.remove(keyName);
            manager.updateRecord(tableName, keyName, keyValue, allRequestParams);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "UPDATE RECORD IN TABLE (" + tableName +") " +
                            "" + keyName + "=" + keyValue));
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
                             @RequestParam(value = "columnCount") String columnCount) {
        model.addAttribute("tableName", tableName);
        model.addAttribute("columnCount", columnCount);
        return "create-table";
    }

    @RequestMapping(value = "/create-table", method = RequestMethod.POST)
    public String creatingTable(Model model, HttpSession session,
                                 @RequestParam Map<String,String> allRequestParams) {
        String tableName = allRequestParams.remove("tableName");
        String keyName = allRequestParams.remove("keyName");
        Map<String, Object> data = getData(allRequestParams);
        try {
            DatabaseManager manager = getManager(session);
            manager.createTable(tableName, keyName, data);
            service.log(new UserAction(manager.getUser(), manager.getDatabase(),
                    "CREATE TABLE (" + tableName +")"));
            return "success";
        } catch (Exception e) {
            return error(model, e);
        }
    }

    private Map<String, Object> getData(@RequestParam Map<String, String> allRequestParams) {
        Map<String, Object> data = new LinkedHashMap<>();
        Iterator<Map.Entry<String, String>> iterator = allRequestParams.entrySet().iterator();
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
        if(manager == null) {
            return DatabaseManager.NULL;
        }
        return manager;
    }

    private String error(Model model, Exception e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
