package com.xmerge.userService.service.convertor;

import com.xmerge.userService.dao.entity.UserDO;
import com.xmerge.userService.dto.req.UserRegisterReqDTO;
import com.xmerge.userService.dto.resp.UserLoginRespDTO;
import com.xmerge.userService.dto.resp.UserRegisterRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Xmerge
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

//    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "verifyStatus", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deletionTime", ignore = true)
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    UserDO toUserDO(UserRegisterReqDTO userRegisterReqDTO);


    UserRegisterRespDTO toUserRegisterRespDTO(UserRegisterReqDTO userRegisterReqDTO);

    UserRegisterRespDTO toUserRegisterRespDTO(UserDO toUserDO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accessToken", ignore = true)
    UserLoginRespDTO toUserLoginRespDTO(UserDO userDO);

}
