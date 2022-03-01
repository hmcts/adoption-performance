package uk.gov.hmcts.reform.Adoption.performance.simulations


import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation1 extends Simulation {

	val httpProtocol = http
		.baseUrl("https://adoption-web.aat.platform.hmcts.net")
		.inferHtmlResources()

	val headers_0 = Map(
		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "document",
		"sec-fetch-mode" -> "navigate",
		"sec-fetch-site" -> "none",
		"sec-fetch-user" -> "?1",
		"upgrade-insecure-requests" -> "1",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_2 = Map(
		"accept" -> "text/css,*/*;q=0.1",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "style",
		"sec-fetch-mode" -> "no-cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_4 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "script",
		"sec-fetch-mode" -> "no-cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_14 = Map(
		"accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "image",
		"sec-fetch-mode" -> "no-cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_18 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"origin" -> "https://idam-web-public.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "font",
		"sec-fetch-mode" -> "cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_24 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"content-type" -> "text/plain;charset=UTF-8",
		"origin" -> "https://idam-web-public.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "empty",
		"sec-fetch-mode" -> "cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_30 = Map(
		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"origin" -> "https://idam-web-public.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "document",
		"sec-fetch-mode" -> "navigate",
		"sec-fetch-site" -> "same-origin",
		"sec-fetch-user" -> "?1",
		"upgrade-insecure-requests" -> "1",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_33 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"origin" -> "https://adoption-web.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "font",
		"sec-fetch-mode" -> "cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_36 = Map(
		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "document",
		"sec-fetch-mode" -> "navigate",
		"sec-fetch-site" -> "same-origin",
		"sec-fetch-user" -> "?1",
		"upgrade-insecure-requests" -> "1",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_42 = Map(
		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"origin" -> "https://adoption-web.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "document",
		"sec-fetch-mode" -> "navigate",
		"sec-fetch-site" -> "same-origin",
		"sec-fetch-user" -> "?1",
		"upgrade-insecure-requests" -> "1",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_86 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"content-type" -> "text/plain;charset=UTF-8",
		"origin" -> "https://adoption-web.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "empty",
		"sec-fetch-mode" -> "cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_87 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"content-type" -> "text/plain;charset=UTF-8",
		"origin" -> "https://adoption-web.aat.platform.hmcts.net",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "empty",
		"sec-fetch-mode" -> "no-cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

	val headers_89 = Map(
		"User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS")

	val headers_120 = Map(
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-GB,en-US;q=0.9,en;q=0.8",
		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98""",
		"sec-ch-ua-mobile" -> "?0",
		"sec-ch-ua-platform" -> "macOS",
		"sec-fetch-dest" -> "empty",
		"sec-fetch-mode" -> "cors",
		"sec-fetch-site" -> "same-origin",
		"user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")

    val uri1 = "https://www.google-analytics.com/analytics.js"
    val uri3 = "https://idam-web-public.aat.platform.hmcts.net"

	val scn = scenario("RecordedSimulation1")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get(uri3 + "/ruxitagentjs_ICA27QVfjqrux_10233220201140653.js"),
            http("request_2")
			.get(uri3 + "/assets/stylesheets/application.css")
			.headers(headers_2),
            http("request_3")
			.get(uri3 + "/assets/stylesheets/govuk-template.css")
			.headers(headers_2),
            http("request_4")
			.get(uri3 + "/assets/javascripts/skiplink-target.js")
			.headers(headers_4),
            http("request_5")
			.get(uri3 + "/assets/javascripts/cookie-manager.js")
			.headers(headers_4),
            http("request_6")
			.get(uri3 + "/assets/javascripts/jquery-3.5.1.min.js")
			.headers(headers_4),
            http("request_7")
			.get(uri3 + "/assets/stylesheets/govuk-frontend-3.12.0.min.css")
			.headers(headers_2),
            http("request_8")
			.get(uri3 + "/assets/javascripts/govuk-frontend-3.12.0.min.js")
			.headers(headers_4),
            http("request_9")
			.get(uri3 + "/assets/stylesheets/fonts.css")
			.headers(headers_2),
            http("request_10")
			.get(uri3 + "/assets/javascripts/details.polyfill.js")
			.headers(headers_4),
            http("request_11")
			.get(uri3 + "/assets/javascripts/govuk-template.js")
			.headers(headers_4),
            http("request_12")
			.get(uri3 + "/ruxitagentjs_D_10233220201140653.js"),
            http("request_13")
			.get(uri1),
            http("request_14")
			.get(uri3 + "/assets/images/gov.uk_logotype_crown_invert_trans.png")
			.headers(headers_14),
            http("request_15")
			.get(uri3 + "/assets/stylesheets/govuk-template-print.css")
			.headers(headers_2),
            http("request_16")
			.get(uri3 + "/assets/stylesheets/images/gov.uk_logotype_crown.png")
			.headers(headers_14),
            http("request_17")
			.get(uri3 + "/assets/stylesheets/images/open-government-licence_2x.png")
			.headers(headers_14),
            http("request_18")
			.get(uri3 + "/assets/stylesheets/fonts/bold-b542beb274-v2.woff2")
			.headers(headers_18),
            http("request_19")
			.get(uri3 + "/assets/stylesheets/images/govuk-crest-2x.png")
			.headers(headers_14),
            http("request_20")
			.get(uri3 + "/assets/stylesheets/fonts/light-94a07e06a1-v2.woff2")
			.headers(headers_18)))
		.pause(1)
		.exec(http("request_21")
			.get(uri3 + "/assets/stylesheets/images/gov.uk_logotype_crown.png")
			.headers(headers_14)
			.resources(http("request_22")
			.get(uri3 + "/assets/stylesheets/images/open-government-licence_2x.png")
			.headers(headers_14),
            http("request_23")
			.get(uri3 + "/assets/stylesheets/images/govuk-crest-2x.png")
			.headers(headers_14),
            http("request_24")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&crc=1030886187&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0024_request.txt")),
            http("request_25")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&contentType=srTe&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&v=10233220201140652&crc=1704386729&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0025_request.txt"))
			.check(status.is(404))))
		.pause(1)
		.exec(http("request_26")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&crc=2014759742&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0026_request.txt"))
			.resources(http("request_27")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&crc=2535608462&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0027_request.txt"))
			.check(status.is(404)),
            http("request_28")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&contentType=srTe&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&v=10233220201140652&crc=1132468005&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0028_request.txt"))
			.check(status.is(404)))
			.check(status.is(404)))
		.pause(4)
		.exec(http("request_29")
			.post(uri3 + "/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&contentType=srTe&modifiedSince=1646129122308&rf=https%3A%2F%2Fidam-web-public.aat.platform.hmcts.net%2Flogin%3Fclient_id%3Dadoption-web%26response_type%3Dcode%26redirect_uri%3Dhttps%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Freceiver&bp=3&app=9178369a29f961fb&v=10233220201140652&crc=1888660809&en=q62q7mnb&end=1")
			.headers(headers_24)
			.body(RawFileBody("recordedsimulation1/0029_request.txt"))
			.check(status.is(404)))
		.pause(4)
		.exec(http("request_30")
			.post(uri3 + "/login?client_id=adoption-web&response_type=code&redirect_uri=https://adoption-web.aat.platform.hmcts.net/receiver")
			.headers(headers_30)
			.formParam("username", "adoption_citizen_test@mailinator.com")
			.formParam("password", "Password123")
			.formParam("save", "Sign in")
			.formParam("selfRegistrationEnabled", "true")
			.formParam("_csrf", "9df0a414-db9e-4e84-93ca-0ce6a7faa153")
			.resources(http("request_31")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_32")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_33")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2")
			.headers(headers_33),
            http("request_34")
			.get("/assets/fonts/bold-b542beb274-v2.woff2")
			.headers(headers_33),
            http("request_35")
			.get("/assets/images/govuk-crest-2x.png")
			.headers(headers_14)))
		.pause(3)
		.exec(http("request_36")
			.get("/applying-with")
			.headers(headers_36)
			.resources(http("request_37")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_38")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_39")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_40")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_41")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(20)
		.exec(http("request_42")
			.post("/applying-with")
			.headers(headers_42)
			.formParam("_csrf", "VWL02ny7-f0rLqCUW08BLzkC9oyyhVZ9vUME")
			.formParam("applyingWith", "withSpouseOrCivilPartner")
			.formParam("otherApplicantRelation", "")
			.resources(http("request_43")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_44")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_45")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_46")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_47")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(1)
		.exec(http("request_48")
			.get("/date-child-moved-in")
			.headers(headers_36)
			.resources(http("request_49")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_50")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_51")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_52")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_53")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(14)
		.exec(http("request_54")
			.post("/date-child-moved-in")
			.headers(headers_42)
			.formParam("_csrf", "mt2GE2Un-2Hli4vvIwpK35B3qvSLddZL3ZRU")
			.formParam("dateChildMovedIn-day", "31")
			.formParam("dateChildMovedIn-month", "3")
			.formParam("dateChildMovedIn-year", "2020")
			.resources(http("request_55")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_56")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_57")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_58")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_59")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(24)
		.exec(http("request_60")
			.get("/applicant1/full-name")
			.headers(headers_36)
			.resources(http("request_61")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_62")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_63")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_64")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_65")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(1)
		.exec(http("request_66")
			.post("/applicant1/full-name")
			.headers(headers_42)
			.formParam("_csrf", "cZ2BOtKk-LBljh90AD-6ssybm4IB4REmrh2k")
			.formParam("applicant1FirstNames", "Adoption")
			.formParam("applicant1LastNames", "Test")
			.resources(http("request_67")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_68")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_69")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_70")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_71")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(3)
		.exec(http("request_72")
			.get("/applicant1/other-names?remove=3a926ebd-7dc4-4a38-8008-ea62a2d39ee3")
			.headers(headers_36)
			.resources(http("request_73")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_74")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_75")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_76")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_77")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(18)
		.exec(http("request_78")
			.post("/applicant1/other-names")
			.headers(headers_42)
			.formParam("_csrf", "28iFxI4P-N91hu3v5nuhTFmGQJAqLs7Y17ss")
			.formParam("applicant1HasOtherNames", "Yes")
			.formParam("applicant1OtherFirstNames", "Previous")
			.formParam("applicant1OtherLastNames", "Name")
			.formParam("addButton", "addButton")
			.resources(http("request_79")
			.get("/ruxitagentjs_ICA27Vfgjqrux_10233220201140653.js"),
            http("request_80")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_81")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_82")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_83")
			.get("/assets/images/govuk-crest-2x.png"),
            http("request_84")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_85")
			.get("/assets/images/govuk-crest-2x.png")
			.headers(headers_14)))
		.pause(1)
		.exec(http("request_86")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Fother-names&bp=3&app=ea7c4b59f27d43eb&crc=2428839901&en=q62q7mnb&end=1")
			.headers(headers_86)
			.body(RawFileBody("recordedsimulation1/0086_request.txt"))
			.resources(http("request_87")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Fother-names&bp=3&app=ea7c4b59f27d43eb&crc=2105196010&en=q62q7mnb&end=1")
			.headers(headers_87)
			.body(RawFileBody("recordedsimulation1/0087_request.txt"))
			.check(status.is(400)),
            http("request_88")
			.get("/applicant1/dob")
			.headers(headers_36),
            http("request_89")
			.get("/ruxitagentjs_ICA27Vfgjqrux_10233220201140653.js")
			.headers(headers_89),
            http("request_90")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_91")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_92")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_93")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_94")
			.get("/assets/images/govuk-crest-2x.png"),
            http("request_95")
			.get("/assets/images/govuk-crest-2x.png")
			.headers(headers_14))
			.check(status.is(400)))
		.pause(1)
		.exec(http("request_96")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Fdob&bp=3&app=ea7c4b59f27d43eb&crc=2175954718&en=q62q7mnb&end=1")
			.headers(headers_86)
			.body(RawFileBody("recordedsimulation1/0096_request.txt"))
			.resources(http("request_97")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Fdob&bp=3&app=ea7c4b59f27d43eb&crc=1573579082&en=q62q7mnb&end=1")
			.headers(headers_87)
			.body(RawFileBody("recordedsimulation1/0097_request.txt")),
            http("request_98")
			.post("/applicant1/dob")
			.headers(headers_42)
			.formParam("_csrf", "JKRrQCqc-mEI0A8SVkddl03sQJnBCpxJwDMo")
			.formParam("applicant1DateOfBirth-day", "4")
			.formParam("applicant1DateOfBirth-month", "10")
			.formParam("applicant1DateOfBirth-year", "1998"),
            http("request_99")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_100")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_101")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_102")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_103")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(1)
		.exec(http("request_104")
			.post("/applicant1/occupation")
			.headers(headers_42)
			.formParam("_csrf", "s7p6TkAj-odn_oCB6ceYOaND-fsawho1Ax18")
			.formParam("applicant1Occupation", "Secondary School Teacher")
			.resources(http("request_105")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_106")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_107")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_108")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_109")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(2)
		.exec(http("request_110")
			.get("/applicant1/address/lookup")
			.headers(headers_36)
			.resources(http("request_111")
			.get("/ruxitagentjs_ICA27Vfgjqrux_10233220201140653.js")
			.headers(headers_89),
            http("request_112")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_113")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_114")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_115")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_116")
			.get("/assets/images/govuk-crest-2x.png"),
            http("request_117")
			.get("/assets/images/govuk-crest-2x.png")
			.headers(headers_14)))
		.pause(1)
		.exec(http("request_118")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Faddress%2Flookup&bp=3&app=ea7c4b59f27d43eb&crc=2525895567&en=q62q7mnb&end=1")
			.headers(headers_86)
			.body(RawFileBody("recordedsimulation1/0118_request.txt")))
		.pause(1)
		.exec(http("request_119")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Fapplicant1%2Faddress%2Flookup&bp=3&app=ea7c4b59f27d43eb&crc=3151023415&en=q62q7mnb&end=1")
			.headers(headers_86)
			.body(RawFileBody("recordedsimulation1/0119_request.txt"))
			.resources(http("request_120")
			.get("/csrf-token-error")
			.headers(headers_120)
			.check(status.is(400))))
		.pause(3)
		.exec(http("request_121")
			.get("/applicant1/address/manual")
			.headers(headers_36)
			.resources(http("request_122")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_123")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_124")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_125")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_126")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(1)
		.exec(http("request_127")
			.post("/applicant1/address/manual")
			.headers(headers_42)
			.formParam("_csrf", "Oksv7baq-FqDLyGuQoh02rJQk1ALCBGBJyRY")
			.formParam("applicant1Address1", "35 Made Up Lane")
			.formParam("applicant1Address2", "")
			.formParam("applicant1AddressTown", "London")
			.formParam("applicant1AddressCounty", "New County")
			.formParam("applicant1AddressPostcode", "AB12 3CD")
			.resources(http("request_128")
			.get("/main.22801efc7f52c871dd54.js")
			.headers(headers_4),
            http("request_129")
			.get("/main.570347a3e13561b4b193.css")
			.headers(headers_2),
            http("request_130")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_131")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_132")
			.get("/assets/images/govuk-crest-2x.png")))
		.pause(2)
		.exec(http("request_133")
			.post("/applicant1/contact-details")
			.headers(headers_42)
			.formParam("_csrf", "f3ifEJlP-FOplFCHhDiMAZ24Bp0Siz8MTwdI")
			.formParam("applicant1EmailAddress", "madeupemail@gmail.com")
			.formParam("applicant1PhoneNumber", "0123456789")
			.formParam("applicant1ContactDetailsConsent", "Yes")
			.resources(http("request_134")
			.get("/assets/fonts/light-94a07e06a1-v2.woff2"),
            http("request_135")
			.get("/assets/fonts/bold-b542beb274-v2.woff2"),
            http("request_136")
			.get("/assets/images/govuk-crest-2x.png"),
            http("request_137")
			.get("/assets/images/govuk-crest-2x.png")
			.headers(headers_14)))
		.pause(1)
		.exec(http("request_138")
			.post("/rb_bf24054dsx?type=js3&sn=v_4_srv_2_sn_55L8E7KN2AARDCHCMIGQI27JKQ6S38N0_perc_100000_ol_0_mul_1_app-3A9178369a29f961fb_1_app-3Aea7c4b59f27d43eb_1_rcs-3Acss_0&svrid=2&flavor=post&vi=SRRDPARMNMEHFLRJRMRQLOCVNWAVHCWT-0&modifiedSince=1646129122308&rf=https%3A%2F%2Fadoption-web.aat.platform.hmcts.net%2Ftask-list&bp=3&app=ea7c4b59f27d43eb&crc=2872854650&en=q62q7mnb&end=1")
			.headers(headers_86)
			.body(RawFileBody("recordedsimulation1/0138_request.txt"))
			.check(status.is(400)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}