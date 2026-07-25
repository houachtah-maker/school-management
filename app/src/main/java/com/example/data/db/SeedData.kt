package com.example.data.db

import com.example.data.model.Course
import com.example.data.model.GradeRecord
import com.example.data.model.SchedulePeriod
import com.example.data.model.Student
import com.example.data.model.Teacher
import com.example.data.model.TeacherAssignment

object SeedData {
    val sampleTeachers = listOf(
        Teacher(id = 1, name = "Dr. Sarah Jenkins", email = "s.jenkins@lincolnhigh.edu", department = "Mathematics", officeRoom = "Room 302", phone = "(555) 234-5678", avatarColorHex = "#2563EB"),
        Teacher(id = 2, name = "Mr. Marcus Vance", email = "m.vance@lincolnhigh.edu", department = "Science", officeRoom = "Lab 104", phone = "(555) 345-6789", avatarColorHex = "#7C3AED"),
        Teacher(id = 3, name = "Ms. Elena Rostova", email = "e.rostova@lincolnhigh.edu", department = "English", officeRoom = "Room 210", phone = "(555) 456-7890", avatarColorHex = "#DB2777"),
        Teacher(id = 4, name = "Mr. David Chen", email = "d.chen@lincolnhigh.edu", department = "Social Studies", officeRoom = "Room 118", phone = "(555) 567-8901", avatarColorHex = "#D97706"),
        Teacher(id = 5, name = "Mrs. Chloe Bennett", email = "c.bennett@lincolnhigh.edu", department = "Science", officeRoom = "Lab 106", phone = "(555) 678-9012", avatarColorHex = "#059669"),
        Teacher(id = 6, name = "Coach Ryan Miller", email = "r.miller@lincolnhigh.edu", department = "Physical Education", officeRoom = "Gym Office 1", phone = "(555) 789-0123", avatarColorHex = "#DC2626")
    )

    val sampleCourses = listOf(
        Course(id = 1, courseCode = "MATH-401", title = "AP Calculus BC", department = "Mathematics", gradeLevel = "Grade 11-12"),
        Course(id = 2, courseCode = "MATH-201", title = "Algebra II Honors", department = "Mathematics", gradeLevel = "Grade 10"),
        Course(id = 3, courseCode = "PHYS-301", title = "Physics Honors", department = "Science", gradeLevel = "Grade 11"),
        Course(id = 4, courseCode = "BIOL-101", title = "AP Biology", department = "Science", gradeLevel = "Grade 10-12"),
        Course(id = 5, courseCode = "ENG-202", title = "World Literature", department = "English", gradeLevel = "Grade 10"),
        Course(id = 6, courseCode = "HIST-301", title = "AP United States History", department = "Social Studies", gradeLevel = "Grade 11"),
        Course(id = 7, courseCode = "PE-100", title = "Physical Education & Fitness", department = "Physical Education", gradeLevel = "All Grades")
    )

    val sampleStudents = listOf(
        Student(id = 1, studentIdNumber = "HS-2026-001", name = "Maya Lin", gradeLevel = "Grade 11", homeroom = "11-A", email = "m.lin@student.lincolnhigh.edu", gpa = 3.92),
        Student(id = 2, studentIdNumber = "HS-2026-002", name = "Lucas Thorne", gradeLevel = "Grade 10", homeroom = "10-B", email = "l.thorne@student.lincolnhigh.edu", gpa = 3.65),
        Student(id = 3, studentIdNumber = "HS-2026-003", name = "Sophia Rodriguez", gradeLevel = "Grade 12", homeroom = "12-A", email = "s.rodriguez@student.lincolnhigh.edu", gpa = 3.88),
        Student(id = 4, studentIdNumber = "HS-2026-004", name = "Ethan Jackson", gradeLevel = "Grade 10", homeroom = "10-A", email = "e.jackson@student.lincolnhigh.edu", gpa = 3.42),
        Student(id = 5, studentIdNumber = "HS-2026-005", name = "Aaliyah Patel", gradeLevel = "Grade 11", homeroom = "11-B", email = "a.patel@student.lincolnhigh.edu", gpa = 4.00),
        Student(id = 6, studentIdNumber = "HS-2026-006", name = "Noah Kim", gradeLevel = "Grade 9", homeroom = "9-C", email = "n.kim@student.lincolnhigh.edu", gpa = 3.78)
    )

