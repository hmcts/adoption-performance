package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scenarios._
import utils.Environment
import io.gatling.core.controller.inject.open.OpenInjectionStep
import io.gatling.commons.stats.assertion.Assertion
import io.gatling.core.pause.PauseType

import scala.concurrent.duration._

class adoptionSimulation extends Simulation {

  val BaseURL = Environment.baseUrl

  /* TEST TYPE DEFINITION */
  /* pipeline = nightly pipeline against the AAT environment (see the Jenkins_nightly file) */
  /* perftest (default) = performance test against the perftest environment */
  val testType = scala.util.Properties.envOrElse("TEST_TYPE", "perftest")

  //set the environment based on the test type
  val environment = testType match {
    case "perftest" => "perftest"
    case "pipeline" => "aat"
    case _ => "**INVALID**"
  }

  /* ******************************** */

  /* ADDITIONAL COMMAND LINE ARGUMENT OPTIONS */
  val debugMode = System.getProperty("debug", "off") //runs a single user e.g. ./gradle gatlingRun -Ddebug=on (default: off)
  val env = System.getProperty("env", environment) //manually override the environment aat|perftest e.g. ./gradle gatlingRun -Denv=aat
  /* ******************************** */

  /* PERFORMANCE TEST CONFIGURATION */
  val testDurationMins = 60
  val numberOfPerformanceTestUsers: Double = 30
  val numberOfPipelineUsers: Double = 10

  //If running in debug mode, disable pauses between steps
  val pauseOption: PauseType = debugMode match {
    case "off" => constantPauses
    case _ => disabledPauses
  }

      val httpProtocol = http
        .baseUrl(BaseURL)
        .doNotTrackHeader("1")
        .inferHtmlResources()
        .silentResources

      before {
        println(s"Test Type: ${testType}")
        println(s"Test Environment: ${env}")
        println(s"Debug Mode: ${debugMode}")
      }

      val adoptionSimulation = scenario("Adoption")
        .exitBlockOnFail {
          exec(_.set("env", s"${env}"))
            .exec(CreateUser.CreateCitizen)
            .exec(adoptionScenario.adoptionHomepage)
            .exec(adoptionScenario.adoptionLogin)
            .exec(adoptionScenario.adoptionApplyingWith)
            .exec(adoptionScenario.adoptionDateOfMove)
            .exec(adoptionScenario.adoptionAgency)
            .exec(adoptionScenario.adoptionYourDetails)
            .exec(adoptionScenario.adoptionYourContact)
            .exec(adoptionScenario.adoptionSecondPersonal)
            .exec(adoptionScenario.adoptionSecondContact)
            .exec(adoptionScenario.childsDetails)
            .exec(adoptionScenario.theFamilyCourtDetails)
            /*
            .exec(adoptionScenario.adoptionPlacementOrder)
            .exec(adoptionScenario.adoptionBirthMother)
            .exec(adoptionScenario.adoptionBirthFather)
            .exec(adoptionScenario.adoptionOtherParent)
            .exec(adoptionScenario.adoptionSiblingDetails)
            .exec(adoptionScenario.adoptionFamilyCourt)
            .exec(adoptionScenario.adoptionUploadDocuments)
             */
            .exec(adoptionScenario.adoptionReview)
        }
        .exec(adoptionScenario.adoptionLogOut)

        .exec {
          session =>
            println(session)
            session
        }

      //defines the Gatling simulation model, based on the inputs
      def simulationProfile(simulationType: String, numberOfPerformanceTestUsers: Double, numberOfPipelineUsers: Double): Seq[OpenInjectionStep] = {
        simulationType match {
          case "perftest" =>
            if (debugMode == "off") {
              Seq(
                rampUsers(numberOfPerformanceTestUsers.toInt) during (testDurationMins minutes)
              )
            }
            else {
              Seq(atOnceUsers(1))
            }
          case "pipeline" =>
            Seq(rampUsers(numberOfPipelineUsers.toInt) during (2 minutes))
          case _ =>
            Seq(nothingFor(0))
        }
      }

      //defines the test assertions, based on the test type
      def assertions(simulationType: String): Seq[Assertion] = {
        simulationType match {
          case "perftest" | "pipeline" => //currently using the same assertions for a performance test and the pipeline
            if (debugMode == "off") {
              Seq(global.successfulRequests.percent.gte(95),
                details("AD_960_Application_Submit").successfulRequests.percent.gte(90))
            }
            else {
              Seq(global.successfulRequests.percent.is(100))
            }
          case _ =>
            Seq()
        }
      }

      setUp(
        adoptionSimulation.inject(simulationProfile(testType, numberOfPerformanceTestUsers, numberOfPipelineUsers)).pauses(pauseOption)
      ).protocols(httpProtocol)
        .assertions(assertions(testType))

}

