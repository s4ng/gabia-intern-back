package com.gmarket.api.domain.comment;

import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.dto.CommentMapper;
import com.gmarket.api.domain.comment.enums.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Bean Component
@Transactional(readOnly = true)
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class CommentService {
    private final CommentRepository commentRepository;

    private String fpcJpql1 ="select m from ";
    private String fpcJpql2 =" m where m.board.boardId = ";
    private String fpcJpql3 ="AND m.status != 'DELETE' ORDER BY m.commentId ACS";

    public List<Comment> findPostComments(BoardType boardType, long boardId){
        return commentRepository.findPostComments(fpcJpql1+BoardType.enumToJpql(boardType)+fpcJpql2+boardId+fpcJpql3);
    }

    @Transactional
    public Comment createComment(BoardType boardType, CommentDto commentDto){
        Comment comment = CommentMapper.dtoToEntity(boardType, commentDto);

        return comment;
    } // 댓글 생성 서비스

    @Transactional
    public Comment updateComment(BoardType boardType, CommentDto commentDto){
        Comment comment = CommentMapper.dtoToEntity(boardType, commentDto);
        comment.modified();
        return commentRepository.updateComment(comment);
    } // 댓글 수정 서비스

    @Transactional
    public Long deleteComment(BoardType boardType, long commentId){
        Comment comment = commentRepository.findComment(commentId);
        return commentRepository.deleteComment(comment);
    } // 댓글 삭제 서비스
}
