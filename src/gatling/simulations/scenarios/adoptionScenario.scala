package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Common, Environment, Headers, CsrfCheck}

object adoptionScenario {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val PaymentURL = Environment.paymentUrl
  val ThinkTime = Environment.thinkTime


  val adoptionHomepage =

    exec(_.setAll(
      "agencyName" -> ("Agent" + Common.randomString(5)),
      "agencyNumber" -> ("0" + Common.randomNumber(9)),
      "agencyContactName" -> ("Agency" + Common.randomString(5)),
      "agencyEmail" -> ("Agency" + Common.randomString(5)+"@gmail.com"),
      "dateChildMovedIn-day" -> Common.getDay(),
      "dateChildMovedIn-month" -> Common.getMonth(),
      "dateChildMovedIn-year" -> Common.getDobYearChild(),
      "SiblingFirstName" -> (Common.randomString(5)),
      "SiblingLastName" -> (Common.randomString(5)),
      "SocialWorkerName" -> (Common.randomString(5)),
      "SocialWorkerPhoneNumber" -> ("0" + Common.randomNumber(9)),
      "SocialWorkerEmail" -> ("Social" + Common.randomString(5)+"@gmail.com"),
      "socialWorkerTeamEmail" -> ("SocialTeam" + Common.randomString(5)+"@gmail.com"),
      "placementOrderNumber" -> (Common.randomString(5)),
      "placementOrderCourt" -> (Common.randomString(5)),
      "placementOrderDate-day" -> Common.getDay(),
      "placementOrderDate-month" -> Common.getMonth(),
      "placementOrderDate-year" -> Common.getDobYearChild(),
      "placementOrderType" -> (Common.randomString(5)),
      "FirstName" -> (Common.randomString(5)),
      "LastName" -> (Common.randomString(5)),
      "Email" -> (Common.randomString(5)+"@gmail.com"),
      "PhoneNumber" -> ("0" + Common.randomNumber(9)),
      "PostCode" -> ("PO16 7GZ"),
      "AddressLine1" -> (Common.randomString(9)),
      "AddressLine2" -> (Common.randomString(9)),
      "Town" -> (Common.randomString(5)),
      "County" -> (Common.randomString(5)),
      "BirthDay" -> Common.getDay(),
      "BirthMonth" -> Common.getMonth(),
      "BirthYear" -> Common.getDobYear(),
      "Nationality" -> (Common.randomString(5)),
      "Occupation" -> (Common.randomString(5)),
      "City" -> (Common.randomString(5)),
      "ChildDay" -> Common.getDay(),
      "ChildMonth" -> Common.getMonth(),
      "ChildYear" -> Common.getDobYearChild()
    ))


    .group("AD_010_Homepage") {
      exec(http("Adoption Homepage")
        .get(BaseURL)
        .headers(Headers.navigationHeader)
        .check(CsrfCheck.save)
        .check(substring("Sign in")))
    }
    .pause(ThinkTime)


  val adoptionLogin =

    group("AD_020_Login") {
      exec(http("Adoption Login")
        .post(IdamURL + "/login?client_id=adoption-web&response_type=code&redirect_uri=" + BaseURL +"/receiver")
        .headers(Headers.commonHeader)
        .formParam("username", "${emailAddress}")
        .formParam("password", "${password}")
        .formParam("save", "Sign in")
        .formParam("selfRegistrationEnabled", "true")
        .formParam("_csrf", "${csrfToken}")
        .check(CsrfCheck.save)
        .check(substring("Are you applying on your own, or with someone else?")))

    }
    .pause(ThinkTime)



  val adoptionApplyingWith =

    group("AD_040_Applying_Post") {
      exec(http("Adoption Applying Post")
        .post(BaseURL + "/applying-with")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applyingWith", "withSpouseOrCivilPartner")
        .formParam("otherApplicantRelation", "")
        .check(substring("Apply to adopt a child placed in your care")))

    }
    .pause(ThinkTime)


  val adoptionDateOfMove =

