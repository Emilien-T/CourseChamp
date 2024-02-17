<template>
  <div class="signup-form">
    <h2>Create an account</h2>
    <h4> Let's get started!</h4>
    <form @submit.prevent="submitForm">
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
            <input type="radio" v-model="selectedMajor" value="Software"> Software
          </label>
        </div>
        <div>
          <label>
            <input type="radio" v-model="selectedMajor" value="Computer"> Computer
          </label>
        </div>
        <div>
          <label>
            <input type="radio" v-model="selectedMajor" value="Electrical"> Electrical
          </label>
        </div>
      </div>
      <button type="submit" @click="submitForm">Sign Up</button>
      <div> <p>Already have an account? <a style="text-decoration: underline;">Log in</a></p></div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
const config = require('../../config');
const frontendUrl = config.dev.host + ':' + config.dev.port;
const axiosClient = axios.create({
  // Note the baseURL, not baseUrl
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});
export default {
  data() {
    return {
      email: '',
      username: '',
      password: '',
      selectedMajor: '' // Variable to store selected major
    };
  },
  methods: {
    submitForm() {
      // Handle form submission (e.g., send data to server)
      const formData = {
        email: this.email,
        username: this.username,
        password: this.password,
        major: this.selectedMajor // Selected major
      };
      console.log(formData); // Replace with your form submission logic
    }
  }
};
</script>


<style scoped>
.signup-form {
  max-width: 400px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: bold;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
