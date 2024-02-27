Feature: Create a course
  As an admin of the website, 
  I would like to add courses on the website
  So that I can keep the course pool available for users up-to-date.

Background:
  Given the following admins exist in the system:
    | email                       | username  | password    |
    | courseChampAdmin@email.com  | TheBest   | Password1!  |
  Given the following students exist in the system:
    | email             | username | password    | major      |
    | student1@mail.com | student1 | J0hn!Super  | Software   |
  Given the following courses exist in the system:
    | department  | courseNumber  | name        | description       |
    | ECSE        | 200           | Circuits I  | circuits and stuff|

  Scenario Outline: Admin successfully adds a new course  (Normal Flow)
    When the admin adds a course with Department abbreviation "<department>" course number "<courseNumber>" course name "<name>", and course description "<description>"
    Then the system should confirm the successful addition
    And a course with Department abbreviation "<department>" and course number "<courseNumber>" should exist in the course pool
    
    Examples:
      | department | courseNumber  | name                                 | description |
      | ECSE       |           223 | Software Engineering Principles      | Not done yet |
      | ECSE       |           321 | Introduction to Software Engineering | Not done yet |

  Scenario Outline: Admin attempts to add a duplicate course (Error Flow)
    When the admin unsuccessfully adds a course with Department abbreviation "<department>" course number "<courseNumber>" course name "<name>", and course description "<description>"
    Then the system should display an error message "<error>"
    And the course with Department abbreviation "<department>" course number "<courseNumber>" should not exist in the course pool twice

    Examples:
      | department | courseNumber  | name       | description         | error                 |
      | ECSE       |           200 | Circuits I | circuits and stuff  | A course with this code already exists |

  Scenario Outline: Admin adds a new course with various incomplete details (Error Flow)
    When the admin unsuccessfully adds a course with Department abbreviation "<department>" course number "<courseNumber>" course name "<name>", and course description "<description>"
    Then the system should display an error message "<error>"
    And the course with Department abbreviation "<department>" course number "<courseNumber>" should not exist in the course pool

    Examples:
      | department | courseNumber  | name                            | description        | error |
      |            |           223 | Software Engineering Principles | circuits and stuff | Department cannot be blank.\nDepartment must be a four-letter alphabetic string.\n|
      | ECSE       |               | Design Principles and methods   | why not?           | Course number cannot be null.|
      | ECSE       |           321 |                                 | Yessir             | Name cannot be blank.\n|
      | ECSE       |           222 | Digital Circuits                |                    | Description cannot be blank.\n|

  Scenario Outline: Admin adds a new course with an invalid course number (Error Flow)
    When the admin unsuccessfully adds a course with Department abbreviation "<department>" course number "<courseNumber>" course name "<name>", and course description "<description>"
    Then the system should display an error message "<error>"
    And the course with Department abbreviation "<department>" course number "<courseNumber>" should not exist in the course pool

    Examples:
      | department | courseNumber | name                            | description     | error|
      | ECSE       | ABC          | Software Engineering Principles | Not decided yet | Course number cannot be null.|
      | ECSE       |        1234  | Mechanical Design               | Not decided yet | Course number must be a positive 3-digit integer.\n|

  Scenario Outline: Admin adds a new course with an invalid department (Error Flow)
    When the admin unsuccessfully adds a course with Department abbreviation "<department>" course number "<courseNumber>" course name "<name>", and course description "<description>"
    Then the system should display an error message "<error>"
    And the course with Department abbreviation "<department>" course number "<courseNumber>" should not exist in the course pool

    Examples:
      | department | courseNumber  | name                            | description | error |
      | EC         |           223 | Software Engineering Principles | Not decided yet |Department must be a four-letter alphabetic string.\n|
      | ECE1       |           321 | Introduction to Circuits        | Not decided yet |Department must be a four-letter alphabetic string.\n|
