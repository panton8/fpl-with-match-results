package com.panton.service.domain

import cats.data.ValidatedNec
import cats.effect.IO
import cats.implicits.catsSyntaxTuple3Semigroupal
import com.panton.domain.errors.ApplicationError
import com.panton.domain.{Email, Name, Password}
import com.panton.http.domain.UserRegistration

object RegistrationValidator {

  def validate(userName: String, email: String, password: String): IO[ValidatedNec[ApplicationError, UserRegistration]] = {
    IO.pure((Name.fromString(userName),
      Email.fromString(email),
      Password.fromString(password)
    ).mapN(UserRegistration.apply))
  }
}
