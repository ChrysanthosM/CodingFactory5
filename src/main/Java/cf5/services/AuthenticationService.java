package cf5.services;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean authenticateUser(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }
}
