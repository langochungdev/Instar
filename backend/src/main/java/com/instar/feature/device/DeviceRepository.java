package com.instar.feature.device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Optional<Device> findByUserIdAndFingerprint(Integer userId, String fingerprint);
    List<Device> findAllByUserId(Integer userId);
}
