package com.roima.hrms.controller;

import com.roima.hrms.dtos.res.AllGameSlotsDto;
import com.roima.hrms.dtos.res.SlotDetailsDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.GameTypeRepository;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.GameSchedulingService;
import com.roima.hrms.utils.RoleUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = "*")
@Tag(name = "Games")
public class GameController {

        private final GameSchedulingService gameSchedulingService;
        private final GameSlotRepository gameSlotRepository;
        private final RoleUtil roleUtil;
        private final GameTypeRepository gameTypeRepository;

        public GameController(GameSchedulingService gameSchedulingService, GameSlotRepository gameSlotRepository,
                        RoleUtil roleUtil, GameTypeRepository gameTypeRepository) {
                this.gameSchedulingService = gameSchedulingService;
                this.gameSlotRepository = gameSlotRepository;
                this.roleUtil = roleUtil;
                this.gameTypeRepository = gameTypeRepository;
        }

        @GetMapping("slots")
        public ResponseEntity<ApiResponse> getAllSlots() {
                List<AllGameSlotsDto> allGamesSlots = gameSchedulingService.getAllSlots();
                ApiResponse<List<AllGameSlotsDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "Fetched all Travel Expenses successfully", allGamesSlots, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }



        @GetMapping("slots/{slotId}")
        public ResponseEntity<ApiResponse> getSlot(@PathVariable Long slotId) {
                SlotDetailsDto slotDetailsDto = gameSchedulingService.getSlotDetails(slotId);

                ApiResponse<SlotDetailsDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "Fetched slot details successfully", slotDetailsDto, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }



//        @GetMapping("slots/book-dev")
//        public ResponseEntity<ApiResponse> bookSlot() {
//                GameSlot gameSlot = gameSlotRepository
//                                .findBySlotStartAndGameType_Name(
//                                                LocalDateTime.of(
//                                                                2026,
//                                                                02,
//                                                                21,
//                                                                13,
//                                                                00,
//                                                                00),
//                                                "pool");
//
//                PlayerGroup playerGroup = gameSchedulingService.createPlayerGroup(List.of(1L, 2L, 3L, 4L));
//                boolean tmp1 = gameSchedulingService.bookSlot(gameSlot, playerGroup);
//
//                int priority = gameSchedulingService.getPriority(roleUtil.getCurrentEmployee(),
//                                gameTypeRepository.findByName("pool").orElseThrow());
//
//                ApiResponse<AllGameSlotsDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
//                                "Fetched all Travel Expenses successfully", null, null);
//                return ResponseEntity.status(HttpStatus.OK).body(res);
//        }

}
