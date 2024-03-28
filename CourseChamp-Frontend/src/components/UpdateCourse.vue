<template>
  <div class="update-course-container">
    <AdminNavBar/>
    <div class="form-container">
      <form @submit.prevent="updateCourse">
        <div class="form-group">
          <p>Select the course you want to update: </p>
          <select id="courseCode" @change="onCouseCodeChange($event)" v-model="courseCode" required>
          <option value="" disabled>Select course</option>
          <option v-for="course in availableCourses" :key="course.courseCode" :value="course.courseCode">{{ course.courseCode }}</option>
          </select>
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Name:</label>
          <input type="text" id="name" v-model="name" required placeholder="Ex: Software Engineering Practice">
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Description:</label>
          <textarea id="description" v-model="description" required
            placeholder="Ex: This is a really fun course"></textarea>
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Syllabus:</label>
          <textarea id="description" v-model="syllabus" required
            placeholder="Ex: Schedule, Grading breakdown, etc."></textarea>
        </div>
        <div class="msg">{{ msg }}</div>
        <div class="submit-container">
          <button @click="updateCourse" type="submit" :disabled="!courseCode || !name || !description || !syllabus"
            class="submit-button">
            Update
            <span class="arrow-icon">
              <img src="../assets/arrow.png" alt="Arrow">
            </span>
          </button>
          <button @click="deleteCourse" type="delete" :disabled="!courseCode" class="submit-button delete-button">
            Delete Course
            <span class="arrow-icon">
              <img src="../assets/arrow.png" alt="Arrow">
            </span>
          </button>
        </div>
      </form>
    </div>
    <button @click="redirectToAdminHome" class="home-button">Return to Home</button>  </div>
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
  data() {
    return {
      courseCode: '',
      name: '',
      description: '',
      syllabus: '',
      msg: '',
      availableCourses: [],
    };
  },
  mounted() {
    // Assuming you're making an API call to fetch reviews
    this.fetchCourses();
  },
  methods: {
    redirectToAdminHome() {
      this.$router.push('/adminhome');
    },
    fetchCourses() {
      axiosClient.get('/courses')
        .then(response => {
          this.availableCourses = response.data;
        })
        .catch(error => {
          console.error('Error fetching courses:', error);
          this.availableCourses = []
        });
    },
    updateCourse() {
        const courseData = {
          department: this.courseCode.substring(0,4),
          courseNumber: Number(this.courseCode.substring(4)),
          name: this.name,
          description: this.description,
          syllabus: this.syllabus
        };
        axiosClient.put('/course/' + this.courseCode + '/update/', courseData).then(response => {
          this.msg = 'Course updated Successfully!'
        }).catch(error => {
          this.msg = error.response.data
        })
      },
      onCouseCodeChange(){
        axiosClient.get('/course/' + this.courseCode).then(response => {
          this.name= response.data.name
          this.description= response.data.description
          this.syllabus= response.data.syllabus
        }).catch(error => {
          console.error(error.response)
        })
      },
      deleteCourse(){
        axiosClient.delete('/course/delete/' + this.courseCode).then(response => {
          this.fetchCourses()
          this.courseCode = ''
          this.name = ''
          this.description = ''
          this.syllabus = ''
          this.msg = 'Course deleted Successfully!'
        }).catch(error => {
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
  background-color: #d4ded7;  /* Sage but better */
  padding: 20px;
  border-radius: 10px;
  margin-top: 80px;  /* Add a top margin */
  width: 50%;  /* Adjust width as needed */
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

  .submit-button, .delete-button {
    background-color: #386849; /* Bottle Green */
    color: #ffffff; /* White */
    padding: 10px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    width: auto; /* Adjust width as needed */
    justify-content: space-between;
  }

  .submit-button .arrow-icon img, .delete-button .arrow-icon img {
    width: 16px; /* Adjust width as needed */
    height: 16px; /* Adjust height as needed */
  }

  .submit-button .arrow-icon, .delete-button .arrow-icon {
    background-color: #ffffff; /* White */
    border-radius: 2px; /* Smaller radius for the arrow icon box */
    padding: 5px;
    margin-left: 8px;
  }
</style>