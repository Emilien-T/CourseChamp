Feature: Login to Existing User Account
As a user of CourseChamp,
I would like to log in to my account with my credentials,
So that I can delete, edit, view and manage my posts.

  Background: 
    Given the following students exist in the system:
      | email             | username | password    | major      |
      | student1@mail.com | student1 | J0hn!Super  | Software   |
      | student2@mail.com | student2 | JohnLover!5 | Computer   |
      | student3@mail.com | student3 | Jk*g@iJHK$% | Electrical |
    And the following admins exist in the system:
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: Admin successfully logs in using email (Normal Flow)
    When an admin attempts to log in with email "<email>" and password "<password>"
    Then the admin shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% |

  Scenario Outline: Admin successfully logs in using username (Alternative Flow)
    When an admin attempts to log in with username "<username>" and password "<password>"
    Then the admin shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email                       | username      | password    |
      | john.jonny@gmail.com        | John          | J0hn!Super  |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 |

  Scenario Outline: Student successfully logs in using email (Normal Flow)
    When a student attempts to log in with email "<email>" and password "<password>"
    Then the student shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email             | username | password    |
      | student1@mail.com | student1 | J0hn!Super  |
      | student2@mail.com | student2 | JohnLover!5 |
      | student3@mail.com | student3 | Jk*g@iJHK$% |

  Scenario Outline: Admin successfully logs in using username (Alternative Flow)
    When a student attempts to log in with username "<username>" and password "<password>"
    Then the student shall successfully login into the system with the account with the email "<email>"

    Examples: 
      | email             | username | password    |
      | student1@mail.com | student1 | J0hn!Super  |
      | student2@mail.com | student2 | JohnLover!5 |
      | student3@mail.com | student3 | Jk*g@iJHK$% |

  Scenario Outline: Admin login with a non-existent email (Error Flow)
    When an admin unsuccessfully attempts to log in with email "<wrongEmail>" and password "<password>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email                       | username      | password    | wrongEmail            | errorMessage            |
      | john.jonny@gmail.com        | John          | J0hn!Super  | kpjm.kpmmu@gmail.com  | Admin account not found |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | blabla@mail.mcgill.ca | Admin account not found |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | lucydavid@hotmail.com | Admin account not found |

  Scenario Outline: Admin login with a wrong password (Error Flow)
    When an admin unsuccessfully attempts to log in with email "<email>" and password "<wrongPassword>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email                       | username      | password    | wrongPassword    | errorMessage                      |
      | john.jonny@gmail.com        | John          | J0hn!Super  | John!Super       | Please enter the correct password |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | ILovePasta!!!123 | Please enter the correct password |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | !@#$%^&*ABCdef   | Please enter the correct password |

  Scenario Outline: Student login with a non-existent email (Error Flow)
    When a student unsuccessfully attempts to log in with email "<wrongEmail>" and password "<password>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email                       | username      | password    | wrongEmail            | errorMessage              |
      | john.jonny@gmail.com        | John          | J0hn!Super  | kpjm.kpmmu@gmail.com  | Student account not found |
      | percy.golber@mail.mcgill.ca | TigerLover123 | JohnLover!5 | blabla@mail.mcgill.ca | Student account not found |
      | lucy.david@hotmail.com      | Lucy!         | Jk*g@iJHK$% | lucydavid@hotmail.com | Student account not found |

  Scenario Outline: Student login with a wrong password (Error Flow)
    When a student unsuccessfully attempts to log in with email "<email>" and password "<wrongPassword>"
    Then the message "<errorMessage>" is issued by the system

    Examples: 
      | email             | username      | password    | wrongPassword   | errorMessage                      |
      | student1@mail.com | John          | J0hn!Super  | WrongPassword1! | Please enter the correct password |
      | student2@mail.com | TigerLover123 | JohnLover!5 | WrongPassword1! | Please enter the correct password |
      | student3@mail.com | Lucy!         | Jk*g@iJHK$% | WrongPassword1! | Please enter the correct password |
