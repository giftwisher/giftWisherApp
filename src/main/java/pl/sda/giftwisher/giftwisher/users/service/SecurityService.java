package pl.sda.giftwisher.giftwisher.users.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
