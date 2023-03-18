package toyproject.community.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.monitor.FileEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toyproject.community.controller.MyPageForm;
import toyproject.community.controller.SignUpForm;
import toyproject.community.domain.User;
import toyproject.community.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signIn(String userId, String password){
        return userRepository.findByUserIdAndPassword(userId, password);
    }

    public Long signUp(SignUpForm signUpForm) {
        User user = userRepository.findByUserId(signUpForm.getUserId());
        if (user == null) {
            User joinUser = new User();
            joinUser.joinUser(signUpForm.getUserId(), signUpForm.getUserName(), signUpForm.getPassword(), signUpForm.getEmail());
            User save = userRepository.save(joinUser);
            return save.getUserIdx();
        }else{
            return 0L;
        }
    }

    public User myPage(String userIdx) {
        User user = userRepository.findByUserIdx(Long.parseLong(userIdx));
        return user;
    }

    @Transactional
    public String myPageUpdate(String userId, MyPageForm myPageForm, MultipartFile file) throws IOException {
        User user = userRepository.findByUserId(userId);
        System.out.println("file = " + file);
        String fileName = fileSave(file);
        System.out.println("fileName = " + fileName);
        //변경감지
        user.editUserInfo(myPageForm.getUsername(), myPageForm.getEmail(), fileName);
        return fileName;
    }

    public String fileSave(MultipartFile file) throws IOException {
        if (file.isEmpty()){
            return "";
        }

        //file Name
        String filename = file.getOriginalFilename();
        System.out.println("filename = " + filename);
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        String extension = filename.substring(filename.lastIndexOf("."));
        System.out.println("extension = " + extension);
        String saveName = uuid + extension;
        System.out.println("saveName = " + saveName);

        //file save
        file.transferTo(new File(saveName));
        return saveName;
    }

}
