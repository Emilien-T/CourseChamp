Feature: Add a Course
  As an admin of the website, 
  I would like to add courses on the website
  So that I can keep the course pool available for users up-to-date.

  Scenario: Admin adds and attempts to add a new course  (Normal Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin adds a new course with Department abbreviation "<Department>" course number "<Course Number>" and course description <"Description">:
    And submits the form
    Then the system should confirm the successful addition
    And the new course should appear in the course pool
      | Department | Course Number | Description                     |
      | ECSE       |           223 | Software Engineering Principles |
      | MECH       |           321 | Deformable Mechanics            |

  Scenario: Admin attempts to add a duplicate course (Error Flow)
    When the admin attempts to add the same course again with Department "<Department>" and course number "<Course Number>"
    Then the system should display an error message 
    And the course should not be added to the course pool
      | Department | Course Number |
      | ECSE       |           223 |

  Scenario: Admin adds a new course with various incomplete details (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin adds a new course with incomplete details:
    And submits the form:
    Then the system should display error message "Please fill out all required fields"
    And inform the admin about the required fields
    And none of the courses should be added to the course pool
      | Department | Course Number | Description                     |
      |            |           223 | Software Engineering Principles |
      | ECSE       |               |                                 |
      | MECH       |           321 |                                 |
      | ECSE       |               | Digital Circuits                |

  Scenario: Admin adds a new course with an invalid course number (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin attempts to add a new course with an invalid course number "<Course Number>":
    And submits the form
    Then the system should display an error message
    And inform the admin that the course number should be a 3-digit number
    And the course should not be added to the course pool
      | Department | Course Number | Description                     |
      | ECSE       | ABC           | Software Engineering Principles |
      | MECH       |          1234 | Mechanical Design               |

  Scenario: Admin adds a new course with an invalid department (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin attempts to add a new course with an invalid department "<Department>" :
    And submits the form
    Then the system should display an error message 
    And inform the admin that the department should be a 4 alphabetical string
    And the course should not be added to the course pool

      | Department | Course Number | Description              |
      | EC         |           223 | Software Engineering Principles    |
      | ECE1       |           321 | Introduction to Circuits |
