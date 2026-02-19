package com.roima.hrms.controller.travel;

import com.roima.hrms.dtos.req.TravelDocumentReqDto;
import com.roima.hrms.dtos.res.TravelDocumentDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.FileService;
import com.roima.hrms.services.TravelDocumentService;
import com.roima.hrms.services.TravelEmployeeService;
import com.roima.hrms.services.TravelPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-plans/{travelPlanId}/documents")
@CrossOrigin(origins = "*")
@Tag(name = "Travel Plan Documents")
public class TravelDocumentController {

    private final TravelPlanService travelPlanService;
    private final TravelEmployeeService travelEmployeeService;
    private final FileService fileService;
    private final TravelDocumentService travelDocumentService;

    public TravelDocumentController(TravelPlanService travelPlanService, TravelEmployeeService travelEmployeeService,
                                    FileService fileService, TravelDocumentService travelDocumentService) {
        this.travelPlanService = travelPlanService;
        this.travelEmployeeService = travelEmployeeService;
        this.fileService = fileService;
        this.travelDocumentService = travelDocumentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<TravelDocumentDto> createTravelDocument(@ModelAttribute
    // TravelDocumentReqDto dto) {
    public ResponseEntity<ApiResponse> createTravelDocument(@ModelAttribute TravelDocumentReqDto dto) {
        TravelDocumentDto tt = travelDocumentService.createTravelDocument(dto);
        ApiResponse<TravelDocumentDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully created Travel Document.", tt, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getTravelDocuments(@PathVariable Long travelPlanId) {
        List<TravelDocumentDto> tt = travelDocumentService.getTravelDocuments(travelPlanId);
        ApiResponse<List<TravelDocumentDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully created Travel Document.", tt, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{docId}")
    public ResponseEntity<ApiResponse> getTravelDocumentById(@PathVariable Long docId) {
        TravelDocumentDto tt = travelDocumentService.getTravelDocument(docId);
        ApiResponse<TravelDocumentDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully got Travel Document.", tt, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{docId}")
    public ResponseEntity<ApiResponse> deleteTravelDocument(@PathVariable Long docId) {
        travelDocumentService.deleteTravelDocument(docId);
        ApiResponse<List<TravelDocumentDto>> res =
                ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully deleted Travel Document.", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    // public ResponseEntity<Object> getFile() throws IOException {
    // HttpHeaders headers = new HttpHeaders();
    //
    //// headers.add("Content-Disposition", String.format("attachment;
    // filename=\"%s\"", file.getName()));
    // headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    // headers.add("Pragma", "no-cache");
    // headers.add("Expires", "0");
    //
    // Object documentBytes = fileService.getFile("document","");
    //// ApiResponse<byte[]> res =
    // ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Fetched all Travel
    // Plans successfully", documentBytes, null);
    //
    // ResponseEntity<Object>
    // responseEntity = ResponseEntity.ok().headers(headers).contentType(
    // MediaType.parseMediaType("application/pdf")).body(documentBytes);
    // return responseEntity;
    //
    // }
    //
    // @PostMapping(value = "/upload", consumes =
    // MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<ApiResponse> fileUpload(@RequestParam("file")
    // MultipartFile file) throws IOException {
    // String resString = fileService.fileUpload(file);
    // ApiResponse<String> res =
    // ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully created
    // Travel Plan.", resString, null);
    // return ResponseEntity.status(HttpStatus.OK).body(res);
    // }
}
