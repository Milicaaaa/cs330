package rs.ac.metropolitan.cs330_pz_4380.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PetDBAdapter(val context: Context) {
    private var DBHelper: DatabaseHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    private class DatabaseHelper internal constructor(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            try {
                db.execSQL(DATABASE_CREATE_PET)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                TAG, "Upgrading the database from version $oldVersion to $newVersion. This will destroy existing data."
            )
            db.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE_PET")
            onCreate(db)
        }
    }

    fun open(): PetDBAdapter {
        db = DBHelper.writableDatabase
        return this
    }

    fun insertPets() {
        val pets = arrayOf(
            Pet("Fluffy", "Dog", "2023-08-21", "fluffy.jpg", "Healthy", "true", 1),
            Pet("Whiskers", "Cat", "2023-08-22", "whiskers.jpg", "Lost", "false", 2),
            Pet("Buddy", "Dog", "2023-08-23", "buddy.jpg", "Found", "true", 1)
        )

        val values = ContentValues()

        for (pet in pets) {
            values.put(KEY_NAME, pet.name)
            values.put(KEY_RACE, pet.race)
            values.put(KEY_DATE, pet.date)
            values.put(KEY_PICTURE, pet.picture)
            values.put(KEY_STATUS, pet.status)
            values.put(KEY_FOUND, pet.found)
            values.put(KEY_USERID, pet.userId)

            db?.insert(DATABASE_TABLE_PET, null, values)
        }
    }


    fun close() {
        DBHelper.close()
    }

    fun insertPet(name: String?, race: String?, date: String?, picture: String?, status: String?,
                  found: Boolean?, userId: Long?): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_NAME, name)
        initialValues.put(KEY_RACE, race)
        initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_PICTURE, picture)
        initialValues.put(KEY_STATUS, status)
        initialValues.put(KEY_FOUND, found)
        initialValues.put(KEY_USERID, userId)

        return db!!.insert(DATABASE_TABLE_PET, null, initialValues)
    }

    fun updatePet(rowId: Long, name: String?, race: String?, date: String?, picture: String?, status: String?,
                  found: Boolean?, userId: Long?): Boolean {
        val args = ContentValues()
        args.put(KEY_NAME, name)
        args.put(KEY_RACE, race)
        args.put(KEY_DATE, date)
        args.put(KEY_PICTURE, picture)
        args.put(KEY_STATUS, status)
        args.put(KEY_FOUND, found)
        args.put(KEY_USERID, userId)

        return db!!.update(DATABASE_TABLE_PET, args, "$KEY_ROWID = $rowId", null) > 0
    }

    fun getPetsByStatus(status: String): Cursor? {
        return db?.query(
            DATABASE_TABLE_PET,
            arrayOf(
                KEY_ROWID, KEY_NAME,
                KEY_RACE, KEY_DATE, KEY_PICTURE, KEY_STATUS, KEY_FOUND, KEY_USERID
            ),
            "$KEY_STATUS = ?",
            arrayOf(status),
            null, null, null
        )
    }


    fun deletePet(rowId: Long): Boolean {
        return db!!.delete(DATABASE_TABLE_PET, "$KEY_ROWID = $rowId", null) > 0
    }

    fun allPetsCursor(): Cursor? {
        return db?.query(
            DATABASE_TABLE_PET,
            arrayOf(
                KEY_ROWID, KEY_NAME,
                KEY_RACE, KEY_DATE, KEY_PICTURE, KEY_STATUS, KEY_FOUND, KEY_USERID
            ),
            null, null, null, null, null
        )
    }

    fun getAllPets(): Cursor {
        return db!!.query(
            DATABASE_TABLE_PET, null, null, null, null, null, null
        )
    }

    fun getPet(rowId: Long): Cursor? {
        val mCursor = db!!.query(
            true, DATABASE_TABLE_PET, null, "$KEY_ROWID = $rowId", null,
            null, null, null, null
        )
        mCursor?.moveToFirst()
        return mCursor
    }

    companion object {
        const val KEY_ROWID = "_id"
        const val KEY_NAME = "name"
        const val KEY_RACE = "race"
        const val KEY_DATE = "date"
        const val KEY_PICTURE = "picture"
        const val KEY_STATUS = "status"
        const val KEY_FOUND = "found"
        const val KEY_USERID = "user_id"
        const val TAG = "PetDBAdapter"
        const val DATABASE_NAME = "MyDB"
        const val DATABASE_TABLE_PET = "pet"
        const val DATABASE_VERSION = 4
        const val DATABASE_CREATE_PET =
            ("create table pet (_id integer primary key autoincrement, "
                    + "name text, race text not null, date text not null, picture text not null, "
                    + "status text not null, found boolean, user_id integer, "
                    + "foreign key (user_id) references user(_id));")
    }
}

data class Pet(
    val name: String,
    val race: String,
    val date: String,
    val picture: String,
    val status: String,
    val found: String,
    val userId: Int
)

