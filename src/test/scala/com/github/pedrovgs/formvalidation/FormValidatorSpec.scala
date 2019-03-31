package com.github.pedrovgs.formvalidation
import java.time.LocalDateTime

import cats.data.{NonEmptyList, Validated}
import com.github.pedrovgs.scalakatas.formvalidation.FormValidator.UnsafeForm
import com.github.pedrovgs.scalakatas.formvalidation._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class FormValidatorSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryForms {

  it should "indicate all the invalid values in an unsafe form with every field as invalid" in {
    val form = UnsafeForm(
      firstName = "",
      lastName = "",
      birthday = LocalDateTime.now(),
      documentId = "48632500",
      phone = "6799",
      email = "pedro"
    )

    val result = FormValidator(LocalDateTime.now(), form)

    result shouldBe Validated.invalid(
      NonEmptyList.of(
        EmptyFirstName(form.firstName),
        EmptyLastName(form.lastName),
        UserTooYoung(form.birthday),
        InvalidDocumentId(form.documentId),
        InvalidPhone(form.phone),
        InvalidEmail(form.email)
      ))
  }

  it should "indicate just one invalid value in an unsafe form where just one field is invalid" in {
    val form = UnsafeForm(
      firstName = "Pedro",
      lastName = "Gómez",
      birthday = LocalDateTime.MIN,
      documentId = "38632509C",
      phone = "677673297",
      email = "pedro"
    )

    val result = FormValidator(LocalDateTime.now(), form)

    result shouldBe Validated.invalid(
      NonEmptyList.of(
        InvalidEmail(form.email)
      ))
  }

  it should "consider as valid form with evey field as valid" in {
    forAll { form: UnsafeForm =>
      FormValidator(LocalDateTime.now(), form).isValid shouldBe true
    }
  }
}
