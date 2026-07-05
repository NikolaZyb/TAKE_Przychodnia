package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Choroba;

@Repository
public interface ChorobaRepository extends JpaRepository<Choroba, Long> {

}
