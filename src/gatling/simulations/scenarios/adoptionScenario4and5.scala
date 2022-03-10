package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{CommonHeader, Environment}

object adoptionScenario4and5 {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val PaymentURL = Environment.paymentUrl
  val PcqURL = Environment.pcqUrl

  val ThinkTime = Environment.thinkTime


  val adoptionUploadDocuments =

    group("adoption_870_Upload_Document") {


      exec(http("Adoption Birth Certificate Details")
        .get(BaseURL + "/upload-your-documents")
        .headers(CommonHeader.get_header)
        .check(substring("Upload the child"))
        .check(substring("documents")))
    }
        .pause(ThinkTime)


    .group("adoption_880_Upload_Document") {
      exec(_.setAll(
        "FileName" -> ("2MB.pdf")
      ))

        .exec(http("Adoption Certificate Upload")
          .post(BaseURL + "/document-manager?_csrf=${csrfToken}")
          .headers(CommonHeader.UploadHeader))
   //       .bodyPart(RawFileBodyPart("files", "${FileName}")
    //        .fileName("${FileName}")
     //       .transferEncoding("binary"))
     //     .check(regex("""document-manager/([0-9a-z-]+?)/binary""").saveAs("Document_ID"))
        //  .check(jsonPath("$.document-manager[0].hashToken").saveAs("hashToken"))
     //     .check(substring("2MB.pdf")))
        .pause(ThinkTime)
    }


      //.header("Authorization", "Bearer ${accessToken}")
      // .header("ServiceAuthorization", "${bearerToken}")
      // .header("accept", "application/json")
      // .header("Content-Type", "multipart/form-data")
      // .formParam("classification", "PUBLIC")
      //.formParam("caseTypeId", "Benefit")
      // .formParam("jurisdictionId", "SSCS")





        .group("adoption_885_Upload_Document_POST") {
      exec(http("Adoption Certificate Upload POST")
        .post(BaseURL + "/upload-your-documents")
        .headers(CommonHeader.post_header)
        .formParam("_csrf", "${csrfToken}")
      //  .formParam("applicant1UploadedFiles", """[{"id":"${Document_ID}","name":"dummyFile.jpg"}]""")
        .formParam("applicant1UploadedFiles", "")
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


        group("adoption_890_Equality") {
          exec(http("Adoption Review Equality")
          .get(BaseURL + "/review-pay-submit/equality")
        //    .get(PcqURL + "/start-page")
            .headers(CommonHeader.get_header)
            .check(substring("Equality and diversity questions")))
        }
          .pause(ThinkTime)


      .group("adoption_900_Equality") {
        exec(http("Adoption Review Equality")
          .post(PcqURL + "/opt-out")
          .headers(CommonHeader.pcq_header)
          .formParam("_csrf", "${csrfToken}"))
     //     .check(substring("Review your answers")))
      }
        .pause(ThinkTime)

    group("adoption_905_Equality_Redirect") {
      exec(http("Adoption Review Equality")
        .get(BaseURL + "/review-pay-submit/check-your-answers")
        .headers(CommonHeader.get_header)
        .check(substring("Review your answers")))
    }
      .pause(ThinkTime)


/*
  group("adoption_905_Equality_Redirect") {
    exec(http("Adoption Review Equality")
      .get(BaseURL + "/review-pay-submit/equality")
      .headers(CommonHeader.get_header)
      .check(substring("Review your answers")))
  }
    .pause(ThinkTime)



group("adoption_905_Equality_Redirect") {
  exec(http("Adoption Review Equality")
    .get(BaseURL + "/review-pay-submit/check-your-answers")
    .headers(CommonHeader.get_header)
    .check(substring("Review your answers")))
}
  .pause(ThinkTime)


  */
.group("adoption_910_Check_Your_Answers") {
exec(http("Adoption Review Check Your Answers")
  .post(BaseURL + "/review-pay-submit/check-your-answers")
  .headers(CommonHeader.post_header)
  .formParam("_csrf", "${csrfToken}")
  .formParam("dateChildMovedIn", "[object Object]")
  .check(substring("Statement of truth")))
}
.pause(ThinkTime)

.group("adoption_920_Statement") {
exec(http("Adoption Review Statement of Truth")
  .post(BaseURL + "/review-pay-submit/statement-of-truth")
  .headers(CommonHeader.post_header)
  .formParam("_csrf", "${csrfToken}")
  .formParam("applicant1IBelieveApplicationIsTrue", "")
  .formParam("applicant1IBelieveApplicationIsTrue", "checked")
  .formParam("applicant2IBelieveApplicationIsTrue", "")
  .formParam("applicant2IBelieveApplicationIsTrue", "checked")
  .formParam("applicant1SotFullName", "${FirstName}")
  .formParam("applicant2SotFullName", "${FirstName}")
  .check(substring("Paying your adoption court fees")))

}
.pause(ThinkTime)

.group("adoption_930_Pay_By_Card") {
exec(http("Adoption Pay by Card")
  .post(BaseURL + "/review-pay-submit/payment/pay-your-fee")
  .headers(CommonHeader.post_header)
  .formParam("_csrf", "${csrfToken}")
  .formParam("paymentType", "payByCard")
  .formParam("hwfRefNumber", "")
  //.check(css("input[name='_csrf']", "value").saveAs("csrfToken"))
  .check(css("input[name='chargeId']", "value").saveAs("chargeId")))
}

.pause(ThinkTime)


.group("adoption_940_Check_Card_Details") {
exec(http("Adoption Pay by Card")
  .post(PaymentURL + "/check_card/${chargeId}")
  .headers(CommonHeader.PaymentHeader)
  .formParam("cardNo", "${CardNumber}"))
 // .check(css("input[name='chargeId']", "value").saveAs("chargeId"))
  //.check(substring("Enter card details")))

}

.group("adoption_950_Card_Details") {
exec(http("Adoption Pay by Card")
  .post(PaymentURL + "/card_details/${chargeId}")
  .headers(CommonHeader.headers_117)
  .formParam("chargeId", "${chargeId}")
  .formParam("csrfToken", "${csrfToken}")
  .formParam("cardNo", "${CardNumber}")
  .formParam("expiryMonth", "${ExpiryDay}")
  .formParam("expiryYear", "${ExpiryYear}")
  .formParam("cardholderName", "${FirstName}")
  .formParam("cvc", "${Cvc}")
  .formParam("addressCountry", "GB")
  .formParam("addressLine1", "${AddressLine1}")
  .formParam("addressLine2", "${AddressLine2}")
  .formParam("addressCity", "${City}")
  .formParam("addressPostcode", "${PostCode}")
  .formParam("email", "${Email}"))
}

.pause(ThinkTime)

.group("adoption_960_Application Submit") {
exec(http("Adoption Application Submit")
  .post(PaymentURL + "/card_details/${chargeId}/confirm")
  .headers(CommonHeader.PaymentHeader2)
  .formParam("csrfToken", "${csrfToken}")
  .formParam("chargeId", "${chargeId}")
  .check(substring("Application Submitted")))
}
.pause(ThinkTime)
}


}
