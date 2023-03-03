package io.github.finkmoritz.springgrouprolepermissions

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

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
            body: U?,
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
    }
}
