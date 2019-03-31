package com.github.pedrovgs.scalakatas.formvalidation

import java.time.LocalDateTime

import cats.data.{NonEmptyList, Validated, ValidatedNel}
import cats.syntax.apply._
import eu.timepit.refined.{W, _}
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.MatchesRegex

object FormValidator {

  type FirstName       = Refined[String, NonEmpty]
  type LastName        = Refined[String, NonEmpty]
  type ValidDocumentId = MatchesRegex[W.`"""\\d{8}[a-zA-Z]{1}"""`.T]
  type DocumentId      = Refined[String, ValidDocumentId]
  type ValidPhone      = MatchesRegex[W.`"""\\d{9}"""`.T]
  type Phone           = Refined[String, ValidPhone]
  type Email           = Refined[String, ValidEmail]

  case class ValidEmail()
  implicit val emailValidate: Validate.Plain[String, ValidEmail] =
    Validate.fromPredicate(e => e.contains("@"), p => s"$p is not a valid email", ValidEmail())

  final case class UnsafeForm(
      firstName: String,
      lastName: String,
      birthday: LocalDateTime,
      documentId: String,
      phone: String,
      email: String
  )
  final case class Form(
      firstName: FirstName,
      lastName: LastName,
      birthday: LocalDateTime,
      documentId: DocumentId,
      phone: Phone,
      email: Email
  )
  type FormValidationResult[A] = ValidatedNel[FormError, A]

  def apply(referenceDate: LocalDateTime, form: UnsafeForm): FormValidationResult[Form] =
    (validateFirstName(form.firstName),
     validateLastName(form.lastName),
     validateBirthday(referenceDate, form.birthday),
     validateDocumentId(form.documentId),
     validatePhone(form.phone),
     validateEmail(form.email)).mapN(Form)

  private def validateFirstName(firstName: String): FormValidationResult[FirstName] =
    Validated
      .fromEither(refineV[NonEmpty](firstName))
      .leftMap(_ => NonEmptyList.of(EmptyFirstName(firstName)))

  private def validateLastName(lastName: String): FormValidationResult[LastName] =
    Validated
      .fromEither(refineV[NonEmpty](lastName))
      .leftMap(_ => NonEmptyList.of(EmptyLastName(lastName)))

  private def validateBirthday(refDate: LocalDateTime, birthday: LocalDateTime): FormValidationResult[LocalDateTime] =
    if (birthday.compareTo(refDate.minusYears(18)) <= 0) {
      Validated.valid(birthday)
    } else {
      Validated.invalidNel(UserTooYoung(birthday))
    }
  private def validateDocumentId(documentId: String): FormValidationResult[DocumentId] =
    Validated
      .fromEither(refineV[ValidDocumentId](documentId))
      .leftMap(_ => NonEmptyList.of(InvalidDocumentId(documentId)))

  private def validatePhone(phone: String): FormValidationResult[Phone] =
    Validated
      .fromEither(refineV[ValidPhone](phone))
      .leftMap(_ => NonEmptyList.of(InvalidPhone(phone)))

  private def validateEmail(email: String): FormValidationResult[Email] =
    Validated
      .fromEither(refineV[ValidEmail](email))
      .leftMap(_ => NonEmptyList.of(InvalidEmail(email)))

}

sealed trait FormError
case class EmptyFirstName(firstName: String)           extends FormError
final case class EmptyLastName(lastName: String)       extends FormError
final case class UserTooYoung(birthday: LocalDateTime) extends FormError
final case class InvalidDocumentId(documentId: String) extends FormError
final case class InvalidPhone(phone: String)           extends FormError
final case class InvalidEmail(email: String)           extends FormError
