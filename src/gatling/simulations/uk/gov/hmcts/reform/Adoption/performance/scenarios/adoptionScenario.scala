package uk.gov.hmcts.reform.Adoption.performance.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Environment, CommonHeader}

object adoptionScenario {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl

  val ThinkTime = Environment.thinkTime


  val adoptionHomepage =

    group("adoption_010_Homepage") {
      exec(http("LAU Homepage")
        .get(BaseURL)
        .headers(CommonHeader.homepage_header)
        .check(substring("Sign in"))
        .check(css("input[name='_csrf']", "value").saveAs("csrfToken")))
    }
    .pause(ThinkTime)


  val adoptionLogin =

      group("LAU_030_Login") {
        exec(http("LAU Login")
          .post(IdamURL + "/login?client_id=adoption-web&response_type=code&redirect_uri=https://adoption-web.aat.platform.hmcts.net/receiver")
          .headers(CommonHeader.headers_30)
          .formParam("username", "adoption_citizen_test@mailinator.com")
          .formParam("password", "Password123")
          .formParam("save", "Sign in")
          .formParam("selfRegistrationEnabled", "true")
          .formParam("_csrf", "${csrfToken}")
          .check(substring("Case Audit Search")))
      }
      .pause(ThinkTime)





}


