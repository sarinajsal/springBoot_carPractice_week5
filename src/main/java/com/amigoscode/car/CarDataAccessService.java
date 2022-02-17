package com.amigoscode.car;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class CarDataAccessService implements CarDAO {

    private JdbcTemplate jdbcTemplate;

    public CarDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car selectCarById(Integer id) {


        String sql = "SELECT id, regnumber, brand, price FROM car WHERE id = ?";

        RowMapper<Car> idRowMapper = (rs, rowNum) -> {
            return new Car(
                    rs.getInt("id"),
                    rs.getString("regnumber"),
                    Brand.valueOf(rs.getString("brand")),
                    rs.getDouble("price")
            );

        };

            List<Car> list = jdbcTemplate.query(sql, idRowMapper, id);
            return list.get(0);


        }

    @Override
    public List<Car> selectAllCars() { //never use star, if you have done joins, selecting from multiple tables, star becomes everything, you want to name columns you get back
        String sql = """
                SELECT id, regnumber, brand, price 
                FROM car
                """;
        RowMapper<Car> carRowMapper =  (rs, rowNum) -> {  //rowmapper to go through each row, gives you result set, which we then turn into ints, strings etc to make a new car object
            Car car = new Car(
                    rs.getInt("id"),
                    rs.getString("regnumber"),
                    Brand.valueOf(rs.getString("brand")),
                    rs.getDouble("price")
            );
            return car; //so its not lost in the heap
        };
        List<Car> cars = jdbcTemplate.query(sql, carRowMapper);
        return cars;
    }



    @Override
    public int insertCar(Car car) { //question marks are placeholders
        String sql = """
INSERT INTO car (regnumber, brand, price) VALUES (?, ?, ?) 
""";
       int rowsAffected = jdbcTemplate.update(sql, car.getRegNumber(), car.getBrand().name(), car.getPrice() ); //update used for insert, update or delete
        return rowsAffected; //first thing to pass is sql query followed by the argumnets for the question marks
    }

    @Override
    public int deleteCar(Integer id) {
        String sql = "DELETE FROM car WHERE id = ?";
        return jdbcTemplate.update(sql, id);

    }

    @Override
    public int updateCar(Integer id, Car update) {

        String sql = """
                UPDATE car SET regnumber = ?, brand = ?, price = ?
                WHERE id = ?
                """;
        System.out.println(update);

        int iDontKnowWhyThisIsAnInt = jdbcTemplate.update(sql, update.getRegNumber(),update.getBrand().name(),update.getPrice(), id);


        return iDontKnowWhyThisIsAnInt;
    }
}
