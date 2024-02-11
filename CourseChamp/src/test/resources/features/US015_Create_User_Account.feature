Feature: Create User Account
As a user of CourseChamp,
I would like to create an account in the CourseChamp System,
So that I can rate and review my previous courses.

  Scenario Outline: New user with unique info (Normal Flow)
    Given no account in the system has the email "<email>" and username "<username>"
    When a new user attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a user shall exist with email "<email>", username "<username>" and password "<password>"
    
    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User registers with an existent email (Error Flow)
    Given an account in the system has the email "<email>"
    When a new user attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                       | username      | password    | message            |
      | john.jonny@gmail.com        | John          | J0hn!Super  | Email already used |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | Email already used |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | Email already used |

  Scenario Outline: User registers with an existent username (Error Flow)
    Given an account in the system has the username "<username>"
    When a new user attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                       | username      | password    | message               |
      | john.jonny@gmail.com        | John          | J0hn!Super  | Username already used |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | Username already used |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | Username already used |

  Scenario Outline: User registers with an empty email, username or password (Error Flow)
    Given no account in the system has the email "<email>" and username "<username>"
    When a new user attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                | username | password   | message                   |
      |                      | John     | J0hn!Super | All fields must be filled |
      | john.jonny@gmail.com |          | J0hn!Super | All fields must be filled |
      | john.jonny@gmail.com | John     |            | All fields must be filled |
