Feature: View and React to Reviews
    As a user of CourseChamp,
    I would like to view and react to reviews
    so that I can quickly and confidently react to posts.

  Background: 
    Given the following students exist in the system:
      | email             | username | password    | major      |
      | student1@mail.com | student1 | J0hn!Super  | Software   |
      | student2@mail.com | student2 | JohnLover!5 | Computer   |
      | student3@mail.com | student3 | Jk*g@iJHK$% | Electrical |
    And the following courses exist in the system:
      | department | courseNumber | name                          | description  |
      | ECSE       |          428 | Software Engineering Practice | Fun course   |
      | ECSE       |          222 | Digital Logic                 | Scary course |
      | MATH       |          262 | Intermediate Calculus         | Charles Roth |
    And the following course offerings exist in the system:
      | courseCode | semester |
      | ECSE222    | W2022    |
      | ECSE222    | F2022    |
      | ECSE428    | W2020    |
      | ECSE428    | F2020    |
      | MATH262    | F2022    |
      | MATH262    | W2023    |
    And the following reviews exist in the system:
      | courseCode | semester | reviewId | student           | rating | comment                               |
      | ECSE222    | W2022    |        1 | student1@mail.com |      4 | Great course very informative!        |
      | ECSE222    | F2022    |        2 | student2@mail.com |      3 | Very hard exams :(                    |
      | ECSE428    | W2020    |        3 | student3@mail.com |      5 | Excellent content and helpful quizzes |
      | ECSE428    | F2020    |        4 | student2@mail.com |      5 | I had so much fun in this course      |
      | MATH262    | F2022    |        5 | student1@mail.com |      3 | Some topics could be explained better |
      | MATH262    | W2023    |        6 | student2@mail.com |      5 | CHARLES ROTH                          |
    And the following votes exist in the system:
      | reviewId | email             | type     |
      |        1 | student1@mail.com | upvote   |
      |        2 | student2@mail.com | downvote |

  Scenario Outline: User views reviews for a course (Normal Flow)
    When the user attempts to view reviews for the course "<courseCode>"
    Then the user should display the following reviews "<reviews>" with the semesters "<semesters>", ratings "<ratings>", upvotes "<upvotes>", and downvotes "<downvotes>"

    Examples: 
      | courseCode | semesters   | reviews                                                                | ratings | upvotes | downvotes |
      | ECSE222    | W2022,F2022 | Great course very informative!,Very hard exams :(                      |     4,3 |     1,0 |       0,1 |
      | ECSE428    | W2020,F2020 | Excellent content and helpful quizzes,I had so much fun in this course |     5,5 |     0,0 |       0,0 |
      | MATH262    | F2022,W2023 | Some topics could be explained better,CHARLES ROTH                     |     3,5 |     0,0 |       0,0 |

  Scenario Outline: User adds an upvote to a review
    Given the user "<email>" has not upvoted the review with id "<reviewId>"
    When the user "<email>" selects the option to upvote a review with the id "<reviewId>"
    Then the review should display as "<courseCode>", "<rating>", "<comment>", "<upvotes>", "<downvotes>"

    Examples: 
      | email             | reviewId | courseCode | rating | comment                          | upvotes | downvotes |
      | student1@mail.com |        4 | ECSE428    |      5 | I had so much fun in this course |       1 |         0 |
      | student3@mail.com |        1 | ECSE222    |      4 | Great course very informative!   |       2 |         0 |
      | student3@mail.com |        2 | ECSE222    |      3 | Very hard exams :(               |       1 |         1 |

  Scenario Outline: User removes an upvote from a review
    Given the user "<email>" has upvoted the review with id "<reviewId>"
    When the user "<email>" selects the option to remove the upvote from the review with the id "<reviewId>"
    Then the review "<reviewId>" after removal should display as "<courseCode>", "<rating>", "<comment>", "<upvotes>", "<downvotes>"

    Examples: 
      | email             | reviewId | courseCode | rating | comment                        | upvotes | downvotes |
      | student1@mail.com |        1 | ECSE222    |      4 | Great course very informative! |       0 |         0 |

  Scenario Outline: User adds a downvote to a review
    Given the user "<email>" has not downvoted the review with id "<reviewId>"
    When the user "<email>" selects the option to downvote a review with the id "<reviewId>"
    Then the review should display as "<courseCode>", "<rating>", "<comment>", "<upvotes>", "<downvotes>"

    Examples: 
      | email             | reviewId | courseCode | rating | comment                               | upvotes | downvotes |
      | student1@mail.com |        1 | ECSE222    |      4 | Great course very informative!        |       0 |         1 |
      | student2@mail.com |        3 | ECSE428    |      5 | Excellent content and helpful quizzes |       3 |         7 |
      | student3@mail.com |        5 | MATH262    |      3 | Some topics could be explained better |       5 |        11 |

  Scenario Outline: User removes an downvote from a review
    Given the user "<email>" has downvoted the review with id "<reviewId>"
    When the user "<email>" selects the option to remove the downvote from the review with the id "<reviewId>"
    Then the review "<reviewId>" after removal should display as "<courseCode>", "<rating>", "<comment>", "<upvotes>", "<downvotes>"

    Examples: 
      | email             | reviewId | courseCode | rating | comment                               | upvotes | downvotes |
      | student1@mail.com |        1 | ECSE222    |      4 | Great course very informative!        |       0 |         0 |
      | student2@mail.com |        3 | ECSE428    |      5 | Excellent content and helpful quizzes |       3 |         6 |
      | student3@mail.com |        5 | MATH262    |      3 | Some topics could be explained better |       5 |        10 |

  Scenario Outline: User views reviews for a different course with no reviews (Error Flow)
    When the user "<email>" unsuccessfully attempts to view reviews for the course "<courseCode>"
    Then the system displays the error message "<errorMessage>" to the user

    Examples: 
      | email             | courseCode | errorMessage                      |
      | student1@mail.com | ECSE223    | No reviews found for this course. |
      | student2@mail.com | ECSE429    | No reviews found for this course. |
      | student3@mail.com | MATH263    | No reviews found for this course. |
