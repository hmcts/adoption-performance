package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Environment, Common}

object CreateUser {

  val IdamAPIURL = Environment.idamAPIURL

  val newUserFeeder = Iterator.continually(Map(
    "emailAddress" -> ("adoption" + Common.getDate() + "@perftest-" + Common.randomString(10) + ".com"),
    "password" -> "Pa55word11"
  ))

  val CreateCitizen =
    feed(newUserFeeder)
      .group("AD_000_CreateCitizen") {
        exec(http("CreateCitizen")
          .post(IdamAPIURL + "/testing-support/accounts")
          .body(ElFileBody("CreateUserTemplate.json")).asJson
          .check(status.is(201)))
      }

}
