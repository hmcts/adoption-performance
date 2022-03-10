package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.adoptionScenario1and2.ThinkTime
import utils.{CommonHeader, Environment}

object adoptionScenario3 {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl

  val ThinkTime = Environment.thinkTime


  val adoptionBirthCertificate =

    group("adoption_290_Birth_Certificate") {
      exec(http("Adoption Birth Certificate Details")
        .get("/children/full-name")
        .headers(CommonHeader.get_header)
        .check(substring("What is the child"))
        .check(substring("full name?")))
    }
      .pause(ThinkTime)
      .group("adoption_300_Birth_Certificate_Name") {
        exec(http("Adoption Birth Certificate Name")
          .post("/children/full-name")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenFirstName", "${FirstName}")
          .formParam("childrenLastName", "${LastName}")
          .check(substring("What is the child"))
          .check(substring("date of birth?")))
      }
      .pause(ThinkTime)

      .group("adoption_320_Birth_Certificate_DoB") {
        exec(http("Adoption Birth Certificate DoB")
          .post(BaseURL + "/children/date-of-birth")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenDateOfBirth-day", "${ChildDay}")
          .formParam("childrenDateOfBirth-month", "${ChildMonth}")
          .formParam("childrenDateOfBirth-year", "${ChildYear}")
          .check(substring("What was the child"))
          .check(substring("sex at birth?")))
      }
      .pause(ThinkTime)

      /*
  .group("adoption_325_Birth_Certificate_DoB_Redirect") {
    exec(http("Adoption Birth Certificate Details Redirect")
      .get("/children/sex-at-birth")
      .headers(CommonHeader.get_header)
      .check(substring("What was the child"))
      .check(substring("sex at birth?")))
  }

       */

      .group("adoption_330_Birth_Certificate_Sex") {
        exec(http("Adoption Birth Certificate Sex")
          .post("/children/sex-at-birth")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenSexAtBirth", "${SexAtBirth}")
          .check(substring("What is their nationality?")))
      }
      .pause(ThinkTime)

      /*
      .group("adoption_335_Birth_Certificate_Sex_Redirect") {
        exec(http("Adoption Birth Certificate Sex Redirect")
          .get("/children/nationality")
          .headers(CommonHeader.get_header)
          .check(substring("What is their nationality?")))
      }

       */

