package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Environment, CommonHeader}

object adoptionScenario1and2 {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val ThinkTime = Environment.thinkTime


  val adoptionHomepage =

    group("adoption_010_Homepage") {
      exec(http("Adoption Homepage")
        .get(BaseURL)
        .headers(CommonHeader.homepage_header)
        .check(substring("Sign in"))
        .check(css("input[name='_csrf']", "value").saveAs("csrfToken")))
    }
      .pause(ThinkTime)


  val adoptionLogin =

    group("Adoption_020_Login") {
      exec(http("Adoption Login")
        .post(IdamURL + "/login?client_id=adoption-web&response_type=code&redirect_uri=" + BaseURL +"/receiver")
        .headers(CommonHeader.Idam_Header)
        .formParam("username", "${username}")
        .formParam("password", "${password}")
        .formParam("save", "Sign in")
        .formParam("selfRegistrationEnabled", "true")
        .formParam("_csrf", "${csrfToken}"))
      // .check(substring("Are you applying on your own, or with someone else?")))

    }
      .pause(ThinkTime)



  val adoptionApplyingWith =

    group("Adoption_030_Apply_With") {
      exec(http("Adoption Applying With")
        .get(BaseURL + "/applying-with")
        .headers(CommonHeader.get_header)
        .check(substring("Are you applying on your own, or with someone else?"))
        .check(css("input[name='_csrf']", "value").saveAs("csrfToken")))
    }
        .pause(ThinkTime)


    .group("Adoption_040_Adoption_Applying_Post") {
      exec(http("Adoption Applying Post")
        .post(BaseURL + "/applying-with")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("applyingWith", "withSpouseOrCivilPartner")
        .formParam("otherApplicantRelation", "")
        .check(substring("Apply to adopt a child placed in your care")))

  }
      .pause(ThinkTime)

  .group("Adoption_050_Adoption_Applying_With_Redirect") {
    exec(http("Adoption Applying With Redirect")
      .get(BaseURL + "/task-list")
      .headers(CommonHeader.get_header)
      .check(substring("Apply to adopt a child placed in your care")))
  }
        .pause(ThinkTime)



  val adoptionDateOfMove =

    group("Adoption_060_Date_Of_Move") {
      exec(http("Adoption Date Child Moved in ")
        .get(BaseURL + "/date-child-moved-in")
        .headers(CommonHeader.get_header)
        .check(substring("When did the child move in with you?")))
    }
        .pause(ThinkTime)


  .group("Adoption_070_Date_Of_Move_POST") {
    exec(http("Adoption Date Child Moved in Post")
      .post(BaseURL + "/date-child-moved-in")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("dateChildMovedIn-day", "${dateChildMovedIn-day}")
      .formParam("dateChildMovedIn-month", "${dateChildMovedIn-month}")
      .formParam("dateChildMovedIn-year", "${dateChildMovedIn-year}"))
     // .check(substring("Apply to adopt a child placed in your care"))

    .group("Adoption_050_Adoption_Applying_With_Redirect") {
      exec(http("Adoption Applying With Redirect")
        .get(BaseURL + "/task-list")
        .headers(CommonHeader.get_header)
        .check(substring("Apply to adopt a child placed in your care")))
    }
      .pause(ThinkTime)
  }
        .pause(ThinkTime)



  val adoptionYourDetails =
    group("Adoption_080_Your_Details") {
      exec(http("Adoption Your Personal Details ")
        .get("https://adoption-web.aat.platform.hmcts.net/applicant1/full-name")
        .headers(CommonHeader.headers_36)
        .check(substring("your full name?"))
        .check(substring("First applicant")))

    }
        .pause(ThinkTime)


      .group("Adoption_090_Your_Details_POST") {
        exec(http("Adoption Your Personal Details Post")
          .post("/applicant1/full-name")
          .headers(CommonHeader.post_header)
          .formParam("_csrf", "${csrfToken}")
          .formParam("applicant1FirstNames", "${FirstName}")
          .formParam("applicant1LastNames", "${LastName}")
          .check(substring("Have you ever legally been known by any other names?")))
      }
          .pause(ThinkTime)

