<template>
    <div class="create-course-container">
      <header>
        <h1>Create A Course</h1>
        <router-link to="/courses" class="back-link">Back To Course</router-link>
      </header>
      <div class="form-container">
        <form @submit.prevent="submitCourse">
          <div class="form-group">
            <label class="input-label">Course Department:</label>
            <input type="text" id="course-department" v-model="courseDepartment" required placeholder="Write course department here...">
          </div>
          <div class="form-group">
            <label class="input-label">Course Number:</label>
            <input type="text" id="course-number" v-model="courseNumber" required placeholder="Write course number here...">
          </div>
          <div class="form-group">
            <label class="input-label">Course Overview:</label>
            <textarea id="course-overview" v-model="courseOverview" required placeholder="Write course overview here..."></textarea>
          </div>
          <div class="submit-container">
            <button type="submit" class="submit-button">
              Submit
              <span class="arrow-icon">
                <img src="../assets/arrow.png" alt="Arrow">
              </span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  
  
<script>
export default {
  data() {
    return {
      courseDepartment: '',
      courseNumber: '',
      courseOverview: ''
    };
  },
  methods: {
    submitCourse() {
      // Handle course submission (e.g., send data to server)
      const courseData = {
        department: this.courseDepartment,
        number: this.courseNumber,
        overview: this.courseOverview
      };
      console.log(courseData); // Replace with your submission logic
      // Reset form fields after submission
      this.courseDepartment = '';
      this.courseNumber = '';
      this.courseOverview = '';
    }
  }
};
</script>
<style scoped>
body{
  margin-top: 0px;
  background-color: #f7f8f8; /* Off White */
  height: 100vh; /* Full height of the viewport */
  box-sizing: border-box; /* Includes padding in the element's total width and height */
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
}

.back-link {
  color: #476141; /* Dark Fern */
  text-decoration: none;
}

.form-container {
  background-color: #d4ded7; /* Sage but better */
  padding: 20px;
  border-radius: 10px;
  margin-top: 20px;
  width: 50%; /* Adjust width as needed */
  margin-left: auto;
  margin-right: auto;
}
.form-group label {
  font-weight: bold;
  color: #000000; /* Black for the labels */
}

.form-group {
  background-color: #d4ded7; /* Sage but better */
  padding: 10px;
  border-radius: 10px;
}

input[type="text"],
textarea {
  background-color: #d4ded7; /* Sage but better */
  border: none;
  border-bottom: 1px solid #c1c1c2; /* Silver Chalice */
  color: #333a33; /* Dark Charcoal */
  width: 100%;
  margin-bottom: 15px;
}

input[type="text"]::placeholder,
textarea::placeholder {
  font-weight: normal;
  color: #c1c1c2; /* Silver Chalice */
}

.submit-container {
  display: flex;
  align-items: center;
  justify-content: start;
}

.submit-button {
  background-color: #386849; /* Bottle Green */
  color: #ffffff; /* White */
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  width: auto; /* Adjust width as needed */
  justify-content: space-between;
}
.submit-button .arrow-icon img {
  width: 16px; /* Adjust width as needed */
  height: 16px; /* Adjust height as needed */
}

.submit-button .arrow-icon {
  background-color: #ffffff; /* White */
  border-radius: 2px; /* Smaller radius for the arrow icon box */
  padding: 5px;
  margin-left: 8px;
}
</style>