package com.panton.http.routes

import cats.effect.IO
import com.panton.domain.Name
import com.panton.service.ClubService
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._


final case class ClubRoutes(clubService: ClubService) {
  private def clubsRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case req@GET -> Root =>
      clubService.getAllClubs().flatMap(clubs => Ok(clubs)).handleErrorWith(e => BadRequest(e.getMessage))

    case req @ GET -> Root / club =>
      clubService.getAllClubs().flatMap { allClubs =>
        if (allClubs.exists(_.name == Name(club))) {
          clubService.getClubByName(Name(club)).flatMap(players => Ok(players))
        } else {
          BadRequest("Incorrect football club")
        }
      }


    case req@GET -> Root / "stats" / "table" =>
      clubService.showTable().flatMap(rows => Ok(rows))
  }

  val routes: HttpRoutes[IO] = clubsRoutes
}
