package io.github.finkmoritz.springgrouprolepermissions.user

import io.github.finkmoritz.springgrouprolepermissions.AbstractSystemTest
import io.github.finkmoritz.springgrouprolepermissions.entity.user.User
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Profile("!prod")
class UserSystemTest : AbstractSystemTest() {
    private val baseUrlUser: String = "$baseUrl/api/user"
    private var user: User? = null

    private val username = "testuser"
    private val password = "testpassword"

    @Test
    @Order(0)
    fun `given user when signing up then succeed`() {
        user = User(
            username = username,
            password = password,
        )
        val response = sendRequest(
            url = "$baseUrlUser/signUp",
            httpMethod = HttpMethod.POST,
            responseType = User::class.java,
            body = user,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        user = response.body

        Assertions.assertNotNull(user?.id)
        Assertions.assertEquals(username, user?.username)
    }

    @Test
    @Order(1)
    fun `given user when signing in then succeed`() {
        val response = sendRequest(
            url = "$baseUrlUser?id={id}",
            httpMethod = HttpMethod.GET,
            responseType = User::class.java,
            body = user,
            uriVariables = arrayOf(user?.id!!)
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        user = response.body

        Assertions.assertNotNull(user?.id)
        Assertions.assertEquals(username, user?.username)
    }

    @Test
    @Order(2)
    fun `given existing user when updating user then succeed`() {
        val modifiedUsername = "modifiedtestusername"
        val modifiedUser = user?.copyWith(
            username = modifiedUsername,
        )

        val response = sendRequest(
            url = "$baseUrlUser/",
            httpMethod = HttpMethod.PUT,
            responseType = User::class.java,
            body = modifiedUser,
            headers = mapOf(HttpHeaders.AUTHORIZATION to "Basic $username:$password")
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        val responseUser = response.body

        Assertions.assertEquals(responseUser?.id, user?.id)
        Assertions.assertEquals(responseUser?.username, modifiedUser?.username)

        user = responseUser
    }

    /*@Test
    @Order(3)
    fun `given existing user when updating user without token then fail`() {
        assertThrows<HttpClientErrorException> {
            val modifiedUser = UserDTO(
                id = user!!.id,
                username = "ModifiedTestUser",
                email = "modifiedtestuser@test.com"
            )

            sendRequest(
                "$baseUrlUser/",
                HttpMethod.PUT,
                UserDTO::class.java,
                modifiedUser,
            )
        }
    }

    @Test
    @Order(4)
    fun `given existing user when deleting user without token then fail`() {
        assertThrows<HttpClientErrorException> {
            sendRequest(
                "$baseUrlUser/${user!!.id}",
                HttpMethod.DELETE,
                Void::class.java,
                null,
            )
        }
    }

    @Test
    @Order(5)
    fun `given existing user when deleting user then succeed`() {
        val response = deleteUser()

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        Assertions.assertNotNull(token)
    }*/
}
