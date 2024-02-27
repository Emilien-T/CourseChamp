Feature: Create a review
As a user with an account of CourseChamp,
I would like to create a review,
So that I can describe my experience with a course. 

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
      | ECSE428    | W2020    |
      | MATH262    | F2022    |

  Scenario Outline: User successfully creates a review (Normal Flow)
    When the user "<email>" attempts to leave a review with the rating "<rating>", with the content "<content>", for the course offering "<courseOffering>" for the course "<courseCode>"
    Then the system shall contain a review with a unique ID, username "<username>", rating "<rating>" , content "<content>" and course "<course>"

    Examples: 
      | email             | rating | content                   | courseCode | courseOffering |
      | student1@mail.com |      5 | You need to be organized  | ECSE222    | W2022          |
      | student2@mail.com |      5 | Great content             | ECSE428    | W2020          |
      | student3@mail.com |      3 | It is a pretty hard class | MATH262    | F2022          |

  Scenario Outline: User creates a review with an empty course or description (Error Flow)
    When the user "<email>" unsuccessfully attempts to leave a review with the rating "<rating>", with the content "<content>", for the course offering "<courseOffering>" for the course "<courseCode>"
    Then a "<errorMessage>" error message is issued

    Examples: 
      | username          | rating | content     | courseCode | courseOffering | errorMessage                 |
      | student1@mail.com |      4 | Big project |            | W2022          | Course Code cannot be blank. |
      | student3@mail.com |      3 |             | MATH262    | F2022          | Text cannot be blank.        |
      | student1@mail.com |      5 | Big rob     | ECSE428    |                | Semester cannot be blank.    |

  Scenario Outline: User creates a review with an invalid rating (Error Flow)
    When the user "<email>" unsuccessfully attempts to leave a review with the rating "<rating>", with the content "<content>", for the course offering "<courseOffering>" for the course "<courseCode>"
    Then a "<errorMessage>" error message is issued

    Examples: 
      | username          | rating | content     | courseCode | courseOffering | errorMessage                |
      | student1@mail.com |     -1 | Big project | ECSE222    | W2022          | Rating must be between 1-5. |
      | student1@mail.com |      6 | Big class   | MATH262    | F2022          | Rating must be between 1-5. |
