<template>
  <div>
    <header><h1>Welcome to CourseChamp!</h1></header>
    
    <img src="../assets/people.png" alt="People Image">
    <button @click="redirectToStudentSignup">Create Student Account</button>
    <button @click="redirectToAdminSignup">Create Admin Account</button>
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
</style>

