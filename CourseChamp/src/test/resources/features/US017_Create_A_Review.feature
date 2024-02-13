Feature: Create a review
As a user with an account of CourseChamp,
I would like to create a review,
So that I can describe my experience with a course. 

  Scenario Outline: User successfully creates a review (Normal Flow)
    Given a user logged in the system has the username “<username>”
    When a user attempts to leave a review with the rating “<rating>”, with the content “<content>”, for a course number “<number>” in the department “<department>”
    Then the system shall contain a review with a unique ID, username “<username>”, rating “<rating>” , content “<content>” and course “<course>”
      | username      | rating | content                   | number | department |
      | John          |      5 | You need to be organized  |    321 | ECSE       |
      | TigerLover123 |      5 | Great content             |    428 | ECSE       |
      | Lucy!         |      3 | It is a pretty hard class |    262 | MATH       |

  Scenario Outline: User creates a review with an empty course or description (Error Flow)
    Given a user logged in the system has the username “<username>”
    When a user attempts to leave a review with the rating “<rating>”, with the content “<content>”, for a course number “<number>” in the department “<department>”
    Then a "<message>" message is issued
      | username | rating | content                 | number | department | message                   |
      | John     |      4 |                         |    321 |            | All fields must be filled |
      | Tiger123 |      5 | 10-person group project |        |            | All fields must be filled |
      | Lucy!    |        |                         |        |            | All fields must be filled |
