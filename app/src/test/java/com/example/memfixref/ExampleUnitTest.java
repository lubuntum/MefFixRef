package com.example.memfixref;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Patterns;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void emailValidation(){
        String emailRegex = "^[A-Za-z\\d_]{0,25}@[A-Za-z]{2,8}\\.[a-z]{2,8}$";
        assertTrue("lubuntum@gmail.com".matches(emailRegex));
        assertFalse("lubuntum".matches(emailRegex));
        assertFalse("lubuntum@gmail".matches(emailRegex));
        assertFalse("lubuntum.com".matches(emailRegex));
    };
}