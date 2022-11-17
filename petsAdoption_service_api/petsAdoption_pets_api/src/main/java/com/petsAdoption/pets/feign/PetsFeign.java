package com.petsAdoption.pets.feign;

import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pets")
public interface PetsFeign {
    @GetMapping("/petDetail/getById")
    public Result<List<PetsDetail>> getById(@RequestParam("id") String id);

    @PostMapping("/petDetail/postPets")
    public Result<Void> postPets(@RequestBody PetsDetail petsDetail);

    @GetMapping("/pets/getPetsById")
    public Result<Pets> getPetsById(@RequestParam("id") String id);

    @PutMapping("/petDetail/updatePets")
    public Result<Void> updatePets(@RequestBody PetsDetail petsDetail);

    @PutMapping("/petDetail/updateCount")
    public Result<Void> updateCount(@RequestBody PetsDetail petsDetail);

    @PutMapping("/petDetail/deduct")
    public Result<Void> deduct(@RequestParam("petId") String petId, @RequestBody Integer count);


}
