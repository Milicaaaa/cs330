import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import rs.ac.metropolitan.cs330_pz_4380.data.PetDBAdapter.Companion.TAG

class UserDBAdapter(val context: Context) {

    private var dbHelper: DBHelper = DBHelper(context)
    private var db: SQLiteDatabase? = null

    fun open(): UserDBAdapter {
        db = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    fun insertUser(name: String?, surname: String?, username: String?, password: String?): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_NAME, name)
        initialValues.put(KEY_SURNAME, surname)
        initialValues.put(KEY_USERNAME, username)
        initialValues.put(KEY_PASSWORD, password)
        return db!!.insert(DATABASE_TABLE, null, initialValues)
    }

    fun updateUser(id: Long, name: String?, surname: String?, username: String?, password: String?): Int {
        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_SURNAME, surname)
            put(KEY_USERNAME, username)
            put(KEY_PASSWORD, password)
        }
        return db!!.update(DATABASE_TABLE, values, "$KEY_ROWID=?", arrayOf(id.toString()))
    }

    fun deleteUser(id: Long): Int {
        return db!!.delete(DATABASE_TABLE, "$KEY_ROWID=?", arrayOf(id.toString()))
    }

    fun getAllUsers(): Cursor {
        return db!!.query(
            DATABASE_TABLE,
            arrayOf(KEY_ROWID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_PASSWORD),
            null, null, null, null, null
        )
    }

    @Throws(SQLException::class)
    fun getUserByUsernameAndPassword(username: String, password: String): Cursor? {
        val dbHandler = UserDBAdapter(context)
        dbHandler.open()

        val mCursor = dbHandler.db?.query(
            true, DATABASE_TABLE, arrayOf(
                KEY_ROWID,
                KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_PASSWORD
            ), "$KEY_USERNAME = ? AND $KEY_PASSWORD = ?", arrayOf(username, password),
            null, null, null, null
        )

        mCursor?.moveToFirst()
        return mCursor
    }

    fun getUserById(id: Long): Cursor {
        return db!!.query(
            DATABASE_TABLE,
            arrayOf(KEY_ROWID, KEY_NAME, KEY_SURNAME, KEY_USERNAME, KEY_PASSWORD),
            "$KEY_ROWID=?", arrayOf(id.toString()), null, null, null
        )
    }

    companion object {
        const val KEY_ROWID = "_id"
        const val KEY_NAME = "first_name"
        const val KEY_SURNAME = "surname"
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
        const val DATABASE_NAME = "MyDB"
        const val DATABASE_TABLE = "user"
        const val DATABASE_VERSION = 4

        const val DATABASE_CREATE =
            ("create table user (_id integer primary key autoincrement, "
                    + "first_name text not null, surname text not null, username text not null, password text not null);")
    }

    class DBHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DATABASE_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                TAG, "Upgrading database from version $oldVersion to $newVersion, which will destroy all old data"
            )
            db.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE")
            onCreate(db)
        }
    }
}

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
)