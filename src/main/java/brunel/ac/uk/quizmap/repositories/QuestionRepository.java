package brunel.ac.uk.quizmap.repositories;

import brunel.ac.uk.quizmap.entities.Question;
import brunel.ac.uk.quizmap.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopic(Topic topic);
}
