package hello.springjpa.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MemberDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    public Member getMemberInfo(PostLoginReq postLoginReq){
        String getPwdQuery = "select memberId, name, pwd, status, date, id from User where ID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new Member(
                        rs.getLong("memberId"),
                        rs.getString("name"),
                        rs.getString("pwd"),
                        rs.getInt("status"),
                        rs.getDate("date"),
                        rs.getString("id")), getPwdParams);
    }
}
}
