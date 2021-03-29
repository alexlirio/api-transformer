package com.company.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransformerRepositoryTest {

	Transformer t1;

	@Autowired
	private ITransformerRepository repository;

	@BeforeEach
	void setup() {
		String transformerName = "Rumble";
		t1 = new Transformer(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		t1 = this.repository.save(t1);
	}

	@Test
	void create() {
		String transformerName = "Rumble";
		t1 = this.repository.save(t1);
		Assertions.assertThat(t1.getName()).isEqualTo(transformerName);
	}

	@Test
	void update() {
		String transformerName = "Overkill";
		t1.setName(transformerName);
		this.repository.save(t1);
		Assertions.assertThat(t1.getName()).isEqualTo(transformerName);
	}

	@Test
	void delete() {
		this.repository.deleteById(t1.getId());
		Assertions.assertThat(this.repository.findById(t1.getId())).isEmpty();
	}

	@Test
	void list() {
		Assertions.assertThat(this.repository.findAll().size()).isGreaterThan(0);
	}
}
