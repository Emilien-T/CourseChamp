<template>
  <div>
    <header><h1>Welcome to CourseChamp!</h1></header>
    <div class="container">
    <div class="button-container">
    <button @click="redirectToStudentSignup">Create Student Account</button>
    <button @click="redirectToAdminSignup">Create Admin Account</button>
    <button @click="redirectToLogin">Log In</button>
  </div>
  <img src="../assets/people.png" alt="People Image">
</div>
  </div>
</template>

<script>
export default {
  methods: {
    redirectToStudentSignup() {
      // Redirect to the student signup page
      this.$router.push('/signup/student');
    },
    redirectToAdminSignup() {
      // Redirect to the admin signup page
      this.$router.push('/signup/admin');
    },
    redirectToLogin(){
      this.$router.push('/login')
    }
  }
}
</script>

<style>
body { 
  margin: 0; 
  padding: 0; 
} 
  button {
    color: #ffffff; /* Set text color to white */
    background-color: #2b4826; /* Set background color to green */
    border: none; /* Remove border */
    padding: 10px 20px; /* Add padding */
    cursor: pointer; /* Change cursor to pointer on hover */
    border-radius: 5px !important; /* Add border radius for rounded corners */
    width: 250px;
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
}
button:hover {
  background-color: #3a5f32; /* Change background color on hover */
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.button-container {
  display: flex;
  flex-direction: column; /* Stack buttons vertically */
  align-items: center; /* Align buttons horizontally */
  margin: 20px; /* Adjust spacing between buttons and image */
  gap: 20px;
}
</style>

