package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scenarios._
import utils.Environment

import scala.concurrent.duration._

class mainSimulation extends Simulation{

  val BaseURL = Environment.baseUrl

  val httpProtocol = http
    .baseUrl(BaseURL)
    .doNotTrackHeader("1")
    .inferHtmlResources()
    .silentResources

  val adoptionSimulation = scenario("LAU Simulation")
    .exitBlockOnFail {
      exec(adoptionScenario.adoptionHomepage)
      .exec(adoptionScenario.adoptionLogin)
    }


  setUp(
    adoptionSimulation.inject(rampUsers(5) during (5 minutes))
    .protocols(httpProtocol)
  )

}

