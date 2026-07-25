package com.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.Student
import com.example.data.model.Teacher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleDialog(
    teachers: List<Teacher>,
    courses: List<Course>,
    defaultDay: String = "Monday",
    onDismiss: () -> Unit,
    onConfirm: (
        day: String,
        periodNumber: Int,
        startTime: String,
        endTime: String,
        courseId: Long,
        teacherId: Long,
        gradeSection: String,
        room: String
    ) -> Unit
) {
    var day by remember { mutableStateOf(defaultDay) }
    var periodNumber by remember { mutableIntStateOf(1) }
    var startTime by remember { mutableStateOf("08:30 AM") }
    var endTime by remember { mutableStateOf("09:20 AM") }
    var selectedCourse by remember { mutableStateOf(courses.firstOrNull()) }
    var selectedTeacher by remember { mutableStateOf(teachers.firstOrNull()) }
    var gradeSection by remember { mutableStateOf("Grade 10-A") }
    var room by remember { mutableStateOf("Room 204") }

    var dayExpanded by remember { mutableStateOf(false) }
    var courseExpanded by remember { mutableStateOf(false) }
    var teacherExpanded by remember { mutableStateOf(false) }

    val daysList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Schedule Period", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Day Dropdown
                ExposedDropdownMenuBox(
                    expanded = dayExpanded,
                    onExpandedChange = { dayExpanded = !dayExpanded }
                ) {
                    OutlinedTextField(
                        value = day,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Day of Week") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dayExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = dayExpanded,
                        onDismissRequest = { dayExpanded = false }
                    ) {
                        daysList.forEach { d ->
                            DropdownMenuItem(
                                text = { Text(d) },
                                onClick = {
                                    day = d
                                    dayExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = periodNumber.toString(),
                        onValueChange = { periodNumber = it.toIntOrNull() ?: 1 },
                        label = { Text("Period #") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("period_num_input")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = gradeSection,
                        onValueChange = { gradeSection = it },
                        label = { Text("Class Section") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("grade_section_input")
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("Start Time") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("End Time") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Course Dropdown
                ExposedDropdownMenuBox(
                    expanded = courseExpanded,
                    onExpandedChange = { courseExpanded = !courseExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCourse?.title ?: "Select Course",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = courseExpanded,
                        onDismissRequest = { courseExpanded = false }
                    ) {
                        courses.forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c.title) },
                                onClick = {
                                    selectedCourse = c
                                    courseExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Teacher Dropdown
                ExposedDropdownMenuBox(
                    expanded = teacherExpanded,
                    onExpandedChange = { teacherExpanded = !teacherExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedTeacher?.name ?: "Select Teacher",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Faculty Teacher") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = teacherExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = teacherExpanded,
                        onDismissRequest = { teacherExpanded = false }
                    ) {
                        teachers.forEach { t ->
                            DropdownMenuItem(
                                text = { Text(t.name) },
                                onClick = {
                                    selectedTeacher = t
                                    teacherExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text("Classroom / Room #") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val course = selectedCourse
                    val teacher = selectedTeacher
                    if (course != null && teacher != null) {
                        onConfirm(
                            day,
                            periodNumber,
                            startTime,
                            endTime,
                            course.id,
                            teacher.id,
                            gradeSection,
                            room
                        )
                    }
                },
                modifier = Modifier.testTag("confirm_add_schedule")
            ) {
                Text("Add Period", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGradeDialog(
    students: List<Student>,
    courses: List<Course>,
    onDismiss: () -> Unit,
    onConfirm: (
        studentId: Long,
        courseId: Long,
        assignmentName: String,
        category: String,
        score: Double,
        maxScore: Double,
        dateRecorded: String,
        comments: String
    ) -> Unit
) {
    var selectedStudent by remember { mutableStateOf(students.firstOrNull()) }
    var selectedCourse by remember { mutableStateOf(courses.firstOrNull()) }
    var assignmentName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Exam") }
    var score by remember { mutableStateOf("95") }
    var maxScore by remember { mutableStateOf("100") }
    var comments by remember { mutableStateOf("") }

    var studentExpanded by remember { mutableStateOf(false) }
    var courseExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Exam", "Homework", "Quiz", "Project")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Grade Record", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Student Dropdown
                ExposedDropdownMenuBox(
                    expanded = studentExpanded,
                    onExpandedChange = { studentExpanded = !studentExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedStudent?.name ?: "Select Student",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Student") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = studentExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = studentExpanded,
                        onDismissRequest = { studentExpanded = false }
                    ) {
                        students.forEach { st ->
                            DropdownMenuItem(
                                text = { Text("${st.name} (${st.gradeLevel})") },
                                onClick = {
                                    selectedStudent = st
                                    studentExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Course Dropdown
                ExposedDropdownMenuBox(
                    expanded = courseExpanded,
                    onExpandedChange = { courseExpanded = !courseExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCourse?.title ?: "Select Course",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = courseExpanded,
                        onDismissRequest = { courseExpanded = false }
                    ) {
                        courses.forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c.title) },
                                onClick = {
                                    selectedCourse = c
                                    courseExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = assignmentName,
                    onValueChange = { assignmentName = it },
                    label = { Text("Assignment / Exam Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("assignment_title_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded }
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    category = cat
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = score,
                        onValueChange = { score = it },
                        label = { Text("Score") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("score_input")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = maxScore,
                        onValueChange = { maxScore = it },
                        label = { Text("Max Score") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = comments,
                    onValueChange = { comments = it },
                    label = { Text("Teacher Comments") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val st = selectedStudent
                    val c = selectedCourse
                    val sc = score.toDoubleOrNull() ?: 0.0
                    val mSc = maxScore.toDoubleOrNull() ?: 100.0
                    if (st != null && c != null && assignmentName.isNotBlank()) {
                        onConfirm(
                            st.id,
                            c.id,
                            assignmentName,
                            category,
                            sc,
                            mSc,
                            "2026-07-25",
                            comments
                        )
                    }
                },
                modifier = Modifier.testTag("confirm_add_grade")
            ) {
                Text("Save Grade", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignTeacherDialog(
    initialTeacher: Teacher? = null,
    teachers: List<Teacher>,
    courses: List<Course>,
    onDismiss: () -> Unit,
    onConfirm: (teacherId: Long, courseId: Long, sectionName: String, classroom: String) -> Unit
) {
    var selectedTeacher by remember { mutableStateOf(initialTeacher ?: teachers.firstOrNull()) }
    var selectedCourse by remember { mutableStateOf(courses.firstOrNull()) }
    var sectionName by remember { mutableStateOf("Section 10-A") }
    var classroom by remember { mutableStateOf("Room 202") }

    var teacherExpanded by remember { mutableStateOf(false) }
    var courseExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Teacher to Course", fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Teacher Dropdown
                ExposedDropdownMenuBox(
                    expanded = teacherExpanded,
                    onExpandedChange = { teacherExpanded = !teacherExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedTeacher?.name ?: "Select Faculty Teacher",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Faculty Teacher") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = teacherExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = teacherExpanded,
                        onDismissRequest = { teacherExpanded = false }
                    ) {
                        teachers.forEach { t ->
                            DropdownMenuItem(
                                text = { Text("${t.name} (${t.department})") },
                                onClick = {
                                    selectedTeacher = t
                                    teacherExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Course Dropdown
                ExposedDropdownMenuBox(
                    expanded = courseExpanded,
                    onExpandedChange = { courseExpanded = !courseExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCourse?.title ?: "Select Course",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course to Assign") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = courseExpanded,
                        onDismissRequest = { courseExpanded = false }
                    ) {
                        courses.forEach { c ->
                            DropdownMenuItem(
                                text = { Text("${c.title} (${c.courseCode})") },
                                onClick = {
                                    selectedCourse = c
                                    courseExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = sectionName,
                    onValueChange = { sectionName = it },
                    label = { Text("Section Name (e.g. 10-A)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = classroom,
                    onValueChange = { classroom = it },
                    label = { Text("Classroom / Lab") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val t = selectedTeacher
                    val c = selectedCourse
                    if (t != null && c != null) {
                        onConfirm(t.id, c.id, sectionName, classroom)
                    }
                },
                modifier = Modifier.testTag("confirm_assign_teacher")
            ) {
                Text("Confirm Assignment", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddTeacherDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, email: String, department: String, officeRoom: String, phone: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("Mathematics") }
    var officeRoom by remember { mutableStateOf("Room 300") }
    var phone by remember { mutableStateOf("(555) 100-2000") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Teacher", fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = department, onValueChange = { department = it }, label = { Text("Department") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = officeRoom, onValueChange = { officeRoom = it }, label = { Text("Office Room") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name, email, department, officeRoom, phone)
                    }
                }
            ) {
                Text("Add Teacher", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddStudentDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, studentIdNumber: String, gradeLevel: String, homeroom: String, email: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var studentIdNumber by remember { mutableStateOf("HS-2026-0${(10..99).random()}") }
    var gradeLevel by remember { mutableStateOf("Grade 10") }
    var homeroom by remember { mutableStateOf("10-A") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Register New Student", fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Student Full Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = studentIdNumber, onValueChange = { studentIdNumber = it }, label = { Text("Student ID Number") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = gradeLevel, onValueChange = { gradeLevel = it }, label = { Text("Grade Level (e.g. Grade 10)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = homeroom, onValueChange = { homeroom = it }, label = { Text("Homeroom Section") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name, studentIdNumber, gradeLevel, homeroom, email.ifBlank { "${name.lowercase().replace(" ", ".")}@student.lincolnhigh.edu" })
                    }
                }
            ) {
                Text("Register Student", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
