package com.padeoe.batterhistorian.dao;


import com.padeoe.batterhistorian.pojo.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by padeoe on 2017/4/30.
 */
public interface DeviceRepository extends CrudRepository<Device, String> {
}
