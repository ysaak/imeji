package ysaak.imeji.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.imeji.data.Tag;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {
    List<Tag> findAllByNameIn(List<String> names);
}
