package com.minboard.mapper;

import com.minboard.dto.BoardAdminDto;
import com.minboard.dto.BoardDto;
import com.minboard.vo.BoardAdminSaveVo;
import com.minboard.vo.BoardAdminUpdateVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardAdminMapper {

    void insertBoardAdminSetting(BoardAdminSaveVo boardAdminSaveVo);

    void updateBoardAdminSetting(BoardAdminUpdateVo boardAdminUpdateVo);

    List<BoardAdminDto> selectBoardCategoryList();

    /** 관리자 게시물 상세보기 **/
    BoardAdminDto selectBoardCategory(int id);

    /** 관리자 게시물 상세보기 **/
    BoardAdminDto selectBoardAdmin(int boardAdminId);

    void deleteBoardAdmin(int boardAdminId);

    int totalCountCategoryBoard(int categoryNumber);

    /** 관리자 게시물 리스트 **/
    List<BoardAdminDto> selectBoardAdminList(BoardAdminDto boardAdminDto);

}