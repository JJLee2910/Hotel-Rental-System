package service.implementation;

import dao.UsersUtil;
import service.LogInService;

import java.io.FileNotFoundException;

public class LogInServiceImpl implements LogInService {
    @Override
    public boolean login(String username, String password) throws Exception {
        try {
            UsersUtil usersUtil = new UsersUtil();
            return usersUtil.login(username, password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        LogInServiceImpl logInService = new LogInServiceImpl();
//        boolean stat = logInService.login("admin", "admin");
//        System.out.println(stat);
//    }
}
