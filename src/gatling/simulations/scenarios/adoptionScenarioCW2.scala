package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.{Common, CsrfCheck, Environment, Headers}


object adoptionScenarioCW2 {

  val BaseURL = Environment.baseUrl
  val IdamURL = Environment.idamUrl
  val PaymentURL = Environment.paymentUrl
  val ThinkTime = Environment.thinkTime
  val postcodeFeeder = csv("postcodes.csv").random


  val refDetails =

    /*======================================================================================
    * 'Other person with parental responsibility' Event
    ======================================================================================*/

    group("AD_740_OtherPersonResponsibility") {
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
        .check(substring("What&#39;s their address?")))
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
        .check(regex("""id="other-parent-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
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
        .check(regex("""id="placement-and-court-order-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * 'Sibling court order details' Event
    ======================================================================================*/

    .group("AD_900_SiblingCourt") {
      exec(http("Sibling Court Order Details")
        .get(BaseURL + "/la-portal/sibling/exists")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Does the child have any siblings or half siblings with court orders?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Does the child have any siblings or half siblings with court orders? - Yes
    ======================================================================================*/

    .group("AD_910_AnySiblings") {
      exec(http("Child have any siblings")
        .post(BaseURL + "/la-portal/sibling/exists")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("hasSiblings", "Yes")
        .check(CsrfCheck.save)
        .check(substring("What is their relationship to the child being adopted?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * What is their relationship to the child being adopted? - Sister
    ======================================================================================*/

    .group("AD_920_TheirRelationship") {
      exec(http("What is their relationship")
        .post(BaseURL + "/la-portal/sibling/relation")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("selectedSiblingRelation", "Sister")
        .check(CsrfCheck.save)
        .check(substring("What type of order is it?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * What type of order is it? - Adoption Order
    ======================================================================================*/

    .group("AD_930_TypeOfOrder") {
      exec(http("What type of order")
        .post(BaseURL + "/la-portal/sibling/placement-order-type")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("selectedSiblingPoType", "Adoption order")
        .formParam("selectedSiblingOtherPlacementOrderType", "Adoption order")
        .check(CsrfCheck.save)
        .check(substring("What is the serial or case number on the order?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * What is the serial or case number on the order?
    ======================================================================================*/

    .group("AD_940_SerialCaseNumber") {
      exec(http("Serial or case number")
        .post(BaseURL + "/la-portal/sibling/placement-order-number")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("siblingPoNumber", "siblingPoNumber#{randomString}")
        .check(CsrfCheck.save)
        .check(substring("Do you want to add another order for the same or another sibling?")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Do you want to add another order for the same or another sibling? - No
    ======================================================================================*/

    .group("AD_950_AnotherSibling") {
      exec(http("Another sibling")
        .post(BaseURL + "/la-portal/sibling/summary")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("addAnotherSiblingPlacementOrder", "No")
        .check(regex("""id="sibling-court-order-details-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * 'Upload Documents' Event
    ======================================================================================*/

    .group("AD_960_UploadDocuments") {
      exec(http("Upload Documents")
        .get(BaseURL + "/la-portal/la-portal/upload-your-documents")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Uploaded files")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Choose a Document to Upload
    ======================================================================================*/

    .group("AD_970_Upload_Document") {
      exec(http("Adoption Certificate Upload")
        .post(BaseURL + "/la-document-manager?_csrf=#{csrfToken}")
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
        .check(jsonPath("$[0].id").saveAs("Document_ID1"))
        .check(substring("2MB.pdf")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Choose a Document to Upload 2
    ======================================================================================*/

    .group("AD_971_Upload_Document2") {
      exec(http("Adoption Certificate Upload 2")
        .post(BaseURL + "/la-document-manager?_csrf=#{csrfToken}")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .header("accept", "application/json")
        .header("content-type", "multipart/form-data")
        .header("sec-fetch-dest", "empty")
        .header("sec-fetch-mode", "cors")
        .bodyPart(RawFileBodyPart("files[]", "2MB2.pdf")
          .fileName("2MB2.pdf")
          .transferEncoding("binary"))
        .asMultipartForm
        .check(jsonPath("$[0].id").saveAs("Document_ID2"))
        .check(substring("2MB2.pdf")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Choose a Document to Upload 3
    ======================================================================================*/

    .group("AD_972_Upload_Document3") {
      exec(http("Adoption Certificate Upload3")
        .post(BaseURL + "/la-document-manager?_csrf=#{csrfToken}")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .header("accept", "application/json")
        .header("content-type", "multipart/form-data")
        .header("sec-fetch-dest", "empty")
        .header("sec-fetch-mode", "cors")
        .bodyPart(RawFileBodyPart("files[]", "2MB3.pdf")
          .fileName("2MB3.pdf")
          .transferEncoding("binary"))
        .asMultipartForm
        .check(jsonPath("$[0].id").saveAs("Document_ID3"))
        .check(substring("2MB3.pdf")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Choose a Document to Upload 4
    ======================================================================================*/

//    .group("AD_973_Upload_Document4") {
//      exec(http("Adoption Certificate Upload4")
//        .post(BaseURL + "/la-document-manager?_csrf=#{csrfToken}")
//        .headers(Headers.commonHeader)
//        .headers(Headers.postHeader)
//        .header("accept", "application/json")
//        .header("content-type", "multipart/form-data")
//        .header("sec-fetch-dest", "empty")
//        .header("sec-fetch-mode", "cors")
//        .bodyPart(RawFileBodyPart("files[]", "2MB4.pdf")
//          .fileName("2MB4.pdf")
//          .transferEncoding("binary"))
//        .asMultipartForm
//        .check(jsonPath("$[0].id").saveAs("Document_ID4"))
//        .check(substring("2MB4.pdf")))
//    }
//    .pause(ThinkTime)


    /*======================================================================================
    * Choose a Document to Upload 5
    ======================================================================================*/

//    .group("AD_974_Upload_Document5") {
//      exec(http("Adoption Certificate Upload5")
//        .post(BaseURL + "/la-document-manager?_csrf=#{csrfToken}")
//        .headers(Headers.commonHeader)
//        .headers(Headers.postHeader)
//        .header("accept", "application/json")
//        .header("content-type", "multipart/form-data")
//        .header("sec-fetch-dest", "empty")
//        .header("sec-fetch-mode", "cors")
//        .bodyPart(RawFileBodyPart("files[]", "2MB5.pdf")
//          .fileName("2MB5.pdf")
//          .transferEncoding("binary"))
//        .asMultipartForm
//        .check(jsonPath("$[0].id").saveAs("Document_ID5"))
//        .check(substring("2MB5.pdf")))
//    }
//    .pause(ThinkTime)


    /*======================================================================================
    * Document Upload Submit
    ======================================================================================*/

    .group("AD_975_DocumentUploadSubmit") {
      exec(http("Document Upload Submit")
        .post(BaseURL + "/la-portal/la-portal/upload-your-documents")
        .headers(Headers.commonHeader)
        .headers(Headers.postHeader)
        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("locale", "en")
        .formParam("laUploadedFiles", """[{"id":"#{Document_ID3}","name":"2MB3.pdf"},{"id":"#{Document_ID2}","name":"2MB2.pdf"},{"id":"#{Document_ID1}","name":"2MB.pdf"}]""")
        .formParam("laCannotUpload", "")
        .check(regex("""id="upload-documents-status" class="govuk-tag app-task-list__tag govuk-tag--blue">Completed""")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * 'Review and Submit' Event
    ======================================================================================*/

    .group("AD_980_Review_And_Submit") {
      exec(http("Review and Submit")
        .get(BaseURL + "/la-portal/check-your-answers")
        .headers(Headers.commonHeader)
        .check(CsrfCheck.save)
        .check(substring("Check your answers")))
    }
    .pause(ThinkTime)


    /*======================================================================================
    * Check your answers
    ======================================================================================*/

//    .group("AD_990_Check_Your_Answers") {
//      exec(http("Check your answers")
//        .post(BaseURL + "/la-portal/check-your-answers")
//        .headers(Headers.commonHeader)
//        .headers(Headers.postHeader)
//        .formParam("_csrf", "#{csrfToken}")
//        .formParam("locale", "en")
//        .formParam("todoVar", "")
//        .check(substring("Statement of truth")))
//    }
//    .pause(ThinkTime)
//
//
//    /*======================================================================================
//    * Statement of truth
//    ======================================================================================*/
//
//    .group("AD_1000_Statement_Of_Truth") {
//      exec(http("Statement of truth")
//        .post(BaseURL + "/la-portal/statement-of-truth")
//        .headers(Headers.commonHeader)
//        .headers(Headers.postHeader)
//        .formParam("_csrf", "#{csrfToken}")
//        .formParam("locale", "en")
//        .formParam("laSotFullName", "#{randomString}laSotFullName")
//        .formParam("laSotJobtitle", "#{randomString}laSotJobtitle")
//        .formParam("laNameSot", "#{randomString}laNameSot")
//        .formParam("laStatementOfTruth", "")
//        .formParam("laStatementOfTruth", "checked")
//        .check(substring("Your application has been submitted")))
//    }
//    .pause(ThinkTime)

}
