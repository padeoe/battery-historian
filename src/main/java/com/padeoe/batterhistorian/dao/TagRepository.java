package com.padeoe.batterhistorian.dao;

import com.padeoe.batterhistorian.pojo.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface TagRepository extends CrudRepository<Tag,Long> {
}
