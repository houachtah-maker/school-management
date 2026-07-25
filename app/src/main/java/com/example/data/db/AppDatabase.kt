package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.SchoolDao
import com.example.data.model.Course
import com.example.data.model.GradeRecord
import com.example.data.model.SchedulePeriod
import com.example.data.model.Student
import com.example.data.model.Teacher
import com.example.data.model.TeacherAssignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Teacher::class,
        Student::class,
        Course::class,
        TeacherAssignment::class,
        SchedulePeriod::class,
        GradeRecord::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun schoolDao(): SchoolDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "high_school_db"
                )
                .addCallback(AppDatabaseCallback(coroutineScope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialDatabase(database.schoolDao())
                    }
                }
            }

            suspend fun populateInitialDatabase(dao: SchoolDao) {
                if (dao.getTeacherCount() == 0) {
                    dao.insertTeachers(SeedData.sampleTeachers)
                    dao.insertCourses(SeedData.sampleCourses)
                    dao.insertStudents(SeedData.sampleStudents)
                    dao.insertTeacherAssignments(SeedData.sampleTeacherAssignments)
                    dao.insertSchedulePeriods(SeedData.sampleSchedulePeriods)
                    dao.insertGradeRecords(SeedData.sampleGradeRecords)
                }
            }
        }
    }
}
