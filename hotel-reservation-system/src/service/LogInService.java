package service;

/**
 * backend logic related to login
 */
public interface LogInService {
    /**
     * @param username
     * @param password
     * @return true if login successful, else false
     */
    boolean login(String username, String password) throws Exception;
}
