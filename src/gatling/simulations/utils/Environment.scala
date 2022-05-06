package utils

object Environment {

  val baseUrl = "https://adoption-web.${env}.platform.hmcts.net"
  val idamUrl = "https://idam-web-public.${env}.platform.hmcts.net"
  val idamAPIURL = "https://idam-api.${env}.platform.hmcts.net"
  val paymentUrl = "https://www.payments.service.gov.uk"

  val thinkTime = 7

}
