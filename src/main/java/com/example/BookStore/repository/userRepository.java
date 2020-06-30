package com.example.BookStore.repository;

import com.example.BookStore.auth.ApplicationUserService;
import com.example.BookStore.mapper.bookMapper;
import com.example.BookStore.mapper.orderMapper;
import com.example.BookStore.mapper.userMapper;
import com.example.BookStore.model.Book;
import com.example.BookStore.model.Order;
import com.example.BookStore.model.User;
import com.example.BookStore.model.UserJoinOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Repository("user")
public class userRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserService applicationUserService;


    public void AddUser(User user){
        try{
            String name = user.getUsername().split(Pattern.quote("."))[0];
            String surname = user.getUsername().split(Pattern.quote("."))[1];
            String BcryptPassword = passwordEncoder.encode(user.getPassword());

            String sql ="INSERT INTO user VALUES(?,?,?,?,?)";
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    name,
                    surname,
                    BcryptPassword,
                    user.getDateOfBirth());
        }catch (ArrayIndexOutOfBoundsException ex){
            log.error("Can not add user. Username is invalid");
        } catch (DataAccessException ex){
            log.error("Can not add user. User is invalid.");
        }
    }

    public UserJoinOrder getUser(String LogInUser){

        HashMap<String,String> params = new HashMap<>();
        params.put("username",LogInUser);

        String user_sql = "SELECT * FROM user WHERE username = :username";
        User user = namedParameterJdbcTemplate.queryForObject(user_sql, params,new userMapper());

        String order_sql = "SELECT id FROM user_order WHERE username = :username";
        List<Order> order = namedParameterJdbcTemplate.query(order_sql,params,new orderMapper());

        List<Integer> allOrder = new ArrayList<>();
        order.forEach(item->allOrder.add(item.getId()));

        UserJoinOrder userJoinOrder = new UserJoinOrder();
        userJoinOrder.setBooks(allOrder);
        userJoinOrder.setName(user.getName());
        userJoinOrder.setSurname(user.getSurname());
        userJoinOrder.setDate_of_birth(user.getDateOfBirth());

        return userJoinOrder;
    }

    public Map<String,Object> addOrder(Order orders,String LogInUser){
        try {

            String sql_addorder = "INSERT INTO user_order VALUES(?,?,ADDTIME(?,?))";
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            orders.getOrderList().forEach(item->jdbcTemplate.update(sql_addorder, LogInUser, item, formattedDate, "0"));

            String sql_getprice = "SELECT * FROM book WHERE id = ?";
            Double price = 0.00;
            for(Integer id : orders.getOrderList()){
                Book b = jdbcTemplate.queryForObject(sql_getprice,new Object[]{id},new bookMapper());
                price+=b.getPrice();
            }

            Map<String,Object> map = new HashMap<>();
            map.put("price",price);

            return  map;

        }catch (DataAccessException ex){
            log.error("No book in database");
            Map<String,Object> error = new HashMap<>();
            error.put("errors","No book in database.");
            return error;
        }
    }

    public void deleteUser(String LogInUser){

        String sql = "DELETE FROM user_order WHERE username = ?";
        jdbcTemplate.update(sql,LogInUser);
    }

    public void login(User user){
        try{
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
        }catch (Exception ex){
            log.error("login error");
        }

    }

    public String getLogInUser(){
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principle).getUsername();
        return username;
    }
}
