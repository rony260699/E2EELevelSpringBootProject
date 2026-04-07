package com.dalgo.SpringBoot.Repository;

import com.dalgo.SpringBoot.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserServiceRepository extends MongoRepository<Users, ObjectId> {
}
