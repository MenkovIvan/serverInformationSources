package ru.menkov.informsources.services.custom;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.menkov.informsources.helpers.RequestStatus;
import ru.menkov.informsources.model.custom.Friends;
import ru.menkov.informsources.repositories.custom.FriendsRepository;
import ru.menkov.informsources.repositories.custom.UserRepository;

@Service
@Slf4j
public class FriendsService extends ru.menkov.informsources.services.Service {

    private final FriendsRepository friendsRepository;

    private final UserRepository userRepository;

    private Gson gson = new Gson();

    @Autowired
    public FriendsService(FriendsRepository friendsRepository, UserRepository userRepository){
        this.friendsRepository = friendsRepository;
        this.userRepository = userRepository;
    }

    public String addFriends(String inputJson){
        Friends friendsFromClient = gson.fromJson(inputJson, Friends.class);

        String message = "Result of add = {";
        Integer status;

        if ((!userRepository.existsUserById(friendsFromClient.getId())
                || !userRepository.existsUserById(friendsFromClient.getFriends()))) {

            message += "incorrect, user or friend with input id are not exist";
            status = RequestStatus.ERROR.getStatus();

        } else if (friendsRepository.existsFriendsByIdAndFriends(friendsFromClient.getId(), friendsFromClient.getFriends())){
            message += "incorrect, user is follow to this friend yet";
            status = RequestStatus.ERROR.getStatus();
        } else{
            message += "correct information about follow was added";
            friendsRepository.save(friendsFromClient);
            status = RequestStatus.OK.getStatus();
        }
        message += "}";

        return getJsonString(message,status);
    }

    public String deleteFriends(String inputJson){
        Friends friendsFromClient = gson.fromJson(inputJson, Friends.class);

        String message = "Result of delete = {";
        Integer status;

        if (friendsRepository.existsFriendsByIdAndFriends(friendsFromClient.getId(), friendsFromClient.getFriends())){
            friendsRepository.delete(friendsFromClient);

            message += "correct, information about unfollow was added";
            status = RequestStatus.OK.getStatus();

        } else{

            message += "incorrect, user or friend with input id are not exist";
            status = RequestStatus.ERROR.getStatus();

        }
        message += "}";
        return getJsonString(message,status);
    }
}
