<template>
  <div class="signup-form">
    <h2>Create an admin account</h2>
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
      <button type="submit" :disabled="formSubmitted" @click="submitForm">Sign Up</button>
      <p class="msg">{{ msg }}</p>
      <div> <p>Already have an account? <a style="text-decoration: underline;">Log in</a></p></div>
    </form>
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
      };
      axiosClient.post('/admin/create', formData).then(response =>{
        this.msg = `Account ${response.data.email}  created successfully!`
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
html{box-sizing: border-box;}
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
