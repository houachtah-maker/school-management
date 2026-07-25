package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScheduleItemWithDetails
import com.example.data.model.StudentWithGrades
import com.example.ui.components.GradeBadge
import com.example.ui.components.KpiMetricCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.PurpleAccent

@Composable
fun DashboardScreen(
    totalStudents: Int,
    totalTeachers: Int,
    totalCourses: Int,
    todaySchedules: List<ScheduleItemWithDetails>,
    topStudents: List<StudentWithGrades>,
    onAddGradeClick: () -> Unit,
    onAssignTeacherClick: () -> Unit,
    onAddScheduleClick: () -> Unit,
    onAddTeacherClick: () -> Unit,
    onViewSchedulesTab: () -> Unit,
    onViewGradesTab: () -> Unit,
    onViewTeachersTab: () -> Unit,
    modifier: Modifier = Modifier
) {
    val avgSchoolGpa = if (topStudents.isNotEmpty()) {
        topStudents.map { it.calculatedGpa }.average()
    } else 3.8

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // High School Metrics Overview
        item {
            Text(
                text = "School Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KpiMetricCard(
                        title = "Enrolled Students",
                        value = totalStudents.toString(),
                        icon = Icons.Default.Group,
                        color = IndigoPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    KpiMetricCard(
                        title = "Faculty Teachers",
                        value = totalTeachers.toString(),
                        icon = Icons.Default.AssignmentInd,
                        color = PurpleAccent,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KpiMetricCard(
                        title = "Active Courses",
                        value = totalCourses.toString(),
                        icon = Icons.Default.Book,
                        color = AmberAccent,
                        modifier = Modifier.weight(1f)
                    )
                    KpiMetricCard(
                        title = "Average School GPA",
                        value = String.format("%.2f", avgSchoolGpa),
                        icon = Icons.Default.Grade,
                        color = EmeraldAccent,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Quick Actions Section
        item {
            Text(
                text = "Administrative Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    QuickActionButton(
                        title = "Add Grade",
                        icon = Icons.Default.Add,
                        containerColor = IndigoPrimary,
                        onClick = onAddGradeClick,
                        testTag = "quick_add_grade"
                    )
                }
                item {
                    QuickActionButton(
                        title = "Assign Teacher",
                        icon = Icons.Default.AssignmentInd,
                        containerColor = PurpleAccent,
                        onClick = onAssignTeacherClick,
                        testTag = "quick_assign_teacher"
                    )
                }
                item {
                    QuickActionButton(
                        title = "New Schedule Block",
                        icon = Icons.Default.CalendarMonth,
                        containerColor = EmeraldAccent,
                        onClick = onAddScheduleClick,
                        testTag = "quick_add_schedule"
                    )
                }
                item {
                    QuickActionButton(
                        title = "Add Faculty Teacher",
                        icon = Icons.Default.PersonAdd,
                        containerColor = AmberAccent,
                        onClick = onAddTeacherClick,
                        testTag = "quick_add_teacher"
                    )
                }
            }
        }

        // Today's Timetable Snapshot
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monday Class Schedule (Snapshot)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = onViewSchedulesTab,
                    colors = ButtonDefaults.textButtonColors()
                ) {
                    Text("View Full")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (todaySchedules.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = "No periods scheduled for today.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    todaySchedules.take(4).forEach { item ->
                        ScheduleMiniRow(item = item)
                    }
                }
            }
        }

        // Top Performing Students / Academic Honor Roll
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Honor Roll & Top Student GPAs",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = onViewGradesTab,
                    colors = ButtonDefaults.textButtonColors()
                ) {
                    Text("View All")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                topStudents.take(4).forEach { studentWithGrades ->
                    HonorRollRow(studentWithGrades = studentWithGrades)
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ScheduleMiniRow(
    item: ScheduleItemWithDetails
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "P${item.schedule.periodNumber}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.courseTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "${item.teacherName} • ${item.schedule.room}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "${item.schedule.startTime} - ${item.schedule.endTime}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun HonorRollRow(
    studentWithGrades: StudentWithGrades
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = studentWithGrades.student.name.take(1),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = studentWithGrades.student.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "${studentWithGrades.student.gradeLevel} • Homeroom ${studentWithGrades.student.homeroom}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            GradeBadge(gpaOrScore = studentWithGrades.calculatedGpa)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = String.format("%.2f GPA", studentWithGrades.calculatedGpa),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}
