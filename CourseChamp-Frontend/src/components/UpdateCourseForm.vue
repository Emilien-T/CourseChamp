<template>
    <div class="update-course-container">
      <div class="form-container">
        <form @submit.prevent="updateCourse">
          <div class="form-group">
            <label class="input-label">Enter the Course Name:</label>
            <input type="text" id="name" v-model="course.name" required placeholder="Ex: Software Engineering Practice">
          </div>
          <div class="form-group">
            <label class="input-label">Enter the Course Description:</label>
            <textarea id="description" v-model="course.description" required placeholder="Ex: This is a really fun course"></textarea>
          </div>
          <div class="form-group">
            <label class="input-label">Enter the Course Syllabus:</label>
            <textarea id="syllabus" v-model="course.syllabus" required placeholder="Ex: Schedule, Grading breakdown, etc."></textarea>
          </div>
          <div class="msg">{{ msg }}</div>
          <div class="submit-container">
            <button @click="updateCourse" type="submit" :disabled="!course || !course.name || !course.description || !course.syllabus" class="submit-button">
              Update
              <span class="arrow-icon">
                <img src="../assets/arrow.png" alt="Arrow">
              </span>
            </button>
            <button @click="deleteCourse" type="delete" :disabled="!course" class="submit-button delete-button">
              Delete Course
              <span class="arrow-icon">
                <img src="../assets/arrow.png" alt="Arrow">
              </span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import AdminNavBar from './AdminNavBar.vue';
  import axios from 'axios'
  var config = require('../../config')
  
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  
  var axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })
  export default {
    components: {
      AdminNavBar
    },
    props: {
      course: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        msg: ''
      };
    },
    methods: {
      updateCourse() {
        const courseData = {
          department: this.course.courseCode.substring(0, 4),
          courseNumber: Number(this.course.courseCode.substring(4)),
          name: this.course.name,
          description: this.course.description,
          syllabus: this.course.syllabus
        };
  
        axiosClient.put('/course/' + this.course.courseCode + '/update/', courseData)
          .then(response => {
            this.msg = 'Course updated Successfully!'
            this.$emit('closeModal');
          })
          .catch(error => {
            this.msg = error.response.data
          })
      },
      deleteCourse() {
        axiosClient.delete('/course/delete/' + this.course.courseCode)
          .then(response => {
            this.msg = 'Course deleted Successfully!'
            this.$emit('courseDeleted');
            // Emit event to close the modal
            this.$emit('closeModal');
          })
          .catch(error => {
            this.msg = error.response.data
          })
      }
    }
  };
  </script>
  
  <style scoped>
  body {
    margin-top: 0px;
    background-color: #f7f8f8;
    /* Off White */
    height: 100vh;
    /* Full height of the viewport */
    box-sizing: border-box;
    /* Includes padding in the element's total width and height */
  }
  
  .home-button {
    margin-top: 20px;
  }
  
  header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
  }
  
  .back-link {
    color: #476141;
    /* Dark Fern */
    text-decoration: none;
  }
  
  .form-container {
    background-color: #d4ded7;
    /* Sage but better */
    padding: 20px;
    border-radius: 10px;
    margin-top: 80px;
    /* Add a top margin */
    width: 50%;
    /* Adjust width as needed */
    margin-left: auto;
    margin-right: auto;
  }
  
  .form-group label {
    font-weight: bold;
    color: #000000;
    /* Black for the labels */
  }
  
  .form-group {
    background-color: #d4ded7;
    /* Sage but better */
    padding: 10px;
    border-radius: 10px;
  }
  
  input[type="text"],
  textarea {
    background-color: #d4ded7;
    /* Sage but better */
    border: none;
    border-bottom: 1px solid #c1c1c2;
    /* Silver Chalice */
    color: #333a33;
    /* Dark Charcoal */
    width: 100%;
    margin-bottom: 15px;
  }
  
  input[type="text"]::placeholder,
  textarea::placeholder {
    font-weight: normal;
    color: #c1c1c2;
    /* Silver Chalice */
  }
  
  .submit-container {
    display: flex;
    justify-content: center;
    gap: 20px;
  }
  
  .submit-button,
  .delete-button {
    background-color: #386849;
    /* Bottle Green */
    color: #ffffff;
    /* White */
    padding: 10px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    width: auto;
    /* Adjust width as needed */
    justify-content: space-between;
  }
  
  .submit-button .arrow-icon img,
  .delete-button .arrow-icon img {
    width: 16px;
    /* Adjust width as needed */
    height: 16px;
    /* Adjust height as needed */
  }
  
  .submit-button .arrow-icon,
  .delete-button .arrow-icon {
    background-color: #ffffff;
    /* White */
    border-radius: 2px;
    /* Smaller radius for the arrow icon box */
    padding: 5px;
    margin-left: 8px;
  }
  </style>
  