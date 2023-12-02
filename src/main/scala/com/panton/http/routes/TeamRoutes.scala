package com.panton.http.routes

import cats.effect._
import cats.implicits.toTraverseOps
import com.panton.domain.Access.{Admin, Base}
import com.panton.domain.errors.SuchTeamDoesNotExist
import com.panton.domain.{GameWeek, Id, User}
import com.panton.http.auth.Auth.authMiddleware
import com.panton.http.domain.TeamCreation
import com.panton.repository.{TeamRepository, UserRepository}
import com.panton.service.TeamService
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

final case class TeamRoutes(teamService: TeamService) {

  private def authedRoutes: AuthedRoutes[User, IO] =
    AuthedRoutes.of {
      case PATCH -> Root / "matchweek" / IntVar(currentWeek) / "update" / "points" as user =>
        user.access match {
          case Base  => IO(Response(Forbidden))
          case Admin =>
            val teams  = teamService.showListOfTeams()
            val update = teams.flatMap(teams => teams.traverse(team => teamService.updateTeamStat(team, GameWeek(currentWeek))))
            update.flatMap(_ => Ok())
        }

      case GET -> Root as user =>
        teamService.showListOfTeams().flatMap(teams => Ok(teams))

      case GET -> Root / IntVar(id) as user =>
        teamService.findById(Id(id)) flatMap {
          case Some(team) => Ok(team)
          case None => NotFound()
        }

      case GET -> Root / "line-up" as user =>
        teamService.teamByOwner(user.id) flatMap {
          case Some(teamId) => teamService.playersFromTeam(teamId).flatMap(Ok(_))
          case None       => BadRequest("You don't have a team yet")
        }

      case GET -> Root / IntVar(id) / "line-up" as user =>
        teamService.playersFromTeam(Id(id)) flatMap { players =>
          if (players.isEmpty)
            NotFound(SuchTeamDoesNotExist.getMessage)
          else
            Ok(players)
        }

      case req@PATCH -> Root / "update" / "captain" as user =>
        for {
          newCaptain <- req.req.as[Id]
          posTeam    <- TeamRepository.findByOwner(user.id)
          response   <- posTeam match {
            case Some(teamId) => Ok(teamService.resetCaptain(teamId, newCaptain)).handleErrorWith(e => BadRequest(e.getMessage))
            case None         => BadRequest("You don't have a team yet")
          }
        } yield response

      case req@PATCH -> Root / "update" / "line-up" as user =>
        for {
          players  <- req.req.as[List[Id]]
          posTeam  <- TeamRepository.findByOwner(user.id)
          response <- posTeam match {
            case Some(teamId) => Ok (teamService.changePlayer(players(1), players.head, teamId)).handleErrorWith (e => BadRequest (e.getMessage))
            case None         => BadRequest("You don't have a team yet")
          }
        } yield response

      case req@PATCH -> Root / "update" / "substitution" as user =>
        for {
          players  <- req.req.as[List[Id]]
          posTeam  <- TeamRepository.findByOwner(user.id)
          response <- posTeam match {
            case Some(teamId) => Ok(teamService.makeSubstitution(players.head, players(1), teamId)).handleErrorWith(e => BadRequest(e.getMessage))
            case None         => BadRequest("You don't have a team yet")
          }
        } yield response

      case req@POST -> Root as user =>
        for {
          team     <- req.req.as[TeamCreation]
          response <- Created(teamService.createTeam(team.name, user.id, team.players, team.captain)).handleErrorWith(e => BadRequest(e.getMessage))
        } yield response
    }

  val routes: HttpRoutes[IO] = authMiddleware(authedRoutes)
}
