package com.pocketful.controller;

import com.pocketful.entity.Account;
import com.pocketful.service.AccountService;
import com.pocketful.service.AuthenticationService;
import com.pocketful.service.SessionManagerService;
import com.pocketful.util.SessionContext;
import com.pocketful.web.dto.account.AccountIdDTO;
import com.pocketful.web.dto.account.AuthenticatedAccountDTO;
import com.pocketful.web.dto.account.SignInRequestDTO;
import com.pocketful.web.dto.account.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/auth")
@RestController
public class AuthController {

    private final AccountService accountService;
    private final SessionManagerService sessionManagerService;
    private final AuthenticationService authenticationService;

    @PostMapping("sign-in")
    ResponseEntity<AuthenticatedAccountDTO> signIn(@RequestBody SignInRequestDTO request) {
        log.info("Authenticating account by email - {}", request.email());
        AuthenticatedAccountDTO account = authenticationService.authenticate(request.email(), request.password());
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("sign-up")
    ResponseEntity<AccountIdDTO> signUp(@RequestBody SignUpRequestDTO request) {
        log.info("Creating account: name - {} | email - {}", request.getName(), request.getEmail());
        Account account = accountService.create(request.getName(), request.getEmail(),  request.getPassword());
        AccountIdDTO accountId = new AccountIdDTO(account.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountId);
    }

    @DeleteMapping("sign-out")
    ResponseEntity<Void> signOut() {
        Account account = SessionContext.get();
        log.info("Signing out account by email - {}", account.getEmail());
        sessionManagerService.invalidate(account.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
