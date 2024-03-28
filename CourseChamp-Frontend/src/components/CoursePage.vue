<template>
  <div>
    <div id="selectCourse">
      <p>Select the course you want to view: </p>
      <select id="courseCode" v-model="courseCode" @change="onCouseCodeChange($event)" required>
        <option value="" disabled>Select your course</option>
        <option v-for="course in availableCourses" :key="course.courseCode" :value="course.courseCode">{{ course.courseCode }}</option>
      </select>
    </div>
    <div>
      <div v-if="isCourseValid" class="course-info">
      <h2>{{ course.name }}</h2>
      <p><strong>Course Code:</strong> {{ course.courseCode }}</p>
      <p><strong>Department:</strong> {{ course.department }}</p>
      <p><strong>Course Number:</strong> {{ course.courseNumber }}</p>
      <p><strong>Description:</strong> {{ course.description }}</p>
      <p v-if="course.syllabus"><strong>Syllabus:</strong> {{ course.syllabus }}</p>
      <p v-if="hasReviews"><strong>Average Rating:</strong> {{ calculateAverageRating }}</p>
      <p v-else>No reviews for this course.</p>
    </div>
    <div v-else class="errorMsg">Course not found</div>
  </div>
    <div class="course-rating-container">
      <CourseRating
        v-for="(review, index) in reviews"
        :key="index"
        :rating="review.rating"
        :semester="review.semester"
        :text="review.text"
        :courseCode="review.courseCode"
      />
    </div>
  </div>
  </template>
  
  <script>
  import CourseRating from './ViewReview.vue'; // Import your CourseRating component
  import axios from 'axios'
  var config = require('../../config')
  
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  
  var axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
    })

  export default {
    name: 'ReviewList',
    components: {
      CourseRating,
    },
    data() {
      return {
        courseCode: 'ECSE428',
        reviews: [],
        errorMsg: '',
        availableCourses: [],
        course: {
        department: '',
        courseNumber: 202,
        courseCode: '',
        name: '',
        description: '',
        syllabus: ''
      }
      };
    },
    computed: {
    isCourseValid() {
      const { courseCode, department, courseNumber, name, description, syllabus } = this.course;
      return courseCode && department && courseNumber && name && description;
    },
    hasReviews() {
      return this.reviews && this.reviews.length > 0;
    },
    calculateAverageRating() {
      let sum = 0;
      let total = 0;
      this.reviews.forEach(review => {
        sum += review.rating;
        total += 1;
      });
      return (sum / total).toFixed(1);
    }
  },
    mounted() {
      // Assuming you're making an API call to fetch reviews
      this.fetchReviews(this.courseCode);
      this.fetchCourse(this.courseCode);
      this.fetchCourses();
    },
    methods: {
      fetchReviews(courseCode) {
        // Assuming you're making an API call to fetch reviews
        // Replace this with your actual API call
        axiosClient.get(`/getreviews/${courseCode}`)
          .then(response =>{
            this.reviews = response.data;
            this.errorMsg = '';
            console.log(this.reviews);
          })
          .catch(error => {
            console.error('Error fetching reviews:', error);
            this.reviews = [];
            this.errorMsg = error.response.data;
          });
      },
      fetchCourses() {
        axiosClient.get('/courses')
        .then(response => {
          this.availableCourses = response.data;
        })
        .catch(error => {
          console.error('Error fetching courses:', error);
        });
      },
      onCouseCodeChange(event){
        this.fetchReviews(this.courseCode);
        this.fetchCourse(this.courseCode);
      },
      fetchCourse(courseCode){
        axiosClient.get(`/course/${courseCode}`).then(response=>{
            this.course = response.data;
          }).catch(error =>{
            console.error('Error fetching reviews:', error);
          })
      }
    },
  };
  </script>

<style scoped>
.course-info {
  background-color: #cfd9d3;
  border: 1px solid #ccc; /* Add border */
  border-radius: 5px; /* Add border radius for rounded corners */
  padding: 20px; /* Add padding */
  width: 400px; /* Set a specific width */
  margin: 0 auto; /* Center the div horizontally */
}
.course-rating-container {
  display: flex; /* Use flexbox layout */
  flex-wrap: wrap; /* Allow items to wrap to the next line */
  justify-content: center; /* Center items horizontally */
}
</style>