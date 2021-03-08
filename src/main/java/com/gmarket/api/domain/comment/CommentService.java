package com.gmarket.api.domain.comment;


import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.UserRepositoryInterface;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final BoardRepositoryInterface boardRepositoryInterface;

    // 댓글 저장
    @Transactional
    public CommentDto save(BoardType boardType, CommentDto commentDto){

        if (!boardType.equals(commentDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if (commentDto.getComment().isEmpty()){
            throw new IllegalStateException("댓글 내용이 입력되지 않았습니다.");
        }

        Optional<Board> optionalBoard = boardRepositoryInterface.findById(commentDto.getBoardId());

        if (optionalBoard.isEmpty()){
            throw new IllegalStateException("존재하지 않은 게시판 입니다");
        }

        if(optionalBoard.get().getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 게시판 입니다");
        }

        Optional<User> optionalUser = userRepositoryInterface.findById(commentDto.getUserId());

        if (optionalUser.isEmpty()){
            throw new IllegalStateException("비회원은 댓글을 달 수 없습니다");
        }

        if (optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("탈퇴한 회원 입니다");
        }

        Comment comment = BoardType.boardTypeToSubClass(boardType);

        comment.dtoToEntity(commentDto);

        // 게시판, 유저 검증 후 세팅
        comment.boardAndUserSetting(optionalBoard.get(), optionalUser.get());

        comment.createdStatus(); // 생성 상태 설정W

        return commentDto.entityToDto(commentRepositoryInterface.save(comment));
    }


    // 댓글 수정
    @Transactional
    public CommentDto update(BoardType boardType, CommentDto commentDto){

        if (!boardType.equals(commentDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if (commentDto.getComment().isEmpty()){
            throw new IllegalStateException("댓글 내용이 입력되지 않았습니다.");
        }

        Optional<Comment> optionalComment = commentRepositoryInterface.findById(commentDto.getCommentId());

        if(optionalComment.isEmpty()){
            throw new IllegalStateException("존재하지 않는 댓글입니다.");
        }

        if(optionalComment.get().getStatus().equals(CommentStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }

        if(!commentDto.getUserId().equals(optionalComment.get().getUser().getUserId())){
            throw new IllegalStateException("댓글 작성자만 수정할 수 있습니다");
        }

        Comment comment = optionalComment.get();

        comment.update(commentDto);

        return commentDto.entityToDto(commentRepositoryInterface.save(comment));
    }

    // 댓글 삭제
    @Transactional
    public void delete(BoardType boardType, Long commentId){

        Optional<Comment> optionalComment = commentRepositoryInterface.findById(commentId);

        if(optionalComment.isEmpty()){
            throw new IllegalStateException("존재하지 않는 댓글입니다");
        }

        if(optionalComment.get().getStatus().equals(CommentStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 댓글입니다");
        }

        Comment comment = optionalComment.get();

        comment.deletedStatus();

        commentRepositoryInterface.save(comment);
    }
}