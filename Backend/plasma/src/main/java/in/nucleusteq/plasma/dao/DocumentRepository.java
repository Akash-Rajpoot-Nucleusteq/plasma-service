package in.nucleusteq.plasma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
