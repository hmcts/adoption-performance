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

    group("AD_010_Homepage") {
      exec(http("Adoption Homepage")
        .get(BaseURL)
        .headers(Headers.navigationHeader)
        .check(CsrfCheck.save)
        .check(substring("Sign in")))
    }
    .pause(ThinkTime)


  val adoptionLogin =

    /*======================================================================================
    * Log In with a Citizen user
    ======================================================================================*/

    group("AD_020_Login") {
      exec(http("Adoption Login")
        .post(IdamURL + "/login?client_id=adoption-web&response_type=code&redirect_uri=" + BaseURL +"/receiver")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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

    /*======================================================================================
    * Choose withSpouseOrCivilPartner when asked who you're applying with
    ======================================================================================*/

    group("AD_030_Applying_Post") {
      exec(http("Adoption Applying Post")
        .post(BaseURL + "/applying-with")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applyingWith", "withSpouseOrCivilPartner")
        .formParam("otherApplicantRelation", "")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""add=(\d+)""").saveAs("adoptionAgency"))
        .check(regex("""id="applying-with-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionDateOfMove =

    /*======================================================================================
    * Click on 'Date child moved in with you'
    ======================================================================================*/

    group("AD_040_Date_Of_Move") {
      exec(http("Adoption Date Child Moved in ")
        .get(BaseURL + "/date-child-moved-in")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("When did the child move in with you?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Choose a date that is later than the Child's Birthday
    ======================================================================================*/

    .group("AD_050_Date_Of_Move_POST") {
      exec(http("Adoption Date Child Moved in Post")
        .post(BaseURL + "/date-child-moved-in")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn-day", Common.getDay())
        .formParam("dateChildMovedIn-month", Common.getMonth())
        .formParam("dateChildMovedIn-year", Common.yearMinusOne())
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="date-child-moved-in-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)

  val adoptionAgency =

  /*======================================================================================
  * Click on 'Adoption agency and social worker'
  ======================================================================================*/

    group("AD_060_Agency") {
      exec(http("Adoption Agency")
        .get(BaseURL + "/children/adoption-agency?add=${adoptionAgency}")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Adoption agency or local authority details
    ======================================================================================*/

    .group("AD_070_Agency_Details") {
      exec(http("Adoption Agency Details")
        .post(BaseURL + "/children/adoption-agency")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("adopAgencyOrLaName", "Agent" + Common.randomString(5))
        .formParam("adopAgencyOrLaPhoneNumber", "07000000000")
        .formParam("adopAgencyOrLaContactName", "Agency" + Common.randomString(5))
        .formParam("adopAgencyOrLaContactEmail", "Agency" + Common.randomString(5)+"@gmail.com")
        .check(CsrfCheck.save)
        .check(substring("Was there another adoption agency or local authority involved in placing the child?")))
    }
    .pause(ThinkTime)

    /*=====================================================================================================
    * Answer 'Yes' to 'Was there another adoption agency or local authority involved in placing the child?'
    ======================================================================================================*/

    .group("AD_080_Other_Agency") {
      exec(http("Adoption Other Agency?")
        .post(BaseURL + "/children/other-adoption-agency")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasAnotherAdopAgencyOrLA", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Other Adoption agency or local authority details
    ======================================================================================*/

    .group("AD_090_Other_Agency_Details") {
      exec(http("Adoption Other Agency Details")
        .post(BaseURL + "/children/adoption-agency")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("adopAgencyOrLaName", "Agent" + Common.randomString(5))
        .formParam("adopAgencyOrLaPhoneNumber", "07000000000")
        .formParam("adopAgencyOrLaContactName", "Agency" + Common.randomString(5))
        .formParam("adopAgencyOrLaContactEmail", "Agency" + Common.randomString(5)+"@gmail.com")
        .check(CsrfCheck.save)
        .check(substring("Details about the child&#39;s social worker")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Adoption Social Worker Details
    ======================================================================================*/

    .group("AD_100_Social_Worker_Details") {
      exec(http("Adoption Social Worker Details")
        .post(BaseURL + "/children/social-worker")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("socialWorkerName", Common.randomString(5))
        .formParam("socialWorkerPhoneNumber", "07000000000")
        .formParam("socialWorkerEmail", "Social" + Common.randomString(5)+"@gmail.com")
        .formParam("socialWorkerTeamEmail", "SocialTeam" + Common.randomString(5)+"@gmail.com")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="adoption-agency-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionYourDetails =

    /*======================================================================================
    * Click on 'Your personal details'
    ======================================================================================*/

    group("AD_110_Your_Details") {
      exec(http("Adoption Your Personal Details ")
        .get(BaseURL + "/applicant1/full-name")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your full name?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input Applicant 1's First and Last Name
    ======================================================================================*/

    .group("AD_120_Your_Details_Full_Name") {
      exec(http("Adoption Your Personal Details Full Name")
        .post(BaseURL + "/applicant1/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1FirstNames", Common.randomString(5))
        .formParam("applicant1LastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Have you ever legally been known by any other names?")))
      }
      .pause(ThinkTime)

    /*======================================================================================
    * Input Applicant 1's Other Name and Press 'Add'
    ======================================================================================*/

    .group("AD_130_Your_Details_Other_Name") {
      exec(http("Adoption Your Personal Details Other Name")
        .post(BaseURL + "/applicant1/other-names")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1HasOtherNames", "Yes")
        .formParam("applicant1OtherFirstNames", Common.randomString(5))
        .formParam("applicant1OtherLastNames", Common.randomString(5))
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("Have you ever legally been known by any other names?")))

    }
    .pause(ThinkTime)

    /*======================================================================================
    * Submit the Other Name
    ======================================================================================*/

    .group("AD_140_Your_Details_DoB") {
      exec(http("Adoption Your Personal Details DoB")
        .post(BaseURL + "/applicant1/other-names")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1HasOtherNames", "Yes")
        .formParam("addAnotherNameHidden", "")
        .formParam("applicant1OtherFirstNames", "")
        .formParam("applicant1OtherLastNames", "")
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your date of birth?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter a Date of Birth that's above 18 years old
    ======================================================================================*/

    .group("AD_150_Your_Details_DoB_POST") {
      exec(http("Adoption Your Personal Details DoB POST")
        .post(BaseURL + "/applicant1/dob")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1DateOfBirth-day", Common.getDay())
        .formParam("applicant1DateOfBirth-month", Common.getMonth())
        .formParam("applicant1DateOfBirth-year", Common.getDobYear())
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your occupation?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter an Occupation
    ======================================================================================*/

    .group("AD_160_Your_Details_Occupation") {
      exec(http("Adoption Your Personal Details Occupation")
        .post(BaseURL + "/applicant1/occupation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1Occupation", Common.randomString(5))
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-personal-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionYourContact =

    /*======================================================================================
    * Click on 'Your contact details'
    ======================================================================================*/

    group("AD_170_Your_Contact") {
      exec(http("Adoption Your Contact Details LookUp")
        .get(BaseURL + "/applicant1/address/lookup")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your home address?")))

    }
    .pause(ThinkTime)

    /*======================================================================================
    * Postcode lookup for Applicant 1
    ======================================================================================*/

    .group("AD_180_Your_Contact_LookUp_Search") {
      exec(Common.postcodeLookup("applicant1", "applicant1"))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Select Address
    ======================================================================================*/

    .group("AD_190_Your_Contact_Select") {
      exec(http("Adoption Your Contact Details Select")
        .post(BaseURL + "/applicant1/address/select")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1SelectAddress", "${addressIndex}")
        .check(CsrfCheck.save)
        .check(substring("What are your contact details?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    *Input Email and Phone Number
    ======================================================================================*/

    .group("AD_200_Your_Contact_POST") {
      exec(http("Adoption Your Contact Details POST")
        .post(BaseURL + "/applicant1/contact-details")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1EmailAddress", Common.randomString(5)+"@gmail.com")
        .formParam("applicant1PhoneNumber", "07000000000")
        .formParam("applicant1ContactDetailsConsent", "Yes")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-contact-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionSecondPersonal =

    /*======================================================================================
    * Click on 'Your personal details' for the Second Applicant
    ======================================================================================*/

    group("AD_210_Second_Person") {
      exec(http("Adoption Second Personal Details")
        .get(BaseURL + "/applicant2/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your full name?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input Applicant 2's First and Last Name
    ======================================================================================*/

    .group("AD_220_Second_Person_POST") {
      exec(http("Adoption Second Personal Details POST")
        .post(BaseURL + "/applicant2/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2FirstNames", Common.randomString(5))
        .formParam("applicant2LastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Have you ever legally been known by any other names?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input Applicant 2's Other Name and Press 'Add'
    ======================================================================================*/

    .group("AD_230_Second_Other_Name") {
      exec(http("Adoption Second Other Name")
        .post(BaseURL + "/applicant2/other-names")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2HasOtherNames", "Yes")
        .formParam("applicant2OtherFirstNames", Common.randomString(5))
        .formParam("applicant2OtherLastNames", Common.randomString(5))
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("Have you ever legally been known by any other names?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Submit the Other Name
    ======================================================================================*/

    .group("AD_240_Second_Other_Name_Redirect") {
      exec(http("Adoption Second Other Name Redirect")
        .post(BaseURL + "/applicant2/other-names")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2HasOtherNames", "Yes")
        .formParam("addAnotherNameHidden", "")
        .formParam("applicant2OtherFirstNames", "")
        .formParam("applicant2OtherLastNames", "")
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your date of birth?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter a Date of Birth that's above 18 years old
    ======================================================================================*/

    .group("AD_250_Second_DoB") {
      exec(http("Adoption Second DoB")
        .post(BaseURL + "/applicant2/dob")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2DateOfBirth-day", Common.getDay())
        .formParam("applicant2DateOfBirth-month", Common.getMonth())
        .formParam("applicant2DateOfBirth-year", Common.getDobYear())
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your occupation?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter an Occupation
    ======================================================================================*/

    .group("AD_260_Second_Occupation") {
      exec(http("Adoption Second Occupation")
        .post(BaseURL + "/applicant2/occupation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2Occupation", Common.randomString(5))
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-contact-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)



  val adoptionSecondContact =

    /*======================================================================================
    * Click on 'Your contact details for the Second Applicant'
    ======================================================================================*/

    group("AD_270_Second_Contact_Same") {
      exec(http("Adoption Second Contact Details Same-Address")
        .get(BaseURL + "/applicant2/same-address")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Do you also live at this address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on 'No'
    ======================================================================================*/

    .group("AD_280_Second_Contact_Same_POST") {
      exec(http("Adoption Second Contact Details Same-Address POST")
        .post(BaseURL + "/applicant2/same-address")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2AddressSameAsApplicant1", "No")
        .check(substring("your home address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Postcode lookup for Applicant 2
    ======================================================================================*/

    .group("AD_290_Second_Contact_LookUp_Search") {
        exec(Common.postcodeLookup("applicant2", "applicant2"))
    }
        .pause(ThinkTime)

    /*======================================================================================
    * Select Address for Applicant 2
    ======================================================================================*/

    .group("AD_300_Second_Contact_Select") {
      exec(http("Adoption Second Contact Details Select")
        .post(BaseURL + "/applicant2/address/select")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2SelectAddress", "${addressIndex}")
        .check(CsrfCheck.save)
        .check(substring("We need both a contact email and telephone number for you.")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Email Address and Phone Number for Applicant 2
    ======================================================================================*/

    .group("AD_310_Second_Contact_POST") {
      exec(http("Adoption Second Contact Details POST")
        .post(BaseURL + "/applicant2/contact-details")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2EmailAddress", Common.randomString(5)+"@gmail.com")
        .formParam("applicant2PhoneNumber", "07000000000")
        .formParam("applicant2ContactDetailsConsent", "Yes")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant2-personal-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionBirthCertificate =

    /*======================================================================================
    * Click on 'Birth certificate details'
    ======================================================================================*/

    group("AD_320_Birth_Certificate") {
      exec(http("Adoption Birth Certificate Details")
        .get(BaseURL + "/children/full-name")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("What is the child&#39;s full name?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the First and Last Name of the Child
    ======================================================================================*/

    .group("AD_330_Birth_Name") {
      exec(http("Adoption Birth Certificate Name")
        .post(BaseURL + "/children/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstName", Common.randomString(5))
        .formParam("childrenLastName", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What is the child&#39;s date of birth?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Choose a DoB that is under the age of 18
    ======================================================================================*/

    .group("AD_340_Birth_DoB") {
      exec(http("Adoption Birth Certificate DoB")
        .post(BaseURL + "/children/date-of-birth")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenDateOfBirth-day", Common.getDay())
        .formParam("childrenDateOfBirth-month", Common.getMonth())
        .formParam("childrenDateOfBirth-year", Common.getDobYearChild())
        .check(CsrfCheck.save)
        .check(substring("What was the child&#39;s sex at birth?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Choose the Child's Sex at Birth
    ======================================================================================*/

    .group("AD_350_Birth_Sex") {
      exec(http("Adoption Birth Certificate Sex")
        .post(BaseURL + "/children/sex-at-birth")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenSexAtBirth", "male")
        .check(CsrfCheck.save)
        .check(substring("What is their nationality?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on all 3 Nationalities and then add a Separate Nationality
    ======================================================================================*/

    .group("AD_360_Birth_Nationality_Add") {
      exec(http("Adoption Birth Certificate Nationality Add")
        .post(BaseURL + "/children/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenNationality", "")
        .formParam("childrenNationality", "")
        .formParam("childrenNationality", "")
        .formParam("childrenNationality", "")
        .formParam("childrenNationality", "British")
        .formParam("childrenNationality", "Irish")
        .formParam("childrenNationality", "Other")
        .formParam("addAnotherNationality", Common.randomString(5))
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("What is their nationality?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Submit the Child's Nationality
    ======================================================================================*/

    .group("AD_370_Birth_Nationality_POST") {
      exec(http("Adoption Birth Certificate Nationality POST")
        .post(BaseURL + "/children/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="children-birth-certificate-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionCertificateDetails =

    /*======================================================================================
    * Click on 'Child's name after adoption'
    ======================================================================================*/

    group("AD_380_Certificate_Details") {
      exec(http("Adoption Certificate Details")
        .get(BaseURL + "/children/full-name-after-adoption")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("What will the child&#39;s full name be after adoption?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Name of the Child
    ======================================================================================*/

    .group("AD_390_Certificate_Name") {
      exec(http("Adoption Certificate Details Name")
        .post(BaseURL + "/children/full-name-after-adoption")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstNameAfterAdoption", Common.randomString(5))
        .formParam("childrenLastNameAfterAdoption", Common.randomString(5))
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="adoption-certificate-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionPlacementOrder =

    /*======================================================================================
    * Click on 'Placement and court orders'
    ======================================================================================*/

    group("AD_400_Placement_Order") {
      exec(http("Adoption Placement Order Details")
        .get(BaseURL + "/children/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .check(CsrfCheck.save)
        .check(substring("What is the serial or case number on the placement order")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Number
    ======================================================================================*/

    .group("AD_410_Placement_Number") {
      exec(http("Adoption Placement Order Number")
        .post(BaseURL + "/children/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Which court made the placement order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Court
    ======================================================================================*/

    .group("AD_420_Placement_Court") {
      exec(http("Adoption Placement Court")
        .post(BaseURL + "/children/placement-order-court")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderCourt", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What date is on the placement order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Date (To be within One Year of the current Date)
    ======================================================================================*/

    .group("AD_430_Placement_Date") {
      exec(http("Adoption Placement Date")
        .post(BaseURL + "/children/placement-order-date")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderDate-day", Common.getDay())
        .formParam("placementOrderDate-month", Common.getMonth())
        .formParam("placementOrderDate-year", Common.yearMinusOne())
        .check(CsrfCheck.save)
        .check(substring("Do you want to add another order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on 'Yes' when asked "Do you want to add another order?"
    ======================================================================================*/

    .group("AD_440_Placement_Summary") {
      exec(http("Adoption Placement Summary")
        .post(BaseURL + "/children/placement-order-summary")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherPlacementOrder", "Yes")
        .check(CsrfCheck.save)
        .check(substring("What type of order is it?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Type
    ======================================================================================*/

    .group("AD_450_Placement_Type_2") {
      exec(http("Adoption Placement Type 2")
        .post(BaseURL + "/children/placement-order-type")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What is the serial or case number on the placement order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Number
    ======================================================================================*/

    .group("AD_460_Placement_Number_2") {
      exec(http("Adoption Placement Order Number 2")
        .post(BaseURL + "/children/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Which court made the placement order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Court
    ======================================================================================*/

    .group("AD_470_Placement_Court_2") {
      exec(http("Adoption Placement Court 2")
        .post(BaseURL + "/children/placement-order-court")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderCourt", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What date is on the placement order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Placement Order Date (Again, to be within one Year of current date)
    ======================================================================================*/

    .group("AD_480_Placement_Date_2") {
      exec(http("Adoption Placement Date 2")
        .post(BaseURL + "/children/placement-order-date")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderDate-day", Common.getDay())
        .formParam("placementOrderDate-month", Common.getMonth())
        .formParam("placementOrderDate-year", Common.yearMinusOne())
        .check(CsrfCheck.save)
        .check(substring("Do you want to add another order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on 'No' when asked "Do you want to add another order?"
    ======================================================================================*/

    .group("AD_490_Placement_Summary_2") {
      exec(http("Adoption Placement Summary 2")
        .post(BaseURL + "/children/placement-order-summary")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="children-placement-order-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionBirthMother =

    /*======================================================================================
    * Click on 'Birth mother details'
    ======================================================================================*/

    group("AD_500_Birth_Mother") {
      exec(http("Adoption Mother's Details")
        .get(BaseURL + "/birth-mother/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .check(CsrfCheck.save)
        .check(substring("What is the full name of the child&#39;s birth mother?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Mother's First and Last Name
    ======================================================================================*/

    .group("AD_510_Mother_Name") {
      exec(http("Adoption Mother's Name")
        .post(BaseURL + "/birth-mother/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherFirstNames", Common.randomString(5))
        .formParam("birthMotherLastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Is the child&#39;s birth mother still alive?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click 'Yes' when asked "Is the child's birth mother still alive?"
    ======================================================================================*/

    .group("AD_520_Mother_Alive") {
      exec(http("Adoption Mother Still Alive?")
        .post(BaseURL + "/birth-mother/still-alive")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherStillAlive", "Yes")
        .formParam("birthMotherNotAliveReason", "")
        .check(CsrfCheck.save)
        .check(substring("What is the nationality of the child&#39;s birth mother?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on all 3 Nationalities and then add a Separate Nationality
    ======================================================================================*/

    .group("AD_530_Mother_Nationality_Add") {
      exec(http("Adoption Mother's Nationality Add")
        .post(BaseURL + "/birth-mother/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "")
        .formParam("birthMotherNationality", "British")
        .formParam("birthMotherNationality", "Irish")
        .formParam("birthMotherNationality", "Other")
        .formParam("addAnotherNationality", Common.randomString(5))
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("What is the nationality of the child&#39;s birth mother?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Submit the Mother's Nationalities
    ======================================================================================*/

    .group("AD_540_Mother_Nationality_POST") {
      exec(http("Adoption Mother's Nationality")
        .post(BaseURL + "/birth-mother/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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
        .check(CsrfCheck.save)
        .check(substring("What is the occupation of the child&#39;s birth mother?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Mother's Occupation
    ======================================================================================*/

    .group("AD_550_Mother_Occupation") {
      exec(http("Adoption Mother's Occupation")
        .post(BaseURL + "/birth-mother/occupation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherOccupation", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Do you have the birth mother&#39;s last known address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Answer "Yes" for when asked "Do you have the birth mother's last known address?"
    ======================================================================================*/

    .group("AD_560_Mother_Address_Known") {
      exec(http("Adoption Mother's Address Known")
        .post(BaseURL + "/birth-mother/address-known")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherAddressKnown", "Yes")
        .formParam("birthMotherAddressNotKnownReason", "")
        .check(substring("What is the birth mother&#39;s last known address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Postcode lookup for Mother
    ======================================================================================*/

    .group("AD_570_Mother_Address_Lookup") {
      exec(Common.postcodeLookup("birth-mother", "birthMother"))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Select Mother Address
    ======================================================================================*/

    .group("AD_580_Mother_Address_Select") {
      exec(http("Adoption Mother's Select")
        .post(BaseURL + "/birth-mother/address/select")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthMotherSelectAddress", "${addressIndex}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="birth-mother-details-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionBirthFather =

    /*======================================================================================
    * Click on 'Birth father details'
    ======================================================================================*/

    group("AD_590_Father_Details") {
      exec(http("Adoption Father's Details")
        .get(BaseURL + "/birth-father/name-on-certificate")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Is the birth father&#39;s name on the birth certificate?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Answer 'Yes' to 'Is the birth father's name on the birth certificate?'
    ======================================================================================*/

    .group("AD_600_Father_Details_POST") {
      exec(http("Adoption Father's Details On Certificate")
        .post(BaseURL + "/birth-father/name-on-certificate")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherNameOnCertificate", "Yes")
        .check(CsrfCheck.save)
        .check(substring("What is the full name of the child&#39;s birth father?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Father's First and Last Name
    ======================================================================================*/

    .group("AD_610_Father_Name") {
      exec(http("Adoption Father's Name")
        .post(BaseURL + "/birth-father/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherFirstNames", Common.randomString(5))
        .formParam("birthFatherLastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Is the child&#39;s birth father still alive?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Answer 'Yes' when asked 'Is the child's birth father still alive?'
    ======================================================================================*/

    .group("AD_620_Father_Alive") {
      exec(http("Adoption Father Still alive?")
        .post(BaseURL + "/birth-father/alive")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherStillAlive", "Yes")
        .formParam("birthFatherUnsureAliveReason", "")
        .check(CsrfCheck.save)
        .check(substring("What is the nationality of the child&#39;s birth father?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click on all 3 Nationalities and then add a Separate Nationality
    ======================================================================================*/

    .group("AD_630_Father_Nationality_Add") {
      exec(http("Adoption Father add Nationality")
        .post(BaseURL + "/birth-father/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "")
        .formParam("birthFatherNationality", "British")
        .formParam("birthFatherNationality", "Irish")
        .formParam("birthFatherNationality", "Other")
        .formParam("addAnotherNationality", Common.randomString(5))
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("What is the nationality of the child&#39;s birth father?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Submit the Father's Nationalities
    ======================================================================================*/

    .group("AD_640_Father_Nationality_POST") {
      exec(http("Adoption Father's Nationality")
        .post(BaseURL + "/birth-father/nationality")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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
        .check(CsrfCheck.save)
        .check(substring("What is the occupation of the child&#39;s birth father?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the Father's Occupation
    ======================================================================================*/

    .group("AD_650_Father_Occupation") {
      exec(http("Adoption Father's Occupation")
        .post(BaseURL + "/birth-father/occupation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherOccupation", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Do you have the birth father’s last known address?")))

    }
    .pause(ThinkTime)

    /*======================================================================================
    * Answer "Yes" for when asked "Do you have the birth father's last known address?"
    ======================================================================================*/

    .group("AD_660_Father_Address_Known") {
      exec(http("Adoption Father's Address Known?")
        .post(BaseURL + "/birth-father/address-known")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherAddressKnown", "Yes")
        .formParam("birthFatherAddressNotKnownReason", "")
        .check(substring("What is the birth father&#39;s last known address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
     * Postcode lookup for Father
     ======================================================================================*/

    .group("AD_670_Father_Address_Lookup") {
      exec(Common.postcodeLookup("birth-father", "birthFather"))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Select Father's Address
    ======================================================================================*/

    .group("AD_680_Father_Address_Select") {
      exec(http("Adoption Father's Address Select")
        .post(BaseURL + "/birth-father/address/select")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("birthFatherSelectAddress", "${addressIndex}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="birth-father-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionOtherParent =

    /*======================================================================================
    * Click on 'Other person with parental responsibility'
    ======================================================================================*/

    group("AD_690_Other_Parent_Exists") {
      exec(http("Adoption Other Parent")
        .get(BaseURL + "/other-parent/exists")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Is there another person who has parental responsibility for the child?")))
    }
    .pause(ThinkTime)

    /*==================================================================================================
    * Answer 'Yes' when asked ' Is there another person who has parental responsibility for the child?'
    ===================================================================================================*/

    .group("AD_700_Other_Exists_POST") {
      exec(http("Adoption Other Parent Exists")
        .post(BaseURL + "/other-parent/exists")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentExists", "Yes")
        .check(CsrfCheck.save)
        .check(substring("What is the full name of the other person with parental responsibility?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input the First and Last name of the Other Parent
    ======================================================================================*/

    .group("AD_710_Other_Parent_Name") {
      exec(http("Adoption Other Parent Name")
        .post(BaseURL + "/other-parent/full-name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentFirstNames", Common.randomString(5))
        .formParam("otherParentLastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Do you have the address of the other person with parental responsibility for the child?")))
    }
    .pause(ThinkTime)

      /*===================================================================================================================
      * Answer 'Yes' when asked 'Do you have the address of the other person with parental responsibility for the child?'
      ====================================================================================================================*/

    .group("AD_720_Other_Parent_Address_Known") {
      exec(http("Adoption Other Parent Address known?")
        .post(BaseURL + "/other-parent/address-known")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentAddressKnown", "Yes")
        .formParam("otherParentAddressNotKnownReason", "")
        .check(substring("What&#39;s their address?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Postcode lookup for Other Parent
    ======================================================================================*/

    .group("AD_730_Other_Parent_Address_Lookup") {
      exec(Common.postcodeLookup("other-parent", "otherParent"))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Select The Other Parent's Address
    ======================================================================================*/

    .group("AD_740_Other_Parent_Address_Select") {
      exec(http("Adoption Other Parent Address Select")
        .post(BaseURL + "/other-parent/address/select")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("otherParentSelectAddress", "${addressIndex}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="other-parent-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)

  val adoptionSiblingDetails =

    /*======================================================================================
    * Click on 'Sibling court order details'
    ======================================================================================*/

    group("AD_750_Sibling_Details") {
      exec(http("Adoption Sibling Details")
        .get(BaseURL + "/sibling/exists")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Does the child have any siblings or half siblings?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click 'Yes' when asked 'Does the child have any siblings or half siblings?'
    ======================================================================================*/

    .group("AD_760_Sibling_Other") {
      exec(http("Adoption Any Sibling?")
        .post(BaseURL + "/sibling/exists")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasSiblings", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Is there a court order in place for any of the child&#39;s siblings or half siblings?")))
    }
    .pause(ThinkTime)

    /*===========================================================================================================
    * Click 'Yes' when asked 'Is there a court order in place for any of the child's siblings or half siblings?'
    ============================================================================================================*/

    .group("AD_770_Sibling_Order_Exists") {
      exec(http("Adoption Court Order Exists?")
        .post(BaseURL + "/sibling/court-order-exists")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("hasPoForSiblings", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Which siblings or half siblings have a court order in place?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter the first Sibling's First and Last name
    ======================================================================================*/

    .group("AD_780_Sibling_Name") {
      exec(http("Adoption Sibling Name")
        .post(BaseURL + "/sibling/name")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("siblingFirstName", Common.randomString(5))
        .formParam("siblingLastNames", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What type of order is it?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter the first Sibling's Placement Order Type
    ======================================================================================*/

    .group("AD_790_Sibling_Type") {
      exec(http("Adoption Order Type?")
        .post(BaseURL + "/sibling/placement-order-type")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What is the serial or case number on the order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter the first Sibling's Placement Order Number
    ======================================================================================*/

    .group("AD_800_Sibling_Number") {
      exec(http("Adoption Order Number")
        .post(BaseURL + "/sibling/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Orders already in place for siblings and half-siblings")))
    }
    .pause(ThinkTime)

    /*==========================================================================================
    * Select 'Yes' when asked 'Do you want to add another order for a sibling or half-sibling?'
    ===========================================================================================*/

    .group("AD_810_Sibling_New_Order") {
      exec(http("Adoption Add New Order?")
        .post(BaseURL + "/sibling/summary")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherSiblingPlacementOrder", "Yes")
        .check(css("input[name='selectedSiblingId']", "value").saveAs("SiblingID"))
        .check(CsrfCheck.save)
        .check(substring("What sibling or half-sibling do you want to add a court order for?")))
    }
    .pause(ThinkTime)

    /*==========================================================================================
    * Click on 'Add a different sibling or half-sibling' and input their First and Last Name
    ===========================================================================================*/

    .group("AD_820_Sibling_Select") {
      exec(http("Adoption Select Sibling")
        .post(BaseURL + "/sibling/select-sibling")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("selectedSiblingId", "${SiblingID}")
        .formParam("siblingFirstName", "")
        .formParam("siblingLastNames", "")
        .check(CsrfCheck.save)
        .check(substring("What type of order is it?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter the Second Sibling's Placement Order Type
    ======================================================================================*/

    .group("AD_830_Sibling_Type_2") {
      exec(http("Adoption Order Type")
        .post(BaseURL + "/sibling/placement-order-type")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderType", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("What is the serial or case number on the order?")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Enter the Second Sibling's Placement Order Number
    ======================================================================================*/

    .group("AD_840_Sibling_Number_2") {
      exec(http("Adoption Order Number")
        .post(BaseURL + "/sibling/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("placementOrderNumber", Common.randomString(5))
        .check(CsrfCheck.save)
        .check(substring("Orders already in place for siblings and half-siblings")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Select 'No' when asked 'Do you want to add another order for a sibling or half-sibling?'
    ======================================================================================*/

    .group("AD_860_Sibling_Summary") {
      exec(http("Adoption Order Summary")
        .post(BaseURL + "/sibling/summary")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherSiblingPlacementOrder", "No")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="sibling-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)

  val adoptionFamilyCourt =

    /*======================================================================================
    * Click on 'Choose your family court'
    ======================================================================================*/

    group("AD_870_Family_Court") {
      exec(http("Adoption Family Court Redirect")
        .get(BaseURL + "/children/find-family-court")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Choose a family court")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Click 'yes' when asked to 'Choose a family court'
    ======================================================================================*/

    .group("AD_880_Family_Court_POST") {
      exec(http("Adoption Family Court Redirect")
        .post(BaseURL + "/children/find-family-court")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("findFamilyCourt", "Yes")
        .formParam("familyCourtName", "")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="find-family-court-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)

  val adoptionUploadDocuments =

    /*======================================================================================
    * Click on 'Upload documents'
    ======================================================================================*/

    group("AD_890_Upload_Document") {
      exec(http("Adoption Birth Certificate Details")
        .get(BaseURL + "/upload-your-documents")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Upload the child&#39;s documents")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Choose a Document to Upload
    ======================================================================================*/

    .group("AD_900_Upload_Document") {
      exec(http("Adoption Certificate Upload")
        .post(BaseURL + "/document-manager?_csrf=${csrfToken}")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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

    /*======================================================================================
    * Click on 'Birth or adoption certificate' and 'Death certificate' and then Submit
    ======================================================================================*/

    .group("AD_910_Upload_Document_POST") {
      exec(http("Adoption Certificate Upload POST")
        .post(BaseURL + "/upload-your-documents")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
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
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="upload-your-documents-status" class="govuk-tag app-task-list__tag ">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionReview = {

    /*======================================================================================
    * Click on 'Review, pay and submit your application'
    ======================================================================================*/

    group("AD_920_Equality_Redirect") {
      exec(http("Adoption Review Equality")
        .get(BaseURL + "/review-pay-submit/payment/payment-callback")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Review your answers")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Review the Answers and then click on 'Save and continue'
    ======================================================================================*/

    .group("AD_930_Check_Your_Answers") {
      exec(http("Adoption Review Check Your Answers")
        .post(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn", "[object Object]")
        .check(CsrfCheck.save)
        .check(substring("Statement of truth")))
    }
    .pause(ThinkTime)

    /*==========================================================================================
    * Click on the two boxes, and then enter the Full Name and the Second Applicant's Full name
    ==========================================================================================*/

    .group("AD_940_Statement") {
      exec(http("Adoption Review Statement of Truth")
        .post(BaseURL + "/review-pay-submit/statement-of-truth")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "${csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1IBelieveApplicationIsTrue", "")
        .formParam("applicant1IBelieveApplicationIsTrue", "checked")
        .formParam("applicant2IBelieveApplicationIsTrue", "")
        .formParam("applicant2IBelieveApplicationIsTrue", "checked")
        .formParam("applicant1SotFullName", Common.randomString(5))
        .formParam("applicant2SotFullName", Common.randomString(5))
        .check(css("input[name='csrfToken']", "value").saveAs("csrfToken"))
        .check(css("input[name='chargeId']", "value").saveAs("chargeId")))

    }
    .pause(ThinkTime)

    /*======================================================================================
    * After the card info is inputted a check occurs
    ======================================================================================*/

    .group("AD_945_Card_Details_Check") {
      exec(http("Check Card")
        .post(PaymentURL + "/check_card/${chargeId}")
        .headers(Headers.paymentHeader)
        .formParam("cardNo", "4444333322221111")
        .check(jsonPath("$.accepted").is("true")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Input all the information necessary
    ======================================================================================*/

    .group("AD_950_Card_Details") {

      exec(http("Adoption Pay by Card")
        .post(PaymentURL + "/card_details/${chargeId}")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("chargeId", "${chargeId}")
        .formParam("csrfToken", "${csrfToken}")
        .formParam("cardNo", "4444333322221111")
        .formParam("expiryMonth", "01")
        .formParam("expiryYear", "26")
        .formParam("cardholderName", Common.randomString(5))
        .formParam("cvc", "123")
        .formParam("addressCountry", "GB")
        .formParam("addressLine1", (Common.randomString(8)))
        .formParam("addressLine2", (Common.randomString(7)))
        .formParam("addressCity", (Common.randomString(5)))
        .formParam("addressPostcode", "PR1 1RF")
        .formParam("email", Common.randomString(5)+"@gmail.com")
        .check(regex("Confirm your payment"))
        .check(css("input[name='csrfToken']", "value").saveAs("csrfToken")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Confirm the Payment
    ======================================================================================*/

    .group("AD_960_Application Submit") {
      exec(http("Adoption Application Submit")
        .post(PaymentURL + "/card_details/${chargeId}/confirm")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("csrfToken", "${csrfToken}")
        .formParam("chargeId", "${chargeId}")
        .check(substring("Application Submitted"))
        .check(regex("""Your reference number<br><strong>(\d{4}-\d{4}-\d{4}-\d{4})""").saveAs("referenceNumber")))
    }
    .pause(ThinkTime)
  }

  val adoptionLogOut =

    /*======================================================================================
    * Log Out
    ======================================================================================*/

    group("AD_970_LogOut") {
      exec(http("Adoption LogOut")
        .get(BaseURL + "/logout")
        .headers(Headers.commonHeader)
        .check(substring("Sign in")))
    }

}
