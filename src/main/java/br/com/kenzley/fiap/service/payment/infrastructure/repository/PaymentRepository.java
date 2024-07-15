package br.com.kenzley.fiap.service.payment.infrastructure.repository;

import br.com.kenzley.fiap.service.payment.infrastructure.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PaymentRepository extends MongoRepository<PaymentEntity, UUID> {
}
