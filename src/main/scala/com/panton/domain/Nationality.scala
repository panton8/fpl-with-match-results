package com.panton.domain
import enumeratum._

sealed trait Nationality extends EnumEntry

final case object Nationality extends Enum[Nationality] with DoobieEnum[Nationality] {

  final case object Argentinian extends Nationality
  final case object Australian extends Nationality
  final case object Austrian extends Nationality
  final case object Belgian extends Nationality
  final case object Brazilian extends Nationality
  final case object Scottish extends Nationality
  final case object Croatian extends Nationality
  final case object Czech extends Nationality
  final case object Danish extends Nationality
  final case object English extends Nationality
  final case object Estonian extends Nationality
  final case object French extends Nationality
  final case object Swedish extends Nationality
  final case object Uruguayan extends Nationality
  final case object Welsh extends Nationality
  final case object Spanish extends Nationality
  final case object Portuguese extends Nationality
  final case object Irish extends Nationality
  final case object Greek extends Nationality
  final case object German extends Nationality
  final case object Italian extends Nationality
  final case object Dutch extends Nationality
  final case object Norwegian extends Nationality


  val values = findValues
}
