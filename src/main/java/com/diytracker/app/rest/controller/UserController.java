package com.diytracker.app.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.diytracker.app.entity.User;
import com.diytracker.app.logging.CustomLogger;
import com.diytracker.app.service.UserService;
import com.diytracker.app.util.LogConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(value = "DIYTracker user controller", description = "multiple methods ")
public class UserController {

	private static final String ERROR_BAD_CREDENTIALS = "Bad credentials";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

//	@Autowired
//	MailService mailService;
//
//	@Autowired
//	JwtTokenUtil jwtTokenUtil;

//	@Value("${jwt.secret}")
//	private String tokenSecret;
//	
//    @Value("${jwt.header}")
//    private String tokenHeader;

	
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ApiOperation(value = "Get a user from username",
       produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The GET call is Successful"),
            @ApiResponse(code = 500, message = "The GET call is Failed"),
            @ApiResponse(code = 404, message = "The API could not be found")
    })
	public @ResponseBody User getUser() {
		//String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
		//CustomLogger.logInfo(LogConstants.REST_LOGGER, "Returning the user account for user {}.", "test");
		
		return userService.getUser("test");
	}
	
	
//	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//	public JwtToken jsonLogin(@RequestBody JwtTokenRequest credentials) throws TechnicalErrorException {
//
//		LOGGER.info("Authenticating");
//		
//		JwtToken response = new JwtToken();
//
//		UserDetails userDetails = userService.loadUserByUsername(credentials.getUsername());
//
//		if (userDetails == null) {
//			response.setError(ERROR_BAD_CREDENTIALS);
//			return response;
//		}
//
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		boolean hashedPasswordValid = passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword());
//
//		if (hashedPasswordValid) {
//
//			Users user = userService.getUser(credentials.getUsername());
//			// Generate valid token
//			String token = jwtTokenUtil.generateToken(user);
//			response.setToken(token);
//			response.setUser(user);
//		} else {
//			response.setError(ERROR_BAD_CREDENTIALS);
//		}
//
//		return response;
//	}

//	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
//	public JwtToken refreshToken(@RequestBody String token) throws NullPointerException, TechnicalErrorException {
//		LOGGER.info("Refreshing token");
//		
//		JwtToken response = new JwtToken();
//
//		String username = jwtTokenUtil.getUsernameFromToken(token);
//		Users user = userService.getUser(username);
//
//		if(user == null) {
//			response.setError(ERROR_BAD_CREDENTIALS);
//			return response;
//		}
//
//		String refreshedToken = jwtTokenUtil.refreshToken(token);
//
//		response.setToken(refreshedToken);
//		response.setUser(user);
//		return response;
//	}
	
//	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
//	public @ResponseBody Users saveUser(@RequestBody Users user) throws TechnicalErrorException {
//		LOGGER.info("Saving user {}", user.getUsername());
//		
//		Set<Role> roles = new HashSet<Role>();
//		roles.add(userService.getRole(1));
//		user.setRoles(roles);
//		user.setEnabled(false);
//		user.setFailedLogins(0);
//		user.setLocked(false);
//		user.setLastLoginDate(new Date());
//
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String hashedPassword = passwordEncoder.encode(user.getPassword());
//
//		user.setPassword(hashedPassword);
//		userService.saveUser(user);
//		String[] adminList = userService.getAdminsMail();
//		mailService.sendAdminMail(adminList);
//		return user;
//	}

//	@RequestMapping(value = "/disabledUsers", method = RequestMethod.GET)
//	public @ResponseBody List<Users> getDisabledUsers() {
//		LOGGER.info("Listing all disabled users (locked and new users)");
//
//		return userService.getDisabledUsers();
//	}

//	@RequestMapping(value = "/enableUser", method = RequestMethod.GET)
//	public @ResponseBody List<Users> enableUser(@RequestParam String username) throws TechnicalErrorException {
//		LOGGER.info("Enabling user {}", username);
//		
//		Users user = userService.getUser(username);
//		user.setEnabled(true);
//		user.setLocked(false);
//		userService.saveUser(user);
//		mailService.sendUserMail(user.getEmail());
//		return userService.getDisabledUsers();
//	}

//	@RequestMapping(value = "/updateRole", method = RequestMethod.GET)
//	public @ResponseBody List<Users> updateRole(@RequestParam String username) throws TechnicalErrorException {
//		
//		Users user = userService.getUser(username);
//		Set<Role> roles = user.getRoles();
//		if(roles.size() == 2) {
//			roles.remove(userService.getRole(2));
//			LOGGER.info("Updating role for user {} from {} to {}", username, userService.getRole(2).getName(), userService.getRole(1).getName());
//		} else {
//			roles.add(userService.getRole(2));
//			LOGGER.info("Updating role for user {} from {} to {}", username, userService.getRole(1).getName(), userService.getRole(2).getName());
//		}
//
//		user.setRoles(roles);
//		userService.saveUser(user);
//
//		return userService.getDisabledUsers();
//
//	}

//	@RequestMapping(value = "/userAccount", method = RequestMethod.GET)
//	public @ResponseBody User getUserAccount(@RequestHeader(value="Authorization") String token) throws NullPointerException, TechnicalErrorException {
//		
//		String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
//		LOGGER.info("Returning the user account for user {}.", username);
//		
//		return userService.getUser(username);
//	}

}
