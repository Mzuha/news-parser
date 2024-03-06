package com.mzuha.newsparser;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;

import com.mzuha.newsparser.model.ArticlesItem;
import com.mzuha.newsparser.service.ArticleService;
import com.mzuha.newsparser.service.ScheduledTasks;
import com.mzuha.newsparser.util.TimeOfDay;
import java.time.ZonedDateTime;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class NewsController {

    private final ArticleService articleService;
    private final ScheduledTasks scheduledTasks;
    @FXML
    public Button prevButton;
    @FXML
    public Button nextButton;
    @FXML
    public ChoiceBox<String> timeChoiseBox;
    @FXML
    public Text description;
    @FXML
    public Text title;
    @FXML
    public Text publishedAt;
    private Long newsId = 1L;
    private int newsSize = 0;

    public NewsController(ArticleService articleService, ScheduledTasks scheduledTasks) {
        this.articleService = articleService;
        this.scheduledTasks = scheduledTasks;
    }

    @FXML
    public void initialize() {
        newsSize = scheduledTasks.parseNews();
        setInfo(articleService.findById(newsId));
    }

    @FXML
    public void handlePrevButtonClick(ActionEvent event) {
        if (newsId > 1) {
            if (timeChoiseBox.getValue() == null) {
                newsId--;
                setInfo(articleService.findById(newsId));
            } else {
                Optional<ArticlesItem> prevWithinDayFragment = articleService.findPrevWithinDayFragment(TimeOfDay.valueOf(timeChoiseBox.getValue()), newsId);
                prevWithinDayFragment.ifPresent(
                        (a) -> {
                            setInfo(a);
                            newsId = prevWithinDayFragment.get().getId();
                        }
                );
            }
        }
        System.out.println(TimeOfDay.getTimeOfDay(ZonedDateTime.parse(publishedAt.getText().substring(14)).toLocalTime()));
    }

    @FXML
    public void handleNextButtonClick(ActionEvent event) {
        if (!(newsId + 1 > newsSize)) {
            if (timeChoiseBox.getValue() == null) {
                newsId++;
                ArticlesItem articleItem = articleService.findById(newsId);
                setInfo(articleItem);
            } else {
                Optional<ArticlesItem> nextWithinDayFragment = articleService.findNextWithinDayFragment(TimeOfDay.valueOf(timeChoiseBox.getValue()), newsId);
                nextWithinDayFragment.ifPresent(
                        (a) -> {
                            setInfo(a);
                            newsId = nextWithinDayFragment.get().getId();
                        }
                );
            }
        }
        System.out.println(TimeOfDay.getTimeOfDay(ZonedDateTime.parse(publishedAt.getText().substring(14)).toLocalTime()));
    }

    @FXML
    public void handleTimeChoiseBoxClick(ActionEvent actionEvent) {
        Optional<ArticlesItem> findFirstWithinDayFragment = articleService.findFirstWithinDayFragment(
                TimeOfDay.valueOf(timeChoiseBox.getValue())
        );
        if (findFirstWithinDayFragment.isPresent()) {
            newsId = findFirstWithinDayFragment.get().getId();
            setInfo(findFirstWithinDayFragment.get());
        } else {
            setMissingNesInfo();
        }
    }

    private void setInfo(ArticlesItem articleItem) {
        title.setText("Title: " + articleItem.getTitle());
        description.setText("Description: " + articleItem.getDescription());
        publishedAt.setText("Published at: " + articleItem.getPublishedAt());
    }

    private void setMissingNesInfo() {
        title.setText("No news was found!");
        description.setText(EMPTY_STRING);
        publishedAt.setText(EMPTY_STRING);
    }
}
