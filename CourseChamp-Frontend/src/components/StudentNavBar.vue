<template>
  <div>
    <nav>
      <ul class="navbar">
        <li><router-link to="/createreview">Create Review</router-link></li>
        <li><router-link to="/viewreview">View Courses</router-link></li>
        <li><router-link to="/userportal">My Account</router-link></li>
        <li><router-link to="/">Logout</router-link></li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  methods: {
        redirectToHomePage() {
        // Redirect to the student signup page
        this.$router.push('/signup');
      },
      redirectToCreateReview() {
        // Redirect to the student signup page
        this.$router.push('/createreview');
      },
      redirectToViewReview() {
        // Redirect to the admin signup page
        this.$router.push('/viewreview');
      },
      redirectToAccountSettings() {
        // Redirect to the admin signup page
        this.$router.push('/userportal');
      }
    }
}
</script>

<style>
nav
{
   position: fixed;
   top: 0px;
   width: 100%; /* Set the width to 100% */
}
body { 
  margin: 0; 
  padding: 0; 
}

.navbar {
  list-style-type: none;
  margin: 0;
  padding: 0;
  background-color: #476141; /* Set background color for the navbar */
  overflow: hidden;
}


.navbar li {
  float: left;
}

.navbar li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 20px;
  text-decoration: none;
}

.navbar li a:hover {
  background-color: #2c4827; /* Change background color on hover */
}

</style>
