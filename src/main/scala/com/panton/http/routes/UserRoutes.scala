package com.panton.http.routes

import cats.effect.IO
import cats.implicits.toSemigroupKOps
import com.panton.domain.Access.{Admin, Base}
import com.panton.domain.errors.SuchUserDoesNotExist
import com.panton.domain.{Email, Id, User}
import com.panton.http.auth.Auth.authMiddleware
import com.panton.http.domain.{UserRegistration, UserSignIn}
import com.panton.service.UserService
import org.http4s.{AuthedRoutes, HttpRoutes, Response}
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

final case class UserRoutes(userService: UserService) {

  private def authedRoutes: AuthedRoutes[User, IO] = AuthedRoutes.of {
    case req @ DELETE -> Root as user =>
      user.access match {
        case Base  => IO(Response(Forbidden))
        case Admin => req.req.decode[Email] { email =>
          Ok(userService.deleteUser(email))
        }.handleErrorWith(e => BadRequest(e.getMessage))
      }

    case GET -> Root as user =>
      userService.showListOfUsers().flatMap(users => Ok(users))

    case GET -> Root / IntVar(id) as user =>
      userService.findById(Id(id)) flatMap {
        case Some(user) => Ok(user)
        case None       => NotFound()
      }
  }

  private def loginRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case req @ POST -> Root / "login" =>
      req.decode[UserSignIn] { newUser =>
        userService.signIn(newUser.email, newUser.password) flatMap {
          case Some(user) => Ok(user)
          case None       => BadRequest(SuchUserDoesNotExist.getMessage)
        }
      }.handleErrorWith(e => BadRequest(e.getMessage))

    case req @ POST -> Root / "register" =>
      for {
        userReg  <- req.as[UserRegistration]
        response <- Created(userService.registration(userReg.userName, userReg.email, userReg.password)).handleErrorWith(e => BadRequest(e.getMessage))
      } yield response
  }

  val routes: HttpRoutes[IO] = loginRoutes <+> authMiddleware(authedRoutes)
}