    val sampleTeacherAssignments = listOf(
        TeacherAssignment(id = 1, teacherId = 1, courseId = 1, sectionName = "Section 12-A", classroom = "Room 302"),
        TeacherAssignment(id = 2, teacherId = 1, courseId = 2, sectionName = "Section 10-A", classroom = "Room 302"),
        TeacherAssignment(id = 3, teacherId = 2, courseId = 3, sectionName = "Section 11-A", classroom = "Lab 104"),
        TeacherAssignment(id = 4, teacherId = 3, courseId = 5, sectionName = "Section 10-A", classroom = "Room 210"),
        TeacherAssignment(id = 5, teacherId = 4, courseId = 6, sectionName = "Section 11-B", classroom = "Room 118"),
        TeacherAssignment(id = 6, teacherId = 5, courseId = 4, sectionName = "Section 10-B", classroom = "Lab 106"),
        TeacherAssignment(id = 7, teacherId = 6, courseId = 7, sectionName = "Section PE-1", classroom = "Main Gymnasium")
    )

    val sampleSchedulePeriods = listOf(
        // Monday
        SchedulePeriod(id = 1, dayOfWeek = "Monday", periodNumber = 1, startTime = "08:30 AM", endTime = "09:20 AM", courseId = 1, teacherId = 1, gradeSection = "Grade 12-A", room = "Room 302"),
        SchedulePeriod(id = 2, dayOfWeek = "Monday", periodNumber = 2, startTime = "09:25 AM", endTime = "10:15 AM", courseId = 3, teacherId = 2, gradeSection = "Grade 11-A", room = "Lab 104"),
        SchedulePeriod(id = 3, dayOfWeek = "Monday", periodNumber = 3, startTime = "10:20 AM", endTime = "11:10 AM", courseId = 5, teacherId = 3, gradeSection = "Grade 10-A", room = "Room 210"),
        SchedulePeriod(id = 4, dayOfWeek = "Monday", periodNumber = 4, startTime = "11:15 AM", endTime = "12:05 PM", courseId = 6, teacherId = 4, gradeSection = "Grade 11-B", room = "Room 118"),
        SchedulePeriod(id = 5, dayOfWeek = "Monday", periodNumber = 5, startTime = "01:00 PM", endTime = "01:50 PM", courseId = 4, teacherId = 5, gradeSection = "Grade 10-B", room = "Lab 106"),
        SchedulePeriod(id = 6, dayOfWeek = "Monday", periodNumber = 6, startTime = "01:55 PM", endTime = "02:45 PM", courseId = 7, teacherId = 6, gradeSection = "Grade 9-C", room = "Gymnasium"),

        // Tuesday
        SchedulePeriod(id = 7, dayOfWeek = "Tuesday", periodNumber = 1, startTime = "08:30 AM", endTime = "09:20 AM", courseId = 2, teacherId = 1, gradeSection = "Grade 10-A", room = "Room 302"),
        SchedulePeriod(id = 8, dayOfWeek = "Tuesday", periodNumber = 2, startTime = "09:25 AM", endTime = "10:15 AM", courseId = 6, teacherId = 4, gradeSection = "Grade 11-B", room = "Room 118"),
        SchedulePeriod(id = 9, dayOfWeek = "Tuesday", periodNumber = 3, startTime = "10:20 AM", endTime = "11:10 AM", courseId = 1, teacherId = 1, gradeSection = "Grade 12-A", room = "Room 302"),
        SchedulePeriod(id = 10, dayOfWeek = "Tuesday", periodNumber = 4, startTime = "11:15 AM", endTime = "12:05 PM", courseId = 3, teacherId = 2, gradeSection = "Grade 11-A", room = "Lab 104"),
        SchedulePeriod(id = 11, dayOfWeek = "Tuesday", periodNumber = 5, startTime = "01:00 PM", endTime = "01:50 PM", courseId = 5, teacherId = 3, gradeSection = "Grade 10-A", room = "Room 210"),

        // Wednesday
        SchedulePeriod(id = 12, dayOfWeek = "Wednesday", periodNumber = 1, startTime = "08:30 AM", endTime = "09:20 AM", courseId = 3, teacherId = 2, gradeSection = "Grade 11-A", room = "Lab 104"),
        SchedulePeriod(id = 13, dayOfWeek = "Wednesday", periodNumber = 2, startTime = "09:25 AM", endTime = "10:15 AM", courseId = 5, teacherId = 3, gradeSection = "Grade 10-A", room = "Room 210"),
        SchedulePeriod(id = 14, dayOfWeek = "Wednesday", periodNumber = 3, startTime = "10:20 AM", endTime = "11:10 AM", courseId = 2, teacherId = 1, gradeSection = "Grade 10-A", room = "Room 302"),
        SchedulePeriod(id = 15, dayOfWeek = "Wednesday", periodNumber = 4, startTime = "11:15 AM", endTime = "12:05 PM", courseId = 4, teacherId = 5, gradeSection = "Grade 10-B", room = "Lab 106"),

        // Thursday
        SchedulePeriod(id = 16, dayOfWeek = "Thursday", periodNumber = 1, startTime = "08:30 AM", endTime = "09:20 AM", courseId = 1, teacherId = 1, gradeSection = "Grade 12-A", room = "Room 302"),
        SchedulePeriod(id = 17, dayOfWeek = "Thursday", periodNumber = 2, startTime = "09:25 AM", endTime = "10:15 AM", courseId = 4, teacherId = 5, gradeSection = "Grade 10-B", room = "Lab 106"),
        SchedulePeriod(id = 18, dayOfWeek = "Thursday", periodNumber = 3, startTime = "10:20 AM", endTime = "11:10 AM", courseId = 6, teacherId = 4, gradeSection = "Grade 11-B", room = "Room 118"),
        SchedulePeriod(id = 19, dayOfWeek = "Thursday", periodNumber = 4, startTime = "11:15 AM", endTime = "12:05 PM", courseId = 7, teacherId = 6, gradeSection = "Grade 9-C", room = "Gymnasium"),

        // Friday
        SchedulePeriod(id = 20, dayOfWeek = "Friday", periodNumber = 1, startTime = "08:30 AM", endTime = "09:20 AM", courseId = 2, teacherId = 1, gradeSection = "Grade 10-A", room = "Room 302"),
        SchedulePeriod(id = 21, dayOfWeek = "Friday", periodNumber = 2, startTime = "09:25 AM", endTime = "10:15 AM", courseId = 3, teacherId = 2, gradeSection = "Grade 11-A", room = "Lab 104"),
        SchedulePeriod(id = 22, dayOfWeek = "Friday", periodNumber = 3, startTime = "10:20 AM", endTime = "11:10 AM", courseId = 5, teacherId = 3, gradeSection = "Grade 10-A", room = "Room 210"),
        SchedulePeriod(id = 23, dayOfWeek = "Friday", periodNumber = 4, startTime = "11:15 AM", endTime = "12:05 PM", courseId = 1, teacherId = 1, gradeSection = "Grade 12-A", room = "Room 302")
    )

