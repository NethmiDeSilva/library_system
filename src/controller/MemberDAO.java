package controller;

import model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class MemberDAO {
    private Connection connection;

    public MemberDAO() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new SQLException("Failed to initialize database connection.", e);
        }
    }

    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM member";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                member.setMobile(rs.getString("mobile"));
                member.setAddress(rs.getString("address"));
                members.add(member);
            }
        }
        return members;
    }

    public void addMember(Member member) throws SQLException {
        String query = "INSERT INTO member(name, mobile, address) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getMobile());
            pstmt.setString(3, member.getAddress());
            pstmt.executeUpdate();
        }
    }

    public void updateMember(Member member) throws SQLException {
        String query = "UPDATE member SET name = ?, mobile = ?, address = ? WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getMobile());
            pstmt.setString(3, member.getAddress());
            pstmt.setInt(4, member.getMemberId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMember(int memberId) throws SQLException {
        String query = "DELETE FROM member WHERE member_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();
        }
    }
}
