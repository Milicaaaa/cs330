package rs.ac.metropolitan.cs330_pz_4380

import DBHelper
import UserDBAdapter
import android.content.Context
import android.database.Cursor
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Mock
    private lateinit var mockContext: Context

    private lateinit var userDBAdapter: UserDBAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userDBAdapter = UserDBAdapter(mockContext)
        userDBAdapter.open()
    }

    @After
    fun tearDown() {
        userDBAdapter.close()
    }

    @Test
    fun testGetAllUsers() {
        val cursor: Cursor = userDBAdapter.getAllUsers()
        assertNotNull(cursor)
        assertTrue(cursor.moveToFirst())
    }

    @Test
    fun testGetUserByUsernameAndPassword() {
        val username = "testuser"
        val password = "testpassword"
        userDBAdapter.insertUser("Test", "User", username, password)
        val cursor = userDBAdapter.getUserByUsernameAndPassword(username, password)
        assertNotNull(cursor)
        if (cursor != null) {
            assertTrue(cursor.moveToFirst())
        }
    }

    @Test
    fun testGetUserById() {
        val id = userDBAdapter.insertUser("Get", "User", "getuser", "password")
        val cursor = userDBAdapter.getUserById(id)
        assertNotNull(cursor)
        assertTrue(cursor.moveToFirst())
    }

}