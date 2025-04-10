package com.sp.fc.user.repository;

import com.sp.fc.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Modifying(clearAutomatically = true)
  @Query(" UPDATE User SET name = ?2, updated = ?3 WHERE userId = ?1 ")
  void updateUserName(Long userId, String userName, LocalDateTime update);

  Optional<User> findByEmail(String username);

  @Query(" SELECT a FROM User a, Authority b WHERE a.userId = b.userId AND b.authority = ?1 ")
  List<User> findAllByAuthoritiesIn(String authority);

  @Query(" SELECT a FROM User a, Authority b WHERE a.userId = b.userId AND b.authority = ?1 ")
  Page<User> findAllByAuthoritiesIn(String authority, Pageable pageable);

  @Query(" SELECT a FROM User a, Authority b WHERE a.school.schoolId = ?1 AND a.userId = b.userId AND b.authority = ?2 ")
  List<User> findAllBySchool(Long schoolId, String authority);

  @Query(" SELECT a FROM User a, User b where a.teacher.userId = b.userId AND b.userId = ?1 ")
  List<User> findAllByTeacherUserId(Long userId);

  @Query(" SELECT count(a) FROM User a, User b where a.teacher.userId = b.userId AND b.userId = ?1 ")
  Long countByAllTeacherUserId(Long userId);

  @Query(" SELECT count(a) FROM User a, Authority b where a.userId = b.userId AND b.authority = ?1 ")
  long countAllByAuthoritiesIn(String authority);

  @Query(" SELECT count(a) FROM User a, Authority b where a.school.schoolId = ?1 AND a.userId = b.userId AND b.authority= ?2 ")
  long countAllByAuthoritiesIn(long schoolId, String authority);

}
