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
    Then the user shall successfully login into the system with the account with the username "<username>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User successfully login using username (Alternative Flow)
    When a user attempts to log in with email "<username>" and password "<password>"
    Then the user shall successfully login into the system with the account with the username "<username>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: User login with a non-existent email (Error Flow)
    When a user attempts to log in with email "<wrongEmail>" and password "<password>"
    Then a "No account with this email exists" message is issued

    Examples: 
      | email                       | username      | password    | wrongEmail            |
      | john.jonny@gmail.com        | John          | J0hn!Super  | kpjm.kpmmu@gmail.com  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | blabla@mail.mcgill.ca |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | lucydavid@hotmail.com |

  Scenario Outline: User login with a wrong password (Error Flow)
    When a user attempts to log in with email "<email>" and password "<wrongPassword>"
    Then a "Given password is wrong" message is issued

    Examples: 
      | email                       | username      | password    | wrongPassword    |
      | john.jonny@gmail.com        | John          | J0hn!Super  | John!Super       |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | ILovePasta!!!123 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | !@#$%^&*ABCdef   |
