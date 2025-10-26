package in.nikita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nikita.entity.ContactMessage;
import in.nikita.repository.ContactMessageRepository;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    @Autowired
    private ContactMessageRepository repo;

    @Override
    public ContactMessage addMessage(ContactMessage message) {
        return repo.save(message);
    }

   

	@Override
	public List<ContactMessage> displayMessage() {
		
		return repo.findAll();
	}
}