    val sampleGradeRecords = listOf(
        GradeRecord(id = 1, studentId = 1, courseId = 1, assignmentName = "Derivatives Midterm Exam", category = "Exam", score = 96.0, maxScore = 100.0, dateRecorded = "2026-07-15", comments = "Exceptional step-by-step problem solving."),
        GradeRecord(id = 2, studentId = 1, courseId = 3, assignmentName = "Thermodynamics Lab Report", category = "Project", score = 94.0, maxScore = 100.0, dateRecorded = "2026-07-18", comments = "Very clear data analysis and error calculation."),
        GradeRecord(id = 3, studentId = 2, courseId = 2, assignmentName = "Quadratic Equations Quiz", category = "Quiz", score = 88.0, maxScore = 100.0, dateRecorded = "2026-07-12", comments = "Minor error in factoring sign."),
        GradeRecord(id = 4, studentId = 2, courseId = 5, assignmentName = "Hamlet Character Analysis Essay", category = "Homework", score = 91.0, maxScore = 100.0, dateRecorded = "2026-07-19", comments = "Insightful thesis statement."),
        GradeRecord(id = 5, studentId = 3, courseId = 1, assignmentName = "Integrals & Area Under Curve", category = "Exam", score = 95.0, maxScore = 100.0, dateRecorded = "2026-07-14", comments = "Great work!"),
        GradeRecord(id = 6, studentId = 4, courseId = 2, assignmentName = "Polynomial Expansion Problem Set", category = "Homework", score = 82.5, maxScore = 100.0, dateRecorded = "2026-07-10", comments = "Good effort, review polynomial division."),
        GradeRecord(id = 7, studentId = 5, courseId = 6, assignmentName = "American Revolution DBQ Essay", category = "Project", score = 100.0, maxScore = 100.0, dateRecorded = "2026-07-21", comments = "Flawless historical argumentation and primary source integration.")
    )
}
