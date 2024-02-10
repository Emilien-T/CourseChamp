Feature: View and React to Reviews
    As a user of CourseChamp, 
    I would like to view and react to reviews
    so that I can quickly and confidently react to posts.

  Background: 
    Given the user is on the course details page for a specific course
    And there are reviews for the course with the following details:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      4 | Great course, very informative!       |       5 |         2 |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |
      | ECSE       |    321 |      3 | Some topics could be explained better |       2 |         3 |

  Scenario: User views reviews for a course (Normal Flow)
    When the user navigates to the reviews section
    Then the user should see a list of reviews for the course
    And each review should display:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      4 | Great course, very informative!       |       5 |         2 |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |
      | ECSE       |    321 |      3 | Some topics could be explained better |       2 |         3 |

  Scenario: User adds an upvote to a review
    When the user selects the option to upvote a review with the following details:
      | Department | Number | Rating | Comment                         | Upvotes | Downvotes |
      | ECSE       |    321 |      4 | Great course, very informative! |       5 |         2 |
    Then the system should increment the upvote count for the selected review
    And the review should display:
      | Department | Number | Rating | Comment                         | Upvotes | Downvotes |
      | ECSE       |    321 |      4 | Great course, very informative! |       6 |         2 |

  Scenario: User removes an upvote from a review
    Given the user has already upvoted a review
    When the user selects the option to upvote a review with the following details:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |
    And then the user selects the option to upvote the same review again
    Then the system should decrement the upvote count for the selected review
    And the review should display:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |       9 |         0 |
    Then the system should decrement the upvote count for the selected review

  Scenario: User adds a downvote to a review
    When the user selects the option to downvote a review with the following details:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |
    Then the system should increment the downvote count for the selected review
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |

  Scenario: User removes an downvote from a review
    Given the user has already downvoted a review
    When the user selects the option to downvote a review with the following details:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         1 |
    And then the user selects the option to upvote the same review again
    Then the system should decrement the downvote count for the selected review
    And the review should display:
      | Department | Number | Rating | Comment                               | Upvotes | Downvotes |
      | ECSE       |    321 |      5 | Excellent content and helpful quizzes |      10 |         0 |
    Then the system should decrement the downvote count for the selected review

  Scenario: User views reviews for a different course with no reviews (Error Flow)
    Given the user is on the course details page for a specific course
    And there are no existing reviews for the course
    When the user navigates to the reviews section
    Then the user should see a message indicating that there are no reviews for this course
    And the user should not see any review entries
