package ridi.web.util.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ridi.modelos.util.Unico;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity <T extends Unico<?>> {
    String token;
    T entity;
    Date expiration_date;
}