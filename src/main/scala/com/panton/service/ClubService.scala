package com.panton.service

import cats.effect.IO
import com.panton.domain.{Club, Id, Name}
import com.panton.repository.ClubRepository
import com.panton.repository.domain.TableInfo

final case class ClubService() {

  def showTable(): IO[List[TableInfo]] = {
    ClubRepository.getTableInfo()
  }

  def getAllClubs(): IO[List[Club]] = {
    ClubRepository.getAllClubs()
  }

  def getClubByName(club: Name): IO[Option[Club]] = {
    ClubRepository.getClubByName(club)
  }


}

