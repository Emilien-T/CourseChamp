<template>
    <div class="manage-courses" style="margin-top: 20px;">
    <AdminNavBar />

      <!-- <h3 class="heading">Manage Courses</h3> -->
    
              <!-- Search input -->
        <div style="float:left;margin:10px;">
            <b-form-input v-model="filter" placeholder="Search by Course Code"></b-form-input>
        </div>

      <!-- Button for adding a new course -->
      <div style="float:right;margin:10px;">
        <b-button size="sm" @click="openAddModal">Add New Course</b-button>

      </div>
  
       <!-- Dropdown menu for filtering by department -->
    <div style="float:left;margin:10px;">
        <b-form-select v-model="selectedDepartment" :options="departmentOptions" placeholder="Filter by Department" @change="filterCoursesByDepartment(selectedDepartment)">
        </b-form-select>
    </div>

      <!-- Table to display courses -->
    
    <b-table striped hover :items="filteredCourses" :fields="fields" responsive="sm" show-empty>

        <!-- Actions column -->
        <template v-slot:cell(actions)="data">
          <b-button size="sm" class="mr-1" @click="editCourse(data.item)">Edit</b-button>
          <b-button size="sm" @click="confirmDelete(data.item)">Delete</b-button>
        </template>
        <!-- Name column -->
        <template v-slot:cell(name)="data">
          <a :href="`#${data.value.replace(/[^a-z]+/i,'-').toLowerCase()}`">{{ data.value }}</a>
        </template>
      </b-table>
  
      <!-- Modal for adding a new course -->
      <b-modal v-model="showAddModal" title="Add New Course" header-class="custom-modal-header" hide-footer hide-header>
        <!-- Include the CreateCourseForm component and emit event to close modal -->
        <div style="font-size: 20px; text-align: center; display: block; margin: 0 auto;">
          <label><strong>Add New Course</strong></label>
        </div>
        
        <CreateCourseForm @courseAdded="courseAdded" />
      </b-modal>
  
      <!-- Modal for editing a course -->

      <b-modal v-model="showEditModal" title="Edit Course" hide-footer hide-header>
        <div style="font-size: 20px; text-align: center; display: block; margin: 0 auto;">
          <label><strong>Edit Course</strong></label>
        </div>
        <UpdateCourseForm :course="selectedCourse" @courseUpdated="courseUpdated" @closeModal="courseUpdate" />
        </b-modal>

  
      <!-- Confirmation modal for deleting a course -->
      <b-modal v-model="showDeleteModal" title="Confirm Delete" ok-title="Delete" @ok="deleteCourse(selectedCourse.courseCode)">
        Are you sure you want to delete this course?
      </b-modal>
    </div>
  </template>
  
  <script>
  import { BTable, BButton, BModal, BPagination, BFormGroup, BFormSelect, BFormCheckbox, BInputGroup, BFormInput } from 'bootstrap-vue'
  import CreateCourseForm from './CreateCourseForm.vue';
  import UpdateCourse from './UpdateCourse.vue'
  import UpdateCourseForm from './UpdateCourseForm.vue';
  import AdminNavBar from './AdminNavBar.vue';
  


  import axios from 'axios';
  var config = require('../../config')
  
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  
  var axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })
  
  export default {
    name: 'ManageCourses',
    components: {
      BButton,
      BTable,
      BModal,
      BPagination,
      BFormGroup,
      BFormSelect,
      BFormCheckbox,
      BInputGroup,
      BFormInput,
      CreateCourseForm,
      UpdateCourse,
      UpdateCourseForm,
      AdminNavBar
    },
    data() {
      return {
        fields: [
          { key: 'courseCode', label: 'Course Code', sortable: true },
          { key: 'name', label: 'Course Name', sortable: true },
          { key: 'department', label: 'Department', sortable: true },
          { key: 'courseNumber', label: 'Course Number', sortable: true },
          { key: 'actions', label: 'Actions' }
        ],
        courses: [],
        currentPage: 1,
        totalRows: 0,
        perPage: 2,
        sortBy: '',
        sortDesc: false,
        sortDirection: 'asc',
        filter: null,
        filterOn: [],
        pageOptions: [2, 5, 10, 15],
        showAddModal: false,
        showEditModal: false,
        selectedCourse: null,
        selectedDepartment: null, 
        departmentOptions: [] 

      }
    },
    computed: {
      sortOptions() {
        return this.fields
          .filter(f => f.sortable)
          .map(f => {
            return { text: f.label, value: f.key }
          })
      },
     
  // Other computed properties remain unchanged
    filteredCourses() {
        if (!this.filter) {
        return this.courses;
        }
        return this.courses.filter(course => course.courseCode.toLowerCase().includes(this.filter.toLowerCase()));
    }


    },
    created() {
      this.fetchCourses();
    },
    methods: {
      async fetchCourses() {
        axiosClient.get('/courses')
          .then(response => {
            this.courses = response.data;
            this.totalRows = this.courses.length;
            const departments = new Set(this.courses.map(course => course.department));
            this.departmentOptions = [
                { value: null, text: 'All Departments' }, // Option for selecting all departments
                ...Array.from(departments).map(department => ({ value: department, text: department }))
            ];
          })
          .catch(error => {
            console.error('Error fetching courses:', error);
            this.courses = []
            this.departmentOptions = [{ value: null, text: 'All Departments' }]; // Reset departmentOptions if an error occurs
          });
      },
      openAddModal() {
        this.showAddModal = true
      },
      filterCoursesByDepartment(department) {
        console.log('Selected Department:', department);
        if (department === null) {
        console.log('All Departments');
        this.fetchCourses(); // Retrieve all courses
        } else {
            axiosClient.get('/courses')
                .then(response => {
                    // Filter courses based on the selected department
                    this.courses = response.data.filter(course => course.department === department);
                    this.totalRows = this.courses.length;
                })
                .catch(error => {
                    console.error('Error fetching courses:', error);
                    this.courses = [];
                });
        }
        },
      editCourse(course) {
        // this.selectedCourse = course
        console.log('YES')
        this.selectedCourse = course;
        this.showEditModal = true
        console.log(this.selectedCourse.courseCode)

        
      },
      confirmDelete(course) {
        if (confirm(`Are you sure you want to delete ${course.courseCode}?`)) {
          this.deleteCourse(course.courseCode);
        }
      },
      courseAdded(course) {
        this.courses.push(course)
        this.showAddModal = false
        this.fetchCourses();
      },
      courseUpdate(){
        this.showEditModal = false
        console.log('We tried')
        this.fetchCourses();
      },

      courseUpdated(updatedCourse) {
        const index = this.courses.findIndex(course => course.id === updatedCourse.id)
        if (index !== -1) {
          this.courses.splice(index, 1, updatedCourse)
        }
        this.showEditModal = false
        this.fetchCourses();
      },
      deleteCourse(courseCode) {
        axiosClient.delete(`/course/delete/${courseCode}`)
          .then(response => {
            console.log('Course deleted successfully!');
            this.fetchCourses();
          })
          .catch(error => {
            console.error('Error deleting course:', error.response.data);
            this.msg = error.response.data;
          });
          this.showEditModal = false
      }
    }
  }
  </script>
  
  <style scoped>
    ::v-deep .custom-modal-header {
    background: black;
    color: white;
    position: static;
    
  }
  </style>
  