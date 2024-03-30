<template>
  <div>
    <StudentNavBar/>
    
    <div class="form-container">
      <form @submit.prevent="submitReview">
        <div class="form-group">
          <label class="input-label">Select the course you are rating:</label>
          <select id="courseCode" v-model="courseCode" required>
            <option value="" disabled>Select your course</option>
            <option v-for="course in courses" :key="course.courseCode" :value="course.courseCode">{{ course.courseCode }}</option>
          </select>
        </div>
        <div class="form-group rating-group">
          <label class="input-label">Enter your rating:</label>
          <div class="rating-options">
            <label v-for="num in 5" :key="num" class="rating-label">
              <input type="radio" name="rating" :value="num" v-model="rating" required>{{ num }}
            </label>
          </div>
        </div>
        <div class="form-group">
          <label class="input-label">Enter your review:</label>
          <textarea id="text" v-model="text" required placeholder="Ex: I loved this class. It's a must take in U1!"></textarea>
        </div>
        <div class="form-group">
          <label class="input-label">Enter the semester for which you are rating the course:</label>
          <select id="semester" v-model="semester" required>
            <option value="" disabled>Select the semester</option>
            <option v-for="semester in semesters" :key="semester.code" :value="semester.code">{{ semester.name }}</option>
          </select>
        </div>
        <div class="msg">{{ msg }}</div>
        <div class="submit-container">
          <button type="submit" :disabled="!courseCode || !rating || !text" class="submit-button">
            Submit
            <span class="arrow-icon">
              <img src="../assets/arrow.png" alt="Arrow">
            </span>
          </button>
        </div>
      </form>
    </div>
    <button @click="redirectToStudentHome">Return to Home</button>
  </div>
</template>

    
    
    
<script>
import axios from 'axios'
import { BTable, BButton, BModal, BPagination, BFormGroup, BInput, BInputGroup, BFormSelect, BFormCheckboxGroup, BFormCheckbox } from 'bootstrap-vue'
import CreateCourse from './CreateCourse.vue'
import UpdateCourse from './UpdateCourse.vue'
import config from '../../config'

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var axiosClient = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  components: {
    BTable,
    BButton,
    BModal,
    BPagination,
    BFormGroup,
    BInput,
    BInputGroup,
    BFormSelect,
    BFormCheckboxGroup,
    BFormCheckbox,
    CreateCourse,
    UpdateCourse
  },
  data() {
    return {
      sortBy: '', // Sort by which field
      sortDesc: false, // Sort direction
      sortDirection: 'asc', // Initial sort direction
      filter: '', // Filter value
      filterOn: [], // Fields to filter on
      perPage: 5, // Number of items per page
      currentPage: 1, // Current page
      totalRows: 0, // Total number of rows
      courses: [], // Array to store courses
      fields: [ // Table fields
        { key: 'courseCode', label: 'Course Code', sortable: true },
        { key: 'courseName', label: 'Course Name', sortable: true },
        { key: 'department', label: 'Department', sortable: true },
        { key: 'courseNumber', label: 'Course Number', sortable: true },
        { key: 'actions', label: 'Actions' } // Actions column
      ],
      showAddModal: false, // Flag to control the visibility of the add course modal
      showEditModal: false, // Flag to control the visibility of the edit course modal
      selectedCourse: null // Selected course for editing
    }
  },
  computed: {
    // Sort options based on table fields
    sortOptions() {
      return this.fields
        .filter(f => f.sortable)
        .map(f => ({ text: f.label, value: f.key }))
    }
  },
  methods: {
    // Fetch courses from the server
    fetchCourses() {
      // Make API call to fetch courses
      axiosClient.get('/courses')
        .then(response => {
          this.courses = response.data
          this.totalRows = this.courses.length
        })
        .catch(error => {
          console.error('Error fetching courses:', error)
        })
    },
    // Add a new course
    addCourse(course) {
      // Make API call to add course
      axiosClient.post('/course/create', course)
        .then(response => {
          this.fetchCourses() // Refresh courses after addition
          this.showAddModal = false // Close the add course modal
        })
        .catch(error => {
          console.error('Error adding course:', error)
        })
    },
    // Edit an existing course
    editCourse(course) {
      this.selectedCourse = course // Set the selected course
      this.showEditModal = true // Show the edit course modal
    },
    // Update a course after editing
    updateCourse(course) {
      // Make API call to update course
      axiosClient.put(`/course/${course.courseCode}/update`, course)
        .then(response => {
          this.fetchCourses() // Refresh courses after update
          this.showEditModal = false // Close the edit course modal
        })
        .catch(error => {
          console.error('Error updating course:', error)
        })
    },
    // Confirm and delete a course
    confirmDelete(course) {
      // Display confirmation message
      if (confirm(`Are you sure you want to delete the course "${course.courseCode}"?`)) {
        // Make API call to delete course
        axiosClient.delete(`/course/delete/${course.courseCode}`)
          .then(response => {
            this.fetchCourses() // Refresh courses after deletion
          })
          .catch(error => {
            console.error('Error deleting course:', error)
          })
      }
    },
    // Handler for course added event
    courseAdded(course) {
      this.addCourse(course)
    },
    // Handler for course updated event
    courseUpdated(course) {
      this.updateCourse(course)
    },
    // Handler for filter change event
    onFiltered(filteredItems) {
      this.totalRows = filteredItems.length
      this.currentPage = 1
    },
    // Handler for open add modal event
    openAddModal() {
      this.showAddModal = true
    }
  },
  mounted() {
    // Fetch courses when component is mounted
    this.fetchCourses()
  }
}
</script>
