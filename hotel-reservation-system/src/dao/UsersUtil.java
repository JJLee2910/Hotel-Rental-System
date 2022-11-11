package dao;

import data.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UsersUtil {

    /**
     * Create Instance varaible
     */
    private List<User> userList = new ArrayList<>();

    /**
     * Users Util Constructor, will read the file and store the data in the room list when object is created
     * Format
     * Username, Password
     */
    public UsersUtil() throws FileNotFoundException {
        readFile();
    }

    /**
     * Read user list from file
     */
    public void readFile() throws FileNotFoundException {
        File myObj = new File(Objects.requireNonNull(getClass().getResource("/database/users.txt")).getFile());
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] userData = data.split(",");
            userList.add(new User(userData[0], userData[1]));
        }
        myReader.close();
    }

    /**
     * User Login
     * @param username username
     * @param password password
     * @return true if valid user, else throw exception
     */
    public boolean login(String username, String password) throws Exception {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        throw new Exception("Invalid username or password");
    }
}
