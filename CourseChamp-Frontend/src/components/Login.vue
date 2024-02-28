<template>
  <div>

    <header>
      <h1>Welcome Back !</h1>
    </header>
    <div class="content-wrapper">
      <div class="container">
        <div class="login-form">
          <form @submit.prevent="submitForm">
            <h5> Let's Pick Up Where We Left Off</h5>
            <div class="form-group">
              <label for="email">Email:</label>
              <input type="email" id="email" v-model="email" required>
            </div>
            <div class="form-group">
              <label for="password">Password:</label>
              <input type="password" id="password" v-model="password" required>
            </div>
            <button type="submit" :disabled="!email || !password" @click="submitForm">Log In</button>
            <div class="msg">
              <p>{{ msg }}</p>
            </div>
            <div>
              <p>Don't have an account?
                <router-link to="/signup" style="text-decoration: underline;">Sign Up</router-link>
              </p>
            </div>
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
        password: this.password,
      };
      // Determine the user type based on the email
      // This is just an example, replace it with your own logic
      console.log(formData);
      axiosClient.post(`/login/student/`, formData).then(response => {
        // Check the type of the user and redirect to the appropriate page
        this.msg = `Logged In successfully!`
      }).catch(error1 =>{
        axiosClient.post(`/login/Admin/`, formData).then(response =>{
          this.msg = `Logged In successfully!`
          this.$router.push('/adminhome');
        }).catch(error2 =>{
          this.msg = error1.response.data + " " + error2.response.data
        })
      })
    }
  }
}
</script>


<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  height: calc(100vh - 100px);
  /* Subtract the height of the header */
  padding-top: 100px;
  /* Add padding equal to the height of the header */
}

.login-form {
  width: 350px;
  height: 550px;
  margin-right: 20px;
  background-color: #cfd9d3;
  padding: 20px;
  border-radius: 10px;
  font-size: 13px;
}

.image-container {
  flex-grow: 1;
  display: flex;
  justify-content: flex-end;
}

.image-container img {
  max-width: 100%;
  max-height: 100%;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: bold;
}

button {
  color: #ffffff;
  /* Set text color to white */
  background-color: #2b4826;
  /* Set background color to green */
  border: none;
  /* Remove border */
  cursor: pointer;
  /* Change cursor to pointer on hover */
  border-radius: 5px !important;
  /* Add border radius for rounded corners */
  width: 200px;
  height: 50px;
  font-size: 16px;
  text-align: center !important;
}

button:hover {
  background-color: #3a5f32;
  /* Change background color on hover */
}

header {
  position: fixed !important;
  z-index: 1000;
  width: 100%;
  top: 0;
  background-color: #476141;
  /* Set background color for the header */
  color: #fff;
  /* Set text color to white */
  padding: 20px;
  /* Add padding */
  text-align: center;
  /* Center align text */
  margin: 0;
  height: 100px;
}

.form-group {
  margin-bottom: 10px;
  /* Decrease margin to make elements closer together */
}

label {
  font-weight: bold;
  margin-bottom: 2px;
  /* Decrease margin to make labels closer to inputs */
  margin-top: 2px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 250px;
  /* Adjust width to make the textboxes smaller */
  height: 30px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  /* Decrease margin to make inputs closer together */
}

button {
  color: #ffffff;
  background-color: #2b4826;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px !important;
  width: 100px;
  margin-top: 10px;
  /* Decrease margin to make button closer to inputs */
}</style>