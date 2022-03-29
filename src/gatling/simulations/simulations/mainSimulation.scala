package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scenarios._
import utils.Environment

import scala.concurrent.duration._

class mainSimulation extends Simulation{

  val BaseURL = Environment.baseUrl
  val feederCitizenLogins =csv("citizenLogins.csv").circular
  val feederDateChildMovedIn =csv("dateChildMovedIn.csv").circular
  val feederApplicant =csv("applicant.csv").circular
  val feederPlacementOrder =csv("placementOrder.csv").circular
  val feederAgencyDetails =csv("agencyDetails.csv").circular
  val feederSocialWorkerDetails =csv("socialWorkerDetails.csv").circular
  val feederSiblingDetails =csv("siblingDetails.csv").circular

  val httpProtocol = http
    .baseUrl(BaseURL)
    .doNotTrackHeader("1")
    .inferHtmlResources()
    .silentResources

  val adoptionSimulation = scenario("LAU Simulation")
    .feed(feederCitizenLogins).feed(feederApplicant).feed(feederDateChildMovedIn).feed(feederPlacementOrder).feed(feederAgencyDetails).feed(feederSocialWorkerDetails)
    .feed(feederSiblingDetails)
    .exitBlockOnFail {
      exec(adoptionScenario1and2.adoptionHomepage)
      .exec(adoptionScenario1and2.adoptionLogin)
      .exec(adoptionScenario1and2.adoptionApplyingWith)
      .exec(adoptionScenario1and2.adoptionDateOfMove)
      .exec(adoptionScenario3.adoptionAgency)
      .exec(adoptionScenario1and2.adoptionYourDetails)
      .exec(adoptionScenario1and2.adoptionYourContact)
      .exec(adoptionScenario1and2.adoptionSecondPersonal)
      .exec(adoptionScenario1and2.adoptionSecondContact)
      .exec(adoptionScenario3.adoptionBirthCertificate)
      .exec(adoptionScenario3.adoptionCertificateDetails)
      .exec(adoptionScenario3.adoptionPlacementOrder)
      .exec(adoptionScenario3.adoptionBirthMother)
      .exec(adoptionScenario3.adoptionBirthFather)
      .exec(adoptionScenario3.adoptionOtherParent)
      .exec(adoptionScenario3.adoptionSiblingDetails)
      .exec(adoptionScenario3.adoptionFamilyCourt)
      .exec(adoptionScenario4and5.adoptionUploadDocuments)
      .exec(adoptionScenario4and5.adoptionReview)

    }


  setUp(
    adoptionSimulation.inject(rampUsers(2) during (10 minutes))
    .protocols(httpProtocol)
  )

}

