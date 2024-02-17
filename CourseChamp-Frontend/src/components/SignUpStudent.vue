<template>
  <div>

    <header><h1>Create a Student Account</h1></header>
    <div class="content-wrapper">
    <div class="container">
      <div class="signup-form">
    <form @submit.prevent="submitForm">
      <h5> Let's get started!</h5>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <div class="form-group">
        <label>Major:</label>
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
      <button type="submit" :disabled="!email || !username || !password || !selectedMajor" @click="submitForm">Sign Up</button>
      <div class="msg"><p>{{ msg }}</p></div>
      <div> <p>Already have an account? <a style="text-decoration: underline;">Log in</a> </p></div>
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
      email: '',
      username: '',
      password: '',
      selectedMajor: '',
      msg: '',
    };
  },
  methods: {
    
  
    submitForm() {
      // Handle form submission (e.g., send data to server)
      this.msg = ''
      const formData = {
        email: this.email,
        username: this.username,
        password: this.password,
        major: this.selectedMajor // Selected major
      };
      axiosClient.post('/student/create', formData).then(response =>{
        this.msg = `Account created successfully!`
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
