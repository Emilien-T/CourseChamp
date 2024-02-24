Feature: Login to Existing User Account
As a user of CourseChamp,
I would like to log in to my account with my credentials,
So that I can delete, edit, view and manage my posts.

  Background: 
    Given the following accounts exist in the system:
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User successfully login using email (Normal Flow)
    When a user attempts to log in with email "<email>" and password "<password>"
    Then the user shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User successfully login using username (Alternative Flow)
    When a user attempts to log in with username "<username>" and password "<password>"
    Then the user shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |

  Scenario Outline: User login with a non-existent email (Error Flow)
    When a user unsuccessfully attempts to log in with email "<wrongEmail>" and password "<password>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email                       | username      | password    | wrongEmail            | errorMessage      |
      | john.jonny@gmail.com        | John          | J0hn!Super  | kpjm.kpmmu@gmail.com  | Account not found |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | blabla@mail.mcgill.ca | Account not found |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | lucydavid@hotmail.com | Account not found |

  Scenario Outline: User login with a wrong password (Error Flow)
    When a user unsuccessfully attempts to log in with email "<email>" and password "<wrongPassword>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email                       | username      | password    | wrongPassword    | errorMessage                      |
      | john.jonny@gmail.com        | John          | J0hn!Super  | John!Super       | Please enter the correct password |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | ILovePasta!!!123 | Please enter the correct password |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | !@#$%^&*ABCdef   | Please enter the correct password |