      .group("adoption_340_Birth_Certificate_Nationality_Add") {
        exec(http("Adoption Birth Certificate Nationality Add")
          .post("/children/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "British")
          .formParam("childrenNationality", "Irish")
          .formParam("childrenNationality", "Other")
          .formParam("addAnotherNationality", "${Nationality}")
          .formParam("addButton", "addButton")
          .check(substring("What is their nationality?"))
          .check(substring("${Nationality}")))
      }
      .pause(ThinkTime)

      .group("adoption_350_Birth_Certificate_Nationality_POST") {
        exec(http("Adoption Birth Certificate Nationality POST")
          .post("/children/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "")
          .formParam("childrenNationality", "British")
          .formParam("childrenNationality", "Irish")
          .formParam("childrenNationality", "Other")
          .formParam("addAnotherNationality", "")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)


  val adoptionCertificateDetails =

    group("adoption_360_Certificate_Details") {
      exec(http("Adoption Certificate Details")
        .get("/children/full-name-after-adoption")
        .headers(CommonHeader.get_header)
        .check(substring("What will the child"))
        .check(substring("full name be after adoption?")))
    }
      .pause(ThinkTime)


      .group("adoption_370_Certificate_Details_Name") {
        exec(http("Adoption Certificate Details Name")
          .post("/children/full-name-after-adoption")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("childrenFirstNameAfterAdoption", "${FirstName}")
          .formParam("childrenLastNameAfterAdoption", "${LastName}")
          .check(substring("Apply to adopt a child placed in your care")))
          .pause(ThinkTime)

      }


  val adoptionPlacementOrder =

    group("adoption_380_Placement_Order") {
      exec(http("Adoption Placement Order Details")
        .get("/children/placement-order-number")
        .headers(CommonHeader.get_header)
        .check(substring("What is the serial or case number on the placement order")))
    }
      .pause(ThinkTime)

      .group("adoption_390_Certificate_Placement_Order_Number") {
        exec(http("Adoption Placement Order Number")
          .post("/children/placement-order-number")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderNumber", "${placementOrderNumber}")
          .check(substring("Which court made the placement order?")))
      }
      .pause(ThinkTime)

      .group("adoption_400_Certificate_Placement_Court") {
        exec(http("Adoption Placement Court")
          .post("/children/placement-order-court")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderCourt", "${placementOrderCourt}")
          .check(substring("What date is on the placement order?")))
      }
      .pause(ThinkTime)

      .group("adoption_410_Certificate_Placement_Date") {
        exec(http("Adoption Placement Date")
          .post("/children/placement-order-date")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderDate-day", "${placementOrderDate-day}")
          .formParam("placementOrderDate-month", "${placementOrderDate-month}")
          .formParam("placementOrderDate-year", "${placementOrderDate-year}")
          .check(substring("Do you want to add another order?")))
      }
      .pause(ThinkTime)

      .group("adoption_420_Certificate_Placement_Summary") {
        exec(http("Adoption Placement Summary")
          .post("/children/placement-order-summary")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("addAnotherPlacementOrder", "Yes")
          .check(substring("What type of order is it?")))
      }
      .pause(ThinkTime)

      .group("adoption_430_Certificate_Placement_Type_2") {
        exec(http("Adoption Placement Type 2")
          .post("/children/placement-order-type")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderType", "${placementOrderType}")
          .check(substring("What is the serial or case number on the placement order?")))
      }
      .pause(ThinkTime)


      .group("adoption_440_Certificate_Placement_Order_Number_2") {
        exec(http("Adoption Placement Order Number 2")
          .post("/children/placement-order-number")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderNumber", "${placementOrderNumber}")
          .check(substring("Which court made the placement order?")))
      }
      .pause(ThinkTime)

      .group("adoption_450_Certificate_Placement_Court_2") {
        exec(http("Adoption Placement Court 2")
          .post("/children/placement-order-court")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderCourt", "${placementOrderCourt}")
          .check(substring("What date is on the placement order?")))
      }
      .pause(ThinkTime)

      .group("adoption_460_Certificate_Placement_Date_2") {
        exec(http("Adoption Placement Date 2")
          .post("/children/placement-order-date")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderDate-day", "${placementOrderDate-day}")
          .formParam("placementOrderDate-month", "${placementOrderDate-month}")
          .formParam("placementOrderDate-year", "${placementOrderDate-year}")
          .check(substring("Do you want to add another order?")))
      }
      .pause(ThinkTime)

      .group("adoption_470_Certificate_Placement_Summary_2") {
        exec(http("Adoption Placement Summary 2")
          .post("/children/placement-order-summary")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("addAnotherPlacementOrder", "No")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)


  val adoptionBirthMother =

    group("adoption_480_Birth_Mother") {
      exec(http("Adoption Mother's Details")
        .get("/birth-mother/full-name")
        .headers(CommonHeader.get_header)
        .check(substring("What is the full name of the child"))
        .check(substring("birth mother?")))
    }
      .pause(ThinkTime)

      .group("adoption_490_Birth_Mother_Name") {
        exec(http("Adoption Mother's Name")
          .post("/birth-mother/full-name")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherFirstNames", "${FirstName}")
          .formParam("birthMotherLastNames", "${LastName}"))
        //.check(substring("Is the child's birth mother still alive?")))
      }
      .pause(ThinkTime)

      .group("adoption_495_Birth_Mother") {
        exec(http("Adoption Mother's Details")
          .get("/birth-mother/still-alive")
          .headers(CommonHeader.get_header)
          .check(substring("Is the child"))
          .check(substring("birth mother still alive?")))
      }
      .pause(ThinkTime)


      .group("adoption_500_Birth_Mother_Alive") {
        exec(http("Adoption Mother Still Alive?")
          .post("/birth-mother/still-alive")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherStillAlive", "Yes")
          .formParam("birthMotherNotAliveReason", "")
          .check(substring("What is the nationality of the child"))
          .check(substring("birth mother?")))
      }
      .pause(ThinkTime)

      .group("adoption_510_Birth_Mother_Nationality_Add") {
        exec(http("Adoption Mother's Nationality Add")
          .post("/birth-mother/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "British")
          .formParam("birthMotherNationality", "Irish")
          .formParam("birthMotherNationality", "Other")
          .formParam("addAnotherNationality", "${Nationality}")
          .formParam("addButton", "addButton")
          .check(substring("What is the nationality of the child"))
          .check(substring("birth mother?"))
          .check(substring("${Nationality}")))
      }
      .pause(ThinkTime)

      .group("adoption_520_Birth_Mother_Nationality_POSt") {
        exec(http("Adoption Mother's Nationality")
          .post("/birth-mother/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "")
          .formParam("birthMotherNationality", "British")
          .formParam("birthMotherNationality", "Irish")
          .formParam("birthMotherNationality", "Other")
          .formParam("addAnotherNationality", "")
          .check(substring("What is the occupation of the child's birth mother?")))
      }
      .pause(ThinkTime)

      .group("adoption_530_Birth_Mother_Occupation") {
        exec(http("Adoption Mother's Occupation")
          .post("/birth-mother/occupation")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherOccupation", "${Occupation}"))
        //  .check(substring("Do you have the birth mother's last known address?")))
      }
      .pause(ThinkTime)

      .group("adoption_535_Birth_Mother_Occupation_Redirect") {
        exec(http("Adoption Mother's Details Redirect")
          .get("/birth-mother/address-known")
          .headers(CommonHeader.get_header)
          .check(substring("Do you have the birth mother"))
          .check(substring("last known address?")))
      }
      .pause(ThinkTime)

      .group("adoption_540_Birth_Mother_Occupation") {
        exec(http("Adoption Mother's Address Known")
          .post("/birth-mother/address-known")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherAddressKnown", "Yes")
          .formParam("birthMotherAddressNotKnownReason", "")
          .check(substring("What is the birth mother"))
          .check(substring("last known address?"))
          .check(substring("Or enter address manually")))
      }
      .pause(ThinkTime)

      .group("adoption_550_Birth_Mother_Address_Manual") {
        exec(http("Adoption Mother's Address Manual")
          .get("/birth-mother/address/manual")
          .headers(CommonHeader.get_header)
          .check(substring("What is the birth mother"))
          .check(substring("last known address?"))
          .check(substring("Building and street")))
      }
      .pause(ThinkTime)

      .group("adoption_560_Birth_Mother_Address_POST") {
        exec(http("Adoption Mother's Address")
          .post("/birth-mother/address/manual")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthMotherAddress1", "${AddressLine1}")
          .formParam("birthMotherAddress2", "${AddressLine2}")
          .formParam("birthMotherAddressTown", "${Town}")
          .formParam("birthMotherAddressCounty", "${County}")
          .formParam("birthMotherAddressPostcode", "${PostCode}")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)


  val adoptionBirthFather =

    group("adoption_570_Birth_Father_Details") {
      exec(http("Adoption Father's Details")
        .get("/birth-father/name-on-certificate")
        .headers(CommonHeader.get_header)
        .check(substring("Is the birth father")))
    }
      .pause(ThinkTime)

      .group("adoption_580_Birth_Father_Details_POST") {
        exec(http("Adoption Father's Details On Certificate")
          .post("/birth-father/name-on-certificate")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherNameOnCertificate", "Yes")
          .check(substring("What is the full name of the child")))
      }
      .pause(ThinkTime)

      .group("adoption_590_Birth_Father_Name") {
        exec(http("Adoption Father's Name")
          .post("/birth-father/full-name")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherFirstNames", "${FirstName}")
          .formParam("birthFatherLastNames", "${LastName}")
          .check(substring("birth father still alive?")))
      }
      .pause(ThinkTime)

      .group("adoption_600_Birth_Father_Alive") {
        exec(http("Adoption Father Still alive?")
          .post("/birth-father/alive")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherStillAlive", "Yes")
          .formParam("birthFatherUnsureAliveReason", "")
          .check(substring("What is the nationality of the"))
          .check(substring("birth father?")))

      }
      .pause(ThinkTime)

      .group("adoption_610_Birth_Father_Nationality_Add") {
        exec(http("Adoption Father add Nationality")
          .post("/birth-father/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "British")
          .formParam("birthFatherNationality", "Irish")
          .formParam("birthFatherNationality", "Other")
          .formParam("addAnotherNationality", "${Nationality}")
          .formParam("addButton", "addButton")
          .check(substring("birth father?"))
          .check(substring("${Nationality}")))
      }
      .pause(ThinkTime)


      .group("adoption_620_Birth_Father_Nationality_POST") {
        exec(http("Adoption Father's Nationality")
          .post("/birth-father/nationality")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "")
          .formParam("birthFatherNationality", "British")
          .formParam("birthFatherNationality", "Irish")
          .formParam("birthFatherNationality", "Other")
          .formParam("addAnotherNationality", "")
          .check(substring("What is the occupation of the"))
          .check(substring("birth father?")))
      }
      .pause(ThinkTime)

      .group("adoption_630_Birth_Father_Occupation") {
        exec(http("Adoption Father's Occupation")
          .post("/birth-father/occupation")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherOccupation", "${Occupation}")
          .check(substring("Do you have the birth father"))
          .check(substring("last known address?")))

      }
      .pause(ThinkTime)

      .group("adoption_640_Birth_Father_Occupation_Address_Known") {
        exec(http("Adoption Father's Address Known?")
          .post("/birth-father/address-known")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherAddressKnown", "Yes")
          .formParam("birthFatherAddressNotKnownReason", "")
          .check(substring("What is the birth father"))
          .check(substring("last known address?")))
      }
      .pause(ThinkTime)

      .group("adoption_640_Birth_Father_Occupation_Address_Manual") {
        exec(http("Adoption Father's Address Manual")
          .get("/birth-father/address/manual")
          .headers(CommonHeader.get_header)
          .check(substring("What is the birth father"))
          .check(substring("last known address?"))
          .check(substring("Building and street")))
      }
      .pause(ThinkTime)


      .group("adoption_640_Birth_Father_Occupation_Address_POST") {
        exec(http("Adoption Father's Address")
          .post("/birth-father/address/manual")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("birthFatherAddress1", "${AddressLine1}")
          .formParam("birthFatherAddress2", "${AddressLine2}")
          .formParam("birthFatherAddressTown", "${Town}")
          .formParam("birthFatherAddressCounty", "${County}")
          .formParam("birthFatherAddressPostcode", "${PostCode}")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)


  val adoptionOtherParent =

    group("adoption_650_Other_Parent_Exists") {
      exec(http("Adoption Other Parent")
        .get("/other-parent/exists")
        .headers(CommonHeader.get_header)
        .check(substring("Is there another person who has parental responsibility for the child?")))
    }
      .pause(ThinkTime)


      .group("adoption_660_Other_Parent_Exists_POST") {
        exec(http("Adoption Other Parent Exists")
          .post("/other-parent/exists")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("otherParentExists", "Yes")
          .check(substring("What is the full name of the other person with parental responsibility?")))
      }
      .pause(ThinkTime)


      .group("adoption_670_Other_Parent_Name") {
        exec(http("Adoption Other Parent Name")
          .post("/other-parent/full-name")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("otherParentFirstNames", "${FirstName}")
          .formParam("otherParentLastNames", "${LastName}")
          .check(substring("Do you have the address of the other person with parental responsibility for the child?")))
      }
      .pause(ThinkTime)


      .group("adoption_680_Other_Parent_Address_Known?") {
        exec(http("Adoption Other Parent Address known?")
          .post("/other-parent/address-known")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("otherParentAddressKnown", "Yes")
          .formParam("otherParentAddressNotKnownReason", "")
          .check(substring("Other parent"))
          .check(substring("their address?")))
      }
      .pause(ThinkTime)

      .group("adoption_690_Other_Parent_Address_Manual") {
        exec(http("Adoption Other Parent Address Manual")
          .get("/other-parent/address/manual")
          .headers(CommonHeader.get_header)
          .check(substring("Other parent"))
          .check(substring("Building and street")))
      }
      .pause(ThinkTime)

      .group("adoption_700_Other_Parent_Address_POST") {
        exec(http("Adoption Other Parent Address Manual")
          .post("/other-parent/address/manual")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("otherParentAddress1", "${AddressLine1}")
          .formParam("otherParentAddress2", "${AddressLine2}")
          .formParam("otherParentAddressTown", "${Town}")
          .formParam("otherParentAddressCounty", "${County}")
          .formParam("otherParentAddressPostcode", "${PostCode}")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)

  val adoptionAgency =

    group("adoption_710_Agency") {
      exec(http("Adoption Agency")
        .get(BaseURL + "/children/adoption-agency?change=1646062432902")
        //Need to capture the ID
        .headers(CommonHeader.get_header)
        .check(substring("Adoption agency or local authority details")))
    }
      .pause(ThinkTime)

      .group("adoption_720_Agency_Details") {
        exec(http("Adoption Agency Details")
          .post(BaseURL + "/children/adoption-agency")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("adopAgencyOrLaName", "${agencyName}")
          .formParam("adopAgencyOrLaPhoneNumber", "${agencyNumber}")
          .formParam("adopAgencyOrLaContactName", "${agencyContactName}")
          .formParam("adopAgencyOrLaContactEmail", "${agencyEmail}")
          .check(substring("Was there another adoption agency or local authority involved in placing the child?")))
      }
      .pause(ThinkTime)

      .group("adoption_730_Other_Agency") {
        exec(http("Adoption Other Agency?")
          .post("/children/other-adoption-agency")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("hasAnotherAdopAgencyOrLA", "Yes")
          .check(substring("Adoption agency or local authority details")))
      }
      .pause(ThinkTime)

      .group("adoption_740_Other_Agency_Details") {
        exec(http("Adoption Other Agency Details")
          .post("/children/adoption-agency")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("adopAgencyOrLaName", "${agencyName}")
          .formParam("adopAgencyOrLaPhoneNumber", "${agencyNumber}")
          .formParam("adopAgencyOrLaContactName", "${agencyContactName}")
          .formParam("adopAgencyOrLaContactEmail", "${agencyEmail}")
          .check(substring("Details about the"))
          .check(substring("social worker")))
      }
      .pause(ThinkTime)

      .group("adoption_750_Social_Worker_Details") {
        exec(http("Adoption Social Worker Details")
          .post("/children/social-worker")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("socialWorkerName", "${SocialWorkerName}")
          .formParam("socialWorkerPhoneNumber", "${SocialWorkerPhoneNumber}")
          .formParam("socialWorkerEmail", "${SocialWorkerEmail}")
          .formParam("socialWorkerTeamEmail", "${socialWorkerTeamEmail}")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)


  val adoptionSiblingDetails =

    group("adoption_760_Sibling_Details") {
      exec(http("Adoption Sibling Details")
        .get("/sibling/exists")
        .headers(CommonHeader.get_header)
        .check(substring("Does the child have any siblings or half siblings?")))
    }
      .pause(ThinkTime)

      .group("adoption_770_Sibling_Other?") {
        exec(http("Adoption Any Sibling?")
          .post("/sibling/exists")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("hasSiblings", "Yes")
          .check(substring("Is there a court order in place for any of the child"))
          .check(substring("siblings or half siblings?")))
      }
      .pause(ThinkTime)

      .group("adoption_780_Sibling_Court_Order_Exists?") {
        exec(http("Adoption Court Order Exists?")
          .post("/sibling/court-order-exists")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("hasPoForSiblings", "Yes")
          .check(substring("Which siblings or half siblings have a court order in place?")))
      }
      .pause(ThinkTime)

      .group("adoption_790_Sibling_Name?") {
        exec(http("Adoption Sibling Name")
          .post("/sibling/select-sibling")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("selectedSiblingId", "addAnotherSibling")
          .formParam("siblingFirstName", "Yay")
          .formParam("siblingLastNames", "Wow")
          .check(substring("What type of order is it?")))
      }
      .pause(ThinkTime)

      .group("adoption_800_Sibling_Order_Type") {
        exec(http("Adoption Order Type?")
          .post("/sibling/placement-order-type")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderType", "Yay")
          //still hard coded
          .check(substring("What is the serial or case number on the order?")))
      }
      .pause(ThinkTime)

      .group("adoption_810_Sibling_Order_Number") {
        exec(http("Adoption Order Number")
          .post("/sibling/placement-order-number")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderNumber", "${placementOrderNumber}")
          .check(substring("Orders already in place for siblings and half-siblings")))
      }
      .pause(ThinkTime)

      .group("adoption_820_Sibling_New_Order?") {
        exec(http("Adoption Add New Order?")
          .post("/sibling/summary")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("addAnotherSiblingPlacementOrder", "Yes")
          .check(css("input[name='selectedSiblingId']", "value").saveAs("SiblingID"))
          .check(substring("What sibling or half-sibling do you want to add a court order for?")))
      }
      .pause(ThinkTime)

      .group("adoption_830_Sibling_Select") {
        exec(http("Adoption Select Sibling")
          .post("/sibling/select-sibling")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("selectedSiblingId", "${SiblingID}")
          .formParam("locale", "en")
          .formParam("siblingFirstName", "${SiblingFirstName}")
          .formParam("siblingLastNames", "${SiblingLastName}")
          .check(substring("What type of order is it?")))
      }
      .pause(ThinkTime)

      .group("adoption_840_Sibling_Select") {
        exec(http("Adoption Order Type")
          .post("/sibling/placement-order-type")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderType", "${placementOrderType}")
          .check(substring("What is the serial or case number on the order?")))
      }
      .pause(ThinkTime)

      .group("adoption_850_Sibling_Select") {
        exec(http("Adoption Order Number")
          .post("/sibling/placement-order-number")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderNumber", "${placementOrderNumber}")
          .check(substring("Orders already in place for siblings and half-siblings")))
      }
      .pause(ThinkTime)
      /*

      .group("adoption_851_Sibling_New_Order?") {
        exec(http("Adoption Add New Order?")
          .post("/sibling/summary")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("addAnotherSiblingPlacementOrder", "Yes")
          .check(css("input[name='selectedSiblingId']", "value").saveAs("SiblingID"))
          .check(substring("What sibling or half-sibling do you want to add a court order for?")))
      }
      .pause(ThinkTime)

      .group("adoption_852_Sibling_Select") {
        exec(http("Adoption Select Sibling")
          .post("/sibling/select-sibling")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("selectedSiblingId", "${SiblingID}")
          .formParam("locale", "en")
          .formParam("siblingFirstName", "${SiblingFirstName}")
          .formParam("siblingLastNames", "${SiblingLastName}")
          .check(substring("What type of order is it?")))
      }
      .pause(ThinkTime)

      .group("adoption_853_Sibling_Select") {
        exec(http("Adoption Order Type")
          .post("/sibling/placement-order-type")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderType", "${placementOrderType}")
          .check(substring("What is the serial or case number on the order?")))
      }
      .pause(ThinkTime)

      .group("adoption_854_Sibling_Select") {
        exec(http("Adoption Order Number")
          .post("/sibling/placement-order-number")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("placementOrderNumber", "${placementOrderNumber}")
          .check(substring("Orders already in place for siblings and half-siblings")))
      }
      .pause(ThinkTime)

       */

      .group("adoption_855_Sibling_Select") {
        exec(http("Adoption Order Number")
          .post("/sibling/remove-placement-order")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("locale", "en")
          .formParam("confirm", "Yes")
          .check(substring("Orders already in place for siblings and half-siblings")))
      }



      .group("adoption_860_Sibling_Select") {
        exec(http("Adoption Order Number")
          .post("/sibling/summary")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("addAnotherSiblingPlacementOrder", "No")
          .check(substring("Apply to adopt a child placed in your care")))
      }
      .pause(ThinkTime)

  val adoptionFamilyCourt =

      group("adoption_861_Family_Court") {
        exec(http("Adoption Family Court Redirect")
          .get("/children/find-family-court")
          .headers(CommonHeader.get_header)
          .check(substring("Choose a family court")))

      }

          .group("adoption_862_Family_Court_POST") {
            exec(http("Adoption Family Court Redirect")
            .post("/children/find-family-court")
              .headers(CommonHeader.post_header)
              .formParam("_csrf", "${csrfToken}")
              .formParam("locale", "en")
              .formParam("findFamilyCourt", "Yes")
              .formParam("familyCourtName", "")
              .check(substring("Apply to adopt a child placed in your care")))
          }


}