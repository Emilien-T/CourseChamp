package ca.mcgill.ecse428.CourseChamp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse428.CourseChamp.dto.AccountRequestDto;
import ca.mcgill.ecse428.CourseChamp.dto.AccountResponseDto;
import ca.mcgill.ecse428.CourseChamp.service.AccountService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins="*")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Returns the Account based on their Email
     * 
     * @param email - Pass in the email argument by using /account={?email}
     * @return the Account with Email and Username
     */
     @GetMapping(value = {"/account", "/account/"})
     public ResponseEntity<AccountResponseDto> getAccountByEmail(@RequestParam String email) {
        //TODO
        return null;
    }

    /**
     * Creates an account
     * 
     * @param accountRequestDto - Pass in a account dto using a JSON request
     * @return the dto response of the new Account
     */
    @PostMapping("/account/create")
    public ResponseEntity<AccountResponseDto> CreateUser(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        //TODO
        return null;
    }

    /**
     * Controller Method that allows the verfication of the email and password of an Account
     * 
     * @param email - Email of the account
     * @param password - Password of the account
     * @return the Account that you are now logged in
     */
    @GetMapping("/login")
    public ResponseEntity<AccountResponseDto> LoginUser(@RequestParam String email, @RequestParam String password){
        //TODO
        return null;
    }
}
