package com.example.electivaiv.common

class Constants {

    companion object {
        //App
        const val APP_NAME = "Ratatouille"
        const val EMPTY_STRING = " "
        const val ZERO = 0

        //Firebase
        const val USERS_COLLECTION = "users"
        const val COMMENTS_COLLECTION = "comments"
        const val UID = "uid"
        const val NAME = "name"

        //Messages
        const val TEST_MESSAGE = "TEST--"
        const val USER_SUCCESSFULLY_REGISTERED_MESSAGE = "Usuario registrado correctamente."
        const val USER_UNSUCCESSFULLY_REGISTERED_MESSAGE = "Error al registrar el usuario."
        const val PASSWORDS_DO_NOT_MATCH_MESSAGE = "Las constraseñas no coinciden."
        const val MINIMUM_PASSWORD_LENGTH_MESSAGE = "La contraseña debe tener al menos 6 carácteres."
        const val VALID_EMAIL_MESSAGE = "El correo no es válido."
        const val ENTER_NAME_MESSAGE = "Ingrese su nombre."
        const val ENTER_LAST_NAME_MESSAGE = "Ingrese su apellido."

        //Comments
        const val AUTHOR_UID = "authorUid"
        const val AUTHOR_NAME = "authorName"
        const val UNKNOWN_AUTHOR = "Desconocido"
        const val ARRAY_IMAGES= "images"
        const val RATE = "rate"
        const val RESTAURANT_NAME = "restaurantName"
        const val TEXT = "text"
    }

}