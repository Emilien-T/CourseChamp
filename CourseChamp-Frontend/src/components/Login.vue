<template>
  <!-- This is the HTML part of the Vue component. It defines the structure of the login form. -->
  <div class="login-page">
    <!-- The 'login-container' holds the form and the logo. -->
    <div class="login-container">
      <!-- 'login-header' holds the logo and the main title and subtitle. -->
      <div class="login-header">
        <!-- The CourseChamp logo is placed here. Replace 'path-to-your-logo.png' with the actual path to your logo file. -->
        <!--<img src="path-to-your-logo.png" alt="CourseChamp Logo" class="logo">-->
        <h2>Log In</h2>
        <p class="login-subtitle">Let's Pick Up Where You Left Off</p>
      </div>
      <!-- The form has a submit event listener that triggers the 'submitLoginForm' method when the form is submitted. -->
      <form @submit.prevent="submitLoginForm" class="login-form">
        <!-- The 'form-group' class is used for styling the input fields. -->
        <div class="form-group">
          <!-- The 'v-model' directive creates a two-way binding for the email input field. -->
          <input type="email" id="login-email" v-model="loginEmail" placeholder="Email" required>
        </div>
        <div class="form-group">
          <!-- The 'v-model' directive creates a two-way binding for the password input field. -->
          <input type="password" id="login-password" v-model="loginPassword" placeholder="Password" required>
        </div>
        <!-- The main action button for form submission. -->
        <button type="submit" class="login-button">Log In</button>
        <!-- A secondary action button. -->
        <button type="button" class="secondary-button">Continue with email</button>
        <!-- A link for users who may have forgotten their password. -->
        <a href="#" class="forgot-password">Forgot Password?</a>
      </form>
    </div>
    <!-- The 'illustration-section' may contain an image or graphic related to the CourseChamp platform. -->
    <div class="illustration-section">
      <!-- Placeholder for the illustration. Replace with actual content as needed. -->
      <p class="tool-number">The #1 tool</p>
    </div>
  </div>
</template>

<script>
// This is the JavaScript part of the Vue component. It defines the data and methods for the component.

// Importing the axios library, which is used to make HTTP requests.
import axios from 'axios';

// Importing the configuration file.
const config = require('../../config');

// Constructing the frontend URL from the configuration.
const frontendUrl = config.dev.host + ':' + config.dev.port;

// Creating an axios client with the backend base URL and headers.
const axiosClient = axios.create({
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  // The data function returns the reactive data for the component.
  data() {
    return {
      // These are the data properties for the email and password input fields.
      loginEmail: '',
      loginPassword: '',
    };
  },
  methods: {
    // This method is triggered when the form is submitted.
    submitLoginForm() {
      // The login data is constructed from the email and password input fields.
      const loginData = {
        email: this.loginEmail,
        password: this.loginPassword,
      };
      // The login data is logged to the console.
      console.log(loginData); 
      // Here you would implement your login logic, for example by sending a POST request to a '/login' endpoint.
    }
  }
};
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100vh;
  background-color: #f4f4f4;
}

.login-container {
  width: 35%;
  padding: 40px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-header .logo {
  max-width: 150px;
  margin-bottom: 20px;
}

.login-header h2 {
  margin-bottom: 10px;
}

.login-subtitle {
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
}

.login-button,
.secondary-button {
  width: 100%;
  padding: 15px;
  margin-bottom: 10px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.login-button {
  background-color: #4CAF50;
  color: white;
}

.secondary-button {
  background-color: transparent;
  color: #4CAF50;
  border: 1px solid #4CAF50;
}

.forgot-password {
  display: block;
  margin-top: 15px;
  color: #4CAF50;
  text-decoration: none;
}

.illustration-section {
  width: 65%;
  /* Include styles for your illustration here */
}

.tool-number {
  position: absolute;
  bottom: 30px;
  right: 30px;
  font-size: large;
  color: #333;
}
</style>