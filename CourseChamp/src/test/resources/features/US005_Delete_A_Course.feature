Feature: Delete a course
   As an admin of the website,
   I would like to delete courses on the website,
   So that I can keep the course pool available for users up-to-date.

  Background: 
    Given the following admins exist in the system:
      | email                      | username | password   |
      | courseChampAdmin@email.com | TheBest  | Password1! |
    And the following courses exist in the system:
      | department | courseNumber | name                          | description  | prerequisite |
      | ECSE       |          428 | Software Engineering Practice | Fun course   |              |
      | ECSE       |          222 | Digital Logic                 | Scary course |              |
      | MATH       |          141 | Calculus 2                    | Integrals    |              |
      | MATH       |          262 | Intermediate Calculus         | Charles Roth | MATH141      |
    And the following course offerings exist in the system:
      | courseCode | semester |
      | ECSE222    | W2022    |
      | ECSE222    | W2023    |
      | ECSE428    | W2020    |
      | ECSE428    | W2021    |
      | ECSE428    | W2022    |
      | MATH262    | F2022    |

  Scenario Outline: Admin successfully deletes a course (Normal Flow)
    When the admin attempts to delete a course with the course code "<courseCode>"
    Then the system should confirm the successful deletion
    And the course with course code "<courseCode>" should not exist in the course pool
    And there shouldn't be any course offering with the course code "<courseCode>" in the system

    Examples: 
      | courseCode |
      | ECSE222    |
      | ECSE428    |
      | MATH262    |

  Scenario Outline: Admin attempts to delete a non-existent course (Error Flow)
    When the admin unsuccessfully attempts to delete a course with the course code "<courseCode>"
    Then the system should display the error message for unsuccessful deletion "<error>"
    And the course with course code "<courseCode>" should not exist in the course pool
    And there shouldn't be any course offering with the course code "<courseCode>" in the system

    Examples: 
      | courseCode | error                                    |
      | PHIL202    | This course doesn't exist in the system. |
      | ENVR201    | This course doesn't exist in the system. |
      | GEOG555    | This course doesn't exist in the system. |

  Scenario Outline: Admin attempts to delete a prerequisite course (Error Flow)
    When the admin unsuccessfully attempts to delete a course with the course code "<courseCode>"
    Then the system should display the error message for unsuccessful deletion "<error>"
    And the course with course code "<courseCode>" should still exist in the course pool
    And there should still exist all the course offerings associated with the course code "<courseCode>" in the system

    Examples: 
      | courseCode | error                                                  |
      | MATH141    | This course cannot be removed as it is a prerequisite. |
