<template>
    <div class="course-rating">
      <div class="rating-header">
        <h1>{{ rating }}/5</h1>
        <p>This student took the class in {{ semester }}.</p>
      </div>
      <div class="course-code">
        <h2>{{ courseCode }}</h2>
      </div>
      <div class="review">
        <p>Review :</p>
        <p>{{ text }}</p>
        <div class="stars">
          <span v-for="star in computedRating" :key="star" class="star">
            ★
          </span>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CourseRating',
    props: {
      rating: {
        type: Number,
        default: 0
      },
      semester: {
        type: String,
        default: "" 
      },
      courseCode: {
        type: String,
        default: ""
      },
      text: {
        type: String,
        default: "" 
      },
    },
    computed: {
      computedRating() {
        // Assuming the rating is out of 5
        console.log(this.rating);
        return Array(Math.round(this.rating)).fill('★');
      },
    },
  };
  </script>
  
  <style scoped>
  .course-rating {
    font-family: 'Arial', sans-serif;
    width: 300px; /* adjust as necessary */
    background-color: #f0f0f0; /* light gray background */
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin: 10px;
  }
  
  .rating-header h1,
  .course-code h2 {
    margin: 0;
    padding: 0;
    color: #333; /* dark text color */
  }
  
  .review p {
    margin: 5px 0;
    color: #333;
  }
  
  .stars {
    color: #f5c518; /* star color */
  }
  </style>
