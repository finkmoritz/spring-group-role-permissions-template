package io.github.finkmoritz.springgrouprolepermissions.groupuserrole

import io.github.finkmoritz.springgrouprolepermissions.AbstractSystemTest
import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import io.github.finkmoritz.springgrouprolepermissions.entity.user.User
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Profile("!prod")
class GroupUserRoleSystemTest : AbstractSystemTest() {
    private val baseUrlGroupUserRole: String = "$baseUrl/api/groupuserrole/group"
    private val username = "user"
    private val password = "password"
    private val groupId = 0
    private lateinit var member: User
    private val memberPassword = "pw"

    @BeforeAll
    fun `create member user`() {
        member = User(
            username = "member",
            password = memberPassword,
        )
        val response = sendRequest(
            url = "$baseUrl/api/user/signUp",
            httpMethod = HttpMethod.POST,
            responseType = User::class.java,
            body = member,
        )
        member = response.body!!
    }

    @AfterAll
    fun `remove member user`() {
        sendRequestWithBasicAuth<Void, Void>(
            username = member.username,
            password = memberPassword,
            url = "$baseUrl/api/user/${member.id}",
            httpMethod = HttpMethod.DELETE,
            responseType = Void::class.java,
        )
    }

    @Test
    @Order(0)
    fun `given user when assigning member role then succeed`() {
        val response = sendRequestWithBasicAuth<Group, Group>(
            username = username,
            password = password,
            url = "$baseUrlGroupUserRole/$groupId/user/${member.id}/role/1",
            httpMethod = HttpMethod.POST,
            responseType = Group::class.java,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    @Order(1)
    fun `given member when trying to revoke member role then fail`() {
        assertThrows<HttpClientErrorException> {
            sendRequestWithBasicAuth<Void, Void>(
                username = member.username,
                password = memberPassword,
                url = "$baseUrlGroupUserRole/$groupId/user/${member.id}/role/1",
                httpMethod = HttpMethod.DELETE,
                responseType = Void::class.java,
            )
        }
    }

    @Test
    @Order(2)
    fun `given admin when trying to revoke member role then succeed`() {
        val response = sendRequestWithBasicAuth<Void, Void>(
            username = username,
            password = password,
            url = "$baseUrlGroupUserRole/$groupId/user/${member.id}/role/1",
            httpMethod = HttpMethod.DELETE,
            responseType = Void::class.java,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }
}
