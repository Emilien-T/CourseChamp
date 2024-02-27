<template>
    <div class="course-rating">
      <div class="rating-header">
        <h1>{{ rating }}/5</h1>
        <p>Overall Quality Based on {{ numberOfRatings }} ratings</p>
      </div>
      <div class="course-code">
        <h2>ECSE 428</h2>
      </div>
      <div class="review">
        <p>{{ reviewText }}</p>
        <div class="stars">
          <span v-for="star in computedRating" :key="star" class="star">
            ★
          </span>
        </div>
        <p>Rating :</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CourseRating',
    /* TODO: implement view review contoller methods */
    data() {
      return {
        rating: 4.8,
        numberOfRatings: 13,
        reviewText: 'Great Course I loved it so much especially the part where we worked on our projects',
      };
    },
    computed: {
      computedRating() {
        // Assuming the rating is out of 5
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
