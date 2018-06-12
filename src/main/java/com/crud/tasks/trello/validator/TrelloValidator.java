package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public List<TrelloBoard> validateTrelloBoards(final List<TrelloBoard> trelloBoards) {
        LOGGER.info("Filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(toList());
        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return filteredBoards;
    }

    public void validateCard(final TrelloCard trelloCard) {

        if(trelloCard.getName().contains("test")) {
            LOGGER.info("There is a card containing \"test\" in its name");
        } else {
            LOGGER.info("There is no card containing \"test\" in its name");
        }
    }
}
