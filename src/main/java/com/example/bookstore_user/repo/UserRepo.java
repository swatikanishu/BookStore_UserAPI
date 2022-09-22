package com.example.bookstore_user.repo;
import com.example.bookstore_user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {



    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)

    User findByEmail(String email);

}

