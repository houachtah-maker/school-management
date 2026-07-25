package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class Teacher(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val department: String,
    val officeRoom: String,
    val phone: String,
    val avatarColorHex: String = "#3F51B5"
)

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentIdNumber: String,
    val name: String,
    val gradeLevel: String, // e.g. "Grade 9", "Grade 10", "Grade 11", "Grade 12"
    val homeroom: String,   // e.g. "10-A"
    val email: String,
    val gpa: Double = 0.0
)

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseCode: String, // e.g. "MATH-301"
    val title: String,      // e.g. "AP Calculus BC"
    val department: String, // e.g. "Mathematics"
    val gradeLevel: String  // e.g. "Grade 11-12"
)

@Entity(tableName = "teacher_assignments")
data class TeacherAssignment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teacherId: Long,
    val courseId: Long,
    val sectionName: String, // e.g. "Section 10-A"
    val classroom: String    // e.g. "Room 204"
)

@Entity(tableName = "schedule_periods")
data class SchedulePeriod(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayOfWeek: String,  // "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
    val periodNumber: Int,  // 1 to 7
    val startTime: String,  // "08:30 AM"
    val endTime: String,    // "09:20 AM"
    val courseId: Long,
    val teacherId: Long,
    val gradeSection: String, // "Grade 10-A", "Grade 11-B", etc.
    val room: String
)

@Entity(tableName = "grade_records")
data class GradeRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val courseId: Long,
    val assignmentName: String, // "Midterm Exam", "Physics Quiz #2", etc.
    val category: String,       // "Exam", "Homework", "Quiz", "Project"
    val score: Double,          // e.g. 95.0
    val maxScore: Double,       // e.g. 100.0
    val dateRecorded: String,   // "2026-07-20"
    val comments: String = ""
)

// Helper DTOs for UI lists with joined data
data class ScheduleItemWithDetails(
    val schedule: SchedulePeriod,
    val courseTitle: String,
    val courseCode: String,
    val teacherName: String
)

data class GradeWithDetails(
    val grade: GradeRecord,
    val studentName: String,
    val courseTitle: String
)

data class TeacherWithAssignments(
    val teacher: Teacher,
    val assignedCourses: List<Course>
)

data class StudentWithGrades(
    val student: Student,
    val grades: List<GradeRecord>,
    val averageScorePercent: Double,
    val calculatedGpa: Double
)
