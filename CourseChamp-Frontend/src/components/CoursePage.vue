<template>
    <div>
      <CourseRating
        v-for="(review, index) in reviews"
        :key="index"
        :rating="review.rating"
        :semester="review.semester"
        :text="review.text"
        :courseCode="review.courseCode"
      />
      <p class="errorMsg"> {{ errorMsg }} </p>
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
        courseCode: '',
        reviews: [],
        errorMsg: '',
      };
    },
    mounted() {
      // Assuming you're making an API call to fetch reviews
      this.fetchReviews('ECSE202');
    },
    methods: {
      fetchReviews(courseCode) {
        // Assuming you're making an API call to fetch reviews
        // Replace this with your actual API call
        axiosClient.get('/getreviews/ECSE202')
          .then(response =>{
            this.reviews = response.data;
            console.log(this.reviews);
          })
          .catch(error => {
            console.error('Error fetching reviews:', error);
            this.errorMsg = error.response.data;
          });
      },
    },
  };
  </script>