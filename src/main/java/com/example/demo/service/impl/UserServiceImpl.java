package com.example.demo.service.impl;

import com.example.demo.common.utils.DistributedLock;
import com.example.demo.common.utils.RedisUtils;
import com.example.demo.dao.UserMapper;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DistributedLock distributedLock;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    private static final String USER_CACHE_PREFIX = "user:";
    private static final long CACHE_EXPIRE_TIME = 1; // 缓存过期时间1小时

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO updateUser(Long id, UserDTO userDTO) {
        // 生成请求ID
        String requestId = UUID.randomUUID().toString();
        // 获取分布式锁
        String lockKey = "user:update:" + id;
        
        try {
            // 尝试获取锁，等待5秒，每100毫秒重试一次
            boolean locked = false;
            for (int i = 0; i < 50; i++) {
                if (distributedLock.tryLock(lockKey, requestId)) {
                    locked = true;
                    break;
                }
                Thread.sleep(100);
            }
            
            if (!locked) {
                throw new RuntimeException("获取锁失败，请稍后重试");
            }

            // 从数据库获取最新用户信息
            User user = userMapper.selectById(id);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 更新用户信息
            BeanUtils.copyProperties(userDTO, user);
            userMapper.updateById(user);

            // 更新缓存
            String cacheKey = USER_CACHE_PREFIX + id;
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            redisUtils.set(cacheKey, userVO, CACHE_EXPIRE_TIME, TimeUnit.HOURS);

            return userVO;
        } catch (Exception e) {
            throw new RuntimeException("更新用户信息失败", e);
        } finally {
            // 释放锁
            distributedLock.releaseLock(lockKey, requestId);
        }
    }

    @Override
    public UserVO getUserById(Long id) {
        // 先从缓存获取
        String cacheKey = USER_CACHE_PREFIX + id;
        Object cacheValue = redisUtils.get(cacheKey);
        if (cacheValue != null) {
            return (UserVO) cacheValue;
        }

        // 缓存未命中，从数据库获取
        User user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }

        // 转换为VO并缓存
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        redisUtils.set(cacheKey, userVO, CACHE_EXPIRE_TIME, TimeUnit.HOURS);

        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO createUser(UserDTO userDTO) {
        // 参数验证
        if (userDTO == null) {
            throw new RuntimeException("用户信息不能为空");
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }

        // 创建用户实体
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setStatus(1); // 设置默认状态

        // 保存到数据库
        userMapper.insert(user);

        // 转换为VO并缓存
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String cacheKey = USER_CACHE_PREFIX + user.getId();
        redisUtils.set(cacheKey, userVO, CACHE_EXPIRE_TIME, TimeUnit.HOURS);

        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 删除数据库记录
        userMapper.deleteById(id);
        
        // 删除缓存
        String cacheKey = USER_CACHE_PREFIX + id;
        redisUtils.delete(cacheKey);
    }
} 