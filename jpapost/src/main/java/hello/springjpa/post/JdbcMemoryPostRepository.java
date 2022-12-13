package hello.springjpa.post;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcMemoryPostRepository extends MemoryPostRepository {

    private final DataSource dataSource;

    public JdbcMemoryPostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    @Override
    public Long save(Post post) {
        String sql = "insert into post(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null; //sql 날리기 위함
        ResultSet rs = null; // 결과를 담을 곳

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, post.getTitle());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys(); // id 값을 꺼내오게 함

            if (rs.next()) { // 값이 있으면
                post.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 삽입 실패");
            }
            return post.getId();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 사용 이후에는 반드시 닫아야함!
        }
    }

    @Override
    public Long findId(String title) {
        /*for(int i=0; i<store.size(); i++) {
            Post post = store.get(i);
            if (post.getTitle() == title) {
                return post.getId();
            }
        }*/

        String sql = "select id from post where title = (?)";

        Connection conn = null;
        PreparedStatement pstmt = null; //sql 날리기 위함
        ResultSet rs = null; // 결과를 담을 곳

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, title);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys(); // id 값을 꺼내오게 함

            if (rs.next()) { // 값이 있으면
                return rs.getLong(1);
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 사용 이후에는 반드시 닫아야함!
        }
    }

    @Override
    public Post find(Long id) {
        //return store.get(id);

        Post post = new Post();
        String sql = "select * from post where id = (?)";

        Connection conn = null;
        PreparedStatement pstmt = null; //sql 날리기 위함
        ResultSet rs = null; // 결과를 담을 곳

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys(); // id 값을 꺼내오게 함

            if (rs.next()) { // 값이 있으면
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return post;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 사용 이후에는 반드시 닫아야함!
        }
    }

    @Override
    public void edit(Post post) {
        /*Post p = store.get(post.getId());
        p.setTitle(post.getTitle());
        p.setDescription(post.getDescription());*/

        String sql = "update post SET id=(?), title=(?), description=(?)";

        Connection conn = null;
        PreparedStatement pstmt = null; //sql 날리기 위함
        ResultSet rs = null; // 결과를 담을 곳

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, post.getId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getDescription());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys(); // id 값을 꺼내오게 함

            if (rs.next()) { // 값이 있으면
                System.out.println("edit id = "+rs.getLong(1)
                        +", title="+rs.getString(2)+", description="+rs.getString(3));
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 사용 이후에는 반드시 닫아야함!
        }
    }

    @Override
    public void remove(Long id) {
        /*store.remove(id);*/

        String sql = "delete from post where id = (?)";

        Connection conn = null;
        PreparedStatement pstmt = null; //sql 날리기 위함
        ResultSet rs = null; // 결과를 담을 곳

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys(); // id 값을 꺼내오게 함

            if (rs.next()) { // 값이 있으면
                System.out.println("delete id = "+ rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 사용 이후에는 반드시 닫아야함!
        }
    }
}
