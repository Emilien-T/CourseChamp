<template>
    <div class="form-container">
      <form @submit.prevent="submitCourse">
        <div class="form-group">
          <label class="input-label">Enter the Department:</label>
          <!--<input type="text" id="department" v-model="department" required placeholder="Ex: ECSE"> -->
          <b-form-select v-model="department" :options="allDepartmentOptions" placeholder="ECSE">
          </b-form-select>
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Number:</label>
          <input type="text" id="course-number" v-model="courseNumber" required placeholder="Ex: 428">
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Name:</label>
          <input type="text" id="name" v-model="name" required placeholder="Ex: Software Engineering Practice">
        </div>
        <div class="form-group">
          <label class="input-label">Enter the Course Description:</label>
          <textarea id="description" v-model="description" required placeholder="Ex: This is a really fun course"></textarea>
        </div>
        <div class="msg">{{ msg }}</div>
        <div class="submit-container">
          <button type="submit" :disabled="!department || !courseNumber || !name || !description" class="submit-button">
            Submit
            <span class="arrow-icon">
              <img src="../assets/arrow.png" alt="Arrow">
            </span>
          </button>
        </div>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  var config = require('../../config')
  
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
  
  var axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })
  export default {
    components: {
      
    },
    data() {
      return {
        department: '',
        courseNumber: '',
        name: '',
        description: '',
        msg: '',
        allDepartmentOptions: ['ACCT', 'AEBI', 'AEHM', 'AEMA', 'AEPH', 'AFRI', 'AGEC', 'AGRI', 'ANAT',
    'ANSC', 'ANTH', 'ARCH', 'ARTH', 'ATOC', 'BIEN', 'BIOC', 'BIOL', 'BREE',
    'CANS', 'CHEE', 'CHEM', 'CIVE', 'CLAS', 'COMP', 'EAST', 'ECON', 'ECSE',
    'ENGL', 'ENVR', 'EPSC', 'EXMD', 'FACC', 'FINE', 'FREN', 'FRSL', 'GEOG',
    'GERM', 'GSFS', 'HISP', 'HIST', 'IGFS', 'INDR', 'INSY', 'ISLA', 'ITAL',
    'JWST', 'LING', 'LLCU', 'LSCI', 'MATH', 'MECH', 'MGCR', 'MGPO', 'MGSC',
    'MICR', 'MIME', 'MIMM', 'MRKT', 'MUAR', 'MUMT', 'MUSP', 'MUTH', 'NEUR',
    'NSCI', 'NUR1', 'NUR2', 'NUTR', 'PHAR', 'PHGY', 'PHIL', 'PHYS', 'PLNT',
    'POLI', 'PSYC', 'PSYT', 'RELG', 'REDM', 'RUSS', 'SEAD', 'SOCI', 'WILD', 'WOOD'] 
      };
    },
    methods: {
      submitCourse() {
        const courseData = {
          department: this.department,
          courseNumber: Number(this.courseNumber),
          name: this.name,
          description: this.description
        };
        axiosClient.post('/course/create', courseData).then(response =>{
          this.msg = `Course created Successfully!`
          this.department = ''
          this.courseNumber = ''
          this.name = ''
          this.description = ''
          this.$emit('courseAdded', courseData); // Emit event when course is added
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
body {
  margin-top: 0px;
  background-color: #f7f8f8; /* Off White */
  height: 100vh; /* Full height of the viewport */
  box-sizing: border-box; /* Includes padding in the element's total width and height */
}

header {
  /* position: fixed; Fixed position to stick at the top */
  /* width: 100%; */
  top: 0;
  background-color: #476141;
  color: #fff;
  padding: 20px;
  text-align: center;
  margin: 0;
}

.back-link {
  color: #476141; /* Dark Fern */
  text-decoration: none;
}

.form-container {
  background-color: #d4ded7; /* Sage but better */
  padding: 20px;
  border-radius: 10px;
  margin-top: 120px; /* Adjusted top margin to push it down below the header */
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

  