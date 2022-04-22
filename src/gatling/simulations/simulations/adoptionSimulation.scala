package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scenarios._
import utils.Environment

import scala.concurrent.duration._

class adoptionSimulation extends Simulation{

  val BaseURL = Environment.baseUrl

  val httpProtocol = http
    .baseUrl(BaseURL)
    .doNotTrackHeader("1")
    .inferHtmlResources()
    .silentResources

  val adoptionSimulation = scenario("Adoption")
    .exitBlockOnFail {
      exec(CreateUser.CreateCitizen)
      .exec(adoptionScenario.adoptionHomepage)
      .exec(adoptionScenario.adoptionLogin)
      .exec(adoptionScenario.adoptionApplyingWith)
      .exec(adoptionScenario.adoptionDateOfMove)
      .exec(adoptionScenario.adoptionAgency)
      .exec(adoptionScenario.adoptionYourDetails)
      .exec(adoptionScenario.adoptionYourContact)
      .exec(adoptionScenario.adoptionSecondPersonal)
      .exec(adoptionScenario.adoptionSecondContact)
      .exec(adoptionScenario.adoptionBirthCertificate)
      .exec(adoptionScenario.adoptionCertificateDetails)
      .exec(adoptionScenario.adoptionPlacementOrder)
      .exec(adoptionScenario.adoptionBirthMother)
      .exec(adoptionScenario.adoptionBirthFather)
      .exec(adoptionScenario.adoptionOtherParent)
      .exec(adoptionScenario.adoptionSiblingDetails)
      .exec(adoptionScenario.adoptionFamilyCourt)
      .exec(adoptionScenario.adoptionUploadDocuments)
      .exec(adoptionScenario.adoptionReview)


    }


  setUp(
    adoptionSimulation.inject(rampUsers(30) during (60 minutes))
    .protocols(httpProtocol)
  )

}

