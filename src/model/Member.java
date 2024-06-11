package model;

public class Member {
    private int member_id;
    private String name;
    private String mobile;
    private String address;

    public int getMemberId() {
        return member_id;
    }
    public void setMemberId(int memberId) {
        this.member_id = memberId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
