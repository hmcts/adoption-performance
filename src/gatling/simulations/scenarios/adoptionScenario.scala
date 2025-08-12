package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Common, Environment, Headers, CsrfCheck}


object adoptionScenario {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val PaymentURL = Environment.paymentUrl
  val ThinkTime = Environment.thinkTime
  val postcodeFeeder = csv("postcodes.csv").random


  val ApplyToAdoptChild =


    exec(_.setAll(
      "randomString" -> Common.randomString(5),
      "randomDay" -> Common.getDay(),
      "randomMonth" -> Common.getMonth(),
      "adultDobYear" -> Common.getDobYear(),
      "adoptionYear" -> Common.yearMinusTwo(),
      "childDobYear" -> Common.getDobYearChild()))

    /*======================================================================================
    * Apply to adopt a child placed in your care
    ======================================================================================*/

    .group("AD_001_ApplyToAdoptChild") {
      exec(http("Apply to adopt a child placed in your care")
        .get(BaseURL + "/eligibility/start?lang=en")
        .headers(Headers.navigationHeader)
        .check(substring("You can apply to adopt a child who's in your care following a")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Before You Start
    ======================================================================================*/

    .group("AD_002_BeforeYouStart") {
      exec(http("Before You Start")
        .get(BaseURL + "/eligibility/multiple-children")
        .headers(Headers.navigationHeader)
        .check(CsrfCheck.save)
        .check(substring("Are you applying to adopt more than one child?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Are you applying to adopt more than one child? - No
    ======================================================================================*/

    .group("AD_003_MoreThanOneChild") {
      exec(http("More Than One Child")
        .post(BaseURL + "/eligibility/multiple-children")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("multipleChildrenEligible", "No")
        .check(CsrfCheck.save)
        .check(substring("Will the child be under 18 years old on the date you submit your application?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Will the child be under 18 years old on the date you submit your application? - Yes
    ======================================================================================*/

    .group("AD_004_Under18") {
      exec(http("Will Child Be Under 18")
        .post(BaseURL + "/eligibility/under-18")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("under18Eligible", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Is the child married or in a civil partnership?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Is the child married or in a civil partnership? - No
    ======================================================================================*/

    .group("AD_005_ChildMarriedOrCivil") {
      exec(http("Child Married Or Civil")
        .post(BaseURL + "/eligibility/married")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("marriedEligible", "No")
        .check(CsrfCheck.save)
        .check(substring("Are you, and the other applicant if relevant, both aged 21 or over?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Are you, and the other applicant if relevant, both aged 21 or over? - Yes
    ======================================================================================*/

    .group("AD_006_Over21") {
      exec(http("Applicants Over 21")
        .post(BaseURL + "/eligibility/under-21")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("under21Eligible", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Is the UK, Channel Islands or Isle of Man the main country of residence (domicile) for you and the other applicant if relevant?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Is the UK, Channel Islands or Isle of Man the main country of residence (domicile) for you and the other applicant if relevant? - Yes
    ======================================================================================*/

    .group("AD_007_MainCountryOfResidence") {
      exec(http("Main Country Of Residence")
        .post(BaseURL + "/eligibility/domicile")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("domicileEligible", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Have you, and the other applicant if relevant, lived in the UK, Channel Islands or Isle of Man for the last 12 months (habitually resident)?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Have you, and the other applicant if relevant, lived in the UK, Channel Islands or Isle of Man for the last 12 months (habitually resident)?
    ======================================================================================*/

    .group("AD_008_LivedInUK") {
      exec(http("Lived In The UK")
        .post(BaseURL + "/eligibility/lived-uk")
        .headers(Headers.commonHeader)
        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
       .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("livedUKEligible", "Yes")
        .check(substring("Sign in or create an account")))
    }
    .pause(ThinkTime)


  val adoptionHomepage =

    /*======================================================================================
    * Adoption Homepage
    ======================================================================================*/

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
    * Adoption Homepage
    ======================================================================================*/

    group("AD_010_Homepage") {
      exec(http("Adoption Homepage")
        .get(BaseURL)
        .headers(Headers.navigationHeader)
        .check(CsrfCheck.save)
        .check(substring("Sign in")))
    }
    .pause(ThinkTime)

    /*======================================================================================
    * Log In with a Citizen user
    ======================================================================================*/

    .group("AD_020_Login") {
      exec(http("Adoption Login")
        .post(IdamURL + "/login?client_id=adoption-web&response_type=code&redirect_uri=" + BaseURL +"/receiver")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("username", "#{emailAddress}")
        .formParam("password", "#{password}")
        .formParam("save", "Sign in")
        .formParam("selfRegistrationEnabled", "true")
        .formParam("_csrf", "#{csrfToken}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applyingWith", "withSpouseOrCivilPartner")
        .formParam("otherApplicantRelation", "")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applying-with-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn-day", "#{randomDay}")
        .formParam("dateChildMovedIn-month", "#{randomMonth}")
        .formParam("dateChildMovedIn-year", "#{adoptionYear}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="date-child-moved-in-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionAgency =

    /*======================================================================================
    * Click on 'Adoption agency and social worker'
    ======================================================================================*/

    group("AD_060_Agency") {
      exec(http("Adoption Agency")
        .get(BaseURL + "/children/social-worker")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("You can get these details from your local authority or adoption agency")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Input the Adoption agency or local authority details
    ======================================================================================*/

    .group("AD_070_Agency_Details") {
      exec(http("Adoption Agency Details")
        .post(BaseURL + "/children/social-worker")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("childSocialWorkerName", "childSocialWorkerName#{randomString}")
        .formParam("childSocialWorkerPhoneNumber", "07000000000")
        .formParam("childSocialWorkerEmail", "")
        .formParam("autoCompleteData", "London Borough of Bromley")
        .formParam("childLocalAuthority", "London Borough of Bromley")
        .formParam("childLocalAuthorityEmail", "LocalAuthority#{randomString}" + "@Justice.gov.uk")
        .check(CsrfCheck.save)
        .check(substring("Your social worker details")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Your social worker details
    ======================================================================================*/

    .group("AD_075_YourSocialWorkerDetails") {
      exec(http("Your Social Worker Details")
        .post(BaseURL + "/children/applicant-social-worker")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicantSocialWorkerName", "applicantSocialWorkerName#{randomString}")
        .formParam("applicantSocialWorkerPhoneNumber", "07455753776")
        .formParam("applicantSocialWorkerEmail", "")
        .formParam("autoCompleteData", "London Borough of Bromley")
        .formParam("applicantLocalAuthority", "London Borough of Bromley")
        .formParam("applicantLocalAuthorityEmail", "applicantLocalAuthorityEmaily#{randomString}" + "@Justice.gov.uk")
        .check(CsrfCheck.save)
        .check(substring("Is there another adoption agency or local authority involved?")))
    }
    .pause(ThinkTime)


    /*=====================================================================================================
    * Answer 'Yes' to 'Is there another adoption agency or local authority involved?'
    ======================================================================================================*/

    .group("AD_080_Other_Agency") {
      exec(http("Adoption Other Agency?")
        .post(BaseURL + "/children/other-adoption-agency")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("hasAnotherAdopAgencyOrLA", "Yes")
        .check(CsrfCheck.save)
        .check(substring("Adoption agency or local authority details")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Input the Other Adoption agency or local authority details
    ======================================================================================*/

    .feed(postcodeFeeder)
    .group("AD_090_Adoption_Agency_Local_Authority") {
      exec(http("Adoption Agency Local Authority")
        .post(BaseURL + "/children/adoption-agency")
        .headers(Headers.commonHeader)
        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
        .header("content-type", "application/x-www-form-urlencoded")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("adopAgencyOrLaName", "Name#{randomString}")
        .formParam("adopAgencyOrLaContactName", "Last#{randomString}")
        .formParam("adopAgencyOrLaPhoneNumber", "0123456789")
        .formParam("adopAgencyAddressLine1", "AddressLine1#{randomString}")
        .formParam("adopAgencyTown", "town#{randomString}")
        .formParam("adopAgencyPostcode", "#{postcode}")
        .formParam("adopAgencyOrLaContactEmail", "adopAgency#{randomString}"+"@gmail.com")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="adoption-agency-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed"""))
      )
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1FirstNames", "AppFirst#{randomString}")
        .formParam("applicant1LastNames", "AppLast#{randomString}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1HasOtherNames", "Yes")
        .formParam("applicant1OtherFirstNames", "OtherFirst#{randomString}")
        .formParam("applicant1OtherLastNames", "OtherLast#{randomString}")
        .formParam("addButton", "addButton")
        .check(CsrfCheck.save)
        .check(substring("Have you ever legally been known by any other names?")))

    }
    .pause(ThinkTime)


    /*======================================================================================
    * Enter a Date of Birth that's above 18 years old
    ======================================================================================*/

    .group("AD_140_Your_Details_DoB") {
      exec(http("Adoption Your Personal Details DoB")
        .post(BaseURL + "/applicant1/dob")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1DateOfBirth-day", "#{randomDay}")
        .formParam("applicant1DateOfBirth-month", "#{randomMonth}")
        .formParam("applicant1DateOfBirth-year", "#{adultDobYear}")
        .check(CsrfCheck.save)
        .check(substring("What&#39;s your occupation?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Enter an Occupation
    ======================================================================================*/

    .group("AD_150_Your_Details_Occupation") {
      exec(http("Adoption Your Personal Details Occupation")
        .post(BaseURL + "/applicant1/occupation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1Occupation", "Occ#{randomString}")
        .check(substring("Extra support during your case")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Extra support during your case
    ======================================================================================*/

    .group("AD_160_Your_Details_Occupation") {
      exec(http("Adoption Extra support during your case")
        .post(BaseURL + "/applicant1/extra-support  ")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1ReasonableAdjustmentDetails", "")
        .formParam("applicant1HasReasonableAdjustment", "No")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-personal-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1SelectAddress", "#{addressIndex}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1EmailAddress", "App#{randomString}" + "@gmail.com")
        .formParam("applicant1PhoneNumber", "07000000000")
        .formParam("applicant1ContactDetailsConsent", "Yes")
        .check(substring("What language do you want to receive emails and documents in?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    *What language do you want to receive emails and documents in? - English
    ======================================================================================*/

    .group("AD_210_Language_To_Receive") {
      exec(http("Adoption Language To Receive")
        .post(BaseURL + "/applicant1/language-preference")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1LanguagePreference", "ENGLISH")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-contact-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2FirstNames", "AppTwoFirst#{randomString}")
        .formParam("applicant2LastNames", "AppTwoLast#{randomString}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2HasOtherNames", "Yes")
        .formParam("applicant2OtherFirstNames", "#{randomString}")
        .formParam("applicant2OtherLastNames", "#{randomString}")
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
        .formParam("_csrf", "#{csrfToken}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2DateOfBirth-day", "#{randomDay}")
        .formParam("applicant2DateOfBirth-month", "#{randomMonth}")
        .formParam("applicant2DateOfBirth-year", "#{adultDobYear}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2Occupation", "Occ#{randomString}")
        .check(substring("Extra support during your case")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Extra support during your case
    ======================================================================================*/

    .group("AD_265_Extra_Support_During_Case") {
      exec(http("Adoption Extra Support During Your Case")
        .post(BaseURL + "/applicant2/extra-support")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2ReasonableAdjustmentDetails", "")
        .formParam("applicant2HasReasonableAdjustment", "No")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant1-contact-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .formParam("_csrf", "#{csrfToken}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2SelectAddress", "#{addressIndex}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2EmailAddress", "AppTwo#{randomString}" + "@gmail.com")
        .formParam("applicant2PhoneNumber", "07000000000")
        .formParam("applicant2ContactDetailsConsent", "Yes")
        .check(substring("What language do you want to receive emails and documents in?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * What language do you want to receive emails and documents in?
    ======================================================================================*/

    .group("AD_310_Language_Receive_Email_App2") {
      exec(http("What language do you want to receive emails and documents in?")
        .post(BaseURL + "/applicant2/language-preference")
        .headers(Headers.commonHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant2LanguagePreference", "ENGLISH")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="applicant2-personal-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


  val childsDetails =

    /*======================================================================================
    * Click on 'Child's details'
    ======================================================================================*/

    group("AD_320_Childs_Details") {
      exec(http("Adoption Child's Details")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstName", "ChildFirst#{randomString}")
        .formParam("childrenLastName", "ChildLast#{randomString}")
        .check(CsrfCheck.save)
        .check(substring("After adoption, what will be")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * After adoption, what will be the child's full name?
    ======================================================================================*/

    .group("AD_335_After_Adoption_Name?") {
      exec(http("Adoption Child's Full Name After Adoption")
        .post(BaseURL + "/children/full-name-after-adoption")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstNameAfterAdoption", "NewChildFirst#{randomString}")
        .formParam("childrenLastNameAfterAdoption", "NewChildLast#{randomString}")
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenDateOfBirth-day", "#{randomDay}")
        .formParam("childrenDateOfBirth-month", "#{randomMonth}")
        .formParam("childrenDateOfBirth-year", "#{childDobYear}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="adoption-certificate-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


  val theFamilyCourtDetails =

    /*======================================================================================
    * Click on 'The Family Court Details'
    ======================================================================================*/

    group("AD_350_Family_Court_Details") {
      exec(http("Adoption The Family Court Details")
        .get(BaseURL + "/children/find-placement-order-court")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Which court made the placement order?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Which court made the placement order?
    ======================================================================================*/

    .group("AD_360_Placement_Order?") {
      exec(http("Adoption Court Made Placement Order?")
        .post(BaseURL + "/children/find-placement-order-court")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("autoCompleteData", "Bromley County Court and Family Court")
        .formParam("placementOrderCourt", "Bromley County Court and Family Court")
        .check(substring("Choose a family court")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Choose a family court - yes
    ======================================================================================*/

    .group("AD_370_Choose_Family_Court") {
      exec(http("Adoption Choose a Family Court")
        .post(BaseURL + "/children/find-family-court")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("findFamilyCourt", "Yes")
        .formParam("autoCompleteData", "")
        .formParam("familyCourtName", "")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="find-family-court-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("childrenFirstNameAfterAdoption", "First#{randomString}")
        .formParam("childrenLastNameAfterAdoption", "Last#{randomString}")
        .check(substring("Apply to adopt a child placed in your care"))
        .check(regex("""id="adoption-certificate-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


  val adoptionReview = {

    /*======================================================================================
    * Click on 'Review, pay and submit your application'
    ======================================================================================*/

    group("AD_395_Equality_Redirect") {
      exec(http("Adoption Review Equality")
        .get(BaseURL + "/review-pay-submit/equality")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Equality and diversity questions")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Click on 'Review, pay and submit your application'
    ======================================================================================*/

    .group("AD_396_Equality_Redirect") {
      exec(http("Adoption Review Equality")
        .get(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Review your answers")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Review the Answers and then click on 'Save and continue'
    ======================================================================================*/

    .group("AD_400_Check_Your_Answers") {
      exec(http("Adoption Review Check Your Answers")
        .post(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("dateChildMovedIn", "[object Object]")
        .check(CsrfCheck.save)
        .check(substring("Statement of truth")))
    }
    .pause(ThinkTime)


    /*==========================================================================================
    * Click on the two boxes, and then enter the Full Name and the Second Applicant's Full name
    ==========================================================================================*/

    .group("AD_410_Statement") {
      exec(http("Adoption Review Statement of Truth")
        .post(BaseURL + "/review-pay-submit/statement-of-truth")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("applicant1IBelieveApplicationIsTrue", "")
        .formParam("applicant1IBelieveApplicationIsTrue", "checked")
        .formParam("applicant2IBelieveApplicationIsTrue", "")
        .formParam("applicant2IBelieveApplicationIsTrue", "checked")
        .formParam("applicant1SotFullName", "AppFirst#{randomString} AppLast#{randomString}")
        .formParam("applicant2SotFullName", "AppTwoFirst#{randomString} AppTwoLast#{randomString}")
        .check(substring("You will be taken to the payment page")))

    }
    .pause(ThinkTime)


    /*==========================================================================================
    * Review your application - Pay and submit
    ==========================================================================================*/

    .group("AD_411_Pay_And_Submit_Review") {
      exec(http("Adoption Review Pay and Submit")
        .get(BaseURL + "/review-pay-submit/payment/pay-your-fee")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .check(css("input[name='csrfToken']", "value").saveAs("csrfToken"))
         .check(css("input[name='chargeId']", "value").saveAs("chargeId")))

    }
    .pause(ThinkTime)


    /*======================================================================================
    * After the card info is inputted a check occurs
    ======================================================================================*/

    .group("AD_412_Card_Details_Check") {
      exec(http("Check Card")
        .post(PaymentURL + "/check_card/#{chargeId}")
        .headers(Headers.paymentHeader)
        .formParam("cardNo", "4444333322221111")
        .check(jsonPath("$.accepted").is("true")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Input all the information necessary
    ======================================================================================*/

    .group("AD_420_Card_Details") {
      exec(http("Adoption Pay By Card")
        .post(PaymentURL + "/card_details/#{chargeId}")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("chargeId", "#{chargeId}")
        .formParam("csrfToken", "#{csrfToken}")
        .formParam("cardNo", "4444333322221111")
        .formParam("expiryMonth", "01")
        .formParam("expiryYear", "26")
        .formParam("cardholderName", "#{randomString}")
        .formParam("cvc", "123")
        .formParam("addressCountry", "GB")
        .formParam("addressLine1", "1 #{randomString}")
        .formParam("addressLine2", "#{randomString} Road")
        .formParam("addressCity", "#{randomString} Town")
        .formParam("addressPostcode", "PR1 1RF")
        .formParam("email", "#{randomString}" + "@gmail.com")
        .check(regex("Confirm your payment"))
        .check(css("input[name='csrfToken']", "value").saveAs("csrfToken")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Confirm the Payment
    ======================================================================================*/

    .group("AD_430_Application_Submit") {
      exec(http("Adoption Application Submit")
        .post(PaymentURL + "/card_details/#{chargeId}/confirm")
        .headers(Headers.commonHeader)
        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
        .header("content-type", "application/x-www-form-urlencoded")
        .formParam("csrfToken", "#{csrfToken}")
        .formParam("chargeId", "#{chargeId}")
        .check(regex("""rel="stylesheet">
                       |  <!-- (\d{16}) -->""".stripMargin).saveAs("referenceNumber")))
    }
    .pause(ThinkTime)
  }


  val adoptionLogOut =

    /*======================================================================================
    * Log Out
    ======================================================================================*/

    group("AD_440_LogOut") {
      exec(http("Adoption LogOut")
        .get(BaseURL + "/logout")
        .headers(Headers.commonHeader)
        .check(substring("Sign in")))
    }

}
