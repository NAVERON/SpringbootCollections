package restful.entity;


import java.util.Map;

public class UserEntity {

    private Long id;
    private String accountId;
    private String name;
    private String nickName;
    private String password;

    private AccountSource accountSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public AccountSource getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(AccountSource accountSource) {
        this.accountSource = accountSource;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public enum AccountSource {
        QQ, WE_CHAT, BAIDU, FEI_SHU
    }
}







