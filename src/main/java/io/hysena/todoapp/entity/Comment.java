package io.hysena.todoapp.entity;

import io.hysena.todoapp.dto.CreateCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(CreateCommentRequestDto requestDto, User user, Todo todo) {
        this.text = requestDto.getText();
        this.user = user;
        this.todo = todo;
    }
}
