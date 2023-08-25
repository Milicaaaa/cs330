import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import rs.ac.metropolitan.cs330_pz_4380.data.PetDBAdapter

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @Override
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(UserDBAdapter.DATABASE_CREATE)
        db.execSQL(PetDBAdapter.DATABASE_CREATE_PET)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(
            TAG, "Upgrading database from version $oldVersion to $newVersion, which will destroy all old data"
        )
        db.execSQL("DROP TABLE IF EXISTS ${UserDBAdapter.DATABASE_TABLE}")
        db.execSQL("DROP TABLE IF EXISTS ${PetDBAdapter.DATABASE_TABLE_PET}")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 4
        const val TAG = "DBHelper"
    }
}