    group("AD_060_Date_Of_Move") {
      exec(http("Adoption Date Child Moved in ")
        .get(BaseURL + "/date-child-moved-in")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("When did the child move in with you?")))
    }
    .pause(ThinkTime)


    .group("AD_070_Date_Of_Move_POST") {
      exec(http("Adoption Date Child Moved in Post")
        .post(BaseURL + "/date-child-moved-in")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn-day", "${dateChildMovedIn-day}")
        .formParam("dateChildMovedIn-month", "${dateChildMovedIn-month}")
        .formParam("dateChildMovedIn-year", "${dateChildMovedIn-year}")
        .check(substring("Apply to adopt a child placed in your care")))
    }

  val adoptionAgency =

    group("AD_710_Agency") {
      exec(http("Adoption Agency")
        .get(BaseURL + "/children/adoption-agency?change=1646062432902")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    .group("AD_720_Agency_Details") {
      exec(http("Adoption Agency Details")
        .post(BaseURL + "/children/adoption-agency")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("adopAgencyOrLaName", "${agencyName}")
        .formParam("adopAgencyOrLaPhoneNumber", "${agencyNumber}")
        .formParam("adopAgencyOrLaContactName", "${agencyContactName}")
        .formParam("adopAgencyOrLaContactEmail", "${agencyEmail}")
        .check(CsrfCheck.save)
        .check(substring("Was there another adoption agency or local authority involved in placing the child?")))
    }
    .pause(ThinkTime)

    .group("AD_730_Other_Agency") {
      exec(http("Adoption Other Agency?")
        .post("/children/other-adoption-agency")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasAnotherAdopAgencyOrLA", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    .group("AD_740_Other_Agency_Details") {
      exec(http("Adoption Other Agency Details")
        .post("/children/adoption-agency")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("adopAgencyOrLaName", "${agencyName}")
        .formParam("adopAgencyOrLaPhoneNumber", "${agencyNumber}")
        .formParam("adopAgencyOrLaContactName", "${agencyContactName}")
        .formParam("adopAgencyOrLaContactEmail", "${agencyEmail}")
        .check(CsrfCheck.save)
        .check(substring("Details about the"))
        .check(substring("social worker")))
    }
    .pause(ThinkTime)

    .group("AD_750_Social_Worker_Details") {
      exec(http("Adoption Social Worker Details")
        .post("/children/social-worker")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("socialWorkerName", "${SocialWorkerName}")
        .formParam("socialWorkerPhoneNumber", "${SocialWorkerPhoneNumber}")
        .formParam("socialWorkerEmail", "${SocialWorkerEmail}")
        .formParam("socialWorkerTeamEmail", "${socialWorkerTeamEmail}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionYourDetails =
    group("AD_080_Your_Details") {
      exec(http("Adoption Your Personal Details ")
        .get(BaseURL + "/applicant1/full-name")
        .headers(Headers.commonHeader)
        .check(substring("your full name?"))
        .check(substring("First applicant"))
        .check(css("input[name='_csrf']", "value").saveAs("csrfToken"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_090_Your_Details_POST") {
      exec(http("Adoption Your Personal Details Post")
        .post(BaseURL + "/applicant1/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1FirstNames", "${FirstName}")
        .formParam("applicant1LastNames", "${LastName}")
        .check(substring("Have you ever legally been known by any other names?"))
        .check(CsrfCheck.save))
      }
      .pause(ThinkTime)


    .group("AD_100_Your_Details_Other_Name") {
      exec(http("Adoption Your Personal Details Other Name")
        .post(BaseURL + "/applicant1/other-names")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1HasOtherNames", "Yes")
        .formParam("applicant1OtherFirstNames", "${FirstName}")
        .formParam("applicant1OtherLastNames", "${LastName}")
        .formParam("addButton", "addButton")
        .check(substring("${FirstName}"))
        .check(substring("Have you ever legally been known by any other names?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_110_Your_Details_DoB") {
      exec(http("Adoption Your Personal Details DoB")
        .post(BaseURL + "/applicant1/other-names")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1HasOtherNames", "Yes")
        .formParam("addAnotherNameHidden", "")
        .formParam("applicant1OtherFirstNames", "")
        .formParam("applicant1OtherLastNames", "")
        .check(substring("your date of birth?"))
        .check(substring("First applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_120_Your_Details_DoB_POST") {
      exec(http("Adoption Your Personal Details DoB POST")
        .post(BaseURL + "/applicant1/dob")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1DateOfBirth-day", "${BirthDay}")
        .formParam("applicant1DateOfBirth-month", "${BirthMonth}")
        .formParam("applicant1DateOfBirth-year", "${BirthYear}")
        .check(substring("your occupation?"))
        .check(substring("First applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_130_Your_Details_Occupation") {
      exec(http("Adoption Your Personal Details Occupation")
        .post(BaseURL + "/applicant1/occupation")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1Occupation", "${Occupation}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionYourContact =
    group("AD_140_Your_Contact") {

   //   exec(Common.postcodeLookup)
      exec(http("Adoption Your Contact Details LookUp")
        .get(BaseURL + "/applicant1/address/lookup")
        .headers(Headers.commonHeader)
        .check(substring("your home address"))
        .check(substring("First applicant"))
        .check(CsrfCheck.save))


    }
    .pause(ThinkTime)

    .group("AD_150_Your_Contact_Manual") {
      exec(http("Adoption Your Contact Details Manual")
        .get(BaseURL + "/applicant1/address/manual")
        .headers(Headers.commonHeader)
        .check(substring("your address"))
        .check(substring("Building and street"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_160_Your_Contact_POST") {
      exec(http("Adoption Your Contact Details Manual POST")
        .post(BaseURL + "/applicant1/address/manual")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1Address1", "${AddressLine1}")
        .formParam("applicant1Address2", "${AddressLine2}")
        .formParam("applicant1AddressTown", "${Town}")
        .formParam("applicant1AddressCounty", "${County}")
        .formParam("applicant1AddressPostcode", "${PostCode}")
        .check(substring("What are your contact details?"))
        .check(substring("First applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_170_Your_Contact_POST") {
      exec(http("Adoption Your Contact Details POST")
        .post(BaseURL + "/applicant1/contact-details")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1EmailAddress", "${Email}")
        .formParam("applicant1PhoneNumber", "${PhoneNumber}")
        .formParam("applicant1ContactDetailsConsent", "Yes")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionSecondPersonal =
    group("AD_180_Second_Person") {
      exec(http("Adoption Second Personal Details")
        .get(BaseURL + "/applicant2/full-name")
        .headers(Headers.commonHeader)
        .check(substring("your full name?"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_190_Second_PersonPOST") {
      exec(http("Adoption Second Personal Details POST")
        .post(BaseURL + "/applicant2/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2FirstNames", "${FirstName}")
        .formParam("applicant2LastNames", "${LastName}")
        .check(substring("Have you ever legally been known by any other names?"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_200_Second_Other_Name") {
      exec(http("Adoption Second Other Name")
        .post(BaseURL + "/applicant2/other-names")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2HasOtherNames", "Yes")
        .formParam("applicant2OtherFirstNames", "${FirstName}")
        .formParam("applicant2OtherLastNames", "${LastName}")
        .formParam("addButton", "addButton")
        .check(substring("${FirstName}"))
        .check(substring("${LastName}"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_210_Second_Other_Name_Redirect") {
      exec(http("Adoption Second Other Name Redirect")
        .post(BaseURL + "/applicant2/other-names")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2HasOtherNames", "Yes")
        .formParam("addAnotherNameHidden", "")
        .formParam("applicant2OtherFirstNames", "${FirstName}")
        .formParam("applicant2OtherLastNames", "${LastName}")
        .check(substring("your date of birth?"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_220_Second_DoB") {
      exec(http("Adoption Second DoB")
        .post(BaseURL + "/applicant2/dob")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2DateOfBirth-day", "${BirthDay}")
        .formParam("applicant2DateOfBirth-month", "${BirthMonth}")
        .formParam("applicant2DateOfBirth-year", "${BirthYear}")
        .check(substring("What's your occupation?"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_230_Second_Occupation") {
      exec(http("Adoption Second Occupation")
        .post(BaseURL + "/applicant2/occupation")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2Occupation", "${Occupation}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)



  val adoptionSecondContact =
    group("AD_240_Second_Contact_Same") {
      exec(http("Adoption Second Contact Details Same-Address")
        .get(BaseURL + "/applicant2/same-address")
        .headers(Headers.commonHeader)
        .check(substring("Do you also live at this address?"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_250_Second_Contact_Same_POST") {
      exec(http("Adoption Second Contact Details Same-Address POST")
        .post(BaseURL + "/applicant2/same-address")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2AddressSameAsApplicant1", "No")
        .check(substring("your home address?"))
        .check(substring("Second applicant")))
    }
    .pause(ThinkTime)

    .group("AD_260_Second_Contact_Manual_Redirect") {
      exec(http("Adoption Second Contact Details manual redirect")
        .get(BaseURL + "/applicant2/address/manual")
        .headers(Headers.commonHeader)
        .check(substring("Building and street"))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
        .pause(ThinkTime)

    .group("AD_270_Second_Contact_Manual_POST") {
      exec(http("Adoption Second Contact Details manual POST")
        .post(BaseURL + "/applicant2/address/manual")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2Address1", "${AddressLine1}")
        .formParam("applicant2Address2", "${AddressLine2}")
        .formParam("applicant2AddressTown", "${Town}")
        .formParam("applicant2AddressCounty", "${County}")
        .formParam("applicant2AddressPostcode", "${PostCode}")
        .check(substring("We need both a contact email and telephone number for you."))
        .check(substring("Second applicant"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_280_Second_Contact_POST") {
      exec(http("Adoption Second Contact Details POST")
        .post(BaseURL + "/applicant2/contact-details")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2EmailAddress", "${Email}")
        .formParam("applicant2PhoneNumber", "${PhoneNumber}")
        .formParam("applicant2ContactDetailsConsent", "Yes")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionBirthCertificate =

    group("AD_290_Birth_Certificate") {
      exec(http("Adoption Birth Certificate Details")
        .get("/children/full-name")
        .headers(Headers.commonHeader)
        .check(substring("What is the child"))
        .check(substring("full name?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_300_Birth_Name") {
      exec(http("Adoption Birth Certificate Name")
        .post("/children/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstName", "${FirstName}")
        .formParam("childrenLastName", "${LastName}")
        .check(substring("What is the child"))
        .check(substring("date of birth?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_320_Birth_DoB") {
      exec(http("Adoption Birth Certificate DoB")
        .post(BaseURL + "/children/date-of-birth")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenDateOfBirth-day", "${ChildDay}")
        .formParam("childrenDateOfBirth-month", "${ChildMonth}")
        .formParam("childrenDateOfBirth-year", "${ChildYear}")
        .check(substring("What was the child"))
        .check(substring("sex at birth?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_330_Birth_Sex") {
      exec(http("Adoption Birth Certificate Sex")
        .post("/children/sex-at-birth")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenSexAtBirth", "male")
        .check(substring("What is their nationality?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_340_Birth_Nationality_Add") {
      exec(http("Adoption Birth Certificate Nationality Add")
        .post("/children/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .check(substring("${Nationality}"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_350_Birth_Nationality_POST") {
      exec(http("Adoption Birth Certificate Nationality POST")
        .post("/children/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .headers(Headers.commonHeader)
        .check(substring("What will the child"))
        .check(substring("full name be after adoption?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_370_Certificate_Name") {
      exec(http("Adoption Certificate Details Name")
        .post("/children/full-name-after-adoption")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstNameAfterAdoption", "${FirstName}")
        .formParam("childrenLastNameAfterAdoption", "${LastName}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionPlacementOrder =

    group("AD_380_Placement_Order") {
      exec(http("Adoption Placement Order Details")
        .get("/children/placement-order-number")
        .headers(Headers.commonHeader)
        .check(substring("What is the serial or case number on the placement order"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_390_Placement_Number") {
      exec(http("Adoption Placement Order Number")
        .post("/children/placement-order-number")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Which court made the placement order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_400_Placement_Court") {
      exec(http("Adoption Placement Court")
        .post("/children/placement-order-court")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderCourt", "${placementOrderCourt}")
        .check(substring("What date is on the placement order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_410_Placement_Date") {
      exec(http("Adoption Placement Date")
        .post("/children/placement-order-date")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderDate-day", "${placementOrderDate-day}")
        .formParam("placementOrderDate-month", "${placementOrderDate-month}")
        .formParam("placementOrderDate-year", "${placementOrderDate-year}")
        .check(substring("Do you want to add another order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_420_Placement_Summary") {
      exec(http("Adoption Placement Summary")
        .post("/children/placement-order-summary")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherPlacementOrder", "Yes")
        .check(substring("What type of order is it?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_430_Placement_Type_2") {
      exec(http("Adoption Placement Type 2")
        .post("/children/placement-order-type")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the placement order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_440_Placement_Number_2") {
      exec(http("Adoption Placement Order Number 2")
        .post("/children/placement-order-number")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Which court made the placement order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_450_Placement_Court_2") {
      exec(http("Adoption Placement Court 2")
        .post("/children/placement-order-court")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderCourt", "${placementOrderCourt}")
        .check(substring("What date is on the placement order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_460_Placement_Date_2") {
      exec(http("Adoption Placement Date 2")
        .post("/children/placement-order-date")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderDate-day", "${placementOrderDate-day}")
        .formParam("placementOrderDate-month", "${placementOrderDate-month}")
        .formParam("placementOrderDate-year", "${placementOrderDate-year}")
        .check(substring("Do you want to add another order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_470_Placement_Summary_2") {
      exec(http("Adoption Placement Summary 2")
        .post("/children/placement-order-summary")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)


  val adoptionBirthMother =

    group("AD_480_Birth_Mother") {
      exec(http("Adoption Mother's Details")
        .get("/birth-mother/full-name")
        .headers(Headers.commonHeader)
        .check(substring("What is the full name of the child"))
        .check(substring("birth mother?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_490_Mother_Name") {
      exec(http("Adoption Mother's Name")
        .post("/birth-mother/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherFirstNames", "${FirstName}")
        .formParam("birthMotherLastNames", "${LastName}")
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_500_Mother_Alive") {
      exec(http("Adoption Mother Still Alive?")
        .post("/birth-mother/still-alive")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherStillAlive", "Yes")
        .formParam("birthMotherNotAliveReason", "")
        .check(substring("What is the nationality of the child"))
        .check(substring("birth mother?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_510_Mother_Nationality_Add") {
      exec(http("Adoption Mother's Nationality Add")
        .post("/birth-mother/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .check(substring("${Nationality}"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_520_Mother_Nationality_POST") {
      exec(http("Adoption Mother's Nationality")
        .post("/birth-mother/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "British")
        .formParam("birthMotherNationality", "Irish")
        .formParam("birthMotherNationality", "Other")
        .formParam("addAnotherNationality", "")
        .check(substring("What is the occupation of the child's birth mother?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_530_Mother_Occupation") {
      exec(http("Adoption Mother's Occupation")
        .post("/birth-mother/occupation")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherOccupation", "${Occupation}")
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_540_Mother_Occupation") {
      exec(http("Adoption Mother's Address Known")
        .post("/birth-mother/address-known")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .headers(Headers.commonHeader)
        .check(substring("What is the birth mother"))
        .check(substring("last known address?"))
        .check(substring("Building and street"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_560_Mother_Address_POST") {
      exec(http("Adoption Mother's Address")
        .post("/birth-mother/address/manual")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .headers(Headers.commonHeader)
        .check(substring("Is the birth father"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_580_Father_Details_POST") {
      exec(http("Adoption Father's Details On Certificate")
        .post("/birth-father/name-on-certificate")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherNameOnCertificate", "Yes")
        .check(substring("What is the full name of the child"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_590_Father_Name") {
      exec(http("Adoption Father's Name")
        .post("/birth-father/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherFirstNames", "${FirstName}")
        .formParam("birthFatherLastNames", "${LastName}")
        .check(substring("birth father still alive?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_600_Father_Alive") {
      exec(http("Adoption Father Still alive?")
        .post("/birth-father/alive")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherStillAlive", "Yes")
        .formParam("birthFatherUnsureAliveReason", "")
        .check(substring("What is the nationality of the"))
        .check(substring("birth father?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_610_Father_Nationality_Add") {
      exec(http("Adoption Father add Nationality")
        .post("/birth-father/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .check(substring("${Nationality}"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_620_Father_Nationality_POST") {
      exec(http("Adoption Father's Nationality")
        .post("/birth-father/nationality")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "British")
        .formParam("birthFatherNationality", "Irish")
        .formParam("birthFatherNationality", "Other")
        .formParam("addAnotherNationality", "")
        .check(substring("What is the occupation of the"))
        .check(substring("birth father?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_630_Father_Occupation") {
      exec(http("Adoption Father's Occupation")
        .post("/birth-father/occupation")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherOccupation", "${Occupation}")
        .check(substring("Do you have the birth father"))
        .check(substring("last known address?"))
        .check(CsrfCheck.save))

    }
    .pause(ThinkTime)

    .group("AD_640_Father_Address_Known") {
      exec(http("Adoption Father's Address Known?")
        .post("/birth-father/address-known")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherAddressKnown", "Yes")
        .formParam("birthFatherAddressNotKnownReason", "")
        .check(substring("What is the birth father"))
        .check(substring("last known address?")))
    }
    .pause(ThinkTime)

    .group("AD_641_Father_Address_Manual") {
      exec(http("Adoption Father's Address Manual")
        .get("/birth-father/address/manual")
        .headers(Headers.commonHeader)
        .check(substring("What is the birth father"))
        .check(substring("Building and street"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_642_Father_Address_POST") {
      exec(http("Adoption Father's Address")
        .post("/birth-father/address/manual")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
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
        .headers(Headers.commonHeader)
        .check(substring("Is there another person who has parental responsibility for the child?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_660_Other_Exists_POST") {
      exec(http("Adoption Other Parent Exists")
        .post("/other-parent/exists")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentExists", "Yes")
        .check(substring("What is the full name of the other person with parental responsibility?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_670_Other_Parent_Name") {
      exec(http("Adoption Other Parent Name")
        .post("/other-parent/full-name")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentFirstNames", "${FirstName}")
        .formParam("otherParentLastNames", "${LastName}")
        .check(substring("Do you have the address of the other person with parental responsibility for the child?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_680_Other_Parent_Address_Known") {
      exec(http("Adoption Other Parent Address known?")
        .post("/other-parent/address-known")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentAddressKnown", "Yes")
        .formParam("otherParentAddressNotKnownReason", "")
        .check(substring("Other parent"))
        .check(substring("their address?")))
    }
    .pause(ThinkTime)

    .group("AD_690_Other_Parent_Address_Manual") {
      exec(http("Adoption Other Parent Address Manual")
        .get("/other-parent/address/manual")
        .headers(Headers.commonHeader)
        .check(substring("Other parent"))
        .check(substring("Building and street"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_700_Other_Parent_Address_POST") {
      exec(http("Adoption Other Parent Address Manual")
        .post("/other-parent/address/manual")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentAddress1", "${AddressLine1}")
        .formParam("otherParentAddress2", "${AddressLine2}")
        .formParam("otherParentAddressTown", "${Town}")
        .formParam("otherParentAddressCounty", "${County}")
        .formParam("otherParentAddressPostcode", "${PostCode}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)

  val adoptionSiblingDetails =

    group("AD_760_Sibling_Details") {
      exec(http("Adoption Sibling Details")
        .get("/sibling/exists")
        .headers(Headers.commonHeader)
        .check(substring("Does the child have any siblings or half siblings?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_770_Sibling_Other") {
      exec(http("Adoption Any Sibling?")
        .post("/sibling/exists")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasSiblings", "Yes")
        .check(substring("Is there a court order in place for any of the child"))
        .check(substring("siblings or half siblings?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_780_Sibling_Order_Exists") {
      exec(http("Adoption Court Order Exists?")
        .post("/sibling/court-order-exists")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasPoForSiblings", "Yes")
        .check(substring("Which siblings or half siblings have a court order in place?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_790_Sibling_Name") {
      exec(http("Adoption Sibling Name")
        .post("/sibling/select-sibling")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("selectedSiblingId", "addAnotherSibling")
        .formParam("siblingFirstName", "Yay")
        .formParam("siblingLastNames", "Wow")
        .check(substring("What type of order is it?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_800_Sibling_Type") {
      exec(http("Adoption Order Type?")
        .post("/sibling/placement-order-type")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_810_Sibling_Number") {
      exec(http("Adoption Order Number")
        .post("/sibling/placement-order-number")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Orders already in place for siblings and half-siblings"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_820_Sibling_New_Order") {
      exec(http("Adoption Add New Order?")
        .post("/sibling/summary")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherSiblingPlacementOrder", "Yes")
        .check(css("input[name='selectedSiblingId']", "value").saveAs("SiblingID"))
        .check(substring("What sibling or half-sibling do you want to add a court order for?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_830_Sibling_Select") {
      exec(http("Adoption Select Sibling")
        .post("/sibling/select-sibling")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("selectedSiblingId", "${SiblingID}")
        .formParam("siblingFirstName", "${SiblingFirstName}")
        .formParam("siblingLastNames", "${SiblingLastName}")
        .check(substring("What type of order is it?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_840_Sibling_Type_2") {
      exec(http("Adoption Order Type")
        .post("/sibling/placement-order-type")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", "${placementOrderType}")
        .check(substring("What is the serial or case number on the order?"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_850_Sibling_Number_2t") {
      exec(http("Adoption Order Number")
        .post("/sibling/placement-order-number")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", "${placementOrderNumber}")
        .check(substring("Orders already in place for siblings and half-siblings"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_855_Sibling_Remove") {
      exec(http("Adoption Remove Placement Order")
        .post("/sibling/remove-placement-order")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("confirm", "Yes")
        .check(substring("Orders already in place for siblings and half-siblings"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_860_Sibling_Summary") {
      exec(http("Adoption Order Summary")
        .post("/sibling/summary")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherSiblingPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)

  val adoptionFamilyCourt =

    group("AD_861_Family_Court") {
      exec(http("Adoption Family Court Redirect")
        .get("/children/find-family-court")
        .headers(Headers.commonHeader)
        .check(substring("Choose a family court"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_862_Family_Court_POST") {
      exec(http("Adoption Family Court Redirect")
        .post("/children/find-family-court")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("findFamilyCourt", "Yes")
        .formParam("familyCourtName", "")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)

  val adoptionUploadDocuments =

    group("AD_870_Upload_Document") {
      exec(http("Adoption Birth Certificate Details")
        .get(BaseURL + "/upload-your-documents")
        .headers(Headers.commonHeader)
        .check(substring("Upload the child"))
        .check(substring("documents"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)


    .group("AD_880_Upload_Document") {
      exec(http("Adoption Certificate Upload")
        .post(BaseURL + "/document-manager?_csrf=${csrfToken}")
        .headers(Headers.commonHeader)
        .header("accept", "application/json")
        .header("content-type", "multipart/form-data")
        .header("sec-fetch-dest", "empty")
        .header("sec-fetch-mode", "cors")
        .bodyPart(RawFileBodyPart("files[]", "2MB.pdf")
          .fileName("2MB.pdf")
          .transferEncoding("binary"))
        .asMultipartForm
        .check(jsonPath("$[0].id").saveAs("Document_ID"))
        .check(substring("2MB.pdf")))
    }
    .pause(ThinkTime)


    .group("AD_885_Upload_Document_POST") {
      exec(http("Adoption Certificate Upload POST")
        .post(BaseURL + "/upload-your-documents")
        .headers(Headers.commonHeader)
        .header("content-type","application/x-www-form-urlencoded")
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1UploadedFiles", """[{"id":"${Document_ID}","name":"2MB.pdf"}]""")
        .formParam("applicant1CannotUpload", "")
        .formParam("applicant1CannotUpload", "checked")
        .formParam("applicant1CannotUploadDocuments", "")
        .formParam("applicant1CannotUploadDocuments", "")
        .formParam("applicant1CannotUploadDocuments", "birthOrAdoptionCertificate")
        .formParam("applicant1CannotUploadDocuments", "deathCertificate")
        .check(substring("Apply to adopt a child placed in your care")))
    }
    .pause(ThinkTime)



  val adoptionReview = {

    group("AD_905_Equality_Redirect") {
      exec(http("Adoption Review Equality")
        .get(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(Headers.commonHeader)
        .check(substring("Review your answers"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_910_Check_Your_Answers") {
      exec(http("Adoption Review Check Your Answers")
        .post(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn", "[object Object]")
        .check(substring("Statement of truth"))
        .check(CsrfCheck.save))
    }
    .pause(ThinkTime)

    .group("AD_920_Statement") {
      exec(http("Adoption Review Statement of Truth")
        .post(BaseURL + "/review-pay-submit/statement-of-truth")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1IBelieveApplicationIsTrue", "")
        .formParam("applicant1IBelieveApplicationIsTrue", "checked")
        .formParam("applicant2IBelieveApplicationIsTrue", "")
        .formParam("applicant2IBelieveApplicationIsTrue", "checked")
        .formParam("applicant1SotFullName", "${FirstName}")
        .formParam("applicant2SotFullName", "${LastName}")
        .check(css("input[name='chargeId']", "value").saveAs("chargeId")))

    }
    .pause(ThinkTime)


    .group("AD_940_Check_Card_Details") {
      exec(http("Adoption Pay by Card")
        .post(PaymentURL + "/check_card/${chargeId}")
        .headers(Headers.commonHeader)
        .header("accept", "*/*")
        .header("sec-fetch-dest", "empty")
        .header("sec-fetch-mode", "cors")
        .formParam("cardNo", "4444333322221111")
        .check(jsonPath("$.accepted").is("true")))

    }
    .pause(ThinkTime)

    .group("AD_950_Card_Details") {
      exec(http("Adoption Pay by Card")
        .post(PaymentURL + "/card_details/${chargeId}")
        .headers(Headers.commonHeader)
        .formParam("chargeId", "${chargeId}")
        .formParam("csrfToken", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("cardNo", "4444333322221111")
        .formParam("expiryMonth", "01")
        .formParam("expiryYear", "26")
        .formParam("cardholderName", "${FirstName}")
        .formParam("cvc", "123")
        .formParam("addressCountry", "GB")
        .formParam("addressLine1", "${AddressLine1}")
        .formParam("addressLine2", "${AddressLine2}")
        .formParam("addressCity", "${City}")
        .formParam("addressPostcode", "PR1 1RF")
        .formParam("email", "${Email}")
        .check(regex("Confirm your payment")))
    }
    .pause(ThinkTime)

    .group("AD_960_Application Submit") {
      exec(http("Adoption Application Submit")
        .post(PaymentURL + "/card_details/${chargeId}/confirm")
        .headers(Headers.commonHeader)
        .formParam("csrfToken", "${csrf}")
        .formParam("locale", "en")
        .formParam("chargeId", "${chargeId}")
        .check(substring("Application Submitted")))
    }
    .pause(ThinkTime)
  }

}
