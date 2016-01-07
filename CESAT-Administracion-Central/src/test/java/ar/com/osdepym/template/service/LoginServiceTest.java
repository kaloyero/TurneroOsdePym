package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.LoginDao;
import org.junit.Test;

import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alejandro on 12/19/14.
 */
public class LoginServiceTest {

    private static final String USER = "user";
    private static final String PASSWORD = "password";

    @Test
    public void testLoginCallsLoginWithUserAndPassword_WhenCallsLoginWithUserAndPassword() {
        // @Given
        LoginService loginService = new LoginService();
        LoginDao loginDao = mock(LoginDao.class);
        field("loginDao").ofType(LoginDao.class).in(loginService).set(loginDao);
        // @When
        loginService.login(USER, PASSWORD);
        // @Then
        verify(loginDao).login(USER, PASSWORD);
    }
}
