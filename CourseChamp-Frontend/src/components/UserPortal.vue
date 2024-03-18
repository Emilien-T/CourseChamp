<template>
    <div>
  
      <header><h1>User Portal</h1></header>
      <div class="content-wrapper">
      <div class="container">
        <div class="signup-form">
      <form @submit.prevent="submitForm">
        <h5> Account Settings </h5>
        <div class="email">
          <label for="email">Email:</label>
          <div class="msg"><p>{{ email }}</p></div>
        </div>
        <div class="form-group">
          <label for="email">Current Username:</label>
          <div class="msg"><p>{{ currentUsername }}</p></div>
          <label for="username">New Username:</label>
          <input type="text" id="username" v-model="username" required>
        </div>
        <div class="form-group">
          <label for="password">Old Password:</label>
          <input type="password" id="oldPassword" v-model="enteredPassword" required>
          <label for="password">New Password:</label>
          <input type="password" id="newPassword" v-model="password" required>
          <label for="password">Confirm New Password:</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword" required>
        </div>
        <div v-if='isStudent' class="form-group">
          <label for="email">Current Major:</label>
          <div class="msg"><p>{{ currentMajor }}</p></div>
          <label>Change Major:</label>
          <div>
            <label>
              <input type="radio" v-model="selectedMajor" value="Software" required> Software
            </label>
          </div>
          <div>
            <label>
              <input type="radio" v-model="selectedMajor" value="Computer" required> Computer
            </label>
          </div>
          <div>
            <label>
              <input type="radio" v-model="selectedMajor" value="Electrical" required> Electrical
            </label>
          </div>
        </div>
        <button type="submit" @click="submitForm">Submit Changes</button>
        <button @click="redirectToHome">Return to Homepage</button>
        <div class="msg"><p>{{ msg }}</p></div>
      </form>
    </div>
    <div class="image-container">
        <img src="../assets/people.png" alt="People Image">
    </div>
      </div>
    </div>
      
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
        email: Vue.prototype.logginInEmail,
        currentUsername: '',
        currentPassword: '',
        enteredPassword: '',
        confirmPassowrd: '',
        currentMajor: '',
        username: '',
        password: '',
        selectedMajor: '',
        msg: '',
        isStudent: Vue.prototype.userType === 'student',
      };
    },
    mounted() {
      // Assuming you're making an API call to fetch info
      this.fetchUserInfo();
    },
    methods: {
      redirectToHome(){
        if(Vue.prototype.userType === 'student'){
          this.$router.push('/studenthome')
        } else {
          this.$router.push('/adminhome')
        }
      },
      fetchUserInfo() {
        // Assuming you're making an API call to fetch info
        // Replace this with your actual API call
        axiosClient.get('/' + Vue.prototype.userType + '/' + this.email)
          .then(response =>{
            this.currentUsername = response.data.name;
            this.currentPassword = response.data.password;
            this.currentMajor = response.data.major;
            console.log(response.data);
          })
          .catch(error => {
            console.error('Error fetching user info:', error);
          });
      },
      submitForm() {
        // Handle form submission (e.g., send data to server)
        this.msg = ''
        const formData = {
          email: this.email,
          username: this.username,
          password: this.enteredPassword,
          major: this.selectedMajor // Selected major
        };
        
        if(this.password !== this.confirmPassowrd) {
          this.msg = 'Password and confirmed password should match!'
        } else {
          if (this.enteredPassword !== this.currentPassword){
          this.msg = 'Wrong Old Password!'
          } else {
        axiosClient.put('/'+ Vue.prototype.userType + '/update', formData).then(response =>{
          this.msg = `Account parameters updated successfully!`
          console.log("yep")
          this.username = ''
          this.password = ''
          this.selectedMajor = ''
        }  
        ).catch(error =>{
          if(error.response.status != 500){
          console.log("nope")
            this.msg = error.response.data
          }
        })
      }}
      }
    }
  };
  </script>
  
  
  <style scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
  }
  
  .signup-form {
    padding-top: 200px;
    width: 350px;
    height: 550px;
    margin-right: 20px; /* Add some spacing between the form and the image */
    background-color: #cfd9d3;
    padding: 20px;
    border-radius: 10px;
    font-size: 13px;
  }
  
  .image-container {
    flex-grow: 1; /* Allow the image container to grow to fill the available space */
  }
  
  /* Your existing CSS styles */
  
  .form-group {
    margin-bottom: 20px;
  }
  
  label {
    display: block;
    font-weight: bold;
  }
  
  button {
      color: #ffffff; /* Set text color to white */
      background-color: #2b4826; /* Set background color to green */
      border: none; /* Remove border */
      cursor: pointer; /* Change cursor to pointer on hover */
      border-radius: 5px !important; /* Add border radius for rounded corners */
      width: 200px;
      height: 50px;
      font-size: 16px;
      text-align: center !important;
    }
  
  button:hover {
    background-color: #3a5f32; /* Change background color on hover */
  }
  
  header {
      position: fixed !important;
      z-index: 1000;
      width: 100%;
      top: 0;
    background-color: #476141; /* Set background color for the header */
    color: #fff; /* Set text color to white */
    padding: 20px; /* Add padding */
    text-align: center; /* Center align text */
    margin: 0;
    height: 100px;
  }
  
  .form-group {
    margin-bottom: 10px; /* Decrease margin to make elements closer together */
  }
  
  label {
    font-weight: bold;
    margin-bottom: 2px; /* Decrease margin to make labels closer to inputs */
    margin-top: 2px;
  }
  
  input[type="text"],
  input[type="email"],
  input[type="password"] {
    width: 250px; /* Adjust width to make the textboxes smaller */
    height: 30px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-bottom: 10px; /* Decrease margin to make inputs closer together */
  }
  
  button {
    color: #ffffff;
    background-color: #2b4826;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px !important;
    width: 100px;
    margin-top: 10px; /* Decrease margin to make button closer to inputs */
  }
  </style>
  