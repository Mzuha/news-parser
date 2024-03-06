package com.mzuha.newsparser.service;

import com.mzuha.newsparser.entity.ArticleEntity;
import com.mzuha.newsparser.model.ArticlesItem;
import com.mzuha.newsparser.repository.ArticleRepository;
import com.mzuha.newsparser.util.ArticleMapper;
import com.mzuha.newsparser.util.TimeOfDay;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    public static final int ONE_HOUR = -1;
    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public ArticlesItem findById(Long id) {
        return articleMapper.mapToItem(
                articleRepository.findById(id).orElse(getArticleEntityThatWasNotFound())
        );
    }

    public Optional<ArticlesItem> findFirstWithinDayFragment(TimeOfDay fragment) {
        return getArticlesItemStream()
                .filter((e) -> isValidDayFragment(fragment, e))
                .findFirst();
    }

    public Optional<ArticlesItem> findNextWithinDayFragment(TimeOfDay fragment, Long id) {
        return getArticlesItemStream()
                .filter((e) -> fragment == TimeOfDay.getTimeOfDay(e.getZonedPublishedAt().toLocalTime()) && e.getId() > id)
                .findFirst();
    }

    public Optional<ArticlesItem> findPrevWithinDayFragment(TimeOfDay fragment, Long id) {
        return getArticlesItemStream()
                .filter((e) -> isValidDayFragment(fragment, e) && e.getId() < id)
                .findFirst();
    }

    private static boolean isValidDayFragment(TimeOfDay fragment, ArticlesItem e) {
        return fragment == TimeOfDay.getTimeOfDay(e.getZonedPublishedAt().toLocalTime());
    }

    private Stream<ArticlesItem> getArticlesItemStream() {
        return articleRepository.findAll().stream()
            .map(articleMapper::mapToItem);
    }

    public void save(ArticleEntity articleEntity) {
        articleRepository.save(articleEntity);
    }

    public Optional<ArticleEntity> findByHeadline(String title) {
        return articleRepository.findByHeadline(title);
    }

    public void clearOldNews() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, ONE_HOUR);
        Date cutoffTime = cal.getTime();
        articleRepository.deleteOldData(cutoffTime);
    }

    private ArticleEntity getArticleEntityThatWasNotFound() {
        return new ArticleEntity();
    }
}
