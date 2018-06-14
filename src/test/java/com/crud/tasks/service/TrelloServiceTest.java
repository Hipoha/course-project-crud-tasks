package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoardsTest() {
        //Given
        TrelloBoardDto board1 = new TrelloBoardDto("1", "board1", new ArrayList<>());
        TrelloBoardDto board2 = new TrelloBoardDto("2", "board2", new ArrayList<>());
        List<TrelloBoardDto> boards = new ArrayList<>(Arrays.asList(board1, board2));

        when(trelloClient.getTrelloBoards()).thenReturn(boards);
        //When
        List<TrelloBoardDto> boardsResult = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(2, boardsResult.size());
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        TrelloCardDto card = new TrelloCardDto("Card1", "Description1", "1", "001");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("1", "Card1", "short");

        when(trelloClient.createNewCard(card)).thenReturn(newCard);
        doNothing().when(emailService).send(any(Mail.class));
        //When
        CreatedTrelloCardDto newCardResult = trelloService.createTrelloCard(card);
        //Then
        assertEquals("1", newCardResult.getId());
        assertEquals("Card1", newCardResult.getName());
        assertEquals("short", newCardResult.getShortUrl());
    }

}
