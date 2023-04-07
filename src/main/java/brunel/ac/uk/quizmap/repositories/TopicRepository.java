package brunel.ac.uk.quizmap.repositories;

import brunel.ac.uk.quizmap.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic getTopicByTitle(String topicName);

    boolean existsTopicByTitle(String topicName);
}
