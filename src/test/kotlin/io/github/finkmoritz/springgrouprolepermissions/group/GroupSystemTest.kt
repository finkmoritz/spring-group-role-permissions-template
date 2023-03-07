package io.github.finkmoritz.springgrouprolepermissions.group

import io.github.finkmoritz.springgrouprolepermissions.AbstractSystemTest
import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Profile("!prod")
class GroupSystemTest : AbstractSystemTest() {
    private val baseUrlGroup: String = "$baseUrl/api/group"
    private lateinit var group: Group
    private val username = "user"
    private val password = "password"

    @Test
    @Order(0)
    fun `given valid group when creating group then succeed`() {
        val groupName = "testgroup"
        group = Group(
            name = groupName,
        )
        val response = sendRequestWithBasicAuth(
            username = username,
            password = password,
            url = baseUrlGroup,
            httpMethod = HttpMethod.POST,
            responseType = Group::class.java,
            body = group,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        group = response.body!!

        Assertions.assertNotNull(group.id)
        Assertions.assertEquals(groupName, group.name)
    }

    @Test
    @Order(1)
    fun `given existing group when getting the group then succeed`() {
        val groupId = group.id!!
        val response = sendRequestWithBasicAuth<Group, Group>(
            username = username,
            password = password,
            url = "$baseUrlGroup/$groupId",
            httpMethod = HttpMethod.GET,
            responseType = Group::class.java,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        group = response.body!!

        Assertions.assertEquals(groupId, group.id)
    }

    @Test
    @Order(2)
    fun `given existing group when updating group then succeed`() {
        val modifiedGroupName = "modifiedgroupname"
        val modifiedGroup = group.copyWith(
            name = modifiedGroupName,
        )

        val response = sendRequestWithBasicAuth(
            username = username,
            password = password,
            url = "$baseUrlGroup/${group.id}",
            httpMethod = HttpMethod.PUT,
            responseType = Group::class.java,
            body = modifiedGroup,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        group = response.body!!

        Assertions.assertEquals(group.id, modifiedGroup.id)
        Assertions.assertEquals(group.name, modifiedGroup.name)
    }

    @Test
    @Order(3)
    fun `given existing group when updating group without token then fail`() {
        assertThrows<HttpClientErrorException> {
            val modifiedGroup = group.copyWith(
                name = "modifiedGroupName",
            )

            sendRequest(
                url = "$baseUrlGroup/${group.id}",
                httpMethod = HttpMethod.PUT,
                responseType = Group::class.java,
                body = modifiedGroup,
            )
        }
    }

    @Test
    @Order(4)
    fun `given existing group when deleting group without token then fail`() {
        assertThrows<HttpClientErrorException> {
            sendRequest<Void, Void>(
                url = "$baseUrlGroup/${group.id}",
                httpMethod = HttpMethod.DELETE,
                responseType = Void::class.java,
            )
        }
    }

    @Test
    @Order(5)
    fun `given existing group when deleting group then succeed`() {
        val response = sendRequestWithBasicAuth<Void, Void>(
            username = username,
            password = password,
            url = "$baseUrlGroup/${group.id}",
            httpMethod = HttpMethod.DELETE,
            responseType = Void::class.java,
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }
}
