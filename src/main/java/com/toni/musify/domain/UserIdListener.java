package com.toni.musify.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.toni.musify.domain.exceptions.BusinessValidationException;
import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.user.model.User;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserId;

@Slf4j
public class UserIdListener {

    @PrePersist
    public void prePopulateUserId(Object entity){
        if(entity instanceof BaseEntity){
            log.trace("is in pre populate user id");
            setUserId((BaseEntity) entity,getCurrentUserId());
        }else{
            throw new UnsupportedOperationException("Cannot persist an entity that does not extend BaseEntity!");
        }
    }

    @PreUpdate
    @PreRemove
    public void preventModificationOfForeignEntity(Object entity){

        if(entity instanceof User)
            return;
        if((entity instanceof BaseEntity)){
            if(!Objects.equals(getCurrentUserId(),((BaseEntity) entity).getUserId())){
                throw new BusinessValidationException("Cannot modify " + entity.getClass().getSimpleName() +
                        " because it was not created by you!");
            }
        }else{
            throw new UnsupportedOperationException("Cannot modify/delete an entity that does not extend BaseEntity!");
        }
    }

    @SneakyThrows
    private void setUserId(BaseEntity entity, Integer userId) {
        Field field=BaseEntity.class.getDeclaredField("userId");
        field.setAccessible(true);
        field.set(entity,userId);
        log.trace("am populat entitatea cu user id=ul {}",userId);
    }


}
