package com.hcl.matrimony.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.matrimony.dto.InterestCreationInput;
import com.hcl.matrimony.dto.InterestCreationResponse;
import com.hcl.matrimony.dto.InterestStatusUpdation;
import com.hcl.matrimony.service.InterestProfileService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserRegistrationController {
	
	
	private final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

	@Autowired
	InterestProfileService interestProfileService;

	@PostMapping("/profile/interest")
	public ResponseEntity<InterestCreationResponse> interestProfiles(@RequestBody InterestCreationInput interestCreationInput) {
		
		logger.info(" enter into UserRegistrationController--> interestProfiles");
		
		
		return interestProfileService.interestProfiles(interestCreationInput.getFromMobile(),
				interestCreationInput.getTargetMobile());

	}
	
	@PutMapping("/profile/interest")
	public ResponseEntity<InterestCreationResponse> interestProfiles(@RequestBody InterestStatusUpdation interestStatusUpdation) {
		return interestProfileService.interestProfilesUpadte(interestStatusUpdation.getFromMobile(), interestStatusUpdation.getTargetMobile(), interestStatusUpdation.getStatus());

	}

}
