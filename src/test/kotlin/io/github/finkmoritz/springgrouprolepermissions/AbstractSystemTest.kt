package io.github.finkmoritz.springgrouprolepermissions

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.*

abstract class AbstractSystemTest {
    companion object {
        @JvmStatic
        protected val restTemplate = RestTemplate()

        @JvmStatic
        protected val baseUrl = "http://localhost:8080"

        @JvmStatic
        protected fun <T, U> sendRequest(
            url: String,
            httpMethod: HttpMethod,
            responseType: Class<T>,
            body: U? = null,
            vararg uriVariables: Any,
            headers: Map<String, String>? = null,
        ): ResponseEntity<T> {
            val hdrs = HttpHeaders()
            headers?.forEach { h ->
                hdrs.add(h.key, h.value)
            }
            return restTemplate.exchange(
                url,
                httpMethod,
                HttpEntity<U>(body, hdrs),
                responseType,
                uriVariables,
            )
        }

        @JvmStatic
        protected fun <T, U> sendRequestWithBasicAuth(
            username: String,
            password: String,
            url: String,
            httpMethod: HttpMethod,
            responseType: Class<T>,
            body: U? = null,
            vararg uriVariables: Any,
            headers: Map<String, String>? = mutableMapOf(),
        ): ResponseEntity<T> {
            val hdrs = headers?.toMutableMap() ?: mutableMapOf()
            hdrs[HttpHeaders.AUTHORIZATION] = "Basic " + Base64.getEncoder().encodeToString("$username:$password".toByteArray())
            return sendRequest(
                url = url,
                httpMethod = httpMethod,
                responseType = responseType,
                body = body,
                uriVariables = uriVariables,
                headers = hdrs,
            )
        }
    }
}
