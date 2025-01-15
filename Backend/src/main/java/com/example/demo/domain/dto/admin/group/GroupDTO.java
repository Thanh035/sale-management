package com.example.demo.domain.dto.admin.group;

//import lombok.*;
//
//import java.io.Serializable;
//import java.time.Instant;
//
//@ToString
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class GroupDTO implements Serializable {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//
//    private String groupName;
//    private String groupCode;
//    private Instant createdDate;
//    private String createdBy;
//
//}


import java.time.Instant;

public record GroupDTO(
        String groupName,
        String groupCode,
        Instant createdDate,
        String createdBy
) {

}
