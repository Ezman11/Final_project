package com.web.project.entities;

import com.web.project.validators.*;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @UniqueID(message = "รหัสนักศึกษา นี้เคยใช้ลงทะเบียนแล้ว")
    @IdFormat(message = "รูปแบบของ รหัสนักศึกษา ไม่ถูกต้อง")
    @Column(name = "student_id")
    private String student_id;

    @NameFormat(message = "รูปแบบของ ชื่อ ไม่ถูกต้อง")
    @Column(name = "name")
    private String name;

    @NameFormat(message = "รูปแบบของ นามสกุล ไม่ถูกต้อง")
    @Column(name = "surname")
    private String surname;

    @Email(message = "รูปแบบ Email ไม่ถูกต้อง")
    @UniqueEmail(message = "Email นี้เคยใช้ลงทะเบียนแล้ว")
    @SuMailCheck(message = "โปรดใช้ Email ของ \"@silpakorn.edu\"")
    @Column(name = "email")
    private String email;

    //@PhoneNumbersFormat(message = "รูปแบบของ เบอร์โทรศัพท์ ไม่ถูกต้อง")
    //@Column(name = "mobile")
    private String mobile;

    @Column(name = "status")
    private String status;

}

