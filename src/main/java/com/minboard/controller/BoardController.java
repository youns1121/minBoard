package com.minboard.controller;

import com.minboard.dto.BoardDto;
import com.minboard.dto.CommentsDto;
import com.minboard.dto.DownloadFileDto;
import com.minboard.dto.UploadFileDto;
import com.minboard.service.BoardService;
import com.minboard.service.CommentService;
import com.minboard.service.FileStoreService;
import com.minboard.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final FileStoreService fileStoreService;
    private final CommentService commentService;

    /** 게시물 생성페이지 **/
    @GetMapping("/new")
    public String formBoard(Model model, BoardSaveVo boardSaveVo) {

        model.addAttribute("board", boardSaveVo.builder().build());
        return "html/boardNew";
    }

    /** 게시물 생성하기 **/
    @ResponseBody
    @PostMapping("/new")
    public String createBoard(@Validated @ModelAttribute("board") BoardSaveVo boardSaveVo) throws IOException {

        boardService.createBoard(boardSaveVo);
        boardService.saveBoardFile(boardSaveVo);
        return boardSaveVo.getId().toString();
    }

    /** 게시물 리스트 **/
    @GetMapping("/list")
    public String getBoardPagingList(@ModelAttribute("boardDto") BoardDto boardDto, Model model) {

        List<BoardDto> boardList = boardService.getBoardList(boardDto);
        model.addAttribute("boardList", boardList);
        return "html/boardList";
    }

    /** 게시물 상세페이지 **/
    @GetMapping("/view/{id}")
    public String getDetailBoardView(@PathVariable("id") int id, Model model) {

        BoardDto detailViewBoard = boardService.getDetailViewBoard(id);
        List<UploadFileDto> uploadFileList = fileStoreService.getUploadFileList(id);
        model.addAttribute("detailViewBoard", detailViewBoard);
        model.addAttribute("uploadFileList", uploadFileList);
        return "html/boardDetail";
    }

    /** 첨부파일 다운로드 **/
    @GetMapping("/attach/{fileId}")
    public ResponseEntity<String> downloadAttach(@PathVariable int fileId) throws MalformedURLException {

        DownloadFileDto downloadFileInfo = fileStoreService.downloadAttachedFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, downloadFileInfo.getContentDisposition())
                .body(downloadFileInfo.getResource());
    }

    /** 게시물 수정하기 **/
    @ResponseBody
    @PostMapping("/update")
    public String updateBoard(@Validated @ModelAttribute("boardUpdateVo") BoardUpdateVo boardUpdateVo,
                              @ModelAttribute("uploadFileUpdateVo") UploadFileUpdateVo uploadFileUpdateVo
                              ) throws IOException {

        boardService.updateBoardFile(boardUpdateVo);
        boardService.updateBoard(boardUpdateVo);
        return boardUpdateVo.getId().toString();
    }

    /** 게시물 수정페이지 **/
    @GetMapping("/update/{id}")
    public String getDetailViewUpdateBoard(@PathVariable("id") int id, Model model) {

        BoardDto boardUpdateVo = boardService.getDetailViewBoard(id);
        List<UploadFileDto> uploadFileList = fileStoreService.getUploadFileList(id);
        model.addAttribute("uploadFileUpdateVo", UploadFileUpdateVo.builder().build());
        model.addAttribute("boardUpdateVo", boardUpdateVo);
        model.addAttribute("uploadFileList", uploadFileList);
        return "html/boardEdit";
    }

    /** 게시물 삭제하기 **/
    @PostMapping("/delete")
    public String deleteBoard(int id) {

        boardService.deleteBoard(id);
        return "redirect:/board/list";
    }

    /** 단일 파일삭제 **/
    @PostMapping("/deleteFile")
    public void deleteFile(int id){
        fileStoreService.deleteFile(id);
    }

    /** 댓글작성하기**/
    @ResponseBody
    @PostMapping("/comment")
    public void insertComment(CommentsSaveVo commentsSaveVo){
        commentService.insertComments(commentsSaveVo);
    }

    /** 게시물의 계층형 댓글전체 보기 **/
    @GetMapping("/commentsList/{id}")
    public String getBoardHierarchicalCommentsList(@PathVariable("id") int id, Model model){

        List<CommentsDto> commentsList = commentService.getBoardHierarchicalCommentsList(id);
        model.addAttribute("commentsList", commentsList);
        return "html/boardCommentsDetail";
    }

    /**댓글 수정하기 **/
    @ResponseBody
    @PostMapping("/comment/update")
    public void updateComment(CommentsUpdateVo commentsUpdateVo){
        commentService.updateComments(commentsUpdateVo);
    }

    /**댓글 삭제 하기 **/
    @ResponseBody
    @PostMapping("/commentDelete")
    public void deleteCommit(int id){
        commentService.deleteComment(id);
    }

    /** 댓글의 답변 작성하기 **/
    @ResponseBody
    @PostMapping("/comment/reply")
    public void insertCommentsReply(CommentsReplySaveVo commentsReplySaveVo){
        commentService.insertCommentsReply(commentsReplySaveVo);
    }
}