package com.purushotham.quizapp.service;

import com.purushotham.quizapp.entity.Question;
import com.purushotham.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;



    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
public List<Question>getQuestionsByCategory(String category){
        return questionRepository.findByCategory(category);
}

public ResponseEntity<String>addQuestion(Question question){
        questionRepository.save(question);
      return new ResponseEntity<>("SUCCESS",HttpStatus.CREATED) ;
}
}
