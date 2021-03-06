package ru.menkov.informsources.repositories.custom;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.menkov.informsources.model.custom.Friends;

@Repository
public interface FriendsRepository extends CrudRepository<Friends,Integer> {
    Boolean existsFriendsByIdAndFriends(Integer id, Integer friend);
}
