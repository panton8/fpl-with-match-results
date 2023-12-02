package com.panton

import cats.effect.{ExitCode, IO, IOApp}
import com.panton.http.routes.{PlayerRoutes, TeamRoutes, UserRoutes, NewsRoutes}
import com.panton.service.{NewsService, PlayerService, TeamService, UserService}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.Router

object AppServer extends IOApp {

  private val userService = UserService()
  private val teamService = TeamService()
  private val playerService = PlayerService()
  private val newsService = NewsService()

  private val httpRoutes = Router[IO](
    "users/" -> UserRoutes(userService).routes,
    "teams/" -> TeamRoutes(teamService).routes,
    "players/" -> PlayerRoutes(playerService).routes,
    "news/" -> NewsRoutes(newsService).routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder.default[IO]
      .withHttpApp(httpRoutes)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}