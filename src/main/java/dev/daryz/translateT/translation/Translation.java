package dev.daryz.translateT.translation;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Translation {
     private String ipAddress;
     private LocalDateTime localDateTime;
     private String sourceLang;
     private String targetLang;
     private String query;
     private String response;
}