        //  .exec(http("Adoption Your Personal Details Redirect")
        //    .get("/applicant1/other-names")

  .group("Adoption_100_Your_Details_Other_Name") {
    exec(http("Adoption Your Personal Details Other Name")
      .post(BaseURL + "/applicant1/other-names")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant1HasOtherNames", "Yes")
      .formParam("applicant1OtherFirstNames", "${FirstName}")
      .formParam("applicant1OtherLastNames", "${LastName}")
      .formParam("addButton", "addButton")
      .check(substring("${FirstName}"))
      .check(substring("Have you ever legally been known by any other names?")))
  }
        .pause(ThinkTime)

  .group("Adoption_110_Your_Details_DoB") {
    exec(http("Adoption Your Personal Details DoB")
      .get(BaseURL + "/applicant1/dob")
      .headers(CommonHeader.get_header)
      .check(substring("your date of birth?"))
      .check(substring("First applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_120_Your_Details_DoB_POST") {
    exec(http("Adoption Your Personal Details DoB POST")
      .post(BaseURL + "/applicant1/dob")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant1DateOfBirth-day", "${BirthDay}")
      .formParam("applicant1DateOfBirth-month", "${BirthMonth}")
      .formParam("applicant1DateOfBirth-year", "${BirthYear}")
      .check(substring("your occupation?"))
      .check(substring("First applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_130_Your_Details_Occupation") {
    exec(http("Adoption Your Personal Details Occupation")
      .post(BaseURL + "/applicant1/occupation")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant1Occupation", "${Occupation}")
      .check(substring("Apply to adopt a child placed in your care")))
  }
        .pause(ThinkTime)




  val adoptionYourContact =
    group("Adoption_140_Your_Contact_Details") {
      exec(http("Adoption Your Contact Details LookUp")
        .get(BaseURL + "/applicant1/address/lookup")
        .headers(CommonHeader.get_header)
        .check(substring("your home address"))
        .check(substring("First applicant")))
    }
        .pause(ThinkTime)

  .group("Adoption_150_Your_Contact_Details_Manual") {
    exec(http("Adoption Your Contact Details Manual")
      .get(BaseURL + "/applicant1/address/manual")
      .headers(CommonHeader.get_header)
      .check(substring("your address"))
      .check(substring("Building and street")))
  }
        .pause(ThinkTime)

  .group("Adoption_160_Your_Contact_Details_Manual_POST") {
    exec(http("Adoption Your Contact Details Manual POST")
      .post(BaseURL + "/applicant1/address/manual")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant1Address1", "${AddressLine1}")
      .formParam("applicant1Address2", "${AddressLine2}")
      .formParam("applicant1AddressTown", "${Town}")
      .formParam("applicant1AddressCounty", "${County}")
      .formParam("applicant1AddressPostcode", "${PostCode}")
      .check(substring("What are your contact details?"))
      .check(substring("First applicant")))
  }
        .pause(ThinkTime)

    .group("Adoption_170_Your_Contact_Details_POST") {
      exec(http("Adoption Your Contact Details POST")
        .post(BaseURL + "/applicant1/contact-details")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("applicant1EmailAddress", "${Email}")
        .formParam("applicant1PhoneNumber", "${PhoneNumber}")
        .formParam("applicant1ContactDetailsConsent", "${DetailsConsent}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
        .pause(ThinkTime)


  val adoptionSecondPersonal =
    group("Adoption_180_Second_Personal_Details") {
      exec(http("Adoption Second Personal Details")
        .get(BaseURL + "/applicant2/full-name")
        .headers(CommonHeader.get_header)
        .check(substring("your full name?"))
        .check(substring("Second applicant")))
    }
        .pause(ThinkTime)

  .group("Adoption_190_Second_Personal_Details_POST") {
    exec(http("Adoption Second Personal Details POST")
      .post(BaseURL + "/applicant2/full-name")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant2FirstNames", "${FirstName}")
      .formParam("applicant2LastNames", "${LastName}")
      .check(substring("Have you ever legally been known by any other names?"))
      .check(substring("Second applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_200_Second_Other_Name") {
    exec(http("Adoption Second Other Name")
      .post(BaseURL + "/applicant2/other-names")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant2HasOtherNames", "Yes")
      .formParam("applicant2OtherFirstNames", "${FirstName}")
      .formParam("applicant2OtherLastNames", "${LastName}")
      .formParam("addButton", "addButton")
      .check(substring("${FirstName}"))
      .check(substring("${LastName}"))
      .check(substring("Second applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_210_Second_Other_Name_Redirect") {
    exec(http("Adoption Second Other Name Redirect")
      .post(BaseURL + "/applicant2/other-names")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant2HasOtherNames", "Yes")
      .formParam("addAnotherNameHidden", "")
      .formParam("applicant2OtherFirstNames", "${FirstName}")
      .formParam("applicant2OtherLastNames", "${LastName}")
      .check(substring("your date of birth?"))
      .check(substring("Second applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_220_Second_DoB") {
    exec(http("Adoption Second DoB")
      .post(BaseURL + "/applicant2/dob")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant2DateOfBirth-day", "${BirthDay}")
      .formParam("applicant2DateOfBirth-month", "${BirthMonth}")
      .formParam("applicant2DateOfBirth-year", "${BirthYear}")
      .check(substring("What's your occupation?"))
      .check(substring("Second applicant")))
  }
        .pause(ThinkTime)

  .group("Adoption_230_Second_Occupation") {
    exec(http("Adoption Second Occupation")
      .post(BaseURL + "/applicant2/occupation")
      .headers(CommonHeader.post_header)
      .formParam("_csrf", "${csrfToken}")
      .formParam("applicant2Occupation", "${Occupation}")
      .check(substring("Apply to adopt a child placed in your care")))
  }
        .pause(ThinkTime)



  val adoptionSecondContact =
    group("Adoption_240_Second_Contact_Details_Same_Address") {
      exec(http("Adoption Second Contact Details Same-Address")
        .get(BaseURL + "/applicant2/same-address")
        .headers(CommonHeader.get_header)
        .check(substring("Do you also live at this address?"))
        .check(substring("Second applicant")))
    }
        .pause(ThinkTime)


    .group("Adoption_250_Second_Contact_Details_Same_Address_POST") {
      exec(http("Adoption Second Contact Details Same-Address POST")
        .post(BaseURL + "/applicant2/same-address")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("applicant2AddressSameAsApplicant1", "No")
        .check(substring("your home address?"))
        .check(substring("Second applicant")))
    }
        .pause(ThinkTime)

    .group("Adoption_260_Second_Contact_Details_Manual_Redirect") {
      exec(http("Adoption Second Contact Details manual redirect")
        .get(BaseURL + "/applicant2/address/manual")
        .headers(CommonHeader.get_header)
        .check(substring("Building and street"))
        .check(substring("Second applicant")))
    }
        .pause(ThinkTime)

    .group("Adoption_270_Second_Contact_Details_Manual_POST") {
      exec(http("Adoption Second Contact Details manual POST")
        .post(BaseURL + "/applicant2/address/manual")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("applicant2Address1", "${AddressLine1}")
        .formParam("applicant2Address2", "${AddressLine2}")
        .formParam("applicant2AddressTown", "${Town}")
        .formParam("applicant2AddressCounty", "${County}")
        .formParam("applicant2AddressPostcode", "${PostCode}")
        .check(substring("We need both a contact email and telephone number for you."))
        .check(substring("Second applicant")))
    }
        .pause(ThinkTime)

    .group("Adoption_280_Second_Contact_Details__POST") {
      exec(http("Adoption Second Contact Details POST")
        .post(BaseURL + "/applicant2/contact-details")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
        .formParam("applicant2EmailAddress", "${Email}")
        .formParam("applicant2PhoneNumber", "${PhoneNumber}")
        .formParam("applicant2ContactDetailsConsent", "${DetailsConsent}")
        .check(substring("Apply to adopt a child placed in your care")))
    }
        .pause(ThinkTime)







}
