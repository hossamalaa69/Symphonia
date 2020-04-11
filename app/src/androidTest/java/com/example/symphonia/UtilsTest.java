package com.example.symphonia;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.symphonia.Helpers.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UtilsTest {


    @Test
    public static void isValidEmailSuccess(){
        assertTrue(Utils.isValidEmail("abc@gmail.com"));
    }

    @Test
    public static void isValidEmailFail(){
        assertFalse(Utils.isValidEmail("sdn dsk.sdcds"));
    }

    @Test
    public static void getNameEmailSuccess(){
        assertEquals(Utils.getNameFromEmail("abc@gmail.com"),"abc");
    }

    @Test
    public static void getNameEmailFails(){
        assertNotEquals(Utils.getNameFromEmail("abc@gmail.com"),"fgfd");
    }



}
