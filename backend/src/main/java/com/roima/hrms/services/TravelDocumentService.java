package com.roima.hrms.services;


import com.roima.hrms.dtos.req.TravelDocumentReqDto;
import com.roima.hrms.dtos.res.TravelDocumentDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelDocumentTypeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TravelDocumentService {

    private final TravelDocumentRepository travelDocumentRepository;
    private final TravelDocumentTypeRepository travelDocumentTypeRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final ModelMapper modelMapper;
    private final RoleUtil roleUtil;
    private final TravelEmployeeRepository travelEmployeeRepository;
    private final FileService fileService;

    public TravelDocumentService(TravelDocumentRepository travelDocumentRepository, TravelDocumentTypeRepository travelDocumentTypeRepository, TravelPlanRepository travelPlanRepository, EmployeeProfileRepository employeeProfileRepository, ModelMapper modelMapper, RoleUtil roleUtil, TravelEmployeeRepository travelEmployeeRepository, FileService fileService) {
        this.travelDocumentRepository = travelDocumentRepository;
        this.travelDocumentTypeRepository = travelDocumentTypeRepository;
        this.travelPlanRepository = travelPlanRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.modelMapper = modelMapper;
        this.roleUtil = roleUtil;
        this.travelEmployeeRepository = travelEmployeeRepository;
        this.fileService = fileService;
    }

    @Transactional
    public TravelDocumentDto createTravelDocument(TravelDocumentReqDto dto) {
        TravelPlan travelPlan = travelPlanRepository.findById(dto.getTravelPlanId()).orElseThrow(TravelPlanNotFoundException::new);
        EmployeeProfile uploadedBy = employeeProfileRepository.findById(dto.getUploadedByEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
//        EmployeeProfile uploadedFor = employeeProfileRepository.findById(dto.getUploadedForEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        TravelDocumentType travelDocumentType = travelDocumentTypeRepository.findById(dto.getDocumentTypeId()).orElseThrow(TravelDocumentTypeNotFoundException::new);


        String ownerType = null;
        EmployeeProfile uploadedFor = null;
        if (roleUtil.isHr() || roleUtil.isAdmin()) {
            ownerType = "Hr";
            // checking if uploaded for is empty cause it can be in dto
            if (dto.getUploadedForEmployeeId() == null) {
                throw new IllegalArgumentException("HR must provide employee id for whom it is uploaded.");
            }

            uploadedFor = employeeProfileRepository.findById(dto.getUploadedForEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        } else if (roleUtil.isEmployee()) {
            ownerType = "employee";
            uploadedFor = uploadedBy; // currently putting uploaded for same as uploaded by
        }

        // checking if the employee blongs to plan
        boolean exists = travelEmployeeRepository.existsByTravelPlanAndEmployeeProfile(travelPlan, uploadedFor);

        if (!exists) {
            throw new RuntimeException("Employee not assigned to travelplan");
        }


        // checking files
        if (dto.getFile() == null || dto.getFile().isEmpty()) {
            throw new RuntimeException("Please upload at least 1 document");
        }

        fileService.validateFile(dto.getFile());

        String filePath = fileService.store(dto.getFile(), "document");

        TravelDocument travelDocument = TravelDocument.builder()
                .travelPlan(travelPlan)
                .ownerType(ownerType)
                .uploadedByEmployee(uploadedBy)
                .uploadedForEmployee(uploadedFor)
                .uploadDate(LocalDateTime.now())
                .documentType(travelDocumentType)
                .filePath(filePath)
                .build();

        travelDocumentRepository.save(travelDocument);

        return toTravelDocumentDto(travelDocument);
    }

    public List<TravelDocumentDto> getTravelDocuments(Long travelPlanId){

        List<TravelDocument> travelDocuments = null;

        if(roleUtil.isEmployee()){
            travelDocuments =travelDocumentRepository.getTravelDocumentsByUploadedForEmployee_IdAndTravelPlan_Id(roleUtil.getCurrentEmployeeId(), travelPlanId);
        }
        else if (roleUtil.isManager()){
            boolean exists = travelEmployeeRepository.findByTravelPlan_Id(travelPlanId).stream().anyMatch(tp->tp.getEmployeeProfile().getManager().getId()==roleUtil.getCurrentEmployeeId());
            if(exists){
                travelDocuments = travelDocumentRepository.getTravelDocumentsByManagerId(roleUtil.getCurrentEmployeeId());
            }
        }
        else{
            travelDocuments = travelDocumentRepository.getTravelDocumentsByTravelPlan_Id(travelPlanId);
        }
        return travelDocuments.stream().map(this::toTravelDocumentDto).toList();
    }

    @Transactional
    public void deleteTravelDocument(Long travelDocumentId){
        TravelDocument travelDocument = travelDocumentRepository.findById(travelDocumentId).orElseThrow(()->new IllegalArgumentException("Travel Document not found"));

        travelDocument.setDeletedBy(roleUtil.getCurrentEmployeeId());
        travelDocument.setIsDeleted(true);
        // later remove this and do soft delete
        travelDocumentRepository.delete(travelDocument);
    }

    private TravelDocumentDto toTravelDocumentDto(TravelDocument travelDocument) {
        // not created another file for this small service. single mappping only

        // not working currently
//        var a = modelMapper.map(travelDocument, TravelDocumentDto.class);
//        return a;

        TravelDocumentDto dto = TravelDocumentDto.builder()
                .id(travelDocument.getId())
                .travelPlanId(travelDocument.getTravelPlan().getId())
                .travelPlanPurpose(travelDocument.getTravelPlan().getPurpose())
                .ownerType(travelDocument.getOwnerType())
                .uploadedById(travelDocument.getUploadedByEmployee().getId())
                .uploadedByName(travelDocument.getUploadedByEmployee().getFirstName() + " " + travelDocument.getUploadedByEmployee().getLastName())
                .uploadedForId(travelDocument.getUploadedForEmployee().getId())
                .uploadedForName(travelDocument.getUploadedForEmployee().getFirstName() + " " + travelDocument.getUploadedForEmployee().getLastName())
                .uploadDate(travelDocument.getUploadDate())
                .documentTypeId(travelDocument.getDocumentType().getId())
                .documentTypeName(travelDocument.getDocumentType().getName())
                .filePath(travelDocument.getFilePath())
                .build();

        return dto;
    }

}