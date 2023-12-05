package com.panton.http.routes

import cats.effect._
import cats.implicits.{toSemigroupKOps, toTraverseOps}
import com.panton.domain.Access.{Admin, Base}
import com.panton.domain.{Access, GameWeek, Id, User}
import com.panton.http.auth.Auth.authMiddleware
import com.panton.http.domain.{MatchCreation, ResultCreation}
import com.panton.service.MatchService
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

final case class MatchRoutes(matchService: MatchService) {
  private def matchRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case req@GET -> Root / "schedule" =>
      matchService.getSchedule().flatMap(matches => Ok(matches))

    case req@GET -> Root / "schedule" / date =>
      if (date.matches("^\\d{4}-\\d{2}-\\d{2}$"))
        matchService.getScheduleByDate(date).flatMap(matches => if (matches.isEmpty) NotFound() else Ok(matches))
      else
        BadRequest("Invalid date format. Please use 'YYYY-MM-DD' format.")

    case req@GET -> Root / "results" =>
      matchService.getResult().flatMap(results => Ok(results)).handleErrorWith(e => BadRequest(e.getMessage))

    case req@GET -> Root / "results" / date =>
      if (date.matches("^\\d{4}-\\d{2}-\\d{2}$"))
        matchService.getResultByDate(date).flatMap(results => if (results.isEmpty) NotFound() else Ok(results))
      else
        BadRequest("Invalid date format. Please use 'YYYY-MM-DD' format.")
  }

  private def authedRoutes: AuthedRoutes[User, IO] = AuthedRoutes.of {
    case req@POST -> Root / "schedule" as user =>
      user.access match {
        case Base => IO(Response(Forbidden))
        case Admin => for {
          matchData <- req.req.as[MatchCreation]
          response <- Created(matchService.addMatch(matchData.date, matchData.time, matchData.home_club, matchData.guest_club, matchData.referee_id)).handleErrorWith(e => BadRequest(e.getMessage))
        } yield response
      }

    case req@PATCH -> Root / "results" as user =>
      user.access match {
        case Base => IO(Response(Forbidden))
        case Admin => for {
          matchData <- req.req.as[ResultCreation]
          response <- matchService.addMatchResult(matchData.matchId, matchData.home_goals, matchData.guest_goals).flatMap(_ => Ok())
        } yield response
      }
  }
  val routes: HttpRoutes[IO] = matchRoutes <+> authMiddleware(authedRoutes)

}