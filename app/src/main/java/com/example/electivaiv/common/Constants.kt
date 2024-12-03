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
        const val PROFILE_PHOTO = "profilePhoto"
        const val UNKNOWN_FIELD = "Propiedad no encontrada"

        //Messages
        const val TEST_MESSAGE = "TEST--"
        const val USER_SUCCESSFULLY_REGISTERED_MESSAGE = "Usuario registrado correctamente."
        const val USER_UNSUCCESSFULLY_REGISTERED_MESSAGE = "Error al registrar el usuario."
        const val PASSWORDS_DO_NOT_MATCH_MESSAGE = "Las constraseñas no coinciden."
        const val MINIMUM_PASSWORD_LENGTH_MESSAGE = "La contraseña debe tener al menos 6 carácteres."
        const val VALID_EMAIL_MESSAGE = "El correo no es válido."
        const val ENTER_NAME_MESSAGE = "Ingrese su nombre."
        const val ENTER_LAST_NAME_MESSAGE = "Ingrese su apellido."
        const val ENTER_EMAIL_MESSAGE = "Ingrese su correo."
        const val ENTER_PASSWORD_MESSAGE = "Ingrese su contraseña."
        const val ENTER_CONFIRM_LOGIN_IN_MESSAGE = "Inicio de sesion correcto."
        const val ENTER_CONFIRM_LOGIN_IN_MESSAGE_ERROR = "Inicio de sesion incorrecto."
        const val USER_UNSUCCESSFULLY_LOGIN_MESSAGE = "Error al iniciar sesion."
        const val DATA_REGISTERED_SUCCESSFULLY = "Datos guardados correctamente."
        const val DATA_REGISTERED_UNSUCCESSFULLY = "Hubo un error al guardar los datos."
        const val AVERAGE_RATING = "Promedio de valoraciones"

        //Comments
        const val AUTHOR_UID = "authorUid"
        const val AUTHOR_NAME = "authorName"
        const val AUTHOR_PROFILE_PHOTO = "authorProfilePhoto"
        const val UNKNOWN_AUTHOR = "Desconocido"
        const val ARRAY_IMAGES= "images"
        const val RATE = "rate"
        const val RESTAURANT_NAME = "restaurantName"
        const val TEXT = "text"
    }

}