package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Course
import com.example.data.model.GradeRecord
import com.example.data.model.GradeWithDetails
import com.example.data.model.ScheduleItemWithDetails
import com.example.data.model.SchedulePeriod
import com.example.data.model.Student
import com.example.data.model.StudentWithGrades
import com.example.data.model.Teacher
import com.example.data.model.TeacherAssignment
import com.example.data.model.TeacherWithAssignments
import com.example.data.repository.SchoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    DASHBOARD,
    SCHEDULES,
    GRADES,
    TEACHERS
}

class SchoolViewModel(private val repository: SchoolRepository) : ViewModel() {

    private val _currentTab = MutableStateFlow(AppTab.DASHBOARD)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _selectedDay = MutableStateFlow("Monday")
    val selectedDay: StateFlow<String> = _selectedDay.asStateFlow()

    private val _selectedGradeFilter = MutableStateFlow("All")
    val selectedGradeFilter: StateFlow<String> = _selectedGradeFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val teachers: StateFlow<List<Teacher>> = repository.allTeachers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val students: StateFlow<List<Student>> = repository.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val teacherAssignments: StateFlow<List<TeacherAssignment>> = repository.allTeacherAssignments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val schedules: StateFlow<List<SchedulePeriod>> = repository.allSchedulePeriods
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gradeRecords: StateFlow<List<GradeRecord>> = repository.allGradeRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val rawScheduleDetails = combine(schedules, courses, teachers) { schedulesList, coursesList, teachersList ->
        val coursesMap = coursesList.associateBy { it.id }
        val teachersMap = teachersList.associateBy { it.id }

        schedulesList.map { period ->
            val course = coursesMap[period.courseId]
            val teacher = teachersMap[period.teacherId]
            ScheduleItemWithDetails(
                schedule = period,
                courseTitle = course?.title ?: "Unknown Course",
                courseCode = course?.courseCode ?: "N/A",
                teacherName = teacher?.name ?: "Unassigned"
            )
        }
    }

