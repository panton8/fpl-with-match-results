package com.panton.http.routes

import cats.effect.IO
import cats.implicits.toSemigroupKOps
import com.panton.domain.Access.{Admin, Base}
import com.panton.domain.{Id, News, User}
import com.panton.http.auth.Auth.authMiddleware
import com.panton.service.{NewsService, UserService}
import org.http4s.dsl.impl.IntVar
import org.http4s.{AuthedRoutes, HttpRoutes, Response}
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

final case class NewsRoutes(newsService: NewsService) {
  private def newsRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case req@GET -> Root =>
      newsService.showListOfNews().flatMap(news => Ok(news))


    case req@GET -> Root / IntVar(id) => newsService.NewsById(Id(id)) flatMap {
      case Some(news) => Ok(news)
      case None => NotFound()
    }

  }

  private def authedRoutes: AuthedRoutes[User, IO] = AuthedRoutes.of {
    case req@POST -> Root as user =>
      user.access match {
        case Base => IO(Response(Forbidden))
        case Admin => for {
          news <- req.req.as[News]
          response <- Created(newsService.publishNews(news.title, news.text)).handleErrorWith(e => BadRequest(e.getMessage))
        } yield response
      }
  }
  val routes: HttpRoutes[IO] = newsRoutes <+> authMiddleware(authedRoutes)
}