package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nikita.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {

}
