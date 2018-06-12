package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapFromDtoToDomain() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("0001", "List1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("0002", "List2", true);
        TrelloListDto trelloListDto3 = new TrelloListDto("0003", "List3", true);
        TrelloListDto trelloListDto4 = new TrelloListDto("0004", "List4", false);
        TrelloListDto trelloListDto5 = new TrelloListDto("0005", "List5", true);
        List<TrelloListDto> listDto1 = new ArrayList<>(Arrays.asList(trelloListDto1, trelloListDto2));
        List<TrelloListDto> listDto2 = new ArrayList<>(Arrays.asList(trelloListDto3, trelloListDto4, trelloListDto5));

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1001", "Board1", listDto1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("1002", "Board2", listDto2);
        List<TrelloBoardDto> listBoardDto = new ArrayList<>(Arrays.asList(trelloBoardDto1, trelloBoardDto2));

        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "card1", "The first card", "1", "0002");

        //When
        List<TrelloList> list1 = trelloMapper.mapToList(listDto1);
        List<TrelloList> list2 = trelloMapper.mapToList(listDto2);
        List<TrelloBoard> listBoard = trelloMapper.mapToBoards(listBoardDto);
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(2, list1.size());
        assertEquals(3, list2.size());
        assertEquals("0001", list1.get(0).getId());
        assertEquals("List1", list1.get(0).getName());
        assertEquals(false, list1.get(0).isClosed());

        assertEquals(2, listBoard.size());
        assertEquals("1001", listBoard.get(0).getId());
        assertEquals("Board1", listBoard.get(0).getName());
        assertEquals(2, listBoard.get(0).getLists().size());
        assertEquals("0001", listBoard.get(0).getLists().get(0).getId());
        assertEquals("List1", listBoard.get(0).getLists().get(0).getName());
        assertEquals(false, listBoard.get(0).getLists().get(0).isClosed());

        assertEquals("card1", trelloCard.getName());
        assertEquals("The first card", trelloCard.getDescription());
        assertEquals("1", trelloCard.getPos());
        assertEquals("0002", trelloCard.getListId());
    }

    @Test
    public void testMapFromDomainToDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("0001", "List1", false);
        TrelloList trelloList2 = new TrelloList("0002", "List2", true);
        TrelloList trelloList3 = new TrelloList("0003", "List3", true);
        TrelloList trelloList4 = new TrelloList("0004", "List4", false);
        TrelloList trelloList5 = new TrelloList("0005", "List5", true);
        List<TrelloList> list1 = new ArrayList<>(Arrays.asList(trelloList1, trelloList2));
        List<TrelloList> list2 = new ArrayList<>(Arrays.asList(trelloList3, trelloList4, trelloList5));

        TrelloBoard trelloBoard1 = new TrelloBoard("1001", "Board1", list1);
        TrelloBoard trelloBoard2 = new TrelloBoard("1002", "Board2", list2);
        List<TrelloBoard> listBoard = new ArrayList<>(Arrays.asList(trelloBoard1, trelloBoard2));

        TrelloCard trelloCard = new TrelloCard(
                "card1", "The first card", "1", "0002");

        //When
        List<TrelloListDto> listDto1 = trelloMapper.mapToListDto(list1);
        List<TrelloListDto> listDto2 = trelloMapper.mapToListDto(list2);
        List<TrelloBoardDto> listBoardDto = trelloMapper.mapToBoardsDto(listBoard);
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(2, listDto1.size());
        assertEquals(3, listDto2.size());
        assertEquals("0001", listDto1.get(0).getId());
        assertEquals("List1", listDto1.get(0).getName());
        assertEquals(false, listDto1.get(0).isClosed());

        assertEquals(2, listBoardDto.size());
        assertEquals("1001", listBoardDto.get(0).getId());
        assertEquals("Board1", listBoardDto.get(0).getName());
        assertEquals(2, listBoardDto.get(0).getLists().size());
        assertEquals("0001", listBoardDto.get(0).getLists().get(0).getId());
        assertEquals("List1", listBoardDto.get(0).getLists().get(0).getName());
        assertEquals(false, listBoardDto.get(0).getLists().get(0).isClosed());

        assertEquals("card1", trelloCardDto.getName());
        assertEquals("The first card", trelloCardDto.getDescription());
        assertEquals("1", trelloCardDto.getPos());
        assertEquals("0002", trelloCardDto.getListId());
    }
}
