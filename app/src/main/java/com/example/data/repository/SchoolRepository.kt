package com.example.data.repository

import com.example.data.dao.SchoolDao
import com.example.data.model.Course
import com.example.data.model.GradeRecord
import com.example.data.model.SchedulePeriod
import com.example.data.model.Student
import com.example.data.model.Teacher
import com.example.data.model.TeacherAssignment
import kotlinx.coroutines.flow.Flow

class SchoolRepository(private val dao: SchoolDao) {

    val allTeachers: Flow<List<Teacher>> = dao.getAllTeachers()
    val allStudents: Flow<List<Student>> = dao.getAllStudents()
    val allCourses: Flow<List<Course>> = dao.getAllCourses()
    val allTeacherAssignments: Flow<List<TeacherAssignment>> = dao.getAllTeacherAssignments()
    val allSchedulePeriods: Flow<List<SchedulePeriod>> = dao.getAllSchedulePeriods()
    val allGradeRecords: Flow<List<GradeRecord>> = dao.getAllGradeRecords()

    fun getSchedulePeriodsByDay(day: String): Flow<List<SchedulePeriod>> =
        dao.getSchedulePeriodsByDay(day)

    fun getGradeRecordsForStudent(studentId: Long): Flow<List<GradeRecord>> =
        dao.getGradeRecordsForStudent(studentId)

    // Teachers
    suspend fun insertTeacher(teacher: Teacher) = dao.insertTeacher(teacher)
    suspend fun updateTeacher(teacher: Teacher) = dao.updateTeacher(teacher)
    suspend fun deleteTeacher(teacher: Teacher) = dao.deleteTeacher(teacher)

    // Students
    suspend fun insertStudent(student: Student) = dao.insertStudent(student)
    suspend fun updateStudent(student: Student) = dao.updateStudent(student)
    suspend fun deleteStudent(student: Student) = dao.deleteStudent(student)

    // Courses
    suspend fun insertCourse(course: Course) = dao.insertCourse(course)

    // Teacher Assignments
    suspend fun insertTeacherAssignment(assignment: TeacherAssignment) =
        dao.insertTeacherAssignment(assignment)

    suspend fun deleteTeacherAssignment(assignment: TeacherAssignment) =
        dao.deleteTeacherAssignment(assignment)

    suspend fun removeTeacherAssignment(teacherId: Long, courseId: Long) =
        dao.removeTeacherAssignment(teacherId, courseId)

    // Schedule Periods
    suspend fun insertSchedulePeriod(period: SchedulePeriod) =
        dao.insertSchedulePeriod(period)

    suspend fun deleteSchedulePeriod(period: SchedulePeriod) =
        dao.deleteSchedulePeriod(period)

    // Grade Records
    suspend fun insertGradeRecord(gradeRecord: GradeRecord) =
        dao.insertGradeRecord(gradeRecord)

    suspend fun deleteGradeRecord(gradeRecord: GradeRecord) =
        dao.deleteGradeRecord(gradeRecord)
}
