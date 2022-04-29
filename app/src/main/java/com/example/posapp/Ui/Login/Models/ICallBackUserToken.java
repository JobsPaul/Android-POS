package com.example.posapp.Ui.Login.Models;

import java.util.Date;

public interface ICallBackUserToken {
    void onToken(String username, String fName, String token, String exp, boolean tag);
}
