Feature: Create User Account
As a user of CourseChamp,
I would like to create an account in the CourseChamp System,
So that I can rate and review my previous courses.

  Scenario Outline: New user with unique info (Normal Flow)
    Given no account in the system has the email “<email>” and username “<username>”
    When a new user attempts to register with email “<email>”, username “<username>” and password “<password>”
    Then a user shall exist with email “<email>”, username “<username>” and password “<password>”
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User registers with an existent email (Error Flow)
    Given an account in the system has the email “<email>”
    When a new user attempts to register with email “<email>”, username “<username>” and password “<password>”
    Then an “Email already used” message is issued
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User registers with an existent username (Error Flow)
    Given an account in the system has the username “<username>”
    When a new user attempts to register with email “<email>”, username “<username>” and password “<password>”
    Then a “Username already used” message is issued
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User registers with an empty email, username or password (Error Flow)
    Given no account in the system has the email “<email>” and username “<username>”
    When a new user attempts to register with email “<email>”, username “<username>” and password “<password>”
    Then a “All fields must be filled” message is issued
      | email                | username | password   |
      |                      | John     | J0hn!Super |
      | john.jonny@gmail.com |          | J0hn!Super |
      | john.jonny@gmail.com | John     |            |
