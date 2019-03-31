package com.github.pedrovgs.scalakatas.formvalidation
import java.time.LocalDateTime

import cats.implicits
import cats.data.ValidatedNec

object FormValidator {
  type FormValidationResult[A] = ValidatedNec[FormError, A]

  def apply(referenceDate: LocalDateTime, form: Form): FormValidationResult[Form] = {
    val tuple = (validateFirstName(form.firstName), validateFirstName(form.firstName))
    tuple.mapN
  }

  private def validateFirstName(firstName: String): FormValidationResult[String] = ???

}

final case class Form(
    firstName: String,
    lastName: String,
    birthday: LocalDateTime,
    documentId: String,
    phone: String,
    email: String
)

sealed class FormError {
  final case class EmptyFirstName(firstName: String)
  final case class EmptyLastName(lastName: String)
  final case class UserTooYoung(birthday: LocalDateTime)
  final case class InvalidDocumentId(documentId: String)
  final case class InvalidPhone(phone: String)
  final case class InvalidEmail(email: String)
}
