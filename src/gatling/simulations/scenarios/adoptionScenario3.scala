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

    group("AD_290_Birth_Certificate") {
      exec(http("Adoption Birth Certificate Details")
        .get("/children/full-name")
        .headers(CommonHeader.get_header)
        .check(substring("What is the child"))
        .check(substring("full name?")))
    }
    .pause(ThinkTime)

    .group("AD_300_Birth_Name") {
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

    .group("AD_320_Birth_DoB") {
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

    .group("AD_330_Birth_Sex") {
      exec(http("Adoption Birth Certificate Sex")
        .post("/children/sex-at-birth")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("childrenSexAtBirth", "${SexAtBirth}")
        .check(substring("What is their nationality?")))
    }
    .pause(ThinkTime)

    .group("AD_340_Birth_Nationality_Add") {
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

    .group("AD_350_Birth_Nationality_POST") {
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

    group("AD_360_Certificate_Details") {
      exec(http("Adoption Certificate Details")
        .get("/children/full-name-after-adoption")
        .headers(CommonHeader.get_header)
        .check(substring("What will the child"))
        .check(substring("full name be after adoption?")))
    }
    .pause(ThinkTime)


    .group("AD_370_Certificate_Name") {
      exec(http("Adoption Certificate Details Name")
        .post("/children/full-name-after-adoption")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("childrenFirstNameAfterAdoption", "${FirstName}")
        .formParam("childrenLastNameAfterAdoption", "${LastName}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionPlacementOrder =

    group("AD_380_Placement_Order") {
      exec(http("Adoption Placement Order Details")
        .get("/children/placement-order-number")
        .headers(CommonHeader.get_header)
        .check(substring("What is the serial or case number on the placement order")))
    }
    .pause(ThinkTime)

    .group("AD_390_Placement_Number") {
      exec(http("Adoption Placement Order Number")
        .post("/children/placement-order-number")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Which court made the placement order?")))
    }
    .pause(ThinkTime)

    .group("AD_400_Placement_Court") {
      exec(http("Adoption Placement Court")
        .post("/children/placement-order-court")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderCourt", "${placementOrderCourt}")
        .check(substring("What date is on the placement order?")))
    }
    .pause(ThinkTime)

    .group("AD_410_Placement_Date") {
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

    .group("AD_420_Placement_Summary") {
      exec(http("Adoption Placement Summary")
        .post("/children/placement-order-summary")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("addAnotherPlacementOrder", "Yes")
        .check(substring("What type of order is it?")))
    }
    .pause(ThinkTime)

    .group("AD_430_Placement_Type_2") {
      exec(http("Adoption Placement Type 2")
        .post("/children/placement-order-type")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the placement order?")))
    }
    .pause(ThinkTime)


    .group("AD_440_Placement_Number_2") {
      exec(http("Adoption Placement Order Number 2")
        .post("/children/placement-order-number")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Which court made the placement order?")))
    }
    .pause(ThinkTime)

    .group("AD_450_Placement_Court_2") {
      exec(http("Adoption Placement Court 2")
        .post("/children/placement-order-court")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderCourt", "${placementOrderCourt}")
        .check(substring("What date is on the placement order?")))
    }
    .pause(ThinkTime)

    .group("AD_460_Placement_Date_2") {
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

    .group("AD_470_Placement_Summary_2") {
      exec(http("Adoption Placement Summary 2")
        .post("/children/placement-order-summary")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("addAnotherPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionBirthMother =

    group("AD_480_Birth_Mother") {
      exec(http("Adoption Mother's Details")
        .get("/birth-mother/full-name")
        .headers(CommonHeader.get_header)
        .check(substring("What is the full name of the child"))
        .check(substring("birth mother?")))
    }
    .pause(ThinkTime)

    .group("AD_490_Mother_Name") {
      exec(http("Adoption Mother's Name")
        .post("/birth-mother/full-name")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("birthMotherFirstNames", "${FirstName}")
        .formParam("birthMotherLastNames", "${LastName}"))
    }
    .pause(ThinkTime)

    .group("AD_495_Mother_Alive_Redirect") {
      exec(http("Adoption Mother's Details")
        .get("/birth-mother/still-alive")
        .headers(CommonHeader.get_header)
        .check(substring("Is the child"))
        .check(substring("birth mother still alive?")))
    }
    .pause(ThinkTime)


    .group("AD_500_Mother_Alive") {
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

    .group("AD_510_Mother_Nationality_Add") {
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
        .check(substring("birth mother?"))
        .check(substring("${Nationality}")))
    }
    .pause(ThinkTime)

    .group("AD_520_Mother_Nationality_POST") {
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

    .group("AD_530_Mother_Occupation") {
      exec(http("Adoption Mother's Occupation")
        .post("/birth-mother/occupation")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("birthMotherOccupation", "${Occupation}"))
    }
    .pause(ThinkTime)

    .group("AD_535_Mother_Occupation_Redirect") {
      exec(http("Adoption Mother's Details Redirect")
        .get("/birth-mother/address-known")
        .headers(CommonHeader.get_header)
        .check(substring("Do you have the birth mother"))
        .check(substring("last known address?")))
    }
    .pause(ThinkTime)

    .group("AD_540_Mother_Occupation") {
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

    .group("AD_550_Mother_Address_Manual") {
      exec(http("Adoption Mother's Address Manual")
        .get("/birth-mother/address/manual")
        .headers(CommonHeader.get_header)
        .check(substring("What is the birth mother"))
        .check(substring("last known address?"))
        .check(substring("Building and street")))
    }
    .pause(ThinkTime)

    .group("AD_560_Mother_Address_POST") {
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
    group("AD_570_Father_Details") {
      exec(http("Adoption Father's Details")
        .get("/birth-father/name-on-certificate")
        .headers(CommonHeader.get_header)
        .check(substring("Is the birth father")))
    }
    .pause(ThinkTime)

    .group("AD_580_Father_Details_POST") {
      exec(http("Adoption Father's Details On Certificate")
        .post("/birth-father/name-on-certificate")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("birthFatherNameOnCertificate", "Yes")
        .check(substring("What is the full name of the child")))
    }
    .pause(ThinkTime)

    .group("AD_590_Father_Name") {
      exec(http("Adoption Father's Name")
        .post("/birth-father/full-name")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("birthFatherFirstNames", "${FirstName}")
        .formParam("birthFatherLastNames", "${LastName}")
        .check(substring("birth father still alive?")))
    }
    .pause(ThinkTime)

    .group("AD_600_Father_Alive") {
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

    .group("AD_610_Father_Nationality_Add") {
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

    .group("AD_620_Father_Nationality_POST") {
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

    .group("AD_630_Father_Occupation") {
      exec(http("Adoption Father's Occupation")
        .post("/birth-father/occupation")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("birthFatherOccupation", "${Occupation}")
        .check(substring("Do you have the birth father"))
        .check(substring("last known address?")))

    }
    .pause(ThinkTime)

    .group("AD_640_Father_Address_Known") {
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

    .group("AD_641_Father_Address_Manual") {
      exec(http("Adoption Father's Address Manual")
        .get("/birth-father/address/manual")
        .headers(CommonHeader.get_header)
        .check(substring("What is the birth father"))
        .check(substring("Building and street")))
    }
    .pause(ThinkTime)


    .group("AD_642_Father_Address_POST") {
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
    group("AD_650_Other_Parent_Exists") {
      exec(http("Adoption Other Parent")
        .get("/other-parent/exists")
        .headers(CommonHeader.get_header)
        .check(substring("Is there another person who has parental responsibility for the child?")))
    }
    .pause(ThinkTime)


    .group("AD_660_Other_Exists_POST") {
      exec(http("Adoption Other Parent Exists")
        .post("/other-parent/exists")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("otherParentExists", "Yes")
        .check(substring("What is the full name of the other person with parental responsibility?")))
    }
    .pause(ThinkTime)

    .group("AD_670_Other_Parent_Name") {
      exec(http("Adoption Other Parent Name")
        .post("/other-parent/full-name")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("otherParentFirstNames", "${FirstName}")
        .formParam("otherParentLastNames", "${LastName}")
        .check(substring("Do you have the address of the other person with parental responsibility for the child?")))
    }
    .pause(ThinkTime)


    .group("AD_680_Other_Parent_Address_Known") {
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

    .group("AD_690_Other_Parent_Address_Manual") {
      exec(http("Adoption Other Parent Address Manual")
        .get("/other-parent/address/manual")
        .headers(CommonHeader.get_header)
        .check(substring("Other parent"))
        .check(substring("Building and street")))
    }
    .pause(ThinkTime)

    .group("AD_700_Other_Parent_Address_POST") {
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

    group("AD_710_Agency") {
      exec(http("Adoption Agency")
        .get(BaseURL + "/children/adoption-agency?change=1646062432902")
        //Need to capture the ID
        .headers(CommonHeader.get_header)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    .group("AD_720_Agency_Details") {
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

    .group("AD_730_Other_Agency") {
      exec(http("Adoption Other Agency?")
        .post("/children/other-adoption-agency")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("hasAnotherAdopAgencyOrLA", "Yes")
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    .group("AD_740_Other_Agency_Details") {
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

    .group("AD_750_Social_Worker_Details") {
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

    group("AD_760_Sibling_Details") {
      exec(http("Adoption Sibling Details")
        .get("/sibling/exists")
        .headers(CommonHeader.get_header)
        .check(substring("Does the child have any siblings or half siblings?")))
    }
    .pause(ThinkTime)

    .group("AD_770_Sibling_Other") {
      exec(http("Adoption Any Sibling?")
        .post("/sibling/exists")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("hasSiblings", "Yes")
        .check(substring("Is there a court order in place for any of the child"))
        .check(substring("siblings or half siblings?")))
    }
    .pause(ThinkTime)

    .group("AD_780_Sibling_Order_Exists") {
      exec(http("Adoption Court Order Exists?")
        .post("/sibling/court-order-exists")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("hasPoForSiblings", "Yes")
        .check(substring("Which siblings or half siblings have a court order in place?")))
    }
    .pause(ThinkTime)

    .group("AD_790_Sibling_Name") {
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

    .group("AD_800_Sibling_Type") {
      exec(http("Adoption Order Type?")
        .post("/sibling/placement-order-type")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the order?")))
    }
    .pause(ThinkTime)

    .group("AD_810_Sibling_Number") {
      exec(http("Adoption Order Number")
        .post("/sibling/placement-order-number")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Orders already in place for siblings and half-siblings")))
    }
    .pause(ThinkTime)

    .group("AD_820_Sibling_New_Order") {
      exec(http("Adoption Add New Order?")
        .post("/sibling/summary")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("addAnotherSiblingPlacementOrder", "Yes")
        .check(css("input[name='selectedSiblingId']", "value").saveAs("SiblingID"))
        .check(substring("What sibling or half-sibling do you want to add a court order for?")))
    }
    .pause(ThinkTime)

    .group("AD_830_Sibling_Select") {
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

    .group("AD_840_Sibling_Type_2") {
      exec(http("Adoption Order Type")
        .post("/sibling/placement-order-type")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the order?")))
    }
    .pause(ThinkTime)

    .group("AD_850_Sibling_Number_2t") {
      exec(http("Adoption Order Number")
        .post("/sibling/placement-order-number")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Orders already in place for siblings and half-siblings")))
    }
    .pause(ThinkTime)

    .group("AD_855_Sibling_Remove") {
      exec(http("Adoption Remove Placement Order")
        .post("/sibling/remove-placement-order")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("confirm", "Yes")
        .check(substring("Orders already in place for siblings and half-siblings")))
    }
    .pause(ThinkTime)

    .group("AD_860_Sibling_Summary") {
      exec(http("Adoption Order Summary")
        .post("/sibling/summary")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("addAnotherSiblingPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)

  val adoptionFamilyCourt =

    group("AD_861_Family_Court") {
      exec(http("Adoption Family Court Redirect")
        .get("/children/find-family-court")
        .headers(CommonHeader.get_header)
        .check(substring("Choose a family court")))
    }
    .pause(ThinkTime)

    .group("AD_862_Family_Court_POST") {
      exec(http("Adoption Family Court Redirect")
        .post("/children/find-family-court")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("findFamilyCourt", "Yes")
        .formParam("familyCourtName", "")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


}