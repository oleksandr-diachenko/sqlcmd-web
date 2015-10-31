package ua.com.juja.positiv.sqlcmd.service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by POSITIV on 31.10.2015.
 */
public class ServiceImpl implements Service{
    @Override
    public List<String> commandList() {
        List<String> commands = Arrays.asList("menu", "help", "connect");
        return commands;
    }
}
