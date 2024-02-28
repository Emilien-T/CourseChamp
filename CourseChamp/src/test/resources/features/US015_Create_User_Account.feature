Feature: Create User Account
As a user of CourseChamp,
I would like to create an account in the CourseChamp System,
So that I can rate and review my previous courses.

  Background: 
    Given the following students exist in the system:
      | email          | username | password    | major      |
      | user1@mail.com | user1    | J0hn!Super  | Software   |
      | user2@mail.com | user2    | JohnLover!5 | Computer   |
      | user3@mail.com | user3    | Jk*g@iJHK$% | Electrical |

  Scenario Outline: New user with unique info (Normal Flow)
    When a new user successfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a user shall exist with email "<email>", username "<username>" and password "<password>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User registers with an existent email (Error Flow)
    When a new user unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email          | username      | password    | message                                        |
      | user1@mail.com | John          | J0hn!Super  | Another account with this email already exists |
      | user2@mail.com | TigerLover123 | JohnLover!5 | Another account with this email already exists |
      | user3@mail.com | Lucy!         | Jk*g@iJHK$% | Another account with this email already exists |

  Scenario Outline: User registers with an existent username (Error Flow)
    When a new user unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                          | username | password    | message                                           |
      | john.jonny123@gmail.com        | user1    | J0hn!Super  | Another account with this username already exists |
      | percy.golber123@mail.mcgill.ca | user2    | JohnLover!5 | Another account with this username already exists |
      | lucy.david123@hotmail.com      | user3    | Jk*g@iJHK$% | Another account with this username already exists |

  Scenario Outline: User registers with an empty email, username or password (Error Flow)
    When a new user unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                | username | password   | message                   |
      |                      | John     | J0hn!Super | Email cannot be blank     |
      | john.jonny@gmail.com |          | J0hn!Super | Username cannot be blank. |
      | john.jonny@gmail.com | John     |            | Password cannot be blank  |

  Scenario Outline: New admin with unique info (Normal Flow)
    When a new admin successfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then an admin shall exist with email "<email>", username "<username>" and password "<password>"

    Examples: 
      | email                   | username      | password    |
      | alice.admin@gmail.com   | Alice_Admin   | Alice!123   |
      | bob.admin@gmail.com     | Bob_Admin     | Bob!456     |
      | charlie.admin@gmail.com | Charlie_Admin | Charlie!789 |

  Scenario Outline: Admin registers with an existent email (Error Flow)
    When a new admin unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email          | username       | password    | message                                       |
      | user1@mail.com | John1          | J0hn!Super  | Another account with this email already exists |
      | user2@mail.com | Tiger_Lover123 | JohnLover!5 | Another account with this email already exists |
      | user3@mail.com | Lucy!!         | Jk*g@iJHK$% | Another account with this email already exists |

  Scenario Outline: Admin registers with an existent username (Error Flow)
    When a new admin unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                          | username | password    | message                                         |
      | john.jonny123@gmail.com        | user1    | J0hn!Super  | Another account with this username already exists |
      | percy.golber123@mail.mcgill.ca | user2    | JohnLover!5 | Another account with this username already exists |
      | lucy.david123@hotmail.com      | user3    | Jk*g@iJHK$% | Another account with this username already exists |

  Scenario Outline: Admin registers with an empty email, username or password (Error Flow)
    When a new admin unsuccessfully attempts to register with email "<email>", username "<username>" and password "<password>"
    Then a "<message>" message is issued

    Examples: 
      | email                | username | password   | message                   |
      |                      | John     | J0hn!Super | Email cannot be blank     |
      | john.jonny@gmail.com |          | J0hn!Super | Username cannot be blank |
      | john.jonny@gmail.com | John     |            | Password cannot be blank  |
