<template>
  <div>
    <StudentNavBar v-if="isStudent" />
    <AdminNavBar v-else />
    <div class="content-wrapper">
      <div class="container">
        <div class="signup-form">
          <form @submit.prevent="submitForm">
            <h5> Account Settings </h5>
            <div class="email">
              <label for="email">Email:</label>
              <div class="msg">
                <p>{{ email }}</p>
              </div>
            </div>
            <div class="form-group">
              <label for="username">Username:</label>
              <input type="text" id="username" v-model="username" required>
            </div>
            <div class="form-group">
              <label for="password">New Password (Optional):</label>
              <input type="password" id="newPassword" v-model="password" required>
              <label for="password">Confirm New Password (Optional):</label>
              <input type="password" id="confirmPassword" v-model="confirmPassword" required>
            </div>
            <div v-if='isStudent' class="form-group">
              <label>Major:</label>
              <div>
                <label>
                  <input type="radio" v-model="selectedMajor" value="Software" required> Software
                </label>
              </div>
              <div>
                <label>
                  <input type="radio" v-model="selectedMajor" value="Computer" required> Computer
                </label>
              </div>
              <div>
                <label>
                  <input type="radio" v-model="selectedMajor" value="Electrical" required> Electrical
                </label>
              </div>
            </div>
            <label for="password">Enter Current Password<br>to Submit Changes:</label>
            <input type="password" id="oldPassword" v-model="enteredPassword" required>
            <button type="submit" @click="submitForm">Submit Changes</button>
            <div class="msg">
              <p>{{ msg }}</p>
            </div>
          </form>
        </div>

        <div v-if='isStudent'>
          <label>Your Reviews:</label>
          <CourseRating v-for="(review, index) in reviews" :key="index" :rating="review.rating"
            :semester="review.semester" :text="review.text" :courseCode="review.courseCode"  :reviewId = "review.id" :showDelete ='true' @delete-review="handleDeleteReview"/>
          <!-- <button @click="deleteReview">Delete</button> -->
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import CourseRating from './ViewReview.vue'; // Import your CourseRating component
import axios from 'axios'
import Vue from 'vue'
import StudentNavBar from './StudentNavBar.vue';
import AdminNavBar from './AdminNavBar.vue';
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var axiosClient = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  components: {
    CourseRating,
    StudentNavBar,
    AdminNavBar
  },
  data() {
    return {
      email: Vue.prototype.logginInEmail,
      currentPassword: '',
      enteredPassword: '',
      confirmPassword: '',
      username: '',
      password: '',
      selectedMajor: '',
      msg: '',
      isStudent: Vue.prototype.userType === 'student',
      reviews: [],
    };
  },
  mounted() {
    // Assuming you're making an API call to fetch info
    this.fetchUserInfo();
    this.fetchReviews();
  },
  methods: {
    
    redirectToHome() {
      if (Vue.prototype.userType === 'student') {
        this.$router.push('/studenthome')
      } else {
        this.$router.push('/adminhome')
      }
    },
    fetchUserInfo() {
      // Assuming you're making an API call to fetch info
      // Replace this with your actual API call
      axiosClient.get('/' + Vue.prototype.userType + '/' + this.email)
        .then(response => {
          this.username = response.data.name;
          this.currentPassword = response.data.password;
          this.selectedMajor = response.data.major;
          console.log(response.data);
        })
        .catch(error => {
          console.error('Error fetching user info:', error);
        });
    },
    fetchReviews() {
      if (Vue.prototype.userType !== 'admin') {
        axiosClient.get('/getreviewsStudent/' + this.email)
          .then(response => {
            this.reviews = response.data;
            console.log(this.reviews);
          })
          .catch(error => {
            console.error('Error fetching reviews:', error);
            this.reviews = [];
          });
      }
    },

    handleDeleteReview(reviewId) {
      if (window.confirm('Are you sure you want to delete this review?')) {
        axiosClient.delete(`/review/${reviewId}`)
      .then(response => {
        console.log('Review deleted successfully!');
        this.fetchReviews(); // Call the fetchReviews() method to refresh the reviews
      })
      .catch(error => {
        console.error('Error deleting review:', error.response.data);
        // Handle error
      });
  }
      },

    submitForm() {
      // Handle form submission (e.g., send data to server)
      this.msg = ''
      var passwordInForm = this.password
      if (passwordInForm === '') {
        passwordInForm = this.currentPassword
      }
      const formData = {
        email: this.email,
        username: this.username,
        password: passwordInForm,
        major: this.selectedMajor // Selected major
      };

      if (this.enteredPassword !== this.currentPassword) {
        this.msg = 'Wrong current password entered!'
      } else {
        if ((this.password !== '' || this.confirmPassword !== '') && this.password !== this.confirmPassword) {
          this.msg = 'Password and confirmed password should match!'
        } else {
          axiosClient.put('/' + Vue.prototype.userType + '/update', formData).then(response => {
            this.msg = `Account parameters updated successfully!`
            console.log(response.data)
            this.username = ''
            this.password = ''
            this.confirmPassword = ''
            this.enteredPassword = ''
            this.selectedMajor = ''
            this.fetchUserInfo();
          }
          ).catch(error => {
            if (error.response.status != 500) {
              this.msg = error.response.data
            }
          })
        }
      }
    }
  },
};

</script>


<style scoped>
body {
  font-family: 'Arial', sans-serif;
}

.container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  margin-top: 100px;
}

.signup-form {
  background-color: #cfd9d3;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.signup-form:hover {
  transform: translateY(-10px);
}

.section-heading {
  color: #2b4826;
  text-align: left;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

input[type="text"]:focus,
input[type="email"]:focus,
input[type="password"]:focus {
  border-color: #2b4826;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  transition: background-color 0.3s ease;
}

.submit-btn {
  background-color: #2b4826;
  color: #ffffff;
}

.return-btn {
  background-color: #476141;
  color: #ffffff;
}

button:hover {
  background-color: #3a5f32;
}

.image-container {
  flex-grow: 1;
  padding: 20px;
}

header {
  background-color: #476141;
  color: #fff;
  padding: 20px;
  text-align: center;
  width: 100%;
  z-index: 1000;
}

.msg {
  color: #2b4826;
  font-style: italic;
}
</style>