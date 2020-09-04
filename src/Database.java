import assets.Advert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public Connection connection;
    private static final String ADVERTS_TABLE = "ADVERTS";

    Database(String url) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
        connection.setAutoCommit(false);

        boolean exists = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT name FROM sqlite_master WHERE type=? AND name=?"
            );
            preparedStatement.setString(1, "table");
            preparedStatement.setString(2, ADVERTS_TABLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        if (!exists) {

            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + ADVERTS_TABLE +
                    "(" +
                    "ID INTEGER PRIMARY KEY," +          //0
                    "PAGE TEXT," +                       //1
                    "NAME TEXT," +                       //2
                    "URL TEXT," +                        //3
                    "LOCATION TEXT," +                   //4
                    "PRICE TEXT," +                      //5
                    "AREA TEXT," +                       //6
                    "PRICE_PER_METER TEXT," +            //7
                    "CONTENT TEXT," +                    //8
                    "CONTENT_TO_COMPARE TEXT," +         //9
                    "MORE_INFO TEXT," +                  //10
                    "Photos TEXT," +                     //11
                    "UNIQUE_FOR_MANY TEXT," +            //12
                    "GOT_DETAILS TEXT," +                //13
                    "TIMESTAMP TEXT," +                  //14
                    "TIMESTAMP_CHANGE TEXT," +           //15
                    "TIMESTAMP_TEXT TEXT," +             //16
                    "TIMESTAMP_CHANGE_TEXT TEXT" +       //17
                    ")";
            try {
                Statement stmt = connection.createStatement();
                stmt.execute(sqlCreateTable);
                connection.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private List<Advert> resultToAdverts(ResultSet resultSet) throws SQLException {
        List<Advert> advertList = new ArrayList<>();
        while (resultSet.next()) {
            Advert advert = new Advert();
            advert.setPage(resultSet.getString("PAGe"));
            advert.setName(resultSet.getString("NAME"));
            advert.setUrl(resultSet.getString("URL"));
            advert.setLocation(resultSet.getString("LOCATION"));
            advert.setPrice(resultSet.getDouble("PRICE"));
            advert.setPricePerMeter(resultSet.getDouble("PRICE_PER_METER"));
            advert.setContent(resultSet.getString("CONTENT"));
            advert.setUniqueForMany(resultSet.getString("UNIQUE_FOR_MANY"));
            advert.setGotDetails(resultSet.getBoolean("GOT_DETAILS"));
            advert.setTimeStamp(resultSet.getLong("TIMESTAMP"));
            advert.setTimeStampChange(resultSet.getLong("TIMESTAMP_CHANGE"));
            advertList.add(advert);
        }
        return advertList;
    }


    public List<Advert> get(String title) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + ADVERTS_TABLE + " WHERE Title = ? ORDER BY TIMESTAMP DESC");
        pstmt.setString(1, title);
        return resultToAdverts(pstmt.executeQuery());
    }

    public List<Advert> get(int id) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + ADVERTS_TABLE + " WHERE ID = ? ORDER BY TIMESTAMP DESC");
        pstmt.setInt(1, id);
        return resultToAdverts(pstmt.executeQuery());
    }

    public void insert_adverts(List<Advert> advertList) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO " + ADVERTS_TABLE + "(" +
                        "PAGE," +                       //1
                        "NAME," +                       //2
                        "URL," +                        //3
                        "LOCATION," +                   //4
                        "PRICE," +                      //5
                        "AREA," +                       //6
                        "PRICE_PER_METER," +            //7
                        "CONTENT," +                    //8
                        "CONTENT_TO_COMPARE," +         //9
                        "MORE_INFO," +                  //10
                        "Photos," +                     //11
                        "UNIQUE_FOR_MANY," +            //12
                        "GOT_DETAILS," +                //13
                        "TIMESTAMP," +                  //14
                        "TIMESTAMP_CHANGE," +           //15
                        "TIMESTAMP_TEXT," +             //16
                        "TIMESTAMP_CHANGE_TEXT)" +      //17
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); //", ?, ?, ?, ?)");
        //                      1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17      //,18,19,20,21);

        for (Advert advert : advertList) {
            pstmt.setString(1, advert.getPage());
            pstmt.setString(2, advert.getName());
            pstmt.setString(3, advert.getUrl());
            pstmt.setString(4, advert.getLocation());
            pstmt.setString(5, advert.getPrice() == null ? null : advert.getPrice().toString());
            pstmt.setString(6, advert.getArea() == null ? null : advert.getArea().toString());
            pstmt.setString(7, advert.getPricePerMeter() == null ? null : advert.getPricePerMeter().toString());
            pstmt.setString(8, advert.getContent());
            pstmt.setString(9, advert.getContentToCompare());
            pstmt.setString(10, advert.getMoreInfo());
            pstmt.setString(11, advert.getPhotos());
            pstmt.setString(12, advert.getUniqueForMany());
            pstmt.setString(12, advert.getGotDetails().toString());
            pstmt.setString(13, String.valueOf(advert.getTimeStamp()));
            pstmt.setString(14, String.valueOf(advert.getTimeStampChange()));
            pstmt.setString(15, AdvertFinder.dateFormat.format(advert.getTimeStamp()));
            pstmt.setString(16, AdvertFinder.dateFormat.format(advert.getTimeStampChange()));
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.commit();
//        ResultSet resultSet = pstmt.getGeneratedKeys();
//        Integer row = Integer.parseInt(String.valueOf(resultSet.getLong(1)));
//        ListIterator<Advert> it = list_ogloszenia.listIterator(list_ogloszenia.size());
//        while (it.hasPrevious()) {
//            it.previous().setDb_id_ogloszenia(row);
//            row -= 1;
//        }TODO
    }
}
