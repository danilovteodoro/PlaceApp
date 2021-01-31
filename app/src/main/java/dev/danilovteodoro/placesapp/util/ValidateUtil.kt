package dev.danilovteodoro.placesapp.util

object ValidateUtil {

    fun isValidEmail(email: String): Boolean {
        //String pattern = "^[a-z]\\w+@[a-z]{3,}.([a-z]{2,4}.)?[a-z]{2,4}(.[a-z]{2})?$";
        val pattern = "^[a-z].*\\w@[a-z]{3,}.([a-z]{2,4}.)?[a-z]{2,4}(.[a-z]{2})?$"
        return email.matches(Regex(pattern))
    }
}