package com.nbcb.thinkingInJava.annotations;


import java.util.List;

/**
 * ģ��һ��util�࣬���Util���������ɸ�Util����
 * ÿ����������ע�⣺ @UseCase
 */
public class PasswordUtil {

    @UseCase(id = 16,
            description = "password must constains at least one number")
    public boolean validatePassword(String password){
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 29)
    public String encryptPassword(String password){
        return "new password";
    }

    @UseCase(id = 33,
            description = "new password can not equal previous ones")
    public boolean checkForNewPassword(
            List<String> previousPasswd, String password){
        return !previousPasswd.contains(password);
    }


}
