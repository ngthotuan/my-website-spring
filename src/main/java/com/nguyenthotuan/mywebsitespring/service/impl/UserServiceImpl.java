package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.domain.User;
import com.nguyenthotuan.mywebsitespring.repository.UserRepository;
import com.nguyenthotuan.mywebsitespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User login(String email, String password) {
        Optional<User> opt = findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                user.setPassword("");
                return user;
            }
        }
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public <S extends User> S save(S s) {
        Optional<User> optional = findByEmail(s.getEmail());
        if (optional.isPresent()) {
            // update
            if (StringUtils.hasText(s.getPassword())) {
                s.setPassword(optional.get().getPassword());
            } else {
                s.setPassword(bCryptPasswordEncoder.encode(s.getPassword()));
            }
        } else {
            s.setPassword(bCryptPasswordEncoder.encode(s.getPassword()));
        }
        return userRepository.save(s);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }
}
