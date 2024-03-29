package com.hcl.matrimony.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.matrimony.dto.InterestCreationInput;
import com.hcl.matrimony.dto.InterestCreationResponse;
import com.hcl.matrimony.dto.InterestStatusUpdation;
import com.hcl.matrimony.dto.RegistrationDto;
import com.hcl.matrimony.service.InterestProfileService;
import com.hcl.matrimony.service.UserRegistrationService;

/**
 * userRegistration Controller
 * 
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserRegistrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	InterestProfileService interestProfileService;

	@PostMapping("/profile/interest")
	public ResponseEntity<InterestCreationResponse> interestProfiles(
			@RequestBody InterestCreationInput interestCreationInput) {

		LOGGER.info(" enter into UserRegistrationController--> interestProfiles");

		return interestProfileService.interestProfiles(interestCreationInput.getFromMobile(),
				interestCreationInput.getTargetMobile());

	}

	@PutMapping("/profile/interest")
	public ResponseEntity<InterestCreationResponse> interestProfilesUpdate(
			@RequestBody InterestStatusUpdation interestStatusUpdation) {
		return interestProfileService.interestProfilesUpadte(interestStatusUpdation.getFromMobile(),
				interestStatusUpdation.getTargetMobile(), interestStatusUpdation.getStatus());

	}

	/**
	 * @param resitrationDto
	 * @return
	 */
	@PostMapping("/profile")
	public ResponseEntity<String> registeration(@RequestBody RegistrationDto resitrationDto) {

		LOGGER.info(" UserRegistrationController :: " + resitrationDto);

		return new ResponseEntity<>(userRegistrationService.registration(resitrationDto), HttpStatus.CREATED);
	}
}
