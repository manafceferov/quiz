package com.jafarov.quiz.entity;

import jakarta.persistence.*;

@Entity
public class Question {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private QuizEntity quiz;

        public Question() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public QuizEntity getQuiz() {
            return quiz;
        }

        public void setQuiz(QuizEntity quiz) {
            this.quiz = quiz;
        }
    }