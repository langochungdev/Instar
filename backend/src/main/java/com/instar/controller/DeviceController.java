package com.instar.controller;
import com.instar.dto.DeviceDto;
import com.instar.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/user/{userId}")
    public List<DeviceDto> getDevices(@PathVariable Integer userId) {
        return deviceService.findByUserId(userId);
    }

    @PostMapping("/register")
    public DeviceDto register(@RequestBody DeviceDto dto) {
        return deviceService.register(dto);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable Integer id) {
        deviceService.remove(id);
        return "Đã xóa thiết bị";
    }
}
