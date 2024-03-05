Feature: User Portal
    As a user of CourseChamp, 
    I would like to have a user portal where I can view and edit my personal information as well as linked to my reviews page 
    so that my account accurately represents me.

  Background: 
    Given the following students exist in the system:
      | email             | username | password    | major      |
      | student1@mail.com | student1 | J0hn!Super  | Software   |
      | student2@mail.com | student2 | JohnLover!5 | Computer   |
      | student3@mail.com | student3 | Jk*g@iJHK$% | Electrical |
      | student4@mail.com | student4 | Password1!  | Software   |
    And the following admins exist in the system:
      | email           | username | password    |
      | admin1@mail.com | admin1   | J0hn!Super  |
      | admin2@mail.com | admin2   | JohnLover!5 |
      | admin3@mail.com | admin3   | Jk*g@iJHK$% |
    And the following courses exist in the system:
      | department | courseNumber | name                          | description  |
      | ECSE       |          428 | Software Engineering Practice | Fun course   |
      | ECSE       |          222 | Digital Logic                 | Scary course |
      | MATH       |          262 | Intermediate Calculus         | Charles Roth |
    And the following course offerings exist in the system:
      | courseCode | semester |
      | ECSE222    | W2022    |
      | ECSE222    | F2022    |
      | ECSE428    | W2020    |
      | ECSE428    | F2020    |
      | MATH262    | F2022    |
      | MATH262    | W2023    |
    And the following reviews exist in the system:
      | courseCode | semester | reviewId | student           | rating | comment                               |
      | ECSE222    | W2022    |        1 | student1@mail.com |      4 | Great course very informative!        |
      | ECSE222    | F2022    |        2 | student2@mail.com |      3 | Very hard exams :(                    |
      | ECSE428    | W2020    |        3 | student3@mail.com |      5 | Excellent content and helpful quizzes |
      | ECSE428    | F2020    |        4 | student2@mail.com |      5 | I had so much fun in this course      |
      | MATH262    | F2022    |        5 | student1@mail.com |      3 | Some topics could be explained better |
      | MATH262    | W2023    |        6 | student2@mail.com |      5 | CHARLES ROTH                          |

  Scenario Outline: Student successfully changes their username (Normal Flow)
    When the student "<email>" attempts to change their username to "<username>"
    Then the student shall have the new username "<username>"

    Examples: 
      | email             | username |
      | student1@mail.com | student4 |
      | student2@mail.com | student5 |
      | student3@mail.com | student6 |

  Scenario Outline: Student unsuccessfully changes their username (Error Flow)
    When the student "<email>" unsuccessfully attempts to change their username to "<username>"
    Then the system shall display the error message "<errorMessage>"

    Examples: 
      | email             | username | errorMessage                                      |
      | student1@mail.com | student2 | Another account with this username already exists |
      | student2@mail.com | student3 | Another account with this username already exists |
      | student3@mail.com | admin1   | Another account with this username already exists |

  Scenario Outline: Student successfully changes their password (Alternate Flow)
    When the student "<email>" attempts to change their password to "<password>"
    Then the student shall have the new password "<password>"

    Examples: 
      | email             | password    |
      | student1@mail.com | Password1!  |
      | student2@mail.com | BigRob$2024 |
      | student3@mail.com | EcSe428!!!  |

  Scenario Outline: Student unsuccessfully changes their password (Error Flow)
    When the student "<email>" unsuccessfully attempts to change their password to "<password>"
    Then the system shall display the error message "<errorMessage>"

    Examples: 
      | email             | password                                    | errorMessage                                                                         |
      | student1@mail.com |                                        2hI$ | Password must have 5-13 character                                                    |
      | student2@mail.com | Thi$isWaaaaaaaaaaaaaaaaayTooLongOfAPassw0rd | Password must have 5-13 character                                                    |
      | student3@mail.com | N0SpecilChar                                | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |
      | student1@mail.com | noupperca$e                                 | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |
      | student2@mail.com | NOLOWERCA$E                                 | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |

  Scenario Outline: Student successfully changes their major (Alternate Flow)
    When the student "<email>" attempts to change their major to "<major>"
    Then the student shall have the new major "<major>"

    Examples: 
      | email             | major      |
      | student1@mail.com | Electrical |
      | student2@mail.com | Software   |
      | student3@mail.com | Computer   |

  Scenario Outline: Admin successfully changes their username (Normal Flow)
    When the admin "<email>" attempts to change their username to "<username>"
    Then the admin shall have the new username "<username>"

    Examples: 
      | email           | username |
      | admin1@mail.com | admin4   |
      | admin2@mail.com | admin5   |
      | admin3@mail.com | admin6   |

  Scenario Outline: Admin unsuccessfully changes their username (Error Flow)
    When the admin "<email>" unsuccessfully attempts to change their username to "<username>"
    Then the system shall display the error message "<errorMessage>"

    Examples: 
      | email           | username | errorMessage                                      |
      | admin1@mail.com | admin2   | Another account with this username already exists |
      | admin2@mail.com | admin3   | Another account with this username already exists |
      | admin3@mail.com | student1 | Another account with this username already exists |

  Scenario Outline: Admin successfully changes their password (Alternate Flow)
    When the admin "<email>" attempts to change their password to "<password>"
    Then the admin shall have the new password "<password>"

    Examples: 
      | email           | password    |
      | admin1@mail.com | Password1!  |
      | admin2@mail.com | BigRob$2024 |
      | admin3@mail.com | EcSe428!!!  |

  Scenario Outline: Admin unsuccessfully changes their password (Error Flow)
    When the admin "<email>" unsuccessfully attempts to change their password to "<password>"
    Then the system shall display the error message "<errorMessage>"

    Examples: 
      | email           | password                                    | errorMessage                                                                         |
      | admin1@mail.com |                                        2hI$ | Password must have 5-13 character                                                    |
      | admin2@mail.com | Thi$isWaaaaaaaaaaaaaaaaayTooLongOfAPassw0rd | Password must have 5-13 character                                                    |
      | admin3@mail.com | N0SpecilChar                                | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |
      | admin1@mail.com | noupperca$e                                 | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |
      | admin2@mail.com | NOLOWERCA$E                                 | Password contains at least one uppercase, lowercase and special character [!@#$%^+=] |

  Scenario: Student successfully views their reviews #1 (Alternate Flow)
    When the student "student1@mail.com" attempts to view their reviews
    Then the system shall display the following reviews to the student:
      | courseCode | semester | reviewId | student           | rating | comment                               |
      | ECSE222    | W2022    |        1 | student1@mail.com |      4 | Great course very informative!        |
      | MATH262    | F2022    |        5 | student1@mail.com |      3 | Some topics could be explained better |

  Scenario: Student successfully views their reviews #2 (Alternate Flow)
    When the student "student2@mail.com" attempts to view their reviews
    Then the system shall display the following reviews to the student:
      | courseCode | semester | reviewId | student           | rating | comment                          |
      | ECSE222    | F2022    |        2 | student2@mail.com |      3 | Very hard exams :(               |
      | ECSE428    | F2020    |        4 | student2@mail.com |      5 | I had so much fun in this course |

  Scenario: Student successfully views their reviews #3 (Alternate Flow)
    When the student "student3@mail.com" attempts to view their reviews
    Then the system shall display the following reviews to the student:
      | courseCode | semester | reviewId | student           | rating | comment                               |
      | ECSE428    | W2020    |        3 | student3@mail.com |      5 | Excellent content and helpful quizzes |

  Scenario: Student unsuccessfully view their reviews (Error Flow)
    When the student "student4@mail.com" unsuccessfully attempts to view their reviews
    Then the system shall display the error message "No reviews found"
