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
  val BaseURL = Environment.baseUrl

  val postcodeFeeder = csv("postcodes.csv").random

  def postcodeLookup(personUrl: String, personParam: String) =
    feed(postcodeFeeder)
      .exec(http("XUI_Common_000_PostcodeLookup")
        .post(BaseURL + s"/${personUrl}/address/lookup")
        .headers(Headers.commonHeader)
        .header("content-type", "application/x-www-form-urlencoded")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam(s"${personParam}AddressPostcode", "#{postcode}")
        .check(regex("""<option value="([0-9]+)">""").findRandom.saveAs("addressIndex")))


  def postcodeLookupLa(personUrl: String, personParam: String) =
    feed(postcodeFeeder)
      .exec(http("XUI_Common_000_PostcodeLookup")
        .post(BaseURL + s"/la-portal/${personUrl}/address/lookup")
        .headers(Headers.commonHeader)
        .header("content-type", "application/x-www-form-urlencoded")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam(s"${personParam}AddressPostcode", "#{postcode}")
        .check(regex("""<option value="([0-9]+)">""").findRandom.saveAs("addressIndex")))



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

  def yearMinusOne(): String = {
    now.minusYears(1).format(patternYear)
  }

  //Date of Birth >= 25 years
  def getDobYear(): String = {
    now.minusYears(25 + rnd.nextInt(70)).format(patternYear)
  }

}