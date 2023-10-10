package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Common, CsrfCheck, Environment, Headers}


object adoptionScenarioCW {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val PaymentURL = Environment.paymentUrl
  val ThinkTime = Environment.thinkTime
  val postcodeFeeder = csv("postcodes.csv").random


  val refDetails =

    group("AD_450_RefDetails_Redirect") {
      exec(http("Ref Details Redirect")
        .get(BaseURL + "/la-portal/kba-case-ref")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Court case reference number")))
    }
      .pause(ThinkTime)


      /*======================================================================================
    * Enter The Reference Details
    ======================================================================================*/

      .group("AD_460_EnterRefDetails") {
        exec(http("Enter Ref Details")
          .post(BaseURL + "/la-portal/kba-case-ref")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("kbaCaseRef", "#{referenceNumber}")
          .formParam("kbaChildName", "ChildFirst#{randomString} ChildLast#{randomString}")
          .formParam("kbaChildrenDateOfBirth-day", "#{randomDay}")
          .formParam("kbaChildrenDateOfBirth-month", "#{randomMonth}")
          .formParam("kbaChildrenDateOfBirth-year", "#{childDobYear}")
          .check(substring("Submitting a response to an adoption application")))
      }
      .pause(ThinkTime)


  /*======================================================================================
* Getting Started
======================================================================================*/

  .group("AD_470_GettingStarted") {
    exec(http("Getting Started")
      .get(BaseURL + "/la-portal/task-list?")
      .headers(Headers.commonHeader)
      .check(substring("Birth certificate details")))
  }
    .pause(ThinkTime)



      /*======================================================================================
  * 'Birth Certificate Details' Event
  ======================================================================================*/

      .group("AD_480_BirthCertificateDetails") {
        exec(http("Birth Certificate Details")
          .get(BaseURL + "/la-portal/child/sex-at-birth")
          .headers(Headers.commonHeader)
          .check(CsrfCheck.save)
          .check(substring("What was the child's sex at birth?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What was the child's sex at birth? - Male
======================================================================================*/

      .group("AD_490_ChildBirthSex") {
        exec(http("Child Birth At Sex")
          .post(BaseURL + "/la-portal/child/sex-at-birth")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("childrenSexAtBirth", "male")
          .formParam("childrenOtherSexAtBirth", "")
          .check(CsrfCheck.save)
          .check(substring("What is their nationality?")))
      }
      .pause(ThinkTime)

      /*======================================================================================
* What is their nationality?
======================================================================================*/

      .group("AD_500_ChildNationality") {
        exec(http("Child Nationality")
          .post(BaseURL + "/la-portal/child/nationality")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "British")
          .formParam("addAnotherNationality", "")
          .check(regex("""id="birth-certificate-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* 'Birth mother details' Event
======================================================================================*/

      .group("AD_510_BirthMotherDetails") {
        exec(http("Birth Mother Details")
          .get(BaseURL + "/la-portal/birth-mother/full-name")
          .headers(Headers.commonHeader)
          .check(CsrfCheck.save)
          .check(substring("What is the full name of the child's birth mother?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* What is the full name of the child's birth mother?
======================================================================================*/

      .group("AD_520_NameOfBirthMother") {
        exec(http("Name Of Birth Mother")
          .post(BaseURL + "/la-portal/birth-mother/full-name")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherFirstNames", "birthMotherFirstNames#{randomString}")
          .formParam("birthMotherLastNames", "birthMotherLastNames#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("Is the child's birth mother still alive?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Is the child's birth mother still alive? - Yes
======================================================================================*/

      .group("AD_530_BirthMotherAlive") {
        exec(http("Birth Mother Alive")
          .post(BaseURL + "/la-portal/birth-mother/still-alive")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherStillAlive", "Yes")
          .formParam("birthMotherNotAliveReason", "")
          .check(CsrfCheck.save)
          .check(substring("What is the nationality of the child's birth mother?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What is the nationality of the child's birth mother? - British
======================================================================================*/

      .group("AD_540_BirthMotherNationality") {
        exec(http("Birth Mother Nationality")
          .post(BaseURL + "/la-portal/birth-mother/nationality")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "British")
          .formParam("addAnotherNationality", "")
          .check(CsrfCheck.save)
          .check(substring("What is the occupation of the child's birth mother?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What is the occupation of the child's birth mother?
======================================================================================*/

      .group("AD_550_BirthMotherOccupation") {
        exec(http("Birth Mother Occupation")
          .post(BaseURL + "/la-portal/birth-mother/occupation")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherOccupation", "birthMotherOccupation#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("Do you have the birth mother's last known address?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Do you have the birth mother's last known address?
======================================================================================*/

      .group("AD_560_BirthMotherAddressKnown") {
        exec(http("Birth Mother Address Known")
          .post(BaseURL + "/la-portal/birth-mother/address-known")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherAddressKnown", "Yes")
          .formParam("birthMotherAddressNotKnownReason", "")
          .check(CsrfCheck.save)
          .check(substring("What is the birth mother's last known address?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * Postcode lookup for Mother
    ======================================================================================*/

      .group("AD_570_MotherAddressLookup") {
        exec(Common.postcodeLookupLa("birth-mother", "birthMother"))
      }
      .pause(ThinkTime)

      /*======================================================================================
      * Select Mother Address
      ======================================================================================*/

      .group("AD_580_MotherAddressSelect") {
        exec(http("Adoption Mother's Select")
          .post(BaseURL + "/la-portal/birth-mother/address/select")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherSelectAddress", "#{addressIndex}")
          .check(substring("When was the last date this address was confirmed?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * When was the last date this address was confirmed?
    ======================================================================================*/

      .group("AD_580_AddressWasConfirmed") {
        exec(http("Address Was Confirmed")
          .post(BaseURL + "/la-portal/birth-mother/last-address-confirmed")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherLastAddressDate-day", "#{randomDay}")
          .formParam("birthMotherLastAddressDate-month", "#{randomMonth}")
          .formParam("birthMotherLastAddressDate-year", "#{adoptionYear}")
          .check(substring("Should the birth mother be sent documents or court orders relating to this adoption?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
  * Should the birth mother be sent documents or court orders relating to this adoption?
  ======================================================================================*/

      .group("AD_590_MotherSentDocuments") {
        exec(http("Mother Be Sent Documents")
          .post(BaseURL + "/la-portal/birth-mother/served-with")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthMotherServedWith", "Yes")
          .formParam("birthMotherNotServedWithReason", "")
          .check(regex("""id="birth-mother-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
      }
      .pause(ThinkTime)

      /*======================================================================================
   * 'Birth father details' Event
   ======================================================================================*/

      .group("AD_600_BirthFatherDetails") {
        exec(http("Birth Father Details")
          .get(BaseURL + "/la-portal/birth-father/name-on-certificate")
          .headers(Headers.commonHeader)
          .check(CsrfCheck.save)
          .check(substring("Is the birth father's name on the birth certificate?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* Is the birth father's name on the birth certificate? - Yes
======================================================================================*/

      .group("AD_610_IsFatherOnBirthCertificate") {
        exec(http("Is Father On Birth Certificate")
          .post(BaseURL + "/la-portal/birth-father/name-on-certificate")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherNameOnCertificate", "Yes")
          .check(CsrfCheck.save)
          .check(substring("What is the full name of the child's birth father?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* What is the full name of the child's birth father?
======================================================================================*/

      .group("AD_620_BirthFatherName") {
        exec(http("Birth Father Name")
          .post(BaseURL + "/la-portal/birth-father/full-name")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherFirstNames", "birthFatherFirstNames#{randomString}")
          .formParam("birthFatherLastNames", "birthFatherLastNames#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("Is the child's birth father still alive?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Is the child's birth father still alive? - Yes
======================================================================================*/

      .group("AD_630_BirthFatherAlive") {
        exec(http("Birth Father Alive")
          .post(BaseURL + "/la-portal/birth-father/still-alive")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherStillAlive", "Yes")
          .formParam("birthFatherUnsureAliveReason", "")
          .check(CsrfCheck.save)
          .check(substring("Does the birth father have parental responsibility?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Does the birth father have parental responsibility? - Yes
======================================================================================*/

      .group("AD_640_FatherHaveResponsibility") {
        exec(http("Father Have Parental Responsibility")
          .post(BaseURL + "/la-portal/birth-father/parental-responsibility")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherResponsibility", "Yes")
          .check(CsrfCheck.save)
          .check(substring("How was parental responsibility granted to the birth father?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* How was parental responsibility granted to the birth father? - Court Order
======================================================================================*/

      .group("AD_650_HowWasResponsibilityGranted") {
        exec(http("How Was Responsibility Granted")
          .post(BaseURL + "/la-portal/birth-father/parental-responsibility/granted")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherResponsibilityReason", "")
          .formParam("birthFatherResponsibilityReason", "")
          .formParam("birthFatherResponsibilityReason", "")
          .formParam("birthFatherResponsibilityReason", "")
          .formParam("birthFatherResponsibilityReason", "")
          .formParam("birthFatherResponsibilityReason", "Court order")
          .formParam("birthFatherOtherResponsibilityReason", "")
          .check(CsrfCheck.save)
          .check(substring("What is the nationality of the child's birth father?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What is the nationality of the child's birth Father? - British
======================================================================================*/

      .group("AD_660_BirthFatherNationality") {
        exec(http("Birth Father Nationality")
          .post(BaseURL + "/la-portal/birth-father/nationality")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "British")
          .formParam("addAnotherNationality", "")
          .check(CsrfCheck.save)
          .check(substring("What is the occupation of the child's birth father?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What is the occupation of the child's birth father?
======================================================================================*/

      .group("AD_680_BirthFatherOccupation") {
        exec(http("Birth Father Occupation")
          .post(BaseURL + "/la-portal/birth-father/occupation")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherOccupation", "birthFatherOccupation#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("last known address")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Do you have the birth father's last known address?
======================================================================================*/

      .group("AD_690_BirthFatherAddressKnown") {
        exec(http("Birth Father Address Known")
          .post(BaseURL + "/la-portal/birth-father/address-known")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherAddressKnown", "Yes")
          .formParam("birthFatherAddressNotKnownReason", "")
          .check(CsrfCheck.save)
          .check(substring("What is the birth father's last known address?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * Postcode lookup for Mother
    ======================================================================================*/

      .group("AD_700_Father_Address_Lookup") {
        exec(Common.postcodeLookupLa("birth-father", "birthFather"))
      }
      .pause(ThinkTime)

      /*======================================================================================
      * Select Father Address
      ======================================================================================*/

      .group("AD_710_MotherAddressSelect") {
        exec(http("Adoption Father's Select")
          .post(BaseURL + "/la-portal/birth-father/address/select")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherSelectAddress", "#{addressIndex}")
          .check(substring("When was the last date this address was confirmed?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * When was the last date this address was confirmed?
    ======================================================================================*/

      .group("AD_720_AddressWasConfirmed") {
        exec(http("Address Was Confirmed")
          .post(BaseURL + "/la-portal/birth-father/last-address-confirmed")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherLastAddressDate-day", "#{randomDay}")
          .formParam("birthFatherLastAddressDate-month", "#{randomMonth}")
          .formParam("birthFatherLastAddressDate-year", "#{adoptionYear}")
          .check(substring("Should the birth father be sent documents or court orders relating to this adoption?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
  * Should the birth father be sent documents or court orders relating to this adoption? - yes
  ======================================================================================*/

      .group("AD_730_FatherSentDocuments") {
        exec(http("Father Be Sent Documents")
          .post(BaseURL + "/la-portal/birth-father/served-with")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("birthFatherServedWith", "Yes")
          .formParam("birthMotherNotServedWithReason", "")
          .check(regex("""id="birth-father-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
      }
      .pause(ThinkTime)

      /*======================================================================================
  * 'Other person with parental responsibility' Event
  ======================================================================================*/

      .group("AD_740_OtherPersonResponsibility") {
        exec(http("Other Person With Responsibility")
          .get(BaseURL + "/la-portal/other-parent/exists")
          .headers(Headers.commonHeader)
          .check(CsrfCheck.save)
          .check(substring("Is there another person who has parental responsibility for the child?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Is there another person who has parental responsibility for the child? - yes
======================================================================================*/

      .group("AD_750_AnotherPerson") {
        exec(http("Another Person With Responsibility")
          .post(BaseURL + "/la-portal/other-parent/exists")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentExists", "Yes")
          .check(CsrfCheck.save)
          .check(substring("What is the full name of the other person with parental responsibility?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What is the full name of the other person with parental responsibility?
======================================================================================*/

      .group("AD_750_OtherName") {
        exec(http("Other Person Name")
          .post(BaseURL + "/la-portal/other-parent/full-name")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentFirstNames", "otherParentFirstNames#{randomString}")
          .formParam("otherParentLastNames", "otherParentLastNames#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("How parental responsibility was granted to the other person?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* How parental responsibility was granted to the other person? - Birth certificate
======================================================================================*/

      .group("AD_760_OtherResponsibilityGranted") {
        exec(http("Other Responsibility Granted")
          .post(BaseURL + "/la-portal/other-parent/parental-responsibility-granted")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentResponsibilityReason", "")
          .formParam("otherParentResponsibilityReason", "")
          .formParam("otherParentResponsibilityReason", "")
          .formParam("otherParentResponsibilityReason", "")
          .formParam("otherParentResponsibilityReason", "")
          .formParam("otherParentResponsibilityReason", "Court order")
          .formParam("otherParentOtherResponsibilityReason", "")
          .check(CsrfCheck.save)
          .check(substring("Do you have the address of the other person with parental responsibility for the child?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* Do you have the address of the other person with parental responsibility for the child? - yes
======================================================================================*/

      .group("AD_770_OtherPersonAddress") {
        exec(http("Other Person Address")
          .post(BaseURL + "/la-portal/other-parent/address-known")
          .headers(Headers.commonHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentAddressKnown", "Yes")
          .formParam("otherParentAddressNotKnownReason", "")
          .check(CsrfCheck.save)
          .check(substring("What's their address?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * Postcode lookup for Other Parent
    ======================================================================================*/

      .group("AD_770_OtherParentAddressLookup") {
        exec(Common.postcodeLookupLa("other-parent", "otherParent"))
      }
      .pause(ThinkTime)

      /*======================================================================================
      * Select The Other Parent's Address
      ======================================================================================*/

      .group("AD_780_OtherParentAddress") {
        exec(http("Adoption Other Parent Address Select")
          .post(BaseURL + "/la-portal/other-parent/address/select")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentSelectAddress", "#{addressIndex}")
          .check(CsrfCheck.save)
          .check(substring("When was the last date this address was confirmed?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
  * When was the last date this address was confirmed?
  ======================================================================================*/

      .group("AD_790_OtherAddressConfirmed") {
        exec(http("Other Address Was Confirmed")
          .post(BaseURL + "/la-portal/other-parent/last-address-confirmed")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentLastAddressDate-day", "#{randomDay}")
          .formParam("otherParentLastAddressDate-month", "#{randomMonth}")
          .formParam("otherParentLastAddressDate-year", "#{adoptionYear}")
          .check(CsrfCheck.save)
          .check(substring("Should the person with parental responsibility be sent documents or court orders relating to this adoption?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* Should the person with parental responsibility be sent documents or court orders relating to this adoption? - yes
======================================================================================*/

      .group("AD_800_OtherSentDocuments") {
        exec(http("Other Be Sent Documents")
          .post(BaseURL + "/la-portal/other-parent/served-with")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("otherParentServedWith", "Yes")
          .formParam("otherParentNotServedWithReason", "")
          .check(regex("""id="other-parent-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* 'Placement and court orders' Event
======================================================================================*/

      .group("AD_810_PlacementCourtOrders") {
        exec(http("Placement Court Orders")
          .get(BaseURL + "/la-portal/child/placement-order-number")
          .headers(Headers.commonHeader)
          .check(CsrfCheck.save)
          .check(substring("What is the serial or case number on the placement order?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
    * What is the serial or case number on the placement order?
    ======================================================================================*/

      .group("AD_820_SerialOrCaseNum") {
        exec(http("Serial Or Case Number")
          .post(BaseURL + "/la-portal/child/placement-order-number")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("placementOrderNumber", "placementOrderNumber#{randomString}")
          .check(CsrfCheck.save)
          .check(substring("What date is on the placement order?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
  * What date is on the placement order?
  ======================================================================================*/

      .group("AD_830_DateOnOrder") {
        exec(http("What Date On Order")
          .post(BaseURL + "/la-portal/child/placement-order-date")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("placementOrderDate-day", "#{randomDay}")
          .formParam("placementOrderDate-month", "#{randomMonth}")
          .formParam("placementOrderDate-year", "#{adoptionYear}")
          .check(CsrfCheck.save)
          .check(substring("Does the child have any other previous or existing orders?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* Does the child have any other previous or existing orders? - yes
======================================================================================*/

      .group("AD_840_OtherOrders") {
        exec(http("Any Other Orders")
          .post(BaseURL + "/la-portal/child/placement-order-summary")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("addAnotherPlacementOrder", "Yes")
          .check(CsrfCheck.save)
          .check(substring("What type of order is it?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* What type of order is it? - Case Order
======================================================================================*/

      .group("AD_850_TypeOfOrder") {
        exec(http("Type Of Orders")
          .post(BaseURL + "/la-portal/child/placement-order-type")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("selectedPlacementOrderType", "Care order")
          .formParam("selectedOtherPlacementOrderType", "")
          .check(CsrfCheck.save)
          .check(substring("What is the serial or case number on the order")))
      }

      .pause(ThinkTime)


      /*======================================================================================
* What is the serial or case number on the order?
======================================================================================*/

      .group("AD_860_OtherOrderNum") {
        exec(http("Other Order Number")
          .post(BaseURL + "/la-portal/child/placement-order-number")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("placementOrderNumber", "otherPlacementOrderNumber#{randomString}r")
          .check(CsrfCheck.save)
          .check(substring("Which court made the order?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Which court made the order?
======================================================================================*/

      .group("AD_870_WhichCourt") {
        exec(http("Which Court")
          .post(BaseURL + "/la-portal/child/placement-order-court")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("autoCompleteData", "Bromley County Court and Family Court")
          .formParam("placementOrderCourt", "Bromley County Court and Family Court")
          .check(CsrfCheck.save)
          .check(substring("What date is on the order?")))
      }
      .pause(ThinkTime)



      /*======================================================================================
* Other Court Date
======================================================================================*/

      .group("AD_880_OtherCourtDate") {
        exec(http("Other Court Date")
          .post(BaseURL + "/la-portal/child/placement-order-date")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("placementOrderDate-day", "#{randomDay}")
          .formParam("placementOrderDate-month", "#{randomMonth}")
          .formParam("placementOrderDate-year", "#{adoptionYear}")
          .check(CsrfCheck.save)
          .check(substring("Does the child have any other previous or existing orders?")))
      }
      .pause(ThinkTime)


      /*======================================================================================
* Does the child have any other previous or existing orders? - No
======================================================================================*/

      .group("AD_890_AnyOtherOrders") {
        exec(http("Any Other Orders")
          .post(BaseURL + "/la-portal/child/placement-order-summary")
          .headers(Headers.commonHeader)
          .headers(Headers.postHeader)
          .formParam("_csrf", "#{csrfToken}")
          .formParam("locale", "en")
          .formParam("addAnotherPlacementOrder", "No")
          .check(regex("""id="placement-and-court-order-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
      }
      .pause(ThinkTime)
}
