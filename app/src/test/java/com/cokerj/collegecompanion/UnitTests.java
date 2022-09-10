package com.cokerj.collegecompanion;

        import org.junit.Test;

        import static org.junit.Assert.*;

        import com.cokerj.collegecompanion.Entity.User;
        import com.cokerj.collegecompanion.Utility.UtilityFunctions;


public class UnitTests {
    User testUser = new User("testUser", "password");

    @Test
    public void user_test_login_succeed(){
        assertTrue(testUser.testLogin("testUser", "password"));
    }

    @Test
    public void user_test_login_fail(){
        assertFalse(testUser.testLogin("testUser", "PASSWORD"));
    }

    @Test
    public void user_test_login_fail_2(){
        assertFalse(testUser.testLogin("TestUser", "password"));
    }

    @Test
    public void user_test_password_create_succeed(){
        assertTrue(new UtilityFunctions().validatePassword("password"));
    }
    @Test
    public void user_test_password_create_fail(){
        assertFalse(new UtilityFunctions().validatePassword("pass"));
    }
}