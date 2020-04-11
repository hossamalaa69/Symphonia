
package com.example.symphonia;

import androidx.test.filters.LargeTest;

import com.example.symphonia.Helpers.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
@LargeTest
public class UtilsTest {

    @Test
    public void isValidEmailSuccess(){
        boolean b = Utils.isValidEmail("abc@gmail.com");
        assertTrue(b);
    }

    @Test
    public void isValidEmailFail(){
        assertFalse(Utils.isValidEmail("sdn dsk.sdcds"));
    }

    @Test
    public void getNameEmailSuccess(){
        assertEquals(Utils.getNameFromEmail("abc@gmail.com"),"abc");
    }

    @Test
    public void getNameEmailFails(){
        assertNotEquals(Utils.getNameFromEmail("abc@gmail.com"),"fgfd");
    }
}
