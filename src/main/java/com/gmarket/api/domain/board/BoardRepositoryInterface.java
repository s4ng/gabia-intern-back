package com.gmarket.api.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositoryInterface extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {



}
