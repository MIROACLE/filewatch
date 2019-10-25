package com.watch.aiface.base.repository.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.watch.aiface.base.repository.BaseRepository;
import com.watch.aiface.base.repository.BaseRepositoryImpl;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

public class BaseRepositoryFactory extends JpaRepositoryFactory {
	public BaseRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return BaseRepository.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
			RepositoryInformation information, EntityManager entityManager) {
		return new BaseRepositoryImpl<T, ID>((Class<T>) information.getDomainType(), entityManager);
	}
}