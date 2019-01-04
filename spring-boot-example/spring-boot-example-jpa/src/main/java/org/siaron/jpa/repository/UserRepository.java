package org.siaron.jpa.repository;

import org.siaron.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xielongwang
 * @create 2018-11-189:07 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}