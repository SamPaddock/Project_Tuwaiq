package com.saraha.paws

import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Util.UserHelper
import junit.framework.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }



    @Test
    fun emailVerification_isCorrect(){
        //assertTrue(UserHelper().emailVerification("sam@gmail.com").second)
        //Assert.assertFalse(UserHelper().emailVerification("sam@google.com").second)
        //Assert.assertFalse(UserHelper().emailVerification("sam@google").second)

    }

    @Test
    fun passwordVerification_isCorrect(){
        assertTrue(UserHelper().passwordValidation("1234",null).second)
        assertTrue(UserHelper().passwordValidation("abed",null).second)
        assertFalse(UserHelper().passwordValidation("abed","asdf").second)
    }
}