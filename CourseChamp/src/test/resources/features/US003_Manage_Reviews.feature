Feature: Manage Reviews
  As a user of CourseChamp, 
  I would like to view all of my reviews and have the option to delete a review 
  so I can ensure all my reviews accurately relect my feelings.

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
    And the following reviews currently exist in the system:
      | courseCode | semester | reviewId | student           | rating | comment                               |
      | ECSE222    | W2022    |        1 | student1@mail.com |      4 | Great course very informative!        |
      | ECSE222    | F2022    |        2 | student2@mail.com |      3 | Very hard exams :(                    |
      | ECSE428    | W2020    |        3 | student3@mail.com |      5 | Excellent content and helpful quizzes |
      | ECSE428    | F2020    |        4 | student2@mail.com |      5 | I had so much fun in this course      |
      | MATH262    | F2022    |        5 | student1@mail.com |      3 | Some topics could be explained better |
      | MATH262    | W2023    |        6 | student2@mail.com |      5 | CHARLES ROTH                          |

  Scenario Outline: Student successfully changes the comment of a review (Normal Flow)
    When the student "<email>" attempts to change the comment of the review "<reviewId>" to "<comment>"
    Then the review "<reviewId>" shall have a new comment "<comment>"

    Examples: 
      | email             | reviewId | comment                          |
      | student1@mail.com |        1 | I miss Boris!!!                  |
      | student2@mail.com |        2 | Very hard exams and VHDL Labs :( |
      | student3@mail.com |        3 | BIG ROB!!!!                      |

  Scenario Outline: Student unsuccessfully changes the comment of a review (Error Flow)
    When the student "<email>" unsuccessfully attempts to change the comment of the review "<reviewId>" to "<comment>"
    Then the system shall display the "<errorMessage>" to the student after

    Examples: 
      | email             | reviewId | comment | errorMessage          |
      | student1@mail.com |        1 |         | Text cannot be blank. |
      | student2@mail.com |        2 |         | Text cannot be blank. |
      | student3@mail.com |        3 |         | Text cannot be blank. |

  Scenario Outline: Student successfully changes the rating of a review (Alternate Flow)
    When the student "<email>" attempts to change the rating of the review "<reviewId>" to "<rating>"
    Then the review "<reviewId>" shall have a new rating "<rating>"

    Examples: 
      | email             | reviewId | rating |
      | student1@mail.com |        1 |      1 |
      | student2@mail.com |        2 |      2 |
      | student3@mail.com |        3 |      4 |

  Scenario Outline: Student unsuccessfully changes the rating of a review (Error Flow)
    When the student "<email>" unsuccessfully attempts to change the rating of the review "<reviewId>" to "<rating>"
    Then the system shall display the "<errorMessage>" to the student after

    Examples: 
      | email             | reviewId | rating | errorMessage                |
      | student1@mail.com |        1 |      0 | Rating must be between 1-5. |
      | student2@mail.com |        2 |     -1 | Rating must be between 1-5. |
      | student3@mail.com |        3 |      6 | Rating must be between 1-5. |

  Scenario Outline: Student successfully changes the semester of a review (Alternate Flow)
    When the student "<email>" attempts to change the semester of the review "<reviewId>" to "<semester>"
    Then the review "<reviewId>" shall have a new semester "<semester>"

    Examples: 
      | email             | reviewId | semester |
      | student1@mail.com |        1 | F2022    |
      | student2@mail.com |        2 | W2022    |
      | student3@mail.com |        3 | F2020    |

  Scenario Outline: Student unsuccessfully changes the semester of a review (Error Flow)
    When the student "<email>" unsuccessfully attempts to change the semester of the review "<reviewId>" to "<semester>"
    Then the system shall display the "<errorMessage>" to the student after

    Examples: 
      | email             | reviewId | semester | errorMessage        |
      | student1@mail.com |        1 | F1999    | Semester not found. |
      | student2@mail.com |        2 | W1965    | Semester not found. |
      | student3@mail.com |        3 | F1994    | Semester not found. |

  Scenario Outline: Student successfully deletes a review (Alternate Flow)
    When the student "<email>" attempts to delete the review "<reviewId>"
    Then the review "<reviewId>" shall no longer exist in the system

    Examples: 
      | email             | reviewId |
      | student1@mail.com |        1 |
      | student2@mail.com |        2 |
      | student3@mail.com |        3 |

  Scenario Outline: Student unsuccessfully deletes a review (Error Flow)
    When the student "<email>" unsuccessfully attempts to delete the review "<reviewId>"
    Then the system shall display the "<errorMessage>" to the student after

    Examples: 
      | email             | reviewId | errorMessage      |
      | student1@mail.com |       -1 | Review not found. |
      | student2@mail.com |       -2 | Review not found. |
      | student3@mail.com |       -3 | Review not found. |
