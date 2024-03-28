<template>
  <div>
    <div class="spacer"></div> <!-- Add a spacer div -->
    <nav>
      <ul class="navbar">
        <li><router-link to="/createreview">Create Review</router-link></li>
        <li><router-link to="/viewreview">View Reviews</router-link></li>
        <li><router-link to="/userportal">Account Settings</router-link></li>
        <li><router-link to="/">Return to Home Page</router-link></li>
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
  margin-top: 20px; /* Add margin to create space between navbar and header */
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
