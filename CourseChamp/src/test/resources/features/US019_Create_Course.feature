Feature: Add a Course
  As an admin of the website, 
  I would like to add courses on the website
  So that I can keep the course pool available for users up-to-date.

  Scenario: Admin adds and attempts to add a new course  (Normal Flow)
    Given the admin is logged in and on the "Course Management" page
    And no course with Department abbreviation "<Department>" and course number "<Course Number>" exists in the course pool
    When the admin adds a course with Department abbreviation "<Department>" course number "<Course Number>" and course name "<Name>":
    Then the system should confirm the successful addition
    And a course with Department abbreviation "<Department>" and course number "<Course Number>" exists in the course pool
      | Department | Course Number | Name                                 |
      | ECSE       |           223 | Software Engineering Principles      |
      | ECSE       |           321 | Introduction to Software Engineering |

  Scenario: Admin attempts to add a duplicate course (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    And a course with Department abbreviation "<Department>" and course number "<Course Number>" exists in the course pool
    When the admin adds a course with Department abbreviation "<Department>" course number "<Course Number>" and course name "<Name>":
    Then the system should display an error message "Course Already Exists"
    And the course should not be added to the course pool
      | Department | Course Number |
      | ECSE       |           223 |

  Scenario: Admin adds a new course with various incomplete details (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin adds a course with Department abbreviation "<Department>" course number "<Course Number>" and course name "<Name>":
    Then the system should display an error message "Incomplete Details"
    And the course should not be added to the course pool
      | Department | Course Number | Name                            |
      |            |           223 | Software Engineering Principles |
      | ECSE       |               |                                 |
      | ECSE       |           321 |                                 |
      | ECSE       |               | Digital Circuits                |

  Scenario: Admin adds a new course with an invalid course number (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin adds a course with Department abbreviation "<Department>" course number "<Course Number>" and course name "<Name>":
    Then the system should display an error message "Course number should be a 3-digit number"
    And the course should not be added to the course pool
      | Department | Course Number | Name                            |
      | ECSE       | ABC           | Software Engineering Principles |
      | ECSE       |          1234 | Mechanical Design               |

  Scenario: Admin adds a new course with an invalid department (Error Flow)
    Given the admin is logged in and on the "Course Management" page
    When the admin adds a course with Department abbreviation "<Department>" course number "<Course Number>" and course name "<Name>":
    Then the system should display an error message "Department should be a 4 character alphabetical string"
    And the course should not be added to the course pool
      | Department | Course Number | Name                            |
      | EC         |           223 | Software Engineering Principles |
      | ECE1       |           321 | Introduction to Circuits        |