    // Combined Schedule Items with details (Course name, Teacher name)
    val scheduleDetails: StateFlow<List<ScheduleItemWithDetails>> = combine(
        rawScheduleDetails,
        _selectedDay,
        _selectedGradeFilter,
        _searchQuery
    ) { items, day, gradeFilter, query ->
        items
            .filter { it.schedule.dayOfWeek.equals(day, ignoreCase = true) }
            .filter { gradeFilter == "All" || it.schedule.gradeSection.contains(gradeFilter, ignoreCase = true) }
            .filter {
                if (query.isBlank()) true else {
                    it.courseTitle.contains(query, ignoreCase = true) ||
                            it.teacherName.contains(query, ignoreCase = true) ||
                            it.schedule.gradeSection.contains(query, ignoreCase = true) ||
                            it.schedule.room.contains(query, ignoreCase = true)
                }
            }
            .sortedBy { it.schedule.periodNumber }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Combined Student Grade Cards & GPA
    val studentsWithGrades: StateFlow<List<StudentWithGrades>> = combine(
        students,
        gradeRecords,
        _searchQuery,
        _selectedGradeFilter
    ) { studentList, gradesList, query, gradeFilter ->
        val gradesByStudent = gradesList.groupBy { it.studentId }

        studentList
            .filter { gradeFilter == "All" || it.gradeLevel.equals(gradeFilter, ignoreCase = true) }
            .filter {
                if (query.isBlank()) true else {
                    it.name.contains(query, ignoreCase = true) ||
                            it.studentIdNumber.contains(query, ignoreCase = true) ||
                            it.homeroom.contains(query, ignoreCase = true)
                }
            }
            .map { student ->
                val stGrades = gradesByStudent[student.id] ?: emptyList()
                val avgScore = if (stGrades.isNotEmpty()) {
                    stGrades.map { (it.score / it.maxScore) * 100.0 }.average()
                } else 0.0

                val calculatedGpa = when {
                    stGrades.isEmpty() -> student.gpa
                    avgScore >= 93.0 -> 4.0
                    avgScore >= 90.0 -> 3.7
                    avgScore >= 87.0 -> 3.3
                    avgScore >= 83.0 -> 3.0
                    avgScore >= 80.0 -> 2.7
                    avgScore >= 77.0 -> 2.3
                    avgScore >= 73.0 -> 2.0
                    avgScore >= 70.0 -> 1.7
                    avgScore >= 60.0 -> 1.0
                    else -> 0.0
                }

                StudentWithGrades(
                    student = student,
                    grades = stGrades,
                    averageScorePercent = avgScore,
                    calculatedGpa = calculatedGpa
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Grade Details for lists
    val gradeDetailsList: StateFlow<List<GradeWithDetails>> = combine(
        gradeRecords,
        students,
        courses,
        _searchQuery
    ) { gradesList, studentList, coursesList, query ->
        val studentsMap = studentList.associateBy { it.id }
        val coursesMap = coursesList.associateBy { it.id }

        gradesList.map { grade ->
            GradeWithDetails(
                grade = grade,
                studentName = studentsMap[grade.studentId]?.name ?: "Unknown Student",
                courseTitle = coursesMap[grade.courseId]?.title ?: "Unknown Course"
            )
        }.filter {
            if (query.isBlank()) true else {
                it.studentName.contains(query, ignoreCase = true) ||
                        it.courseTitle.contains(query, ignoreCase = true) ||
                        it.grade.assignmentName.contains(query, ignoreCase = true) ||
                        it.grade.category.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Combined Teachers with Assignments
    val teachersWithAssignmentsList: StateFlow<List<TeacherWithAssignments>> = combine(
        teachers,
        teacherAssignments,
        courses,
        _searchQuery
    ) { teacherList, assignmentsList, coursesList, query ->
        val coursesMap = coursesList.associateBy { it.id }
        val assignmentsByTeacher = assignmentsList.groupBy { it.teacherId }

        teacherList
            .filter {
                if (query.isBlank()) true else {
                    it.name.contains(query, ignoreCase = true) ||
                            it.department.contains(query, ignoreCase = true) ||
                            it.officeRoom.contains(query, ignoreCase = true)
                }
            }
            .map { teacher ->
                val assignedCourseIds = assignmentsByTeacher[teacher.id]?.map { it.courseId } ?: emptyList()
                val assignedCoursesList = assignedCourseIds.mapNotNull { coursesMap[it] }
                TeacherWithAssignments(
                    teacher = teacher,
                    assignedCourses = assignedCoursesList
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setSelectedDay(day: String) {
        _selectedDay.value = day
    }

    fun setSelectedGradeFilter(grade: String) {
        _selectedGradeFilter.value = grade
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Actions
    fun addSchedulePeriod(
        day: String,
        periodNumber: Int,
        startTime: String,
        endTime: String,
        courseId: Long,
        teacherId: Long,
        gradeSection: String,
        room: String
    ) {
        viewModelScope.launch {
            repository.insertSchedulePeriod(
                SchedulePeriod(
                    dayOfWeek = day,
                    periodNumber = periodNumber,
                    startTime = startTime,
                    endTime = endTime,
                    courseId = courseId,
                    teacherId = teacherId,
                    gradeSection = gradeSection,
                    room = room
                )
            )
        }
    }

    fun deleteSchedulePeriod(period: SchedulePeriod) {
        viewModelScope.launch {
            repository.deleteSchedulePeriod(period)
        }
    }

    fun addGradeRecord(
        studentId: Long,
        courseId: Long,
        assignmentName: String,
        category: String,
        score: Double,
        maxScore: Double,
        dateRecorded: String,
        comments: String
    ) {
        viewModelScope.launch {
            repository.insertGradeRecord(
                GradeRecord(
                    studentId = studentId,
                    courseId = courseId,
                    assignmentName = assignmentName,
                    category = category,
                    score = score,
                    maxScore = maxScore,
                    dateRecorded = dateRecorded,
                    comments = comments
                )
            )
        }
    }

    fun deleteGradeRecord(record: GradeRecord) {
        viewModelScope.launch {
            repository.deleteGradeRecord(record)
        }
    }

    fun assignTeacherToCourse(teacherId: Long, courseId: Long, sectionName: String, classroom: String) {
        viewModelScope.launch {
            repository.insertTeacherAssignment(
                TeacherAssignment(
                    teacherId = teacherId,
                    courseId = courseId,
                    sectionName = sectionName,
                    classroom = classroom
                )
            )
        }
    }

    fun removeTeacherAssignment(teacherId: Long, courseId: Long) {
        viewModelScope.launch {
            repository.removeTeacherAssignment(teacherId, courseId)
        }
    }

    fun addTeacher(name: String, email: String, department: String, officeRoom: String, phone: String) {
        viewModelScope.launch {
            val colors = listOf("#2563EB", "#7C3AED", "#DB2777", "#D97706", "#059669", "#DC2626")
            val colorHex = colors[(name.hashCode().coerceAtLeast(0)) % colors.size]
            repository.insertTeacher(
                Teacher(
                    name = name,
                    email = email,
                    department = department,
                    officeRoom = officeRoom,
                    phone = phone,
                    avatarColorHex = colorHex
                )
            )
        }
    }

    fun addStudent(name: String, studentIdNumber: String, gradeLevel: String, homeroom: String, email: String) {
        viewModelScope.launch {
            repository.insertStudent(
                Student(
                    name = name,
                    studentIdNumber = studentIdNumber,
                    gradeLevel = gradeLevel,
                    homeroom = homeroom,
                    email = email,
                    gpa = 3.5
                )
            )
        }
    }
}
