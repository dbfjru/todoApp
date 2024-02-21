package io.hysena.todoapp.entity;

import io.hysena.todoapp.dto.CreateTodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
public class Todo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column
    private Boolean complete = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(CreateTodoRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

}
