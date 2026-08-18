package com.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ecommerce.Model.*;
import com.Ecommerce.dto.UserRequest;

public interface UserRepo extends JpaRepository<User, Long> // first what is the table type, second primary key
                                                            // type
{

}
