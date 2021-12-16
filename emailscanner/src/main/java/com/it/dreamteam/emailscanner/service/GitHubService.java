package com.it.dreamteam.emailscanner.service;

import com.it.dreamteam.emailscanner.exception.NameNotFoundException;
import com.it.dreamteam.emailscanner.model.ProfileInfo;
import lombok.SneakyThrows;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GHUserSearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedSearchIterable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GitHubService {
    public static GitHub github;

    public ProfileInfo findProfileInfo(String name) {

        GHUser user = findUserByName(name);

        //3. получить рабочий имейл и дату
        GHCommit lastCommit = getLastCommit(user);

        try {
            ProfileInfo info = ProfileInfo.builder()
                    .login(user.getLogin())
                    .picture(user.getAvatarUrl())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();

            if (lastCommit != null) {
                info.setWorkEmail(lastCommit.getCommitShortInfo().getCommitter().getEmail());
                info.setLastCommit(lastCommit.getCommitShortInfo().getCommitter().getDate());
            }
            return info;
        } catch (IOException e) {
            return null;
        }
    }

    public GHUser findUserByName(String name) {

        GHUserSearchBuilder ghUserSearchBuilder = github.searchUsers();
        List<GHUser> ghUsers = ghUserSearchBuilder.q(name).language(null).list()._iterator(10).nextPage();
        if (ghUsers.isEmpty()) {
            throw new NameNotFoundException("Пользователь с таким именем не наден");
        } else if (ghUsers.size() == 1) {
            return ghUsers.get(0);
        } else {
            throw new RuntimeException(String.format("Найдено %d+ аккаунтов с заданым именем", ghUsers.size()));
        }
    }

    @SneakyThrows
    private GHCommit getLastCommit(GHUser user) {
        Comparator<GHRepository> comparator = (o1, o2) -> {
            try {
                if (o1.getUpdatedAt().before(o2.getUpdatedAt())) {
                    return 1;
                } else if (o1.getUpdatedAt().after(o2.getUpdatedAt())) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (IOException e) {
                return 0;
            }
        };

        List<GHRepository> list = new ArrayList<>(user.getRepositories().values());
        list.sort(comparator);

        PagedIterable<GHCommit> ghCommits = list.get(0).listCommits();
        List<GHCommit> commits = ghCommits._iterator(10).nextPage();
        for (GHCommit commit : commits) {
            if (Objects.equals(commit.getCommitter().getLogin(), user.getLogin())) {
                return commit;
            }
        }
        return null;
    }

    public List<GHUser> findUsersByName(String name, String language) {
        GHUserSearchBuilder ghUserSearchBuilder = github.searchUsers();
        PagedSearchIterable<GHUser> list = ghUserSearchBuilder.q(name).language(language).list();

        return list._iterator(10).nextPage();
    }

}
