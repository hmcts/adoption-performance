package utils

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

object Common {

  val rnd = new Random()
  val now = LocalDate.now()
  val patternDay = DateTimeFormatter.ofPattern("dd")
  val patternMonth = DateTimeFormatter.ofPattern("MM")
  val patternYear = DateTimeFormatter.ofPattern("yyyy")
  val patternDate = DateTimeFormatter.ofPattern("yyyyMMdd")

  val postcodeFeeder = csv("postcodes.csv").random

  val postcodeLookup =
    feed(postcodeFeeder)
      .exec(http("XUI_Common_000_PostcodeLookup")
       //.get("/api/addresses?postcode=${postcode}")
        .get("/applicant1/address/lookup")
        .headers(Headers.commonHeader)
        .header("accept", "application/json")
        .check(jsonPath("$.header.totalresults").ofType[Int].gt(0))
        .check(regex(""""(?:BUILDING|ORGANISATION)_.+" : "(.+?)",(?s).*?"(?:DEPENDENT_LOCALITY|THOROUGHFARE_NAME)" : "(.+?)",.*?"POST_TOWN" : "(.+?)",.*?"POSTCODE" : "(.+?)"""")
          .ofType[(String, String, String, String)].findRandom.saveAs("addressLines")))

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def randomNumber(length: Int) = {
    rnd.alphanumeric.filter(_.isDigit).take(length).mkString
  }

  def getDate(): String = {
    now.format(patternDate)
  }

  def getDay(): String = {
    (1 + rnd.nextInt(28)).toString.format(patternDay).reverse.padTo(2, '0').reverse //pads single-digit dates with a leading zero
  }

  def getMonth(): String = {
    (1 + rnd.nextInt(12)).toString.format(patternMonth).reverse.padTo(2, '0').reverse //pads single-digit dates with a leading zero
  }

  //Dob >= 25 years
  def getMarriageYear(): String = {
    now.minusYears(25 + rnd.nextInt(70)).format(patternYear)
  }

  def getPostcode(): String = {
    randomString(2).toUpperCase() + rnd.nextInt(10).toString + " " + rnd.nextInt(10).toString + randomString(2).toUpperCase()
  }

  def getDobYearChild(): String = {
    now.minusYears(2 + rnd.nextInt(15)).format(patternYear)
  }

  //Date of Birth >= 25 years
  def getDobYear(): String = {
    now.minusYears(25 + rnd.nextInt(70)).format(patternYear)
  }

}