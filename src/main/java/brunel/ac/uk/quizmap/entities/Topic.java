package brunel.ac.uk.quizmap.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int questionNumbers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", orphanRemoval = true)
    private List<Question> questions;

    public int getQuestionNumbers() {
        return questions.size();
    }

    @PrePersist
    @PreUpdate
    private void updateQuestionNumbers() {
        this.questionNumbers = getQuestionNumbers();
    }
}
