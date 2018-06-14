package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void validateTrelloBoardsTest() {
        //Given
        TrelloBoard board1 = new TrelloBoard("0001", "Board1", new ArrayList<>());
        TrelloBoard board2 = new TrelloBoard("0002", "Board2", new ArrayList<>());
        TrelloBoard board3 = new TrelloBoard("0003", "Test", new ArrayList<>());

        List<TrelloBoard> boardsNoTest = new ArrayList<>(Arrays.asList(board1, board2));
        List<TrelloBoard> boardsWithTest = new ArrayList<>(Arrays.asList(board1, board2, board3));
        //When
        List<TrelloBoard> filteredBoardsNoTest = trelloValidator.validateTrelloBoards(boardsNoTest);
        List<TrelloBoard> filteredBoardsWithTest = trelloValidator.validateTrelloBoards(boardsWithTest);
        //Then
        assertEquals(2, filteredBoardsNoTest.size());
        assertEquals(2, filteredBoardsWithTest.size());
    }

    @Test
    public void validateCardTest() {
        //Given
        TrelloCard card1 = new TrelloCard("Card1", "a", "1", "1");
        TrelloCard card2 = new TrelloCard("TestCard1", "b", "2", "2");
        //When
        trelloValidator.validateCard(card1);
        trelloValidator.validateCard(card2);
        //Then
    }

}
