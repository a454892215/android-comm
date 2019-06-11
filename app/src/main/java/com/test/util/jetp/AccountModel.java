package com.test.util.jetp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AccountModel extends AndroidViewModel {
    // 创建LiveData
    private MutableLiveData<AccountBean> mAccount = new MutableLiveData<>();

    public MutableLiveData<AccountBean> getmAccount() {
        return mAccount;
    }

    public void setmAccount(MutableLiveData<AccountBean> mAccount) {
        this.mAccount = mAccount;
    }

    public AccountModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
