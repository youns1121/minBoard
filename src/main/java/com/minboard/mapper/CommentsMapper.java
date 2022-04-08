package com.minboard.mapper;

import com.minboard.dto.CommentsDto;
import com.minboard.vo.CommentsReplySaveVo;
import com.minboard.vo.CommentsSaveVo;
import com.minboard.vo.CommentsUpdateVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {

    /** 댓글 작성하기 **/
    void insertComments(CommentsSaveVo commentsSaveVo);

    /** 게시물의 댓글전체 보기 **/
    List<CommentsDto> getBoardCommentsList(int boardId);

    /** 게시물의 계층형 댓글전체 보기 **/
    List<CommentsDto> getBoardHierarchicalCommentsList(int boardId);

    /** 댓글 수정하기 **/
    void updateComments(CommentsUpdateVo commentsUpdateVo);

    /** 댓글 수정 페이지 **/
    CommentsDto getUpdateComments(int id);

    /** 게시물 댓글 총 갯수 **/
    Integer getBoardCommentsTotalCount(int boardId);

    /** 댓글 삭제하기 **/
    void deleteComment(int id);

    /** 게시글의 모든댓글 삭제하기 **/
    void deleteAllComment(int boardId);

    /** 댓글의 답변 작성하기 **/
    void insertCommentsReply(CommentsReplySaveVo commentsReplySaveVo);

    int findBySameGroupYn(CommentsReplySaveVo commentsReplySaveVo);

    void CommentsReplySortUpdate(CommentsReplySaveVo commentsReplySaveVo);

    void insertCommentsSetGroup(CommentsSaveVo commentsSaveVo);

    /** 계층형 공식계산 **/
    int hierarchicalCalculationFormula(CommentsReplySaveVo commentsReplySaveVo);

    /** 공식결과값이 0일때 **/
    int calculationFormulaResultZero(CommentsReplySaveVo commentsReplySaveVo);

    /** 결과값0 입력 **/
    void insertResultZero(CommentsReplySaveVo commentsReplySaveVo);

    void calculationFormulaResultNotZero(CommentsReplySaveVo commentsReplySaveVo);

    void insertResultNotZero(CommentsReplySaveVo commentsReplySaveVo);


}
