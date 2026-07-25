package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.db.AppDatabase
import com.example.data.model.Teacher
import com.example.data.repository.SchoolRepository
import com.example.ui.components.SchoolHeaderBar
import com.example.ui.screens.AddGradeDialog
import com.example.ui.screens.AddScheduleDialog
import com.example.ui.screens.AddStudentDialog
import com.example.ui.screens.AddTeacherDialog
import com.example.ui.screens.AssignTeacherDialog
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GradesScreen
import com.example.ui.screens.SchedulesScreen
import com.example.ui.screens.TeachersScreen
import com.example.ui.theme.HighSchoolManagerTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.SchoolViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HighSchoolManagerTheme {
                HighSchoolApp()
            }
        }
    }
}

class SchoolViewModelFactory(private val repository: SchoolRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SchoolViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SchoolViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun HighSchoolApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = remember { AppDatabase.getInstance(context, scope) }
    val repository = remember { SchoolRepository(database.schoolDao()) }
    val factory = remember { SchoolViewModelFactory(repository) }
    val viewModel: SchoolViewModel = viewModel(factory = factory)

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val selectedGrade by viewModel.selectedGradeFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val teachers by viewModel.teachers.collectAsStateWithLifecycle()
    val students by viewModel.students.collectAsStateWithLifecycle()
    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val scheduleDetails by viewModel.scheduleDetails.collectAsStateWithLifecycle()
    val studentsWithGrades by viewModel.studentsWithGrades.collectAsStateWithLifecycle()
    val gradeDetailsList by viewModel.gradeDetailsList.collectAsStateWithLifecycle()
    val teachersWithAssignments by viewModel.teachersWithAssignmentsList.collectAsStateWithLifecycle()

    // Dialog state management
    var showAddScheduleDialog by remember { mutableStateOf(false) }
    var showAddGradeDialog by remember { mutableStateOf(false) }
    var showAssignTeacherDialog by remember { mutableStateOf(false) }
    var initialTeacherForAssignment by remember { mutableStateOf<Teacher?>(null) }
    var showAddTeacherDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SchoolHeaderBar()
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("bottom_navigation")
            ) {
                NavigationBarItem(
                    selected = currentTab == AppTab.DASHBOARD,
                    onClick = { viewModel.selectTab(AppTab.DASHBOARD) },
                    icon = { Icon(imageVector = Icons.Default.Dashboard, contentDescription = "Overview") },
                    label = { Text("Overview") },
                    modifier = Modifier.testTag("nav_tab_overview")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.SCHEDULES,
                    onClick = { viewModel.selectTab(AppTab.SCHEDULES) },
                    icon = { Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Schedules") },
                    label = { Text("Schedules") },
                    modifier = Modifier.testTag("nav_tab_schedules")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.GRADES,
                    onClick = { viewModel.selectTab(AppTab.GRADES) },
                    icon = { Icon(imageVector = Icons.Default.Grade, contentDescription = "Grades") },
                    label = { Text("Grades") },
                    modifier = Modifier.testTag("nav_tab_grades")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.TEACHERS,
                    onClick = { viewModel.selectTab(AppTab.TEACHERS) },
                    icon = { Icon(imageVector = Icons.Default.AssignmentInd, contentDescription = "Teachers") },
                    label = { Text("Teachers") },
                    modifier = Modifier.testTag("nav_tab_teachers")
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                AppTab.DASHBOARD -> {
                    DashboardScreen(
                        totalStudents = students.size,
                        totalTeachers = teachers.size,
                        totalCourses = courses.size,
                        todaySchedules = scheduleDetails,
                        topStudents = studentsWithGrades,
                        onAddGradeClick = { showAddGradeDialog = true },
                        onAssignTeacherClick = {
                            initialTeacherForAssignment = null
                            showAssignTeacherDialog = true
                        },
                        onAddScheduleClick = { showAddScheduleDialog = true },
                        onAddTeacherClick = { showAddTeacherDialog = true },
                        onViewSchedulesTab = { viewModel.selectTab(AppTab.SCHEDULES) },
                        onViewGradesTab = { viewModel.selectTab(AppTab.GRADES) },
                        onViewTeachersTab = { viewModel.selectTab(AppTab.TEACHERS) }
                    )
                }

                AppTab.SCHEDULES -> {
                    SchedulesScreen(
                        selectedDay = selectedDay,
                        onDaySelect = { viewModel.setSelectedDay(it) },
                        selectedGrade = selectedGrade,
                        onGradeSelect = { viewModel.setSelectedGradeFilter(it) },
                        searchQuery = searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        schedulesList = scheduleDetails,
                        onAddScheduleClick = { showAddScheduleDialog = true },
                        onDeleteScheduleClick = { viewModel.deleteSchedulePeriod(it) }
                    )
                }

                AppTab.GRADES -> {
                    GradesScreen(
                        studentsWithGrades = studentsWithGrades,
                        gradeDetailsList = gradeDetailsList,
                        selectedGrade = selectedGrade,
                        onGradeSelect = { viewModel.setSelectedGradeFilter(it) },
                        searchQuery = searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onAddGradeClick = { showAddGradeDialog = true },
                        onAddStudentClick = { showAddStudentDialog = true },
                        onDeleteGradeClick = { viewModel.deleteGradeRecord(it) }
                    )
                }

                AppTab.TEACHERS -> {
                    TeachersScreen(
                        teachersWithAssignments = teachersWithAssignments,
                        selectedGrade = selectedGrade,
                        onGradeSelect = { viewModel.setSelectedGradeFilter(it) },
                        searchQuery = searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onAddTeacherClick = { showAddTeacherDialog = true },
                        onAssignTeacherClick = { teacher ->
                            initialTeacherForAssignment = teacher
                            showAssignTeacherDialog = true
                        },
                        onRemoveAssignment = { teacherId, courseId ->
                            viewModel.removeTeacherAssignment(teacherId, courseId)
                        }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddScheduleDialog) {
        AddScheduleDialog(
            teachers = teachers,
            courses = courses,
            defaultDay = selectedDay,
            onDismiss = { showAddScheduleDialog = false },
            onConfirm = { day, periodNum, start, end, courseId, teacherId, gradeSec, room ->
                viewModel.addSchedulePeriod(day, periodNum, start, end, courseId, teacherId, gradeSec, room)
                showAddScheduleDialog = false
            }
        )
    }

    if (showAddGradeDialog) {
        AddGradeDialog(
            students = students,
            courses = courses,
            onDismiss = { showAddGradeDialog = false },
            onConfirm = { studentId, courseId, assignmentName, category, score, maxScore, date, comments ->
                viewModel.addGradeRecord(studentId, courseId, assignmentName, category, score, maxScore, date, comments)
                showAddGradeDialog = false
            }
        )
    }

    if (showAssignTeacherDialog) {
        AssignTeacherDialog(
            initialTeacher = initialTeacherForAssignment,
            teachers = teachers,
            courses = courses,
            onDismiss = { showAssignTeacherDialog = false },
            onConfirm = { teacherId, courseId, sectionName, classroom ->
                viewModel.assignTeacherToCourse(teacherId, courseId, sectionName, classroom)
                showAssignTeacherDialog = false
            }
        )
    }

    if (showAddTeacherDialog) {
        AddTeacherDialog(
            onDismiss = { showAddTeacherDialog = false },
            onConfirm = { name, email, department, officeRoom, phone ->
                viewModel.addTeacher(name, email, department, officeRoom, phone)
                showAddTeacherDialog = false
            }
        )
    }

    if (showAddStudentDialog) {
        AddStudentDialog(
            onDismiss = { showAddStudentDialog = false },
            onConfirm = { name, studentId, gradeLevel, homeroom, email ->
                viewModel.addStudent(name, studentId, gradeLevel, homeroom, email)
                showAddStudentDialog = false
            }
        )
    }
}
