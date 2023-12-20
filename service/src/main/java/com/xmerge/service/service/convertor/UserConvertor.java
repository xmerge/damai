package com.xmerge.service.service.convertor;

import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dto.req.UserRegisterReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @author Xmerge
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

//    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);


    @Mapping(target = "verifyStatus", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletionTime", ignore = true)
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    UserDO convert(UserRegisterReqDTO userRegisterReqDTO);
}
