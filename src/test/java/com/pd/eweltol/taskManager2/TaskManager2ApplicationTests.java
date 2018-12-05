package com.pd.eweltol.taskManager2;

import com.pd.eweltol.taskManager2.repository.ProblemRepository;
import com.pd.eweltol.taskManager2.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TaskManager2ApplicationTests {

	@Autowired
	ProblemRepository problemRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	public void contextLoads() {

		problemRepository.delete(problemRepository.findAll().get(0));

		//userRepository.delete(userRepository.findByUsername("supermanager"));
	}

}
