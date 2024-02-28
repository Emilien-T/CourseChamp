<template>
    <div class="create-review-container">
      <header>
        <h1>Create A Review</h1>
        <router-link to="/reviews" class="back-link">Back To Review</router-link>
      </header>
      <div class="form-container">
        <form @submit.prevent="submitReview">
          <div class="form-group">
            <label class="input-label">Enter the course code of the course you are rating:</label>
            <input type="text" id="courseCode" v-model="courseCode" required placeholder="Ex: ECSE200">
          </div>
          <div class="form-group">
            <label class="input-label">Enter your rating:</label>
            <input type="text" id="rating" v-model="rating" required placeholder="Ex: 5">
          </div>
          <div class="form-group">
            <label class="input-label">Enter your review:</label>
            <textarea id="text" v-model="text" required placeholder="Ex: I loved this class. It's a must take in U1! "></textarea>
          </div>
          <div class="form-group">
            <label class="input-label">Enter the semester for which you are rating the course:</label>
            <input type="text" id="semester" v-model="semester" required placeholder="Ex: W2023">
          </div>
          <div class="msg">{{ msg }}</div>
          <div class="submit-container">
            <button type="submit" :disabled="!rating || !text" class="submit-button">
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
  import Vue from 'vue'
  var config = require('../../config')
  
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  
  var axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })
  export default {
    data() {
      return {
        courseCode: '',
        rating: '',
        text: '',
        semester: '',
        msg: ''
      };
    },
    methods: {
      redirectToStudentHome() {
        // Redirect to the student signup page
        this.$router.push('/studenthome');
      },submitReview() {
        
        // Handle review submission (e.g., send data to server)
        const reviewData = {
          rating: this.rating,
          text: this.text,
          studentEmail: this.logginInEmail,
          courseCode: this.courseCode,
          semester: this.semester
        };
        console.log(reviewData); // Replace with your submission logic
        axiosClient.post('/review/create', reviewData).then(response =>{
          this.msg = `Review created Successfully!`
          this.rating = ''
          this.text = ''
        }  
        ).catch(error =>{
          if(error.response.status != 500){
            this.msg = error.response.data
          }
        })
      }
    }
  };
  </script>
  <style scoped>
  body{
    margin-top: 0px;
    background-color: #f7f8f8; /* Off White */
    height: 100vh; /* Full height of the viewport */
    box-sizing: border-box; /* Includes padding in the element's total width and height */
  }
  
  header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
  }
  
  .back-link {
    color: #476141; /* Dark Fern */
    text-decoration: none;
  }
  
  .form-container {
    background-color: #d4ded7; /* Sage but better */
    padding: 20px;
    border-radius: 10px;
    margin-top: 20px;
    width: 50%; /* Adjust width as needed */
    margin-left: auto;
    margin-right: auto;
  }
  .form-group label {
    font-weight: bold;
    color: #000000; /* Black for the labels */
  }
  
  .form-group {
    background-color: #d4ded7; /* Sage but better */
    padding: 10px;
    border-radius: 10px;
  }
  
  input[type="text"],
  textarea {
    background-color: #d4ded7; /* Sage but better */
    border: none;
    border-bottom: 1px solid #c1c1c2; /* Silver Chalice */
    color: #333a33; /* Dark Charcoal */
    width: 100%;
    margin-bottom: 15px;
  }
  
  input[type="text"]::placeholder,
  textarea::placeholder {
    font-weight: normal;
    color: #c1c1c2; /* Silver Chalice */
  }
  
  .submit-container {
    display: flex;
    align-items: center;
    justify-content: start;
  }
  
  .submit-button {
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
  .submit-button .arrow-icon img {
    width: 16px; /* Adjust width as needed */
    height: 16px; /* Adjust height as needed */
  }
  
  .submit-button .arrow-icon {
    background-color: #ffffff; /* White */
    border-radius: 2px; /* Smaller radius for the arrow icon box */
    padding: 5px;
    margin-left: 8px;
  }
  </style>