package com.roima.hrms.controller;

import com.roima.hrms.dtos.req.GameReqDto;
import com.roima.hrms.dtos.req.SlotBookingReqDto;
import com.roima.hrms.dtos.res.AllGameSlotsDto;
import com.roima.hrms.dtos.res.GameDetailsDto;
import com.roima.hrms.dtos.res.SlotBookingDto;
import com.roima.hrms.dtos.res.SlotDetailsDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.GameTypeRepository;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.GameSchedulingService;
import com.roima.hrms.utils.RoleUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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

        @PostMapping("slots/{slotId}")
        public ResponseEntity<ApiResponse> bookSlot(@PathVariable Long slotId,
                        @RequestBody SlotBookingReqDto slotBookingReqDto) {
                gameSchedulingService.bookSLotNew(slotId, slotBookingReqDto);
                ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "Fetched slot details successfully", true, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        @GetMapping("slots/bookings")
        public ResponseEntity<ApiResponse> getBookings() {
                List<List<SlotBookingDto>> slotDetailsDto = gameSchedulingService.getBookings();

                ApiResponse<List<List<SlotBookingDto>>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "Fetched slot details successfully", slotDetailsDto, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        @DeleteMapping("slots/bookings/{slotId}")
        public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long slotId) {

                gameSchedulingService.deleteBookingOrQueue(slotId);

                ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "Fetched slot details successfully", true, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        @PostMapping("add")
        public ResponseEntity<ApiResponse> addGame(@Valid @RequestBody GameReqDto gameReqDto) {
                gameSchedulingService.addGame(gameReqDto);
                ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "created game successfully", true, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        @GetMapping("list")
        public ResponseEntity<ApiResponse> getGameList() {
                List<GameDetailsDto> dtos = gameSchedulingService.getGameList();
                ApiResponse<List<GameDetailsDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                                "created game successfully", dtos, null);
                return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        // @GetMapping("slots/book-dev")
        // public ResponseEntity<ApiResponse> bookSlot() {
        // GameSlot gameSlot = gameSlotRepository
        // .findBySlotStartAndGameType_Name(
        // LocalDateTime.of(
        // 2026,
        // 02,
        // 21,
        // 13,
        // 00,
        // 00),
        // "pool");
        //
        // PlayerGroup playerGroup = gameSchedulingService.createPlayerGroup(List.of(1L,
        // 2L, 3L, 4L));
        // boolean tmp1 = gameSchedulingService.bookSlot(gameSlot, playerGroup);
        //
        // int priority =
        // gameSchedulingService.getPriority(roleUtil.getCurrentEmployee(),
        // gameTypeRepository.findByName("pool").orElseThrow());
        //
        // ApiResponse<AllGameSlotsDto> res =
        // ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
        // "Fetched all Travel Expenses successfully", null, null);
        // return ResponseEntity.status(HttpStatus.OK).body(res);
        // }

}
