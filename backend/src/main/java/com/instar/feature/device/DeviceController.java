package com.instar.feature.device;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @GetMapping("/user/{userId}")
    public List<Device> getDevices(@PathVariable Integer userId) {
        return deviceRepository.findAll()
                .stream()
                .filter(d -> d.getUser().getId().equals(userId))
                .toList();
    }

    @PostMapping("/register")
    public Device register(@RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable Integer id) {
        deviceRepository.deleteById(id);
        return "Đã xóa thiết bị";
    }
}
