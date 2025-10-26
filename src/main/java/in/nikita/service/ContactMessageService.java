package in.nikita.service;

import java.util.List;

import in.nikita.entity.ContactMessage;

public interface ContactMessageService {
	//for inserting data 
		ContactMessage addMessage(ContactMessage message);
		
		//for display msg
		List<ContactMessage> displayMessage();

}
