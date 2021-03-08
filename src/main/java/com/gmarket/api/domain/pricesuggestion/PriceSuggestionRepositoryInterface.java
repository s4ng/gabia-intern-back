package com.gmarket.api.domain.pricesuggestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceSuggestionRepositoryInterface extends JpaRepository<PriceSuggestion, Long> {

}
