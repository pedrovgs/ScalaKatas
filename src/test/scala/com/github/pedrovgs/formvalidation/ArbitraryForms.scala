package com.github.pedrovgs.formvalidation
import java.time.LocalDateTime

import com.github.pedrovgs.scalakatas.formvalidation.FormValidator.{FirstName, LastName, UnsafeForm}
import eu.timepit.refined.scalacheck.string._
import org.scalacheck.{Arbitrary, Gen}

trait ArbitraryForms {

  implicit val arbitraryForm: Arbitrary[UnsafeForm] = Arbitrary {
    for {
      firstName  <- Arbitrary.arbitrary[FirstName]
      lastName   <- Arbitrary.arbitrary[LastName]
      birthday   <- Gen.const(LocalDateTime.MIN)
      documentId <- Gen.const("44632508A")
      phone      <- Gen.const("999673292")
      email      <- Gen.const("p@k.com")
    } yield UnsafeForm(firstName.value, lastName.value, birthday, documentId, phone, email)
  }
}
