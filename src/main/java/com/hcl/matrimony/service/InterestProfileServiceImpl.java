package com.hcl.matrimony.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hcl.matrimony.controller.UserRegistrationController;
import com.hcl.matrimony.dto.Estatus;
import com.hcl.matrimony.dto.InterestCreationResponse;
import com.hcl.matrimony.entity.InterestShown;
import com.hcl.matrimony.repository.InterestShownRepository;
import com.hcl.matrimony.repository.UserProfilesRepository;

@Service
public class InterestProfileServiceImpl implements InterestProfileService {

	private final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

	
	@Autowired
	InterestShownRepository interestShownRepository;
	@Autowired
	UserProfilesRepository userProfilesRepository;
	
	
	//  interestProfiles for creating the interest from one user to onether user

	@Override 
	public ResponseEntity<InterestCreationResponse> interestProfiles(Long fromMobile, Long targetMobile) {
		logger.info("InterestProfileServiceImpl--->interestProfiles enterd");
		
		if (userProfilesRepository.findByMobile(fromMobile).isEmpty()
				|| userProfilesRepository.findByMobile(targetMobile).isEmpty())
			throw new MatromonyException(" profiles not existed");

		if (!interestShownRepository.findByFromMobileAndTargetMobile(fromMobile, targetMobile).isEmpty())
			throw new MatromonyException(" alreay interest  created");

		InterestShown interestedShown = new InterestShown();
		interestedShown.setDate(LocalDateTime.now());
		interestedShown.setFromMobile(fromMobile);
		interestedShown.setTargetMobile(targetMobile);
		interestShownRepository.save(interestedShown);

		InterestCreationResponse interestCreationResponse = new InterestCreationResponse();
		interestCreationResponse.setMessage("interest crated succsessfully");
		interestCreationResponse.setStatusCode(HttpStatus.CREATED.value());
		
		logger.info("InterestProfileServiceImpl--->interestProfiles empleted");

		return ResponseEntity.status(HttpStatus.CREATED).body(interestCreationResponse);

	}
	
	
	//  interestProfilesUpadte for accept or reject the request profile


	@Override
	public ResponseEntity<InterestCreationResponse> interestProfilesUpadte(Long fromMobile, Long targetMobile,
			String status) {

		logger.info("InterestProfileServiceImpl--->interestProfilesUpadte enterd");

		List<InterestShown> interestShown = interestShownRepository.findByFromMobileAndTargetMobile(fromMobile,
				targetMobile); 
		if (interestShown.isEmpty())
			throw new MatromonyException(" not interest profiles found");
		
		if((!status.equalsIgnoreCase(Estatus.ACCEPT.toString())) && (!status.equalsIgnoreCase(Estatus.REJECT.toString())))
			throw new MatromonyException("incorrect staus");

		
		
			if(status.equalsIgnoreCase(Estatus.ACCEPT.toString()))
				interestShown.get(0).setStatus(Estatus.ACCEPT.toString());
			if(status.equalsIgnoreCase(Estatus.REJECT.toString()))
				interestShown.get(0).setStatus(Estatus.REJECT.toString());

		 
			interestShownRepository.save(interestShown.get(0));

		InterestCreationResponse interestCreationResponse = new InterestCreationResponse();
		interestCreationResponse.setMessage(" status succsessfully updated");
		interestCreationResponse.setStatusCode(HttpStatus.CREATED.value());

		logger.info("InterestProfileServiceImpl--->interestProfilesUpadte completed");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(interestCreationResponse);

	}

}
