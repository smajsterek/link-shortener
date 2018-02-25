package pl.linkshortener.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.linkshortener.dao.entities.Link;

@Repository
public interface Links extends CrudRepository<Link, String> {
}
