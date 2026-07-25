package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Course
import com.example.data.model.GradeRecord
import com.example.data.model.SchedulePeriod
import com.example.data.model.Student
import com.example.data.model.Teacher
import com.example.data.model.TeacherAssignment
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {
    // Teachers
    @Query("SELECT * FROM teachers ORDER BY name ASC")
    fun getAllTeachers(): Flow<List<Teacher>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacher(teacher: Teacher): Long

    @Update
    suspend fun updateTeacher(teacher: Teacher)

    @Delete
    suspend fun deleteTeacher(teacher: Teacher)

    // Students
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    // Courses
    @Query("SELECT * FROM courses ORDER BY title ASC")
    fun getAllCourses(): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course): Long

    // Teacher Assignments
    @Query("SELECT * FROM teacher_assignments")
    fun getAllTeacherAssignments(): Flow<List<TeacherAssignment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacherAssignment(assignment: TeacherAssignment): Long

    @Delete
    suspend fun deleteTeacherAssignment(assignment: TeacherAssignment)

    @Query("DELETE FROM teacher_assignments WHERE teacherId = :teacherId AND courseId = :courseId")
    suspend fun removeTeacherAssignment(teacherId: Long, courseId: Long)

    // Schedule Periods
    @Query("SELECT * FROM schedule_periods ORDER BY periodNumber ASC")
    fun getAllSchedulePeriods(): Flow<List<SchedulePeriod>>

    @Query("SELECT * FROM schedule_periods WHERE dayOfWeek = :day ORDER BY periodNumber ASC")
    fun getSchedulePeriodsByDay(day: String): Flow<List<SchedulePeriod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedulePeriod(period: SchedulePeriod): Long

    @Delete
    suspend fun deleteSchedulePeriod(period: SchedulePeriod)

    // Grade Records
    @Query("SELECT * FROM grade_records ORDER BY id DESC")
    fun getAllGradeRecords(): Flow<List<GradeRecord>>

    @Query("SELECT * FROM grade_records WHERE studentId = :studentId")
    fun getGradeRecordsForStudent(studentId: Long): Flow<List<GradeRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeRecord(gradeRecord: GradeRecord): Long

    @Delete
    suspend fun deleteGradeRecord(gradeRecord: GradeRecord)

    // Bulk Seed helper inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachers(teachers: List<Teacher>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<Student>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacherAssignments(assignments: List<TeacherAssignment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedulePeriods(periods: List<SchedulePeriod>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeRecords(records: List<GradeRecord>)

    @Query("SELECT COUNT(*) FROM teachers")
    suspend fun getTeacherCount(): Int
}
