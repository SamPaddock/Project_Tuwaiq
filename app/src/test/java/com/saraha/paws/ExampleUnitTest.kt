package com.saraha.paws

import android.util.Log
import android.util.Patterns
import androidx.core.util.PatternsCompat
import com.saraha.paws.Util.UserHelper
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun emailVerification(email: String): Boolean {
        var isValid: Boolean
        if (email.isEmpty() == true) {
            isValid = false
        }
        val matches = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        if (!matches){
            isValid = false
        } else {
            isValid = true
        }
        return isValid
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun emailVerification_isCorrect(){
        assertTrue(emailVerification("sam@gmail.com"))
        assertTrue(emailVerification("sam@google.com"))
        assertFalse(emailVerification("sam@google"))
    }

    @Test
    fun passwordVerification_isCorrect(){
        assertTrue(UserHelper().passwordValidation("1234",null).second)
        assertTrue(UserHelper().passwordValidation("abed",null).second)
        assertFalse(UserHelper().passwordValidation("abed","asdf").second)
    }
}