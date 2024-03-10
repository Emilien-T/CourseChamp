Feature: Update a Course's information
   As an admin of the website,
   I would like update a course's information,
   So that general information about a course,
   Reviews and a space to write reviews are in the same place and up to date.

Background:
  Given the following admins exist in the system:
    | email                       | username  | password    |
    | courseChampAdmin@email.com  | TheBest   | Password1!  |
    And the following courses exist in the system:
      | department | courseNumber | name                          | description  |
      | ECSE       |          428 | Software Engineering Practice | Fun course   |
      | ECSE       |          222 | Digital Logic                 | Scary course |
      | MATH       |          141 | Calculus 2                    | Integrals    |
      | MATH       |          262 | Intermediate Calculus         | Charles Roth |

  Scenario Outline: Admin successfully updates a course's information (Normal Flow)
    When the admin attempts to update the course "<courseCode>", with name "<newName>", description "<newDescription>", and syllabus "<newSyllabus>"
    Then the system should confirm the successful update
    And the course with course code "<courseCode>" should have the name "<newName>", description "<newDescription>", and syllabus "<newSyllabus>"
    
    Examples:
      | courseCode  | newName               | newDescription                | newSyllabus                   |
      | ECSE222     | Digital Clock         | I said I like it like that    | VHDL then VHDL                |
      | ECSE428     | That Software Course  | O champs Elysees              | Interviews with Prof.Robert   |
      | MATH262     | Calculus me that      | J'habite seul avec Maman      | Headache exercises            |

  Scenario Outline: Admin unsuccessfully attempts to update various fields of a course to invalid values (Error Flow)
    When the admin attempts to update the course "<courseCode>", with name "<newName>", description "<newDescription>", and syllabus "<newSyllabus>"
    Then the system should display the error message for unsuccessful update of a course's information "<error>"
    And the course with course code "<courseCode>" should have the name "<finalName>", description "<finalDescription>", and syllabus "<finalSyllabus>"
    
    Examples:
      | courseCode  | newName               | newDescription                | newSyllabus                   | finalName             | finalDescription          | finalSyllabus                 | error                         |
      | ECSE222     |                       | I said I like it like that    | VHDL then VHDL                | Digital Logic         | I said I like it like that| VHDL then VHDL                | Name cannot be blank.         |
      | ECSE428     | That Software Course  |                               | Interviews with Prof.Robert   | That Software Course  | Fun course                | Interviews with Prof.Robert   | Description cannot be blank.  |
      | MATH262     | Calculus me that      | J'habite seul avec Maman      |                               | Calculus me that      | J'habite seul avec Maman  |                               | Syllabus cannot be blank.     |

  Scenario Outline: Admin unsuccessfully attempts to update the name of a course to be the same as another course (Error Flow)
    When the admin attempts to update the course "<courseCode>", with name "<newName>", description "<newDescription>", and syllabus "<newSyllabus>"
    Then the system should display the error message for unsuccessful update of a course's information "<error>"
    And the course with course code "<courseCode>" should have the name "<finalName>", description "<finalDescription>", and syllabus "<finalSyllabus>"
    
    Examples:
      | courseCode  | newName               | newDescription                | newSyllabus                   | finalName             | finalDescription          | finalSyllabus                 | error                                         |
      | ECSE222     | Calculus 2            | I said I like it like that    | VHDL then VHDL                | Digital Logic         | I said I like it like that| VHDL then VHDL                | Name cannot be the same as another course's.  |